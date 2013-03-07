package com.billdawson.timodules.animation.utils;

import java.util.HashMap;
import java.util.Map;

import org.appcelerator.titanium.proxy.TiViewProxy;

public class AnimationUtils {
	enum ObjectType {
		VIEW, GENERAL
	}

	private static final AnimationUtils mAnimationUtils = new AnimationUtils();
	private static final Map<ObjectType, Map<String, String>> mPropertyMap = new HashMap<ObjectType, Map<String, String>>();
	static {
		mPropertyMap.put(ObjectType.VIEW, new HashMap<String, String>());
		mPropertyMap.get(ObjectType.VIEW).put("opacity", "alpha");
	}

	private AnimationUtils() {
	};

	public static AnimationUtils getInstance() {
		return mAnimationUtils;
	}

	public String translatePropertyName(Object object, String tiPropertyName) {
		ObjectType objType = ObjectType.GENERAL;

		// Currently we only have property name maps for mapping from Titanium
		// views to Android native views.
		if (object instanceof TiViewProxy) {
			objType = ObjectType.VIEW;
		}

		String newName = mPropertyMap.get(objType).get(tiPropertyName);
		if (newName == null) {
			newName = tiPropertyName;
		}

		return newName;

	}
}
