package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;

import android.app.Activity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;

@Kroll.proxy
public abstract class AnimatorProxy extends KrollProxy implements
		AnimatorListener {
	private static final String TAG = "AnimatorProxy";
	private static final long DEFAULT_DURATION = 300;

	protected static final String WARN_ACTIVITY = "The current Activity could not be determined. No animation will be started.";
	protected static final String WARN_ANIMATOR = "An Android Animator object could not be built. No animation will be started.";
	private static final String EVENT_END = "end";
	private static final String EVENT_REPEAT = "repeat";

	private Object mTarget = null;
	private long mStartDelay = AndroidanimationModule.NO_LONG_VALUE;
	private long mDuration = DEFAULT_DURATION;
	private Animator mAnimator = null;
	private int mInterpolator = AndroidanimationModule.NO_INT_VALUE;
	private float[] mInterpolatorValues = null;

	protected void setAnimator(Animator animator) {
		mAnimator = animator;
	}

	protected Animator getAnimator() {
		return mAnimator;
	}

	protected abstract void buildAnimator();

	protected Interpolator buildInterpolator() {
		final int valueCount = mInterpolatorValues == null ? 0
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

	protected void setCommonAnimatorProperties() {
		if (mAnimator != null) {

			mAnimator.setDuration(mDuration);

			mAnimator.removeAllListeners();
			mAnimator.addListener(this);

			if (mStartDelay != AndroidanimationModule.NO_LONG_VALUE) {
				mAnimator.setStartDelay(mStartDelay);
			}

			if (mInterpolator != AndroidanimationModule.NO_INT_VALUE) {
				mAnimator.setInterpolator(buildInterpolator());
			}

		}
	}

	// Public facing Kroll methods/properties.

	@Kroll.method
	public long getDuration() {
		return mDuration;
	}

	@Kroll.method
	public AnimatorProxy setDuration(long milliseconds) {
		mDuration = milliseconds;
		return this;
	}

	@Kroll.method
	public void cancel() {
		if (mAnimator != null) {
			Activity activity = TiApplication.getAppCurrentActivity();
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mAnimator.cancel();
					}
				});
			} else {
				Log.w(TAG, WARN_ACTIVITY.replace("started", "canceled"));
			}
		}
	}

	@Kroll.method
	public void end() {
		if (mAnimator != null) {
			Activity activity = TiApplication.getAppCurrentActivity();
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mAnimator.end();
					}
				});
			} else {
				Log.w(TAG, WARN_ACTIVITY.replace("started", "ended"));
			}
		}
	}

	@Kroll.method
	public void start() {
		buildAnimator();
		if (mAnimator != null) {
			Activity activity = TiApplication.getAppCurrentActivity();
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mAnimator.start();
					}
				});
			} else {
				Log.w(TAG, WARN_ACTIVITY);
			}

		} else {
			Log.w(TAG, WARN_ANIMATOR);
		}
	}

	@Kroll.method
	public boolean isRunning() {
		return mAnimator == null ? false : mAnimator.isRunning();
	}

	@Kroll.method
	public boolean isStarted() {
		return mAnimator == null ? false : mAnimator.isStarted();
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
	@Kroll.getProperty
	public long getStartDelay() {
		return mStartDelay;
	}

	@Kroll.method
	@Kroll.setProperty
	public void setStartDelay(long startDelay) {
		this.mStartDelay = startDelay;
	}

	// AnimatorListener implementation.

	@Override
	public void onAnimationCancel(Animator animation) {
		fireEvent(TiC.EVENT_CANCEL, null);
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		fireEvent(EVENT_END, null);

	}

	@Override
	public void onAnimationRepeat(Animator animation) {
		fireEvent(EVENT_REPEAT, null);
	}

	@Override
	public void onAnimationStart(Animator animation) {
		fireEvent(TiC.EVENT_START, null);
	}

}
