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

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiMessenger;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;

import android.graphics.Color;
import android.view.View;

import com.billdawson.timodules.animation.utils.AnimationUtils;
import com.billdawson.timodules.animation.views.ViewWrapper;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;

enum PropertyDataType {
	FLOAT, INT, UNKNOWN
}

/**
 * Use this to animate *native* View properties when faced with any of these circumstances:
 * 
 * - The View property which you wish to animate cannot be animated via the
 *   [ViewPropertyAnimator](@ref views.ViewPropertyAnimator_). A very common
 *   example of such a property would be `backgroundColor`.
 * - You want to animate a View property *from* a specific value *to* a specific
 *   value, and the *from* value is not the current value for that property. In
 *   that case, you cannot use a `ViewPropertyAnimator` because its methods do not
 *   accept *from* values, so use this class instead.
 * - You wish to include one or more animations together in an
 *   [AnimatorSet](@ref AnimatorSet_). The `AnimatorSet` does not
 *   accept `ViewPropertyAnimator` instances; you must give it instances of this class.
 *   
 * Your first choice should always be the [ViewPropertyAnimator](@ref views.ViewPropertyAnimator_)
 * because it is slightly more performant (according to Android), has a simpler interface and
 * will accept values that specify units (such as "12dp"). But if any of the
 * conditions listed above applies, then turn to this class, the `ObjectAnimator`.
 *   
 * In actual fact, `ObjectAnimator` can be used to animate the properties of any kind
 * of object -- not just Views -- as long as the property has *Java* setter and getter methods.  In
 * Javascript, however, you only have access to the objects you create from scratch
 * (i.e., true Javascript objects) and Titanium-based objects such as views, which
 * (under the covers) have counterparts in Java. Many properties of Titanium objects, however,
 * do not have true getter and setter methods in their Java counterparts, so
 * they can't be animated directly using the `ObjectAnimator`.
 * 
 * Therefore, when you use `ObjectAnimator` to animate a Titanium view
 * (`View`, `ImageView`, `Label`, `TableView`, etc.), we "unwrap" the view
 * to get to the real [Android View][1] and animate *that* instead. And the
 * Java class for Android View has getter and setter methods for its
 * properties, so `ObjectAnimator` works just fine with it.
 * 
 * This also means that the property name that you pass as the second
 * argument to [ofInt](@ref ObjectAnimatorFactory#ofInt) and
 * [ofFloat](@ref ObjectAnimatorFactory#ofFloat) must be the *Android*
 * property name, so being familiar with the getter and setter methods
 * of [Android View][1] would be helpful.
 * 
 * The preferred way to instantiate this class is via one of the two factory
 * methods available in the module.
 * 
 *     var animator = module.objectAnimator.ofInt(view, "propertyName", fromVal,
 *         toVal);
 * 
 * or
 * 
 *     var animator = module.objectAnimator.ofFloat(view, "propertyName", fromVal,
 *         toVal);
 * 
 * As you might guess, you'll use [ofInt](@ref ObjectAnimatorFactory#ofInt) when the
 * property you want to animate has an integer data type, while
 * [ofFloat](@ref ObjectAnimatorFactory#ofFloat) should be used when animating
 * a property that has a float datatype.
 * 
 * Since you are animating *native* Android properties, be aware that most integer
 * values (and some float values) represent *pixels*.  In this `ObjectAnimator`
 * class we **do not do pixel/density calculations automatically for you**, which
 * means you cannot animate with values such as "20dp". But this module does
 * contain a [toPixels](@ref AndroidAnimation#toPixels) method to help
 * you make those calculations before you pass the values to an `ObjectAnimator`.
 * 
 * Here is an example of using `ObjectAnimator` to animate a view's `backgroundColor`
 * from red to green:
 * 
 *     var win = Ti.UI.createWindow(),
 *         view = Ti.UI.createView({
 *             backgroundColor: "green"
 *         }),
 *         animMod = require("com.billdawson.timodules.animation"),
 *         animator = animMod.objectAnimator.ofInt(view, "backgroundColor",
 *             "red");
 *     
 *     animator.setDuration(1000);
 *     animator.setEvaluator(animMod.ARGB_EVALUATOR);
 *     win.add(view);
 *     win.addEventListener("open", function() {
 *         animator.start();
 *     });
 * 
 * [1]: http://developer.android.com/reference/android/view/View.html
 * @since 1.0
 * 
 */
