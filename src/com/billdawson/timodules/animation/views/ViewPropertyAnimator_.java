package com.billdawson.timodules.animation.views;

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

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.TiMessenger;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.billdawson.timodules.animation.utils.AnimationUtils.Axis;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Provides a fluent interface to quickly and easily
 * animate several properties of an Android View.
 * 
 * When animating a view, you should attempt to use this
 * class if possible since it is optimized (within native
 * Android) for animating views. There will be some cases
 * when this class won't suffice; see the
 * [ObjectAnimator](@ref com.billdawson.timodules.animation.ObjectAnimator_)
 * documentation for some examples of when you might use it instead
 * of this `ViewPropertyAnimator`.
 * 
 * Here's an example of using this class to move a view down and
 * to the right using an accelerate-decelerate interpolator.
 * Note how you acquire an instance of this class (`module.viewPropertyAnimator.animate(view)`).
 * 
 *     // where "module" is an instance of this module and
 *     // "view" is a view you've placed on the window already.
 *     module.viewPropertyAnimator.animate(view)
 *         .setInterpolator(module.ACCELERATE_DECELERATE_INTERPOLATOR)
 *         .setDuration(3000)
 *         .xBy("100dp")
 *         .yBy("200dp");
 * 
 * Note also that you don't need to tell it to start. The `ViewPropertyAnimator`
 * begins its animation when the call chain is finished.
 * 
 * **Do not create an instance using "traditional" Titanium factory methods
 * such as `module.createViewPropertyAnimator({...})`.**
 * 
 * @since 1.0
 */
