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

/**
 * \mainpage Android Animation for Titanium
 *
 * Description
 * ------------
 *
 * This Titanium Android module makes the [Honeycomb (Android 3.0) animation API][1]
 * available to Titanium Android apps. It uses Jake Wharton's excellent
 * [NineOldAndroids][2] library to make the animations available on pre-Honeycomb
 * devices as well.
 *
 * The module doesn't follow the Android API _exactly_, but fairly closely.
 * 
 * It does this by wrapping and exposing two types of animators that are native to the Honeycomb Animation
 * API: [ObjectAnimator](@ref ObjectAnimator_) ([native][7]) and [ViewPropertyAnimator](@ref ViewPropertyAnimator_)
 * ([native][8]).
 * 
 * Additionally, it provides access to the [AnimatorSet](@ref AnimatorSet_) ([native][9]), which can run multiple
 * `ObjectAnimator` instances either simultaneously or back-to-back.
 * 
 * The real star of the bunch is the [ViewPropertyAnimator](@ref ViewPropertyAnimator_), which is the one
 * you should be trying to use first.
 *
 * Accessing the Module in Your App Code
 * -------------------------------------
 *
 * ### Get the module's ZIP file
 *
 * You can either build the module from [source][3] (this assumes you know how to build Titanium
 * modules; no instructions are provided here for that) or get a recent [packaged ZIP from Github][10].
 *
 * ### Place the module ZIP where it's accessible
 *
 * For a module to be accessible to the Titanium project build scripts (which
 * will package the module in to your application when it is built), the module's
 * ZIP file must either be placed in the project's root folder or in your root Titanium
 * installation folder (e.g. `Library/Application Support/Titanium` on OS X, if that's
 * indeed where your Titanium installation folder is.)
 *
 * Where your Titanium application project is built, the build scripts will take
 * care of unzipping the module in either of those places.
 *
 * ### Prepare your tiapp.xml
 *
 * Here's an example of the `<modules>` section of your project's tiapp.xml file
 * modified so as to register this module:
 *
 *     <modules>
 *         <module platform="android" version="1.0">com.billdawson.timodules.animation</module>
 *     </modules>
 *
 * ### Accessing the module in code
 *
 * Here's an example of getting a reference to an instance of the module in your
 * application's Javascript code:
 *
 *     var animMod = require("com.billdawson.timodules.animation");
 * 
 * Usage
 * ------
 * 
 * Please see the [example project][5] and the main documentation for several usage
 * examples. Meanwhile, find a few short samples below.
 * 
 * ### ViewPropertyAnimator
 * 
 * You'll see in the example below that the 
 * [ViewPropertyAnimator](@ref views.ViewPropertyAnimator_) has a fluent
 * interface (e.g. method chaining), just like the native Android 
 * [ViewPropertyAnimator][6].
 * 
 *     var mod = require("com.billdawson.timodules.animation"),
 * 	       view = Ti.UI.createView();
 * 
 * 	   // setup the window and view, etc....
 * 	   // ... then later ...
 *     
 *     mod.viewPropertyAnimator.animate(view)
 *        .setDuration(1000)
 *        .withEndAction(function() {
 *            Ti.API.info("Animation done");
 *         })
 *        .scaleXBy(0.5)
 *        .scaleXBy(0.5)
 *        .alpha(0.75);
 *
 * Note from the example above how you get an instance of the `ViewPropertyAnimator`:
 * *not* through a `createViewPropertyAnimator` method, but rather through a special
 * factory method:
 * 
 *     mod.viewPropertyAnimator.animate(view);
 * 
 * Note also that you likely don't even need to hold a reference to the instance in
 * a variable since the interface is fluent.
 * 
 * ### ObjectAnimator
 * 
 * In this example of using an [ObjectAnimator](@ref ObjectAnimator_),
 * we animate the `backgroundColor` property of a View. The
 * `backgroundProperty` is a good example here because
 * the `ViewPropertyAnimator` (above) *cannot* be
 * used to animate the `backgroundColor` (the same
 * is true for native Android), so you must use
 * `ObjectAnimator` to animate it.
 * 
 *     var mod = require("com.billdawson.timodules.animation"),
 *         view = Ti.UI.createView({
 *             backgroundColor: "#FF00FF00"
 *         }),
 *         animator = mod.objectAnimator.ofInt(
 *             view, "backgroundColor", "#AAFF0000");
 *     
 *     // setup the window and view, etc....
 *     // ... then later ...
 *     
 *     // Use the Android evaluator that is
 *     // specifically for ARGB (alpha/red/green/blue)
 *     // values.
 *     animator.setEvaluator(mod.ARGB_EVALUATOR);
 *     animator.setDuration(2000); // 2 secs.
 *     animator.addEventListener("start", function() {
 *         Ti.API.info("Animation started!");
 *     });
 *     
 *     // Animate backgroundColor from
 *     // #FF00FF00 to #AAFF0000 over
 *     // 2 seconds.
 *     animator.start();
 *
 * ### AnimatorSet
 * 
 * The [AnimatorSet](@ref AnimatorSet_) can be used to run several
 * [ObjectAnimator](@ref ObjectAnimator_) animations together
 * simultaneously or sequentially.
 * 
 *     var mod = require("com.billdawson.timodules.animation"),
 *         view = Ti.UI.createView({
 *             backgroundColor: "#FF00FF00"
 *         }),
 *         animateColor = mod.objectAnimator.ofInt(
 *             view, "backgroundColor", "#AAFF0000"),
 *         animateScaleX = mod.objectAnimator.ofFloat(
 *             view, "scaleX", 2),
 *         animateScaleY = mod.objectAnimator.ofFloat(
 *             view, "scaleY", 2),
 *         animatorSet = mod.createAnimatorSet();
 *         
 *     // setup the window and view, etc....
 *     // ... then later ...
 *     
 *     animatorSet.playTogether([animateColor, animateScaleX, animateScaleY]);
 *     animatorSet.start();
 * 
 * Author
 * ------
 * 
 * [Bill Dawson][4]
 * 
 * Source Code
 * -----------
 * 
 * The source is open and can be found on [Github][3].
 * 
 * License
 * -------
 * 
 *     The original code herein is...
 *     
 *     Copyright 2013 William Dawson.
 *     
 *     The NineOldAndroids library, upon which this module is built, is...
 *     
 *     Copyright 2012 Jake Wharton.
 *     
 *     Both projects:
 *     
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *     
 *         http://www.apache.org/licenses/LICENSE-2.0
 *     
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 * 
 *
 * [1]: http://android-developers.blogspot.com/2011/02/animation-in-honeycomb.html
 * [2]: http://nineoldandroids.com
 * [3]: https://github.com/billdawson/ti-android-animation
 * [4]: http://github.com/billdawson
 * [5]: https://github.com/billdawson/ti-android-animation/tree/master/example
 * [6]: http://developer.android.com/reference/android/view/ViewPropertyAnimator.html
 * [7]: http://developer.android.com/reference/android/animation/ObjectAnimator.html
 * [8]: http://developer.android.com/reference/android/view/ViewPropertyAnimator.html
 * [9]: http://developer.android.com/reference/android/animation/AnimatorSet.html
 * [10]: http://github.com/billdawson/ti-android-animation/tree/gh-pages/packaged
 */

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.view.View;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.billdawson.timodules.animation.utils.AnimationUtils.Axis;
import com.billdawson.timodules.animation.views.ViewPropertyAnimatorFactory;
import com.billdawson.timodules.animation.views.ViewPropertyAnimator_;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * This is the module itself. In your Javascript code you won't use
 * the name `AndroidAnimation` itself, but instead set a variable to
 * the result of `require`ing the module using its full id. For example:
 * 
 *      var animationModule = require("com.billdawson.timodules.animation);
 *      
 * @since 1.0
 */
@Kroll.module(name = "AndroidAnimation", id = "com.billdawson.timodules.animation")
public class AndroidAnimation extends KrollModule {

	@SuppressWarnings("unused")
	private static final String TAG = "AndroidAnimation";

	private static final ObjectAnimatorFactory mObjectAnimatorFactory = new ObjectAnimatorFactory();
	private static final ViewPropertyAnimatorFactory mViewPropertyAnimatorFactory = new ViewPropertyAnimatorFactory();

	public static final long NO_LONG_VALUE = Long.MIN_VALUE;
	public static final int NO_INT_VALUE = Integer.MIN_VALUE;

	/**
	 * Constant for use when specifying that animation should use the Android
	 * `IntEvaluator`.
	 * 
	 * You generally won't need to use this directly, because if you create an
	 * `ObjectAnimator` to animate integer values, it will automatically use
	 * this type of evaluator.
	 * 
	 * For more information about evaluators, see
	 * [the Android documenation](http://developer.android.com/reference/android/animation/TypeEvaluator.html).
	 * 
	 * @see ObjectAnimator_#setEvaluator(int)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int INT_EVALUATOR = 1;

	/**
	 * Constant for use when specifying that animation should use the Android
	 * `FloatEvaluator`.
	 * 
	 * You generally won't need to use this directly, because if you create an
	 * `ObjectAnimator` to animate float values, it will automatically use
	 * this type of evaluator.
	 * 
	 * For more information about evaluators, see
	 * [the Android documenation](http://developer.android.com/reference/android/animation/TypeEvaluator.html).
	 * 
	 * @see ObjectAnimator_#setEvaluator(int)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int FLOAT_EVALUATOR = 2;

	/**
	 * Constant for use when specifying that animation should use the Android
	 * `ArgbEvaluator`.
	 * 
	 * You will want to use this evaluator if you are animating view color
	 * properties, such as `backgroundColor`.
	 * 
	 * For more information about evaluators, see
	 * [the Android documenation](http://developer.android.com/reference/android/animation/TypeEvaluator.html).
	 * 
	 * @see ObjectAnimator_#setEvaluator(int)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int ARGB_EVALUATOR = 3;

	/**
	 * Constant indicating that the animation should repeat forever.
	 * 
	 * You can read about `INFINITE`, `RESTART` and `REVERSE` in the
	 * [Android documentation](http://developer.android.com/reference/android/animation/ValueAnimator.html#constants).
	 * @see ObjectAnimator_#setRepeatCount(int)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int INFINITE = ValueAnimator.INFINITE;

	/**
	 * Constant indicating that when "repeating", the animation should start over from the
	 * beginning.
	 * 
	 * You can read about `INFINITE`, `RESTART` and `REVERSE` in the
	 * [Android documentation](http://developer.android.com/reference/android/animation/ValueAnimator.html#constants).
	 * @see ObjectAnimator_#setRepeatMode(int)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int RESTART = ValueAnimator.RESTART;

	/**
	 * Constant indicating that when "repeating", the animation should reverse itself.
	 * 
	 * You can read about `INFINITE`, `RESTART` and `REVERSE` in the
	 * [Android documentation](http://developer.android.com/reference/android/animation/ValueAnimator.html#constants).
	 * @see ObjectAnimator_#setRepeatMode(int)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int REVERSE = ValueAnimator.REVERSE;

	/**
	 * Constant for the accelerate interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int ACCELERATE_INTERPOLATOR = 1;

	/**
	 * Constant for the decelerate interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int DECELERATE_INTERPOLATOR = 2;

	/**
	 * Constant for the accelerate decelerate interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 3;

	/**
	 * Constant for the anticipate interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int ANTICIPATE_INTERPOLATOR = 4;

	/**
	 * Constant for the anticipate overshoot interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int ANTICIPATE_OVERSHOOT_INTERPOLATOR = 5;

	/**
	 * Constant for the bounce interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int BOUNCE_INTERPOLATOR = 6;

	/**
	 * Constant for the cycle interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int CYCLE_INTERPOLATOR = 7;

	/**
	 * Constant for the overshoot interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int OVERSHOOT_INTERPOLATOR = 8;

	/**
	 * Constant for the linear interpolator.
	 * 
	 * Read about different animation interpolators in the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * 
	 * @see ObjectAnimator_#setInterpolator(int)
	 * @see AnimatorSet_#setInterpolator(int)
	 * @see ViewPropertyAnimator_#setInterpolator(int, Object...)
	 * @since 1.0
	 */
	@Kroll.constant
	public static final int LINEAR_INTERPOLATOR = 9;

	public AndroidAnimation() {
		super();
	}

	/**
	 * Returns a factory for the [ObjectAnimator](@ref ObjectAnimator_),
	 * from which you can call `ofInt` or `ofFloat` to
	 * instantiate an `ObjectAnimator`.
	 * @return	[Factory object](@ref ObjectAnimatorFactory) with which to instantiate an `ObjectAnimator`. 
	 * @since 	1.0
	 */
	@Kroll.method
	@Kroll.getProperty
	public ObjectAnimatorFactory getObjectAnimator() {
		return mObjectAnimatorFactory;
	}

	/**
	 * Returns a factory for the [ViewPropertyAnimator](@ref ViewPropertyAnimator_),
	 * from which you can call `animate` to
	 * instantiate a `ViewPropertyAnimator`.
	 * 
	 * @return	[Factory object](@ref ViewPropertyAnimatorFactory) with which to instantiate a `ViewPropertyAnimator`. 
	 * @since	1.0
	 */
	@Kroll.method
	@Kroll.getProperty
	public ViewPropertyAnimatorFactory getViewPropertyAnimator() {
		return mViewPropertyAnimatorFactory;
	}

	/**
	 * Converts dips, sips, etc. into pixels, taking density into account.
	 * Useful when animating using the [ObjectAnimator](@ref ObjectAnimator_],
	 * which does *not* perform such calculations automatically for you.
	 * (The [ViewPropertyAnimator](@ref ViewPropertyAnimator_), however,
	 * will do these calculations for you, so when using it you will likely
	 * not need to be using this function.)
	 * 
	 * @param view			The view you will eventually be animating, or any view on
	 * 						the same window. This is needed to determine the density
	 * 						of the hosting screen.
	 * @param originalValue A number with a unit specifier, such as "12dp" if you wished
	 * 						to convert 12dp to pixels.
	 * @param direction		(Optional) "horizontal" (default) or "vertical". Only relevant
	 * 						if converting from inches, centimeters or millimeters.
	 * @return 				Number of pixels
	 * @since				1.0
	 */
	@Kroll.method
	public float toPixels(TiViewProxy view, Object originalValue,
			@Kroll.argument(optional = true) String direction) {
		Axis axis = Axis.X;
		if (direction != null && direction.toLowerCase() == "vertical") {
			axis = Axis.Y;
		}

		TiUIView tiView = view.peekView();
		if (tiView == null) {
			throw new IllegalStateException(
					"The view must be rendered before toPixels() can be called because the density of its host screen must be determined.");
		}

		View nativeView = tiView.getNativeView();
		if (nativeView == null) {
			throw new IllegalStateException(
					"The view must be rendered before toPixels() can be called because the density of its host screen must be determined.");
		}

		return AnimationUtils.toPixels(
				AnimationUtils.getDisplayMetrics(nativeView), originalValue,
				axis);

	}

}