@Kroll.proxy(creatableInModule = AndroidAnimation.class, name = "ObjectAnimator")
public class ObjectAnimator_ extends Animator_ {
	private static final String TAG = "ObjectAnimator_";
	private static final String PROPERTY_BACKGROUND_COLOR = "backgroundColor";
	private static final String ERR_INT_VALUE = "Values must be set to numeric array or array of strings containing color codes.";

	private PropertyDataType mPropertyType;
	private String mPropertyName;
	private float[] mFloatValues;
	private int[] mIntValues;
	private int mRepeatCount = AndroidAnimation.NO_INT_VALUE;
	private int mRepeatMode = AndroidAnimation.NO_INT_VALUE;
	private int mEvaluator = AndroidAnimation.NO_INT_VALUE;

	public ObjectAnimator_() {
		super();
		this.mPropertyType = PropertyDataType.UNKNOWN;
	}

	protected ObjectAnimator_(Object object, String propertyName,
			PropertyDataType propertyType, Object... values) {
		this();
		setTarget(object);
		mPropertyName = propertyName;
		mPropertyType = propertyType;
		if (mPropertyType == PropertyDataType.FLOAT) {
			setFloatValues(values);
		} else if (mPropertyType == PropertyDataType.INT) {
			setIntValues(values);
		}
	}

	@Override
	protected void buildAnimator() {
		ObjectAnimator animator = (ObjectAnimator) getAnimator();
		if (animator == null) {
			if (mPropertyType == PropertyDataType.UNKNOWN) {
				Log.w(TAG, "Property data type unknown, cannot animate.");
				return;
			}

			Object target = getTarget();
			String propertyName = AnimationUtils.translatePropertyName(target,
					mPropertyName);

			Object actualObject = target;

			if (target instanceof TiViewProxy) {
				TiUIView intermediateObject = ((TiViewProxy) target).peekView();
				if (intermediateObject != null) {
					actualObject = intermediateObject.getNativeView();
					if (actualObject != null
							&& propertyName.equals(PROPERTY_BACKGROUND_COLOR)
							&& mIntValues.length == 1) {
						// There is no "getBackgroundColor" on Android views,
						// so we wrap it.
						actualObject = new ViewWrapper((View) actualObject);
					}
				} else {
					Log.w(TAG, "View not available for animation.");
				}
			}

			if (actualObject == null) {
				Log.w(TAG, "Object not available for animation (null).");
				return;
			}

			switch (mPropertyType) {
				case FLOAT:
					animator = ObjectAnimator.ofFloat(actualObject,
							propertyName, mFloatValues);
					break;
				case INT:
					animator = ObjectAnimator.ofInt(actualObject, propertyName,
							mIntValues);
					break;
				case UNKNOWN:
					break;
			}

		}

		if (mRepeatCount != AndroidAnimation.NO_INT_VALUE) {
			animator.setRepeatCount(mRepeatCount);
		}

		if (mRepeatMode != AndroidAnimation.NO_INT_VALUE) {
			animator.setRepeatMode(mRepeatMode);
		}

		if (mEvaluator != AndroidAnimation.NO_INT_VALUE) {
			switch (mEvaluator) {
				case AndroidAnimation.INT_EVALUATOR:
					animator.setEvaluator(new IntEvaluator());
					break;
				case AndroidAnimation.FLOAT_EVALUATOR:
					animator.setEvaluator(new FloatEvaluator());
					break;
				case AndroidAnimation.ARGB_EVALUATOR:
					animator.setEvaluator(new ArgbEvaluator());
					break;
				default:
					Log.w(TAG, "Evaluator set to unknown value: " + mEvaluator);
			}
		}

		setAnimator(animator);

		super.setCommonAnimatorProperties();

	}

	// Public-facing Kroll methods/properties.

