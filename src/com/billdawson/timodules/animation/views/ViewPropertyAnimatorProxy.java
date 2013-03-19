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

@Kroll.proxy
public class ViewPropertyAnimatorProxy extends KrollProxy implements
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

	public ViewPropertyAnimatorProxy(TiViewProxy view) {
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

	@Kroll.method
	public ViewPropertyAnimatorProxy setDuration(long milliseconds) {
		mAnimator.setDuration(milliseconds);
		return this;
	}

	@Kroll.method
	public long getDuration() {
		return mAnimator.getDuration();
	}

	@Kroll.method
	public long getStartDelay() {
		return mAnimator.getStartDelay();
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy setStartDelay(long milliseconds) {
		mAnimator.setStartDelay(milliseconds);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy setInterpolator(int interpolator,
			Object[] interpolatorValues) {

		float[] floatValues = AnimationUtils
				.unboxFloatValues(interpolatorValues);
		mAnimator.setInterpolator(AnimationUtils.buildInterpolator(
				interpolator, floatValues));
		return this;
	}

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

	@Kroll.method
	public ViewPropertyAnimatorProxy alpha(float value) {
		alphaVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy alphaBy(float value) {
		alphaByVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy opacity(float value) {
		// Convenience for those used to "opacity" in Titanium.
		return alpha(value);
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy opacityBy(float value) {
		// Convenience for those used to "opacity" in Titanium.
		return alphaBy(value);
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy x(Object value) {
		xVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy xBy(Object value) {
		xByVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy y(Object value) {
		yVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy yBy(Object value) {
		yByVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotation(float value) {
		rotationVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationBy(float value) {
		rotationByVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationX(float value) {
		rotationXVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationXBy(float value) {
		rotationXByVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationY(float value) {
		rotationYVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationYBy(float value) {
		rotationYByVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationX(Object value) {
		translationXVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationXBy(Object value) {
		translationXByVal = toPixels(value, Axis.X);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationY(Object value) {
		translationYVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationYBy(Object value) {
		translationYByVal = toPixels(value, Axis.Y);
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleX(float value) {
		scaleXVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleXBy(float value) {
		scaleXByVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleY(float value) {
		scaleYVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleYBy(float value) {
		scaleYByVal = value;
		scheduleStarter();
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy setListener(KrollFunction func) {
		mListener = func;
		return this;
	}

	private void callListener(String eventName) {
		if (mListener == null && mStartAction == null && mEndAction == null) {
			return;
		}

		KrollDict args = new KrollDict();
		args.put(TiC.PROPERTY_NAME, eventName);
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

	@Kroll.method
	public ViewPropertyAnimatorProxy withStartAction(KrollFunction function) {
		mStartAction = function;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy withEndAction(KrollFunction function) {
		mEndAction = function;
		return this;
	}

}
