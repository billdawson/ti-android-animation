package com.billdawson.timodules.animation.views;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;

@Kroll.proxy
public class ViewPropertyAnimatorFactoryProxy extends KrollProxy {
	public ViewPropertyAnimatorFactoryProxy() {
		super();
	}

	@Kroll.method
	public ViewPropertyAnimatorProxy animate(TiViewProxy view) {
		return new ViewPropertyAnimatorProxy(view);
	}

}