	/**
	 * Gets the evaluator.
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.getProperty
	public int getEvaluator() {
		return mEvaluator;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.setProperty
	public void setEvaluator(int evaluator) {
		mEvaluator = evaluator;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	public void reverse() {
		buildAnimator();
		final ObjectAnimator animator = (ObjectAnimator) getAnimator();
		if (animator != null) {
			TiMessenger.postOnMain(new Runnable() {
				@Override
				public void run() {
					animator.reverse();
				}
			});
		} else {
			Log.w(TAG, WARN_ANIMATOR);
		}
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.getProperty
	public String getPropertyName() {
		return mPropertyName;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.setProperty
	public void setPropertyName(String propertyName) {
		this.mPropertyName = propertyName;
	}

	/**
	 * If you are animating a property whose data type is `int`,
	 * you may use this setter method to animate between one or more values.
	 * However, it's preferred that you use the [ofInt](@ref ObjectAnimatorFactory#ofInt)
	 * method to create the `ObjectAnimator` and set its `int` values
	 * all at once.
	 * 
	 * If you pass just one value here, then the animation will be from
	 * the _current_ value to the value you give here. If you pass
	 * more than one value, then the animation will start from the
	 * first value you provide and end with the last value you provide.
	 * The two typical use cases are to provide either one value or
	 * two values.  However it is possible to pass more than two values,
	 * in which case the animation will "go through" the intermediary
	 * values on its way to the final value.
	 * 
	 * If you are animating the view's backgroundColor, you can
	 * pass strings representing colors instead of integers.
	 * For example, "black", "#000", and such would be
	 * recognized and changed on the fly for you to their valid Android
	 * color integers.  Besides this special case, only pass
	 * integer values. We do **not** do pixel calculations for you,
	 * so if you are animating a pixel value be sure to use
	 * [toPixels](@ref AndroidAnimation#toPixels) first to get
	 * the correct pixel value.
	 * 
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.setProperty
	public void setIntValues(Object... values) {
		if (mPropertyType == PropertyDataType.UNKNOWN) {
			mPropertyType = PropertyDataType.INT;
		}

		if (values == null || values.length == 0) {
			mIntValues = null;
			return;
		}

		mIntValues = new int[values.length];

		for (int i = 0; i < values.length; i++) {
			Object member = values[i];
			if (member instanceof Number) {
				mIntValues[i] = ((Number) member).intValue();
			} else if (member instanceof String) {
				try {
					int colorVal = TiConvert.toColor((String) member);
					// HACK For whatever reason Titanium's toColor will
					// default to "yellow" (pre 3.1.0) or "transparent"
					// (3.1.0+) if it can't figure out the passed
					// value. Therefore if the returned value is either
					// yellow or transparent, only use it if we already
					// know we're animating the backgroundColor property.
					// Else throw.
					if (colorVal != Color.TRANSPARENT
							&& colorVal != Color.YELLOW) {
						mIntValues[i] = colorVal;
					} else {
						String value = (String) member;
						if (!value.startsWith("#")
								&& value.toLowerCase() != "yellow"
								&& value.toLowerCase() != "transparent") {
							// Doesn't look right, since it appears that a color
							// value matching transparent or yellow wasn't
							// really
							// passed in. So only use this value if in fact we
							// know we are animating backgroundColor. Otherwise
							// this
							// looks too fishy and so we'll throw.
							if (mPropertyName != null
									&& mPropertyName.equals("backgroundColor")) {
								mIntValues[i] = colorVal;
							} else {
								throw new IllegalArgumentException(
										ERR_INT_VALUE);
							}
						}
					}

				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(ERR_INT_VALUE);
				}
			} else {
				throw new IllegalArgumentException(ERR_INT_VALUE);
			}
		}
	}

	/**
	 * If you are animating a property whose data type is `float`,
	 * you may use this setter method to animate between one or more values.
	 * However, it's preferred that you use the [ofFloat](@ref ObjectAnimatorFactory#ofFloat)
	 * method to create the `ObjectAnimator` and set its `float` values
	 * all at once.
	 * 
	 * If you pass just one value here, then the animation will be from
	 * the _current_ value to the value you give here. If you pass
	 * more than one value, then the animation will start from the
	 * first value you provide and end with the last value you provide.
	 * The two typical use cases are to provide either one value or
	 * two values.  However it is possible to pass more than two values,
	 * in which case the animation will "go through" the intermediary
	 * values on its way to the final value.
	 * 
	 * We do **not** do pixel calculations for you,
	 * so if you are animating a pixel value be sure to use
	 * [toPixels](@ref AndroidAnimation#toPixels) first to get
	 * the correct pixel value.
	 * 
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.setProperty
	public void setFloatValues(Object... values) {
		if (mPropertyType == PropertyDataType.UNKNOWN) {
			mPropertyType = PropertyDataType.FLOAT;
		}

		if (values == null || values.length == 0) {
			mFloatValues = null;
			return;
		}

		mFloatValues = AnimationUtils.unboxFloatValues(values);

	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.getProperty
	public int getRepeatCount() {
		return mRepeatCount;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.setProperty
	public void setRepeatCount(int repeatCount) {
		this.mRepeatCount = repeatCount;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.getProperty
	public int getRepeatMode() {
		return mRepeatMode;
	}

	/**
	 * @since 1.0
	 */
	@Kroll.method
	@Kroll.setProperty
	public void setRepeatMode(int repeatMode) {
		this.mRepeatMode = repeatMode;
	}

}
