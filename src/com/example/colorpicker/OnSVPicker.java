package com.example.colorpicker;

public class OnSVPicker implements OnColorPickerListener {

	private OnUpdateColorPicker colorPicker;

	public OnSVPicker(OnUpdateColorPicker colorPicker){
		this.colorPicker = colorPicker;
	}
	
	@Override
	public void onSelect(float x, float y) {
		colorPicker.updatePreviewBox((int)x, (int)y);
		colorPicker.updateSelectionMark((int)x, (int)y);
	}

}
