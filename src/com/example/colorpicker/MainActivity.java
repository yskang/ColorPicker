package com.example.colorpicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends Activity{

	private Button startButton;
	private ColorPicker colorPickerMaker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startButton = (Button) findViewById(R.id.startColorPicker);

		colorPickerMaker = new ColorPicker(this);
		startButton.setOnClickListener(new OnStartButton(colorPickerMaker
				.getDialog()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
