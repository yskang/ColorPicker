package com.example.colorpicker;

public interface OnColorPickerListener {
	enum BitmapType {HUE, SV};
	void onHueSelect(float x, float y);
	void onSVSelect(float x, float y);
}
