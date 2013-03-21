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

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;

/**
 * You do not use this class directly. It is what is returned
 * by [module.objectAnimator](@ref AndroidAnimation#getObjectAnimator)
 * and contains the `ofInt` and `ofFloat` factory methods which
 * return new [ObjectAnimator](@ref ObjectAnimator_) instances.
 * 
 * @since 1.0
 */
@Kroll.proxy(creatableInModule = AndroidAnimation.class, name = "ObjectAnimatorFactory")
public class ObjectAnimatorFactory extends KrollProxy {
	@SuppressWarnings("unused")
	private static final String TAG = "ObjectAnimatorFactory";

	/**
	 * Get an [ObjectAnimator](@ref ObjectAnimator_) instance prepared
	 * to animate a float property of the passed object.
	 * @param object		Object (likely a view) whose property will be animated.
	 * @param propertyName	The name of the property to animate.
	 * @param varArgs		One or more values to animate from/to. If you pass
	 * 						just one, the animation will begin with the property's
	 * 						current value and animate to the given value. If you pass
	 * 						more than one value, the animation will begin with the first
	 * 						value and proceed through the others to the final value.
	 * @return 				[ObjectAnimator](@ref ObjectAnimator_) ready to animate the
	 * 						given property to the given values.
	 * @since 1.0
	 */
	@Kroll.method
	public ObjectAnimator_ ofFloat(Object object, String propertyName,
			Object... varArgs) {

		return new ObjectAnimator_(object, propertyName,
				PropertyDataType.FLOAT, varArgs);
	}

	/**
	 * Get an [ObjectAnimator](@ref ObjectAnimator_) instance prepared
	 * to animate an integer property of the passed object.
	 * 
	 * @param object		Object (likely a view) whose property will be animated.
	 * @param propertyName	The name of the property to animate.
	 * @param varArgs		One or more values to animate from/to. If you pass
	 * 						just one, the animation will begin with the property's
	 * 						current value and animate to the given value. If you pass
	 * 						more than one value, the animation will begin with the first
	 * 						value and proceed through the others to the final value. Note
	 * 						that although you will usually pass numbers, you can also
	 * 						pass color values in the form of strings (e.g. "#FFF") and
	 * 						this will convert them automatically to the integer color value.
	 * @return 				[ObjectAnimator](@ref ObjectAnimator_) ready to animate the
	 * 						given property to the given values.
	 * @since 1.0
	 */
	@Kroll.method
	public ObjectAnimator_ ofInt(Object object, String propertyName,
			Object... varArgs) {

		return new ObjectAnimator_(object, propertyName, PropertyDataType.INT,
				varArgs);
	}

}
