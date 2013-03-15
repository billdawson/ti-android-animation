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

@Kroll.proxy
public class ObjectAnimatorFactoryProxy extends KrollProxy {
	@SuppressWarnings("unused")
	private static final String TAG = "ObjectAnimatorFactoryProxy";

	@Kroll.method
	public ObjectAnimatorProxy ofFloat(Object object, String propertyName,
			Object[] varArgs) {

		return new ObjectAnimatorProxy(object, propertyName,
				PropertyDataType.FLOAT, varArgs);
	}

	@Kroll.method
	public ObjectAnimatorProxy ofInt(Object object, String propertyName,
			Object[] varArgs) {

		return new ObjectAnimatorProxy(object, propertyName,
				PropertyDataType.INT, varArgs);
	}

}
