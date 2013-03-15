package com.billdawson.timodules.animation;

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
