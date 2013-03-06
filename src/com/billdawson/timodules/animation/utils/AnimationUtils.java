package com.billdawson.timodules.animation.utils;

import java.util.HashMap;
import java.util.Map;

public class AnimationUtils {
	private static final AnimationUtils mAnimationUtils = new AnimationUtils();
	private static final Map<String, String> mPropertyMap = new HashMap<String, String>();
	static {
		mPropertyMap.put("opacity", "alpha");
	}
	private AnimationUtils() {};
	
	public static AnimationUtils getInstance() {
		return mAnimationUtils;
	}
	
	public String translatePropertyName(String tiPropertyName) {
		String newName = mPropertyMap.get(tiPropertyName);
		if (newName == null) {
			newName = tiPropertyName;
		}
		return newName;
	}
}
