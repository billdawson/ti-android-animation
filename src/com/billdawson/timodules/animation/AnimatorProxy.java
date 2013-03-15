package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;

import android.app.Activity;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;

@Kroll.proxy
public abstract class AnimatorProxy extends KrollProxy implements
		AnimatorListener {
	private static final String TAG = "AnimatorProxy";
	private static final long DEFAULT_DURATION = 300;

	protected static final String WARN_ACTIVITY = "The current Activity could not be determined. No animation will be started.";
	protected static final String WARN_ANIMATOR = "An Android Animator object could not be built. No animation will be started.";
	public static final String EVENT_END = "end";
	public static final String EVENT_REPEAT = "repeat";

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

	protected void setCommonAnimatorProperties() {
		if (mAnimator != null) {

			mAnimator.setDuration(mDuration);

			mAnimator.removeAllListeners();
			mAnimator.addListener(this);

			if (mStartDelay != AndroidanimationModule.NO_LONG_VALUE) {
				mAnimator.setStartDelay(mStartDelay);
			}

			if (mInterpolator != AndroidanimationModule.NO_INT_VALUE) {
				mAnimator.setInterpolator(AnimationUtils.buildInterpolator(
						mInterpolator, mInterpolatorValues));
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
			return;
		}

		if (!values.getClass().isArray()) {
			values = new Object[] { values };
		}

		Object[] arrayValues = (Object[]) values;

		mInterpolatorValues = AnimationUtils.unboxFloatValues(arrayValues);
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
