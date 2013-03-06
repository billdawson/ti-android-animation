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
import com.nineoldandroids.animation.ObjectAnimator;

enum PropertyType {FLOAT, INT, UNKNOWN}

@Kroll.proxy
public class ObjectAnimatorProxy extends KrollProxy {
	private static final String TAG = "ObjectAnimatorProxy";

	private static final AnimationUtils utils = AnimationUtils.getInstance();
	private static final long NO_LONG_VALUE = Long.MIN_VALUE;
	private ObjectAnimator mObjectAnimator = null;
	private PropertyType mPropertyType;
	private String mTiPropertyName;
	private TiViewProxy mTiView;
	private float[] mFloatValues;
	private long mDuration = NO_LONG_VALUE;
	
	// Static factory methods.
	public static ObjectAnimatorProxy ofFloat(TiViewProxy view, String propertyName, float... values) {
		return new ObjectAnimatorProxy(view, propertyName, values);
	}
	
	// Local contructors
	private ObjectAnimatorProxy() {
		super();
		this.mPropertyType = PropertyType.UNKNOWN;
	}
	
	private ObjectAnimatorProxy(TiViewProxy view, String propertyName, PropertyType propertyType) {
		this();
		this.mTiView = view;
		this.mTiPropertyName = propertyName;
		this.mPropertyType = propertyType;
	}
	
	private ObjectAnimatorProxy(TiViewProxy view, String propertyName, float... values) {
		this(view, propertyName, PropertyType.FLOAT);
		this.mFloatValues = values;
	}
	
	private ObjectAnimator getObjectAnimator() {
		if (mObjectAnimator == null) {
			View view = null;
			if (mTiView != null) {
				TiUIView intermediateView = mTiView.peekView();
				if (intermediateView != null) {
					view = intermediateView.getNativeView();
				} else {
					Log.d(TAG, "peekView returned null");
				}
			}
			if (view == null) {
				return null; // TODO warn
			}
			if (mPropertyType == PropertyType.UNKNOWN) {
				return null; // TODO warn
			}
			String propertyName = utils.translatePropertyName(mTiPropertyName);
			if (mPropertyType == PropertyType.FLOAT) {
				mObjectAnimator = ObjectAnimator.ofFloat(view, propertyName, mFloatValues);
			}
		}
		return mObjectAnimator;
	}
	
	// Kroll methods
	@Kroll.method @Kroll.setProperty
	public void setDuration(long milliseconds) {
		mDuration = milliseconds;
	}
	
	
	@Kroll.method
	public void start()  {
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
