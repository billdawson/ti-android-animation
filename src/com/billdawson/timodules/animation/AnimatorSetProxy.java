package com.billdawson.timodules.animation;

import org.appcelerator.kroll.annotations.Kroll;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;

@Kroll.proxy(creatableInModule = AndroidanimationModule.class)
public class AnimatorSetProxy extends AnimatorProxy {

	enum PlayOrder {
		SEQUENTIALLY, TOGETHER, UNKNOWN
	};

	@SuppressWarnings("unused")
	private static final String TAG = "AnimatorSetProxy";

	private AnimatorProxy[] mChildAnimations;
	private PlayOrder mPlayOrder = PlayOrder.UNKNOWN;

	@Override
	protected void buildAnimator() {

		if (mPlayOrder == PlayOrder.UNKNOWN) {
			throw new IllegalStateException(
					"playTogether or playSequentially must be called before using the AnimatorSet.");
		}

		AnimatorSet animator = (AnimatorSet) getAnimator();

		int childCount = mChildAnimations == null ? 0 : mChildAnimations.length;
		Animator[] children = new Animator[childCount];

		for (int i = 0; i < childCount; i++) {
			AnimatorProxy oneProxy = mChildAnimations[i];
			oneProxy.buildAnimator();
			children[i] = oneProxy.getAnimator();

		}

		if (animator == null) {
			animator = new AnimatorSet();
		}

		if (mPlayOrder == PlayOrder.SEQUENTIALLY) {
			animator.playSequentially(children);
		} else {
			animator.playTogether(children);
		}

		if (getTarget() != null) {
			animator.setTarget(getTarget());
		}

		setAnimator(animator);

		super.setCommonAnimatorProperties();

	}

	@Kroll.method
	public AnimatorProxy[] getChildAnimations() {
		return mChildAnimations;
	}

	@Kroll.method
	public void playSequentially(AnimatorProxy[] animations) {
		mChildAnimations = animations;
		mPlayOrder = PlayOrder.SEQUENTIALLY;
	}

	@Kroll.method
	public void playTogether(AnimatorProxy[] animations) {
		mChildAnimations = animations;
		mPlayOrder = PlayOrder.TOGETHER;
	}

}
