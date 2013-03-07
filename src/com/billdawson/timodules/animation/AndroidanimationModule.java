package com.billdawson.timodules.animation;

/**
 * TODOs
 * * Handling dps, etc.
 * * Optionally not change a TiView to a View (i.e., really animate the TiView's properties)
 */

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;

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
