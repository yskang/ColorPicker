package com.example.colorpicker;

public class OnSVPickerListener implements OnColorPickerListener {

	private OnUpdateColorPicker colorPicker;

	public OnSVPickerListener(OnUpdateColorPicker colorPicker){
		this.colorPicker = colorPicker;
	}
	
	@Override
	public void onSelect(float x, float y) {
		colorPicker.updatePreviewBox((int)x, (int)y);
		colorPicker.updateSelectionMark((int)x, (int)y);
	}

}
