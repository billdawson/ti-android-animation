package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;

@Kroll.proxy
public class ObjectAnimatorFactoryProxy extends KrollProxy {
	@Kroll.method
	public ObjectAnimatorProxy ofFloat(TiViewProxy view, String propertyName,
			float[] values) {
		return ObjectAnimatorProxy.ofFloat(view, propertyName, values);
	}

	@Kroll.method
	public ObjectAnimatorProxy ofFloat(TiViewProxy view, String propertyName,
			float value) {
		return ObjectAnimatorProxy.ofFloat(view, propertyName,
				new float[] { value });
	}

	@Kroll.method
	public ObjectAnimatorProxy ofInt(TiViewProxy view, String propertyName,
			int[] values) {
		return ObjectAnimatorProxy.ofInt(view, propertyName, values);
	}

	@Kroll.method
	public ObjectAnimatorProxy ofInt(TiViewProxy view, String propertyName,
			int value) {
		return ObjectAnimatorProxy.ofInt(view, propertyName,
				new int[] { value });
	}
}
