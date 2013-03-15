package com.billdawson.timodules.animation;

/*
 * Copyright (C) 2013 William Dawson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import com.billdawson.timodules.animation.views.ViewPropertyAnimatorFactoryProxy;
import com.nineoldandroids.animation.ValueAnimator;

@Kroll.module(name = "Androidanimation", id = "com.billdawson.timodules.animation")
public class AndroidanimationModule extends KrollModule {

	@SuppressWarnings("unused")
	private static final String TAG = "AndroidanimationModule";

	private static final ObjectAnimatorFactoryProxy mObjectAnimatorFactory = new ObjectAnimatorFactoryProxy();
	private static final ViewPropertyAnimatorFactoryProxy mViewPropertyAnimatorFactory = new ViewPropertyAnimatorFactoryProxy();

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
	@Kroll.constant
	public static final int LINEAR_INTERPOLATOR = 9;

	public AndroidanimationModule() {
		super();
	}

	@Kroll.method
	@Kroll.getProperty
	public ObjectAnimatorFactoryProxy getObjectAnimator() {
		return mObjectAnimatorFactory;
	}

	@Kroll.method
	@Kroll.getProperty
	public ViewPropertyAnimatorFactoryProxy getViewPropertyAnimator() {
		return mViewPropertyAnimatorFactory;
	}

}
