package com.example.colorpicker;

public class OnSVPicker implements OnColorPickerListener {

	private OnUpdateColorPicker colorPicker;

	public OnSVPicker(OnUpdateColorPicker colorPicker){
		this.colorPicker = colorPicker;
	}
	
	@Override
	public void onSelect(float x, float y) {
		colorPicker.updateSVBox((int)x, (int)y);
	}

}
