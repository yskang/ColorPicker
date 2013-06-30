package com.yskang.colorpicker;

public class OnHuePicker implements OnColorPickerListener {

	private OnUpdateColorPicker colorPicker;

	public OnHuePicker(OnUpdateColorPicker colorPicker){
		this.colorPicker = colorPicker;
	}
	
	@Override
	public void onSelect(float x, float y) {
		colorPicker.updateHueBar((int)x, (int)y);
	}
	
}
