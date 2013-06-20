package com.example.colorpicker;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnColorTouchListener implements OnTouchListener {

	private OnColorPickerListener colorPicker;

	public OnColorTouchListener(OnColorPickerListener colorPicker) {
		this.colorPicker = colorPicker;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
				colorPicker.onSelect(event.getX(), event.getY());
			return true;
		default:
			break;
		}
		return false;
	}

}
