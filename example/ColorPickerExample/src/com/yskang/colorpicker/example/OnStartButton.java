package com.yskang.colorpicker.example;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class OnStartButton implements OnClickListener {

	private Dialog dialog;

	public OnStartButton(Dialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void onClick(View v) {
		dialog.show();
	}

}
