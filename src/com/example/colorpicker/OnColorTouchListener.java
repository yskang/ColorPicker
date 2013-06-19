package com.example.colorpicker;

import com.example.colorpicker.OnColorPickerListener.BitmapType;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnColorTouchListener implements OnTouchListener {

	private OnColorPickerListener colorPicker;
	private BitmapType bitmapType;

	public OnColorTouchListener(OnColorPickerListener colorPicker, BitmapType bitmapType) {
		this.colorPicker = colorPicker;
		this.bitmapType = bitmapType;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			if(bitmapType == BitmapType.HUE){
				colorPicker.onHueSelect(event.getX(), event.getY());
			}else{
				colorPicker.onSVSelect(event.getX(), event.getY());
			}
			return true;
		default:
			break;
		}
		return false;
	}

}
