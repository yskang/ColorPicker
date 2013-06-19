package com.example.colorpicker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private Button startButton;
	private DisplaySize displaySize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		displaySize = new DisplaySize(this);
		
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout colorPickerView = (RelativeLayout)inflater.inflate(R.layout.color_picker, null);
		
		ImageView hueBar = (ImageView) colorPickerView.findViewById(R.id.HueBar);
		hueBar.setImageBitmap(makeHueBitmap());
		
		ImageView svBox = (ImageView) colorPickerView.findViewById(R.id.SVBox);
		svBox.setImageBitmap(makeSVBitmap(Color.YELLOW));
				
		startButton = (Button) findViewById(R.id.startColorPicker);
		startButton.setOnClickListener(new OnStartButtonListener(this, colorPickerView));
	}

	
	private Bitmap makeHueBitmap() {
		
		int width = (int)(displaySize.getDisplayWidthInPixel()*0.8);
		int height = (int)(displaySize.getDisplayHeightInPixel()*0.05);
		
		Canvas canvas = new Canvas();
		
		Bitmap hueBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		canvas.setBitmap(hueBitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.RED};
		float[] colors_position = {0.0f, 0.17f, 0.34f, 0.51f, 0.68f, 0.85f, 1.0f};
		
		LinearGradient shader = new LinearGradient(0, (float)(height*0.5), width, (float)(height*0.5), colors, colors_position, Shader.TileMode.CLAMP);
		paint.setShader(shader);
		
		canvas.drawRect(0, 0, width, height, paint);
		
		return hueBitmap;
	}
	
	private Bitmap makeSVBitmap(int selectedColor){
		
		int width = (int)(displaySize.getDisplayWidthInPixel()*0.8);
		int height = (int)(displaySize.getDisplayHeightInPixel()*0.3);
		
		Canvas canvas = new Canvas();
		
		Bitmap svBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		canvas.setBitmap(svBitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		Shader shaderSaturation = new LinearGradient(1, 1, width-1, 1, Color.WHITE, selectedColor, TileMode.CLAMP);
		Shader shaderValue = new LinearGradient(1, 1, 1, height - 1, 0, Color.BLACK, TileMode.CLAMP);
		
		ComposeShader shader = new ComposeShader(shaderSaturation, shaderValue, PorterDuff.Mode.DARKEN);
		
		paint.setShader(shader);
		canvas.drawRect(1, 1, width-1, height-1, paint);
		
		return svBitmap;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
