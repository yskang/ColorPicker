package com.yskang.colorpicker.example;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import com.yskang.colorpicker.example.R;
import com.yskang.colorpicker.ColorPicker;
import com.yskang.colorpicker.OnColorSelectedListener;
import com.yskang.colorpicker.OnStartButton;

public class MainActivity extends Activity {

	private Button startButton_1;
	private Button startButton_2;
	private ColorPicker colorPicker_1;
	private ColorPicker colorPicker_2;
	private int color_1 = Color.rgb(55, 128, 128);
	private int color_2 = Color.MAGENTA;
	private ArrayList<Integer> presetColors = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		presetColors.add(Color.BLUE);
		presetColors.add(Color.CYAN);
		presetColors.add(Color.GREEN);
		presetColors.add(Color.RED);
		
		
		startButton_1 = (Button) findViewById(R.id.startColorPicker_1);
		startButton_1.setBackgroundColor(color_1);
		OnColorSelectedListener button1ColorSelectedListener = new OnColorSelectedListener() {
			@Override
			public void onSelected(int selectedColor) {
				startButton_1.setBackgroundColor(selectedColor);
			}
		};
		colorPicker_1 = new ColorPicker(this, color_1, button1ColorSelectedListener, presetColors);
		startButton_1.setOnClickListener(new OnStartButton(colorPicker_1
				.getDialog()));
		
		startButton_2 = (Button) findViewById(R.id.startColorPicker_2);
		startButton_2.setBackgroundColor(color_2);
		OnColorSelectedListener button2ColorSelectedListener = new OnColorSelectedListener() {
			
			@Override
			public void onSelected(int selectedColor) {
				startButton_2.setBackgroundColor(selectedColor);
			}
		};
		colorPicker_2 = new ColorPicker(this, color_2, button2ColorSelectedListener, presetColors);
		startButton_2.setOnClickListener(new OnStartButton(colorPicker_2
				.getDialog()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
