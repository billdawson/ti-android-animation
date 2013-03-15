package com.billdawson.timodules.animation.views;

import java.lang.ref.WeakReference;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiMessenger;
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
	private static final float NOVAL = Float.MIN_VALUE;

	private ViewPropertyAnimator mAnimator = null;
	private WeakReference<KrollFunction> mListener = null;

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
		alphaVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy alphaBy(float value) {
		alphaByVal = value;
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
				doStart();
			}
		});
	}

	private void doCancel() {
		// TODO
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy x(float value) {
		xVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy xBy(float value) {
		xByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy y(float value) {
		yVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy yBy(float value) {
		yByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotation(float value) {
		rotationVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationBy(float value) {
		rotationByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationX(float value) {
		rotationXVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationXBy(float value) {
		rotationXByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationY(float value) {
		rotationYVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy rotationYBy(float value) {
		rotationYByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationX(float value) {
		translationXVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationXBy(float value) {
		translationXByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationY(float value) {
		translationYVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy translationYBy(float value) {
		translationYByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleX(float value) {
		scaleXVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleXBy(float value) {
		scaleXByVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleY(float value) {
		scaleYVal = value;
		return this;
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy scaleYBy(float value) {
		scaleYByVal = value;
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
		Log.d(TAG, "ONANIMATIONSTART");
		callListener("start");
	}

}
