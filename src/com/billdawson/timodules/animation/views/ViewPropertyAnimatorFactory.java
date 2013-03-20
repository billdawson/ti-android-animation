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

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;

@Kroll.proxy(creatableInModule = com.billdawson.timodules.animation.AndroidanimationModule.class,
		name="ViewPropertyAnimatorFactory")
public class ViewPropertyAnimatorFactory extends KrollProxy {
	public ViewPropertyAnimatorFactory() {
		super();
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy animate(TiViewProxy view) {
		return new ViewPropertyAnimatorProxy(view);
	}

}
