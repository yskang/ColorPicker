package com.example.colorpicker;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

public class OnStartButtonListener implements OnClickListener {

	private AlertDialog dialog;

	public OnStartButtonListener(AlertDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void onClick(View v) {
		dialog.show();
	}

}
