package com.example.colorpicker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class OnStartButtonListener implements OnClickListener {

	private Context context;
	private AlertDialog dialog;
	private AlertDialog.Builder builder;

	public OnStartButtonListener(Context context, View customView) {
		this.context = context;
		builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.dialogTitle);
		
		builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builder.setView(customView);

		Paint paint = new Paint();
		
		dialog = builder.create();
		
		
	}
	
	@Override
	public void onClick(View v) {
		dialog.show();
	}

}
