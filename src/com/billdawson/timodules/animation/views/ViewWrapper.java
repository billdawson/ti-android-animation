package com.billdawson.timodules.animation.views;

import java.lang.reflect.Field;

import org.appcelerator.kroll.common.Log;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Wraps a view so that certain property getters/setters that don't really exist
 * (such as "backgroundColor") can be animated.
 * 
 * @author Bill Dawson
 * 
 */

public class ViewWrapper {
	private static final String TAG = "ViewWrapper";
	private static final String COLOR_DRAWABLE_STATE_VAR = "mState";
	private static final String COLOR_DRAWABLE_USE_COLOR_VAR = "mUseColor";
	private static final String ERR_BACKGROUND_COLOR = "Unable to determine the current background color. Black will be returned as the color value.";
	private static Field mBackgroundStateField = null;
	private static Field mBackgroundUseColorField = null;
	private static boolean mBackgroundReflectionDone = false;

	private final View mView;

	public ViewWrapper(View v) {
		mView = v;
	}

	public int getBackgroundColor() {
		if (mView == null) {
			return 0;
		}

		Drawable background = mView.getBackground();

		if (background instanceof ColorDrawable) {
			ColorDrawable cd = (ColorDrawable) background;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				return cd.getColor();
			} else {
				if (!mBackgroundReflectionDone) {
					initBackgroundReflectionValues(cd);
				}

				if (mBackgroundStateField == null
						|| mBackgroundUseColorField == null) {
					Log.w(TAG, ERR_BACKGROUND_COLOR);
					return Color.BLACK;
				}

				Object colorStatusInstance = null;

				try {
					colorStatusInstance = mBackgroundStateField.get(cd);
				} catch (Exception e) {
					Log.w(TAG, ERR_BACKGROUND_COLOR, e);
					return Color.BLACK;
				}

				if (colorStatusInstance == null) {
					Log.w(TAG, ERR_BACKGROUND_COLOR);
					return Color.BLACK;
				}

				int colorValue = Color.BLACK;

				try {
					colorValue = mBackgroundUseColorField
							.getInt(colorStatusInstance);
				} catch (Exception e) {
					Log.w(TAG, ERR_BACKGROUND_COLOR, e);
				}

				return colorValue;

			}
		}

		return 0;
	}

	private void initBackgroundReflectionValues(ColorDrawable cd) {
		mBackgroundReflectionDone = true;

		Class<ColorDrawable> cdClass = ColorDrawable.class;

		try {
			mBackgroundStateField = cdClass
					.getDeclaredField(COLOR_DRAWABLE_STATE_VAR);
			mBackgroundStateField.setAccessible(true);
		} catch (Exception e) {
			Log.e(TAG,
					"Reflection failed while trying to determine background color of view.",
					e);
			mBackgroundStateField = null;
			return;
		}

		try {
			mBackgroundUseColorField = mBackgroundStateField.getType()
					.getDeclaredField(COLOR_DRAWABLE_USE_COLOR_VAR);
			mBackgroundUseColorField.setAccessible(true);
		} catch (Exception e) {
			Log.e(TAG,
					"Reflection failed while trying to determine background color of view.",
					e);
			mBackgroundUseColorField = null;
			return;
		}

	}

	public void setBackgroundColor(int value) {
		if (mView == null) {
			Log.w(TAG, "Wrapped view is null. Cannot set background color.");
			return;
		}
		mView.setBackgroundColor(value);
	}

}
