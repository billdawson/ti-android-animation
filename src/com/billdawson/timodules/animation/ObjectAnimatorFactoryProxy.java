package com.billdawson.timodules.animation;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.util.TiConvert;

@Kroll.proxy
public class ObjectAnimatorFactoryProxy extends KrollProxy {
	@SuppressWarnings("unused")
	private static final String TAG = "ObjectAnimatorFactoryProxy";

	private static final String INT_TYPE_ERROR_MSG = "ofInt expects an integer or an array of integers. Color codes (e.g. #ff0000) may also be passed as strings.";
	private static final String FLOAT_TYPE_ERROR_MSG = "ofFloat expects a float or an array of floats.";

	@Kroll.method
	public ObjectAnimatorProxy ofFloat(Object object, String propertyName,
			Object arg) {

		if (arg == null
				|| (!arg.getClass().isArray() && !(arg instanceof Number))) {
			throw new IllegalArgumentException(FLOAT_TYPE_ERROR_MSG);
		}

		if (arg instanceof Number) {
			return ObjectAnimatorProxy.ofFloat(object, propertyName,
					new float[] { ((Number) arg).floatValue() });

		} else {
			Object[] values = (Object[]) arg;
			float[] floatValues = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				Object member = values[i];
				if (member instanceof Number) {
					floatValues[i] = ((Number) member).floatValue();
				} else {
					throw new IllegalArgumentException(FLOAT_TYPE_ERROR_MSG);
				}
			}
			return ObjectAnimatorProxy.ofFloat(object, propertyName,
					floatValues);
		}
	}

	@Kroll.method
	public ObjectAnimatorProxy ofInt(Object object, String propertyName,
			Object arg) {

		if (arg == null
				|| (!arg.getClass().isArray() && !(arg instanceof String) && !(arg instanceof Number))) {
			throw new IllegalArgumentException(INT_TYPE_ERROR_MSG);
		}

		if (arg instanceof Number) {
			return ObjectAnimatorProxy.ofInt(object, propertyName,
					new int[] { ((Number) arg).intValue() });

		} else if (arg instanceof String) {
			// Perhaps color code.
			try {
				int colorVal = TiConvert.toColor((String) arg);
				return ObjectAnimatorProxy.ofInt(object, propertyName,
						new int[] { colorVal });
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(INT_TYPE_ERROR_MSG);
			}

		} else {
			Object[] values = (Object[]) arg;
			int[] intValues = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				Object member = values[i];
				if (member instanceof Number) {
					intValues[i] = ((Number) member).intValue();
				} else if (member instanceof String) {
					// Might be a color, which parses to an int.
					try {
						int colorVal = TiConvert.toColor((String) member);
						intValues[i] = colorVal;
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(INT_TYPE_ERROR_MSG);
					}
				} else {
					throw new IllegalArgumentException(INT_TYPE_ERROR_MSG);
				}
			}
			return ObjectAnimatorProxy.ofInt(object, propertyName, intValues);
		}
	}

}