@Kroll.proxy(creatableInModule = com.billdawson.timodules.animation.AndroidAnimation.class, name = "ViewPropertyAnimator")
public class ViewPropertyAnimator_ extends KrollProxy implements
		AnimatorListener {

	private static final float NOVAL = Float.MIN_VALUE;
	private static final String ERR_VIEW_UNAVAILABLE = "A ViewPropertyAnimator cannot be created until the view it is to animate has been created.";

	private Runnable mAnimationStarter = new Runnable() {
		@Override
		public void run() {
			start();
		}
	};

	private Handler mHandler = new Handler(TiMessenger.getRuntimeMessenger()
			.getLooper());

	private ViewPropertyAnimator mAnimator = null;
	private KrollFunction mListener = null;
	private KrollFunction mStartAction = null;
	private KrollFunction mEndAction = null;

	private DisplayMetrics mDisplayMetrics;

	private float xVal = NOVAL;
	private float xByVal = NOVAL;
	private float yVal = NOVAL;
	private float yByVal = NOVAL;

	private float alphaVal = NOVAL;
	private float alphaByVal = NOVAL;

	private float rotationVal = NOVAL;
	private float rotationByVal = NOVAL;
	private float rotationXVal = NOVAL;
	private float rotationXByVal = NOVAL;
	private float rotationYVal = NOVAL;
	private float rotationYByVal = NOVAL;

	private float scaleXVal = NOVAL;
	private float scaleXByVal = NOVAL;
	private float scaleYVal = NOVAL;
	private float scaleYByVal = NOVAL;

	private float translationXVal = NOVAL;
	private float translationXByVal = NOVAL;
	private float translationYVal = NOVAL;
	private float translationYByVal = NOVAL;

	public ViewPropertyAnimator_(TiViewProxy view) {
		super();

		if (view == null) {
			throw new IllegalStateException(ERR_VIEW_UNAVAILABLE);
		}

		TiUIView tiView = view.peekView();
		if (tiView == null) {
			throw new IllegalStateException(ERR_VIEW_UNAVAILABLE);
		}

		View nativeView = tiView.getNativeView();
		if (nativeView == null) {
			throw new IllegalStateException(ERR_VIEW_UNAVAILABLE);
		}

		mDisplayMetrics = AnimationUtils.getDisplayMetrics(nativeView);

		mAnimator = ViewPropertyAnimator.animate(nativeView);
		mAnimator.setListener(this);
	}

	/**
	 * Specify the duration of the animation.
	 * @param	Milliseconds Number of milliseconds (seconds / 1000) over which the animation should run.
	 * @return	This instance (for method chaining).
	 */
	@Kroll.method
	public ViewPropertyAnimator_ setDuration(long milliseconds) {
		mAnimator.setDuration(milliseconds);
		return this;
	}

	/**
	 * The duration of the animation.
	 * @return Milliseconds
	 * @since 1.0
	 */
	@Kroll.method
	public long getDuration() {
		return mAnimator.getDuration();
	}

	/**
	 * A delay, in milliseconds, before the animation should begin.
	 * @return Milliseconds
	 * @since 1.0
	 */
	@Kroll.method
	public long getStartDelay() {
		return mAnimator.getStartDelay();
	}

	/**
	 * A delay, in milliseconds, before the animation should begin.
	 * @param	milliseconds The duration of the delay, after which
	 * 			the animation should begin changing the view property(ies)
	 * 			after it has been started.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ setStartDelay(long milliseconds) {
		mAnimator.setStartDelay(milliseconds);
		return this;
	}

	/**
	 * Specify which interpolator the animation should use.
	 * 
	 * For an overview of interpolators, see the
	 * [Android documentation](http://developer.android.com/reference/android/view/animation/Interpolator.html).
	 * @param	interpolator		A value indicating which interpolator to use.
	 * 								The values are in constants on the
	 * 								[main module class](@ref AndroidAnimation).
	 * @param	interpolatorValues  One or more values to pass to the interpolator.
	 * 								Some interpolators accept values to direct/alter
	 * 								their calculations; the bounce interpolator
	 * 								is an example of such. See the Android
	 * 								documentation for more details.
	 * @return	This instance (for method chaining).
	 * @since	1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ setInterpolator(int interpolator,
			Object... interpolatorValues) {

		float[] floatValues = AnimationUtils
				.unboxFloatValues(interpolatorValues);
		mAnimator.setInterpolator(AnimationUtils.buildInterpolator(
				interpolator, floatValues));
		return this;
	}

	/**
	 * Start the animation. You probably rarely call this since
	 * the animations start automatically.
	 * @since 1.0
	 */
	@Kroll.method
	public void start() {
		mHandler.removeCallbacks(mAnimationStarter);
		TiMessenger.postOnMain(new Runnable() {
			@Override
			public void run() {
				doStart();
			}
		});
	}

	private void doStart() {
		if (xVal != NOVAL) {
			mAnimator.x(xVal);
		}

		if (xByVal != NOVAL) {
			mAnimator.xBy(xByVal);
		}

		if (yVal != NOVAL) {
			mAnimator.y(yVal);
		}

		if (yByVal != NOVAL) {
			mAnimator.yBy(yByVal);
		}

		if (scaleXVal != NOVAL) {
			mAnimator.scaleX(scaleXVal);
		}

		if (scaleXByVal != NOVAL) {
			mAnimator.scaleXBy(scaleXByVal);
		}

		if (scaleYVal != NOVAL) {
			mAnimator.scaleY(scaleYVal);
		}

		if (scaleYByVal != NOVAL) {
			mAnimator.scaleYBy(scaleYByVal);
		}

		if (translationXVal != NOVAL) {
			mAnimator.translationX(translationXVal);
		}

		if (translationXByVal != NOVAL) {
			mAnimator.translationXBy(translationXByVal);
		}

		if (translationYVal != NOVAL) {
			mAnimator.translationY(translationYVal);
		}

		if (translationYByVal != NOVAL) {
			mAnimator.translationYBy(translationYByVal);
		}

		if (alphaVal != NOVAL) {
			mAnimator.alpha(alphaVal);
		}

		if (alphaByVal != NOVAL) {
			mAnimator.alphaBy(alphaByVal);
		}

		if (rotationVal != NOVAL) {
			mAnimator.rotation(rotationVal);
		}

		if (rotationByVal != NOVAL) {
			mAnimator.rotationBy(rotationByVal);
		}

		if (rotationXVal != NOVAL) {
			mAnimator.rotationX(rotationXVal);
		}

		if (rotationXByVal != NOVAL) {
			mAnimator.rotationXBy(rotationXByVal);
		}

		if (rotationYVal != NOVAL) {
			mAnimator.rotationY(rotationYVal);
		}

		if (rotationYByVal != NOVAL) {
			mAnimator.rotationYBy(rotationYByVal);
		}
	}

	/**
	 * Cancel a running animation.
	 * @since 1.0
	 */
	@Kroll.method
	public void cancel() {
		TiMessenger.postOnMain(new Runnable() {
			@Override
			public void run() {
				doCancel();
			}
		});
	}

	private void doCancel() {
		mAnimator.cancel();
	}

	private void scheduleStarter() {
		mHandler.removeCallbacks(mAnimationStarter);
		mHandler.post(mAnimationStarter);
	}

	private float toPixels(Object value, Axis axis) {
		return AnimationUtils.toPixels(mDisplayMetrics, value, axis);
	}

	/**
	 * Animate the alpha (opacity) of the view to the given
	 * value.
	 * @param	value	The value to be animated to.
	 * @returns This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ alpha(float value) {
		alphaVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the alpha (opacity) of the view *by* the given
	 * value.
	 * @param	value	The value to be animated by, as an offset of the current value.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ alphaBy(float value) {
		alphaByVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * Synonymous with [alpha](@ref #alpha(float)).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ opacity(float value) {
		// Convenience for those used to "opacity" in Titanium.
		return alpha(value);
	}

	/**
	 * Synonymous with [alphaBy](@ref #alphaBy(float)).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ opacityBy(float value) {
		// Convenience for those used to "opacity" in Titanium.
		return alphaBy(value);
	}

	/**
	 * Animate the view's [native "x" value](http://developer.android.com/reference/android/view/View.html#setX%28float%29)
	 * to the given value.
	 * @param	value	The value to animate to. You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ x(Object value) {
		xVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "x" value](http://developer.android.com/reference/android/view/View.html#setX%28float%29)
	 * *by* the given value.
	 * @param	value	The value to animate by as offset of the current value.
	 * 					You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ xBy(Object value) {
		xByVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "y" value](http://developer.android.com/reference/android/view/View.html#setY%28float%29)
	 * to the given value.
	 * @param	value	The value to animate to. You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ y(Object value) {
		yVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "y" value](http://developer.android.com/reference/android/view/View.html#setY%28float%29)
	 * *by* the given value.
	 * @param	value	The value to animate by as offset of the current value.
	 * 					You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ yBy(Object value) {
		yByVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ rotation(float value) {
		rotationVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ rotationBy(float value) {
		rotationByVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ rotationX(float value) {
		rotationXVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ rotationXBy(float value) {
		rotationXByVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ rotationY(float value) {
		rotationYVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ rotationYBy(float value) {
		rotationYByVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "translationX" value](http://developer.android.com/reference/android/view/View.html#setTranslationX%28float%29)
	 * to the given value.
	 * @param	value	The value to animate to. You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ translationX(Object value) {
		translationXVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "translationX" value](http://developer.android.com/reference/android/view/View.html#setTranslationX%28float%29)
	 * *by* the given value.
	 * @param	value	The value to animate by as offset of the current value.
	 * 					You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ translationXBy(Object value) {
		translationXByVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "translationY" value](http://developer.android.com/reference/android/view/View.html#setTranslationY%28float%29)
	 * to the given value.
	 * @param	value	The value to animate to. You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ translationY(Object value) {
		translationYVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "translationY" value](http://developer.android.com/reference/android/view/View.html#setTranslationY%28float%29)
	 * *by* the given value.
	 * @param	value	The value to animate by as offset of the current value.
	 * 					You can pass a string that contains units,
	 * 					such as "12dp", and the calculation will be done for you.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ translationYBy(Object value) {
		translationYByVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "scaleX" value](http://developer.android.com/reference/android/view/View.html#setScaleX%28float%29)
	 * to the given value.
	 * @param	value	The scaling factor value to animate to. A value of 1 means the true size in the layout (i.e., no scaling).
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ scaleX(float value) {
		scaleXVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "scaleX" value](http://developer.android.com/reference/android/view/View.html#setScaleX%28float%29)
	 * *by* the given value.
	 * @param	value	The scaling factor value to animate by, as an offset of the current scaling factor.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ scaleXBy(float value) {
		scaleXByVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "scaleY" value](http://developer.android.com/reference/android/view/View.html#setScaleY%28float%29)
	 * to the given value.
	 * @param	value	The scaling factor value to animate to. A value of 1 means the true size in the layout (i.e., no scaling).
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ scaleY(float value) {
		scaleYVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * Animate the view's [native "scaleY" value](http://developer.android.com/reference/android/view/View.html#setScaleX%28float%29)
	 * *by* the given value.
	 * @param	value	The scaling factor value to animate by, as an offset of the current scaling factor.
	 * @return	This instance (for method chaining).
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ scaleYBy(float value) {
		scaleYByVal = value;
		scheduleStarter();
		return this;
	}

	/**
	 * A function (callback) to be called when the animation starts, ends or is canceled.
	 * 
	 * As an alternative to this, see [withStartAction](@ref withStartAction) and
	 * [withEndAction](@ref withEndAction).
	 * @param	func	The callback function. When called, it will be passed a single argument object, which mimics
	 * 					standard Titanium event listeners. The argument object's `type` property
	 * 					will be set to "start", "end" or "cancel", depending on the event provoking
	 * 					the callback. 
	 * @return	This instance (for method chaining).
	 * @since	1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ setListener(KrollFunction func) {
		mListener = func;
		return this;
	}

	private void callListener(String eventName) {
		if (mListener == null && mStartAction == null && mEndAction == null) {
			return;
		}

		KrollDict args = new KrollDict();
		args.put(TiC.EVENT_PROPERTY_TYPE, eventName);
		args.put(TiC.EVENT_PROPERTY_SOURCE, this);

		if (mListener != null) {
			mListener.call(this.getKrollObject(), args);
		}

		if (eventName.equals("start") && mStartAction != null) {
			mStartAction.call(this.getKrollObject(), args);
		}

		if (eventName.equals("end") && mEndAction != null) {
			mEndAction.call(this.getKrollObject(), args);
		}
	}

	@Override
	public void onAnimationCancel(Animator animator) {
		callListener("cancel");
	}

	@Override
	public void onAnimationEnd(Animator animator) {
		callListener("end");
	}

	@Override
	public void onAnimationRepeat(Animator animator) {
		callListener("repeat");
	}

	@Override
	public void onAnimationStart(Animator animator) {
		callListener("start");
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ withStartAction(KrollFunction function) {
		mStartAction = function;
		return this;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public ViewPropertyAnimator_ withEndAction(KrollFunction function) {
		mEndAction = function;
		return this;
	}

}
