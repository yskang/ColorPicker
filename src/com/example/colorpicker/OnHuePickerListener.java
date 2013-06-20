package com.example.colorpicker;

public class OnHuePickerListener implements OnColorPickerListener {

	private OnUpdateColorPicker colorPicker;

	public OnHuePickerListener(OnUpdateColorPicker colorPicker){
		this.colorPicker = colorPicker;
	}
	
	@Override
	public void onSelect(float x, float y) {
		colorPicker.updateSVBitmap((int)x, (int)y);
	}
	
}
