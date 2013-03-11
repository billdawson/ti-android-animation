package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.billdawson.timodules.animation.views.ViewWrapper;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;

enum PropertyDataType {
	FLOAT, INT, UNKNOWN
}

@Kroll.proxy(creatableInModule = AndroidanimationModule.class)
public class ObjectAnimatorProxy extends KrollProxy {
	private static final String TAG = "ObjectAnimatorProxy";
	private static final AnimationUtils utils = AnimationUtils.getInstance();
	private static final String PROPERTY_BACKGROUND_COLOR = "backgroundColor";
	private static final String ERR_FLOAT_VALUE = "Values must be set to numeric array";
	private static final String ERR_INT_VALUE = "Values must be set to numeric array or array of strings containing color codes.";

	private ObjectAnimator mObjectAnimator = null;
	private PropertyDataType mPropertyType;
	private String mPropertyName;
	private Object mTarget;
	private float[] mFloatValues;
	private int[] mIntValues;
	private long mDuration = AndroidanimationModule.NO_LONG_VALUE;
	private int mEvaluator = AndroidanimationModule.NO_INT_VALUE;
	private long mStartDelay = AndroidanimationModule.NO_LONG_VALUE;
	private int mRepeatCount = AndroidanimationModule.NO_INT_VALUE;
	private int mRepeatMode = AndroidanimationModule.NO_INT_VALUE;
	private int mInterpolator = AndroidanimationModule.NO_INT_VALUE;
	private float[] mInterpolatorValues = null;

	public ObjectAnimatorProxy() {
		super();
		this.mPropertyType = PropertyDataType.UNKNOWN;
	}

	protected ObjectAnimatorProxy(Object object, String propertyName,
			PropertyDataType propertyType, Object[] values) {
		this();
		mTarget = object;
		mPropertyName = propertyName;
		mPropertyType = propertyType;
		if (mPropertyType == PropertyDataType.FLOAT) {
			setFloatValues(values);
		} else if (mPropertyType == PropertyDataType.INT) {
			setIntValues(values);
		}
	}

