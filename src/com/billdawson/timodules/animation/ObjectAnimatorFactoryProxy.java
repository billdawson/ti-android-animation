package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;

@Kroll.proxy
public class ObjectAnimatorFactoryProxy extends KrollProxy {
	@Kroll.method
	public ObjectAnimatorProxy ofFloat(TiViewProxy view, String propertyName, float[] values) {
		return ObjectAnimatorProxy.ofFloat(view, propertyName, values);
	}
	
	@Kroll.method
	public ObjectAnimatorProxy ofFloat(TiViewProxy view, String propertyName, float value) {
		return ObjectAnimatorProxy.ofFloat(view, propertyName, new float[]{value});
	}

}
