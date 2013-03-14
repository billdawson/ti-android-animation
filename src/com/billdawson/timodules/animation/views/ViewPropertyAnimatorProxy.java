package com.billdawson.timodules.animation.views;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;

import com.nineoldandroids.view.ViewPropertyAnimator;

@Kroll.proxy
public class ViewPropertyAnimatorProxy extends KrollProxy {

	private ViewPropertyAnimator mAnimator;

	public ViewPropertyAnimatorProxy() {
		super();
	}

	public ViewPropertyAnimatorProxy(TiViewProxy view) {
		this();
		mAnimator = ViewPropertyAnimator.animate(view.getOrCreateView()
				.getNativeView());
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy alpha(float value) {
		mAnimator.alpha(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy alphaBy(float value) {
		mAnimator.alphaBy(value);
		return this;
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
	public ViewPropertyAnimatorProxy setStartDelay(long startDelay) {
		mAnimator.setStartDelay(startDelay);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy setInterpolator(int interpolator) {
		// TODO
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy setListener(Void v) {
		// TODO -- a new variant of addEventListener?
		return this;
	}

	@Kroll.method
	public void start() {
		mAnimator.start();
	}

	@Kroll.method
	public void cancel() {
		mAnimator.cancel();
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy x(float value) {
		mAnimator.x(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy xBy(float value) {
		mAnimator.xBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy y(float value) {
		mAnimator.y(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy yBy(float value) {
		mAnimator.yBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotation(float value) {
		mAnimator.rotation(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationBy(float value) {
		mAnimator.rotationBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationX(float value) {
		mAnimator.rotationX(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationXBy(float value) {
		mAnimator.rotationXBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationY(float value) {
		mAnimator.rotationY(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationYBy(float value) {
		mAnimator.rotationYBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationX(float value) {
		mAnimator.translationX(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationXBy(float value) {
		mAnimator.translationXBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationY(float value) {
		mAnimator.translationY(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationYBy(float value) {
		mAnimator.translationYBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleX(float value) {
		mAnimator.scaleX(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleXBy(float value) {
		mAnimator.scaleXBy(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleY(float value) {
		mAnimator.scaleY(value);
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleYBy(float value) {
		mAnimator.scaleYBy(value);
		return this;
	}

}
