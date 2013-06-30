package com.yskang.colorpicker;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DisplaySize {
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	
	public DisplaySize(Context context){
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		display.getMetrics(displayMetrics);
	}
	
	public int getWidgetWidthInPixel(){
		return getPixel(294);
	}

	public int getWidgetHeightInPixel(){
		return getPixel(72);
	}
	
	public int getDisplayWidthInPixel(){
		return displayMetrics.widthPixels;
	}

	public int getDisplayHeightInPixel(){
		return displayMetrics.heightPixels;
	}

	public int getPixel(int dp) {
		int pixel;
		pixel = (int) ((float)dp * displayMetrics.densityDpi / 160f);
		return pixel;
	}
}
