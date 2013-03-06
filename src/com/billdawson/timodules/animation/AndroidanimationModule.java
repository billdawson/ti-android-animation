package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;

@Kroll.module(name = "Androidanimation", id = "com.billdawson.timodules.animation")
public class AndroidanimationModule extends KrollModule {

	@SuppressWarnings("unused")
	private static final String TAG = "AndroidanimationModule";
	
	private static final ObjectAnimatorFactoryProxy mObjectAnimatorFactory = new ObjectAnimatorFactoryProxy();

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	public AndroidanimationModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
	}

	// Methods
	@Kroll.method @Kroll.getProperty
	public ObjectAnimatorFactoryProxy getObjectAnimator() {
		return mObjectAnimatorFactory;
	}

}
