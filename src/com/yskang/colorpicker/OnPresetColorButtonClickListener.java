package com.yskang.colorpicker;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;

public class OnPresetColorButtonClickListener implements OnClickListener{
	private int color;
    private SeekBar listener;
    private HuePicker hueBar;
    private SVPicker svBox;

    public OnPresetColorButtonClickListener(int color, HuePicker hueBar, SVPicker svBox, SeekBar listener){
        this.hueBar = hueBar;
        this.svBox = svBox;
		this.color = color;
        this.listener = listener;
    }
	
	@Override
	public void onClick(View v) {
        int alpha = (color & 0xFF000000) >>> 24;
        listener.setProgress(alpha);

		hueBar.setUpdateInitialColor(this.color);
        svBox.setUpdateInitialColor(hueBar.getBaseColor(), this.color);

	}

}
