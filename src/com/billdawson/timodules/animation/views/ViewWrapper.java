package com.billdawson.timodules.animation.views;

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
	// private static final String COLOR_DRAWABLE_STATE_VAR = "mState";
	// private static final String COLOR_DRAWABLE_USE_COLOR_VAR = "mUseColor";
	private final View mView;

	public ViewWrapper(View v) {
		mView = v;
	}

	public int getBackgroundColor() {
		if (mView == null) {
			return 0;
		}

		Drawable d = mView.getBackground();

		if (d instanceof ColorDrawable) {
			ColorDrawable cd = (ColorDrawable) d;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				return cd.getColor();
			} else {
				return Color.BLUE; // TODO
			}
		}

		return 0;
	}

	public void setBackgroundColor(int value) {
		if (mView == null) {
			Log.w(TAG, "Wrapped view is null. Cannot set background color.");
			return;
		}
		mView.setBackgroundColor(value);
	}

}
