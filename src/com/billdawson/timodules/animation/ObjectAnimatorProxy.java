package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.nineoldandroids.animation.ObjectAnimator;

enum PropertyType {
	FLOAT, INT, UNKNOWN
}

@Kroll.proxy
public class ObjectAnimatorProxy extends KrollProxy {
	private static final String TAG = "ObjectAnimatorProxy";
	private static final AnimationUtils utils = AnimationUtils.getInstance();
	private static final long NO_LONG_VALUE = Long.MIN_VALUE;

	private ObjectAnimator mObjectAnimator = null;
	private PropertyType mPropertyType;
	private String mTiPropertyName;
	private Object mObject;
	private float[] mFloatValues;
	private int[] mIntValues;
	private long mDuration = NO_LONG_VALUE;

	// Static factory methods.
	public static ObjectAnimatorProxy ofFloat(Object object,
			String propertyName, float... values) {
		return new ObjectAnimatorProxy(object, propertyName, values);
	}

	public static ObjectAnimatorProxy ofInt(Object object, String propertyName,
			int... values) {
		return new ObjectAnimatorProxy(object, propertyName, values);
	}

	// Local contructors
	private ObjectAnimatorProxy() {
		super();
		this.mPropertyType = PropertyType.UNKNOWN;
	}

	private ObjectAnimatorProxy(Object object, String propertyName,
			PropertyType propertyType) {
		this();
		this.mObject = object;
		this.mTiPropertyName = propertyName;
		this.mPropertyType = propertyType;
	}

	private ObjectAnimatorProxy(Object object, String propertyName,
			float... values) {
		this(object, propertyName, PropertyType.FLOAT);
		this.mFloatValues = values;
	}

	private ObjectAnimatorProxy(Object object, String propertyName,
			int... values) {
		this(object, propertyName, PropertyType.INT);
		this.mIntValues = values;
	}

	private ObjectAnimator getObjectAnimator() {
		if (mObjectAnimator == null) {
			if (mPropertyType == PropertyType.UNKNOWN) {
				Log.w(TAG, "Property data type unknown, cannot animate.");
				return null;
			}

			Object actualObject = mObject;
			if (mObject instanceof TiViewProxy) {
				TiUIView intermediateObject = ((TiViewProxy) mObject)
						.peekView();
				if (intermediateObject != null) {
					actualObject = intermediateObject.getNativeView();
				} else {
					Log.w(TAG, "View not available for animation.");
				}
			}

			if (actualObject == null) {
				Log.w(TAG, "Object not available for animation (null).");
				return null;
			}

			String propertyName = utils.translatePropertyName(mObject,
					mTiPropertyName);

			switch (mPropertyType) {
			case FLOAT:
				mObjectAnimator = ObjectAnimator.ofFloat(actualObject,
						propertyName, mFloatValues);
				break;
			case INT:
				mObjectAnimator = ObjectAnimator.ofInt(actualObject,
						propertyName, mIntValues);
				break;
			case UNKNOWN:
				break;
			}

		}
		return mObjectAnimator;
	}

	// Kroll methods/properties
	@Kroll.method
	@Kroll.setProperty
	public void setDuration(long milliseconds) {
		mDuration = milliseconds;
	}

	@Kroll.method
	public void start() {
		final ObjectAnimator animator = getObjectAnimator();
		if (animator != null) {
			if (mDuration != NO_LONG_VALUE) {
				animator.setDuration(mDuration);
			}
			Activity activity = TiApplication.getAppCurrentActivity();
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Log.d(TAG, "Calling start");
						animator.start();
					}
				});
			}

		} else {
			// TODO warn
		}
	}

}
