package com.billdawson.timodules.animation.views;

import java.lang.ref.WeakReference;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewPropertyAnimator;

@Kroll.proxy
public class ViewPropertyAnimatorProxy extends KrollProxy implements
		AnimatorListener {

	@SuppressWarnings("unused")
	private static final String TAG = "ViewPropertyAnimatorProxy";

	private ViewPropertyAnimator mAnimator = null;
	private WeakReference<KrollFunction> mListener = null;

	public ViewPropertyAnimatorProxy() {
		super();
	}

	public ViewPropertyAnimatorProxy(TiViewProxy view) {
		this();
		mAnimator = ViewPropertyAnimator.animate(view.getOrCreateView()
				.getNativeView());
		mAnimator.setListener(this);
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
	public ViewPropertyAnimatorProxy setInterpolator(int interpolator,
			Object[] interpolatorValues) {
		if (interpolatorValues != null && interpolatorValues.length > 0
				&& interpolatorValues[0].getClass().isArray()) {
			interpolatorValues = (Object[]) interpolatorValues[0];
		}

		float[] floatValues = AnimationUtils
				.unboxFloatValues(interpolatorValues);
		mAnimator.setInterpolator(AnimationUtils.buildInterpolator(
				interpolator, floatValues));
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

	@Kroll.method
	public ViewPropertyAnimatorProxy setListener(KrollFunction func) {
		mListener = func == null ? null
				: new WeakReference<KrollFunction>(func);
		return this;
	}

	private void callListener(String eventName) {
		if (mListener == null) {
			return;
		}

		KrollFunction func = mListener.get();

		if (func != null) {
			KrollDict args = new KrollDict();
			args.put(TiC.PROPERTY_NAME, eventName);
			args.put(TiC.EVENT_PROPERTY_SOURCE, this);
			func.call(this.getKrollObject(), args);
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

}
