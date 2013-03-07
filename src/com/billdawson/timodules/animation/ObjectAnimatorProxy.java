package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;
import android.view.View;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.billdawson.timodules.animation.views.ViewWrapper;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;

enum PropertyDataType {
	FLOAT, INT, UNKNOWN
}

@Kroll.proxy
public class ObjectAnimatorProxy extends KrollProxy {
	private static final String TAG = "ObjectAnimatorProxy";
	private static final AnimationUtils utils = AnimationUtils.getInstance();
	private static final String PROPERTY_BACKGROUND_COLOR = "backgroundColor";

	private ObjectAnimator mObjectAnimator = null;
	private PropertyDataType mPropertyType;
	private String mTiPropertyName;
	private Object mObject;
	private float[] mFloatValues;
	private int[] mIntValues;
	private long mDuration = AndroidanimationModule.NO_LONG_VALUE;
	private int mEvaluator = AndroidanimationModule.NO_INT_VALUE;

	// Static factory methods.
	public static ObjectAnimatorProxy ofFloat(Object object,
			String propertyName, float[] values) {
		return new ObjectAnimatorProxy(object, propertyName, values);
	}

	public static ObjectAnimatorProxy ofInt(Object object, String propertyName,
			int... values) {
		return new ObjectAnimatorProxy(object, propertyName, values);
	}

	// Local contructors
	private ObjectAnimatorProxy() {
		super();
		this.mPropertyType = PropertyDataType.UNKNOWN;
	}

	private ObjectAnimatorProxy(Object object, String propertyName,
			PropertyDataType propertyType) {
		this();
		this.mObject = object;
		this.mTiPropertyName = propertyName;
		this.mPropertyType = propertyType;
	}

	private ObjectAnimatorProxy(Object object, String propertyName,
			float[] values) {
		this(object, propertyName, PropertyDataType.FLOAT);
		this.mFloatValues = values;
	}

	private ObjectAnimatorProxy(Object object, String propertyName,
			int... values) {
		this(object, propertyName, PropertyDataType.INT);
		this.mIntValues = values;
	}

	private ObjectAnimator getObjectAnimator() {
		if (mObjectAnimator == null) {
			if (mPropertyType == PropertyDataType.UNKNOWN) {
				Log.w(TAG, "Property data type unknown, cannot animate.");
				return null;
			}

			String propertyName = utils.translatePropertyName(mObject,
					mTiPropertyName);

			Object actualObject = mObject;
			if (mObject instanceof TiViewProxy) {
				TiUIView intermediateObject = ((TiViewProxy) mObject)
						.peekView();
				if (intermediateObject != null) {
					actualObject = intermediateObject.getNativeView();
					if (actualObject != null
							&& propertyName.equals(PROPERTY_BACKGROUND_COLOR)
							&& mIntValues.length == 1) {
						actualObject = new ViewWrapper((View) actualObject);
					}
				} else {
					Log.w(TAG, "View not available for animation.");
				}
			}

			if (actualObject == null) {
				Log.w(TAG, "Object not available for animation (null).");
				return null;
			}

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

		if (mObjectAnimator != null) {
			if (mDuration != AndroidanimationModule.NO_LONG_VALUE) {
				mObjectAnimator.setDuration(mDuration);
			}

			if (mEvaluator != AndroidanimationModule.NO_INT_VALUE) {
				switch (mEvaluator) {
				case AndroidanimationModule.INT_EVALUATOR:
					mObjectAnimator.setEvaluator(new IntEvaluator());
					break;
				case AndroidanimationModule.FLOAT_EVALUATOR:
					mObjectAnimator.setEvaluator(new FloatEvaluator());
					break;
				case AndroidanimationModule.ARGB_EVALUATOR:
					mObjectAnimator.setEvaluator(new ArgbEvaluator());
					break;
				default:
					Log.w(TAG, "Evaluator set to unknown value: " + mEvaluator);
				}
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
	@Kroll.getProperty
	public int getEvaluator() {
		return mEvaluator;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setEvaluator(int evaluator) {
		mEvaluator = evaluator;
	}

	@Kroll.method
	public void start() {
		final ObjectAnimator animator = getObjectAnimator();
		if (animator != null) {
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
