package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;

@Kroll.proxy
public class ObjectAnimatorFactoryProxy extends KrollProxy {
	@SuppressWarnings("unused")
	private static final String TAG = "ObjectAnimatorFactoryProxy";

	@Kroll.method
	public ObjectAnimatorProxy ofFloat(Object object, String propertyName,
			Object arg) {

		if (arg == null || !arg.getClass().isArray()) {
			arg = new Object[] { arg };
		}

		return new ObjectAnimatorProxy(object, propertyName,
				PropertyDataType.FLOAT, (Object[]) arg);
	}

	@Kroll.method
	public ObjectAnimatorProxy ofInt(Object object, String propertyName,
			Object arg) {
		if (arg == null || !arg.getClass().isArray()) {
			arg = new Object[] { arg };
		}
		return new ObjectAnimatorProxy(object, propertyName,
				PropertyDataType.INT, (Object[]) arg);
	}

}
