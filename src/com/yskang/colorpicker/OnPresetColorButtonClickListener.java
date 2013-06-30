package com.yskang.colorpicker;

import android.view.View;
import android.view.View.OnClickListener;

public class OnPresetColorButtonClickListener implements OnClickListener{
	private OnUpdateColorPicker onUpdateColorPicker;
	private int color;

	public OnPresetColorButtonClickListener(OnUpdateColorPicker onUpdateColorPicker, int color){
		this.onUpdateColorPicker = onUpdateColorPicker;
		this.color = color;
	}
	
	@Override
	public void onClick(View v) {
		onUpdateColorPicker.updatePresetColor(color);
	}

}
