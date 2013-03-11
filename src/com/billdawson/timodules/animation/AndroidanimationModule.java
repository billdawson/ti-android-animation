package com.billdawson.timodules.animation;

/**
 * TODOs
 * * Handling dps, etc.
 * * Optionally not change a TiView to a View (i.e., really animate the TiView's properties)
 */

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;

import com.nineoldandroids.animation.ValueAnimator;

@Kroll.module(name = "Androidanimation", id = "com.billdawson.timodules.animation")
public class AndroidanimationModule extends KrollModule {

	@SuppressWarnings("unused")
	private static final String TAG = "AndroidanimationModule";

	private static final ObjectAnimatorFactoryProxy mObjectAnimatorFactory = new ObjectAnimatorFactoryProxy();

	public static final long NO_LONG_VALUE = Long.MIN_VALUE;
	public static final int NO_INT_VALUE = Integer.MIN_VALUE;

	@Kroll.constant
	public static final int INT_EVALUATOR = 1;
	@Kroll.constant
	public static final int FLOAT_EVALUATOR = 2;
	@Kroll.constant
	public static final int ARGB_EVALUATOR = 3;

	@Kroll.constant
	public static final int INFINITE = ValueAnimator.INFINITE;
	@Kroll.constant
	public static final int RESTART = ValueAnimator.RESTART;
	@Kroll.constant
	public static final int REVERSE = ValueAnimator.REVERSE;

	@Kroll.constant
	public static final int ACCELERATE_INTERPOLATOR = 1;
	@Kroll.constant
	public static final int DECELERATE_INTERPOLATOR = 2;
	@Kroll.constant
	public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 3;
	@Kroll.constant
	public static final int ANTICIPATE_INTERPOLATOR = 4;
	@Kroll.constant
	public static final int ANTICIPATE_OVERSHOOT_INTERPOLATOR = 5;
	@Kroll.constant
	public static final int BOUNCE_INTERPOLATOR = 6;
	@Kroll.constant
	public static final int CYCLE_INTERPOLATOR = 7;
	@Kroll.constant
	public static final int OVERSHOOT_INTERPOLATOR = 8;

	public AndroidanimationModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
	}

	// Methods
	@Kroll.method
	@Kroll.getProperty
	public ObjectAnimatorFactoryProxy getObjectAnimator() {
		return mObjectAnimatorFactory;
	}

}
