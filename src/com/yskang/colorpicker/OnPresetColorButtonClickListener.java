package com.yskang.colorpicker;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;

public class OnPresetColorButtonClickListener implements OnClickListener{
	private int color;
    private SeekBar alphaSeekBarListener;
    private ColorPicker colorPicker;
    private HuePicker hueBar;
    private SVPicker svBox;

    public OnPresetColorButtonClickListener(ColorPicker colorPicker, int color, HuePicker hueBar, SVPicker svBox, SeekBar alphaSeekBarListener){
        this.colorPicker = colorPicker;
        this.hueBar = hueBar;
        this.svBox = svBox;
		this.color = color;
        this.alphaSeekBarListener = alphaSeekBarListener;
    }
	
	@Override
	public void onClick(View v) {
        int alpha = (color & 0xFF000000) >>> 24;
        alphaSeekBarListener.setProgress(alpha);

        colorPicker.setInitColor(color);

		hueBar.setUpdateInitialColor(this.color);
        svBox.setUpdateInitialColor(hueBar.getBaseColor(), this.color);
	}

}
