package com.billdawson.timodules.animation.utils;

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

import java.util.HashMap;
import java.util.Map;

import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiDimension;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.billdawson.timodules.animation.AndroidAnimation;

public class AnimationUtils {
	enum ObjectType {
		VIEW, GENERAL
	}

	public enum Axis {
		X, Y
	}

	private static final String TAG = "AnimationUtils";
	private static final Map<ObjectType, Map<String, String>> mPropertyMap = new HashMap<ObjectType, Map<String, String>>();
	private static final float POINT_DPI = 72F;
	private static final float MM_INCH = 25.4F;
	private static final float CM_INCH = 2.54F;

	static {
		mPropertyMap.put(ObjectType.VIEW, new HashMap<String, String>());
		mPropertyMap.get(ObjectType.VIEW).put("opacity", "alpha");
	}

	private AnimationUtils() {
	};

	public static String translatePropertyName(Object object,
			String tiPropertyName) {
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

	public static float[] unboxFloatValues(Object[] origValues) {
		if (origValues == null || origValues.length == 0) {
			return null;
		}

		int length = origValues.length;

		float[] result = new float[length];

		for (int i = 0; i < length; i++) {
			Object member = origValues[i];
			if (!(member instanceof Number)) {
				if (member == null) {
					throw new IllegalArgumentException(
							"Array must contain only numbers. Null values are not acceptable.");
				} else {
					throw new IllegalArgumentException(
							"Array must contain only numbers. "
									+ member.getClass().getName()
									+ " value is not acceptable.");
				}
			}

			result[i] = ((Number) member).floatValue();
		}

		return result;

	}

	public static Interpolator buildInterpolator(int interpolatorType,
			float[] interpolatorValues) {
		final int valueCount = interpolatorValues == null ? 0
				: interpolatorValues.length;

		switch (interpolatorType) {
			case AndroidAnimation.ACCELERATE_DECELERATE_INTERPOLATOR:
				return new AccelerateDecelerateInterpolator();

			case AndroidAnimation.ACCELERATE_INTERPOLATOR:
				if (valueCount > 0) {
					return new AccelerateInterpolator(interpolatorValues[0]);
				} else {
					return new AccelerateInterpolator();
				}

			case AndroidAnimation.ANTICIPATE_INTERPOLATOR:
				if (valueCount > 0) {
					return new AnticipateInterpolator(interpolatorValues[0]);
				} else {
					return new AnticipateInterpolator();
				}

			case AndroidAnimation.ANTICIPATE_OVERSHOOT_INTERPOLATOR:
				if (valueCount == 0) {
					return new AnticipateOvershootInterpolator();
				} else if (valueCount == 1) {
					return new AnticipateOvershootInterpolator(
							interpolatorValues[0]);
				} else {
					return new AnticipateOvershootInterpolator(
							interpolatorValues[0], interpolatorValues[1]);
				}

			case AndroidAnimation.BOUNCE_INTERPOLATOR:
				return new BounceInterpolator();

			case AndroidAnimation.CYCLE_INTERPOLATOR:
				if (valueCount > 0) {
					return new CycleInterpolator(interpolatorValues[0]);
				} else {
					Log.w(TAG,
							"No values provided for Cycle Interpolator. Defaulting to 0.");
					return new CycleInterpolator(0f);
				}

			case AndroidAnimation.DECELERATE_INTERPOLATOR:
				if (valueCount > 0) {
					return new DecelerateInterpolator(interpolatorValues[0]);
				} else {
					return new DecelerateInterpolator();
				}

			case AndroidAnimation.OVERSHOOT_INTERPOLATOR:
				if (valueCount > 0) {
					return new OvershootInterpolator(interpolatorValues[0]);
				} else {
					return new OvershootInterpolator();
				}

			default:
				Log.w(TAG, "Unknown interpolator: " + interpolatorType);
				return null;
		}
	}

	public static DisplayMetrics getDisplayMetrics(View view) {
		DisplayMetrics metrics = new DisplayMetrics();
		Activity host = (Activity) view.getContext();
		host.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	public static float toPixels(DisplayMetrics metrics, Object value, Axis axis) {
		TiDimension tiDimension = TiConvert.toTiDimension(value,
				axis == Axis.X ? TiDimension.TYPE_WIDTH
						: TiDimension.TYPE_HEIGHT);
		return toPixels(metrics, tiDimension, axis);
	}

	public static float toPixels(DisplayMetrics metrics,
			TiDimension tiDimension, Axis axis) {

		int tiUnitSpecifier = tiDimension.getUnits();
		float tiValue = Double.valueOf(tiDimension.getValue()).floatValue();
		float axisDpi = axis == Axis.X ? metrics.xdpi : metrics.ydpi;

		switch (tiUnitSpecifier) {
			case TypedValue.COMPLEX_UNIT_PX:
			case TiDimension.COMPLEX_UNIT_UNDEFINED:
				return tiValue;
			case TypedValue.COMPLEX_UNIT_DIP:
				return metrics.density * tiValue;
			case TypedValue.COMPLEX_UNIT_SP:
				return metrics.scaledDensity * tiValue;
			case TypedValue.COMPLEX_UNIT_PT:
				return tiValue * (axisDpi / POINT_DPI);
			case TypedValue.COMPLEX_UNIT_MM:
				return (tiValue / MM_INCH) * axisDpi;
			case TiDimension.COMPLEX_UNIT_CM:
				return (tiValue / CM_INCH) * axisDpi;
			case TypedValue.COMPLEX_UNIT_IN:
				return tiValue * axisDpi;
		}

		Log.w(TAG,
				"Dimension type passed to toPixels() is unknown; returning original value.");
		return tiValue;
	}

}