	private ObjectAnimator getObjectAnimator() {
		if (mObjectAnimator == null) {
			if (mPropertyType == PropertyDataType.UNKNOWN) {
				Log.w(TAG, "Property data type unknown, cannot animate.");
				return null;
			}

			String propertyName = utils.translatePropertyName(mTarget,
					mPropertyName);

			Object actualObject = mTarget;
			if (mTarget instanceof TiViewProxy) {
				TiUIView intermediateObject = ((TiViewProxy) mTarget)
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

			if (mStartDelay != AndroidanimationModule.NO_LONG_VALUE) {
				mObjectAnimator.setStartDelay(mStartDelay);
			}

			if (mRepeatCount != AndroidanimationModule.NO_INT_VALUE) {
				mObjectAnimator.setRepeatCount(mRepeatCount);
			}

			if (mRepeatMode != AndroidanimationModule.NO_INT_VALUE) {
				mObjectAnimator.setRepeatMode(mRepeatMode);
			}

			if (mInterpolator != AndroidanimationModule.NO_INT_VALUE) {
				mObjectAnimator.setInterpolator(buildInterpolator());
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

	private Interpolator buildInterpolator() {
		int valueCount = mInterpolatorValues == null ? 0
				: mInterpolatorValues.length;
		switch (mInterpolator) {
		case AndroidanimationModule.ACCELERATE_DECELERATE_INTERPOLATOR:
			return new AccelerateDecelerateInterpolator();
		case AndroidanimationModule.ACCELERATE_INTERPOLATOR:
			if (valueCount > 0) {
				return new AccelerateInterpolator(mInterpolatorValues[0]);
			} else {
				return new AccelerateInterpolator();
			}
		case AndroidanimationModule.ANTICIPATE_INTERPOLATOR:
			if (valueCount > 0) {
				return new AnticipateInterpolator(mInterpolatorValues[0]);
			} else {
				return new AnticipateInterpolator();
			}
		case AndroidanimationModule.ANTICIPATE_OVERSHOOT_INTERPOLATOR:
			if (valueCount == 0) {
				return new AnticipateOvershootInterpolator();
			} else if (valueCount == 1) {
				return new AnticipateOvershootInterpolator(
						mInterpolatorValues[0]);
			} else {
				return new AnticipateOvershootInterpolator(
						mInterpolatorValues[0], mInterpolatorValues[1]);
			}
		case AndroidanimationModule.BOUNCE_INTERPOLATOR:
			return new BounceInterpolator();
		case AndroidanimationModule.CYCLE_INTERPOLATOR:
			if (valueCount > 0) {
				return new CycleInterpolator(mInterpolatorValues[0]);
			} else {
				Log.w(TAG,
						"No values provided for Cycle Interpolator. Defaulting to 0.");
				return new CycleInterpolator(0f);
			}
		case AndroidanimationModule.DECELERATE_INTERPOLATOR:
			if (valueCount > 0) {
				return new DecelerateInterpolator(mInterpolatorValues[0]);
			} else {
				return new DecelerateInterpolator();
			}
		case AndroidanimationModule.OVERSHOOT_INTERPOLATOR:
			if (valueCount > 0) {
				return new OvershootInterpolator(mInterpolatorValues[0]);
			} else {
				return new OvershootInterpolator();
			}
		default:
			Log.w(TAG, "Unknown interpolator: " + mInterpolator);
			return null;
		}
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
						animator.start();
					}
				});
			}

		} else {
			// TODO warn
		}
	}

	@Kroll.method
	public void stop() {
		// TODO

	}

	@Kroll.method
	public void cancel() {
		if (mObjectAnimator != null) {
			Activity activity = TiApplication.getAppCurrentActivity();
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mObjectAnimator.cancel();
					}
				});
			}
		}
	}

	@Kroll.method
	public void end() {
		if (mObjectAnimator != null) {
			Activity activity = TiApplication.getAppCurrentActivity();
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mObjectAnimator.end();
					}
				});
			}
		}
	}

	@Kroll.method
	@Kroll.getProperty
	public String getPropertyName() {
		return mPropertyName;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setPropertyName(String propertyName) {
		this.mPropertyName = propertyName;
	}

	@Kroll.method
	@Kroll.getProperty
	public Object getTarget() {
		return mTarget;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setTarget(Object target) {
		this.mTarget = target;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setIntValues(Object[] values) {
		if (mPropertyType == PropertyDataType.UNKNOWN) {
			mPropertyType = PropertyDataType.INT;
		}

		if (values == null || values.length == 0) {
			mIntValues = null;
			return;
		}

		if (values[0].getClass().isArray()) {
			values = (Object[]) values[0];
		}

		if (values == null || values.length == 0) {
			mIntValues = null;
			return;
		}

		mIntValues = new int[values.length];

		for (int i = 0; i < values.length; i++) {
			Object member = values[i];
			if (member instanceof Number) {
				mIntValues[i] = ((Number) member).intValue();
			} else if (member instanceof String) {
				try {
					int colorVal = TiConvert.toColor((String) member);
					mIntValues[i] = colorVal;
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(ERR_INT_VALUE);
				}
			} else {
				throw new IllegalArgumentException(ERR_INT_VALUE);
			}
		}
	}

	@Kroll.method
	@Kroll.setProperty
	public void setFloatValues(Object[] values) {
		if (mPropertyType == PropertyDataType.UNKNOWN) {
			mPropertyType = PropertyDataType.FLOAT;
		}

		if (values == null || values.length == 0 || values[0] == null) {
			mFloatValues = null;
			return;
		}

		mFloatValues = new float[values.length];

		for (int i = 0; i < values.length; i++) {
			Object member = values[i];
			if (!(member instanceof Number)) {
				throw new IllegalArgumentException(ERR_FLOAT_VALUE);
			}
			mFloatValues[i] = ((Number) values[i]).floatValue();
		}
	}

	@Kroll.method
	@Kroll.getProperty
	public long getStartDelay() {
		return mStartDelay;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setStartDelay(long startDelay) {
		this.mStartDelay = startDelay;
	}

	@Kroll.method
	@Kroll.getProperty
	public int getRepeatCount() {
		return mRepeatCount;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setRepeatCount(int repeatCount) {
		this.mRepeatCount = repeatCount;
	}

	@Kroll.method
	@Kroll.getProperty
	public int getRepeatMode() {
		return mRepeatMode;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setRepeatMode(int repeatMode) {
		this.mRepeatMode = repeatMode;
	}

	@Kroll.method
	@Kroll.getProperty
	public int getInterpolator() {
		return mInterpolator;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setInterpolator(int interpolator) {
		this.mInterpolator = interpolator;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setInterpolatorValues(Object values) {
		if (values == null) {
			mInterpolatorValues = null;
		}

		if (!values.getClass().isArray()) {
			values = new Object[] { values };
		}

		Object[] arrayValues = (Object[]) values;

		if (arrayValues.length == 0) {
			mInterpolatorValues = null;
		}

		mInterpolatorValues = new float[arrayValues.length];

		for (int i = 0; i < arrayValues.length; i++) {
			Object member = arrayValues[i];
			if (!(member instanceof Number)) {
				throw new IllegalArgumentException(
						"Interpolator values must be numeric.");
			}
			mInterpolatorValues[i] = ((Number) member).floatValue();
		}

	}

}
