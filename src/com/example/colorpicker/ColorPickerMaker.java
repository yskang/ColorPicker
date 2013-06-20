package com.example.colorpicker;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ColorPickerMaker implements OnUpdateColorPicker{

	private Context context;
	private AlertDialog dialog;
	private RelativeLayout colorPickerView;
	private DisplaySize displaySize;
	private Bitmap hueBitmap;
	private ImageView svBox;
	private Bitmap svBitmap;
	private TextView previewBox;

	public ColorPickerMaker(Context context) {
		this.context = context;
		displaySize = new DisplaySize(context);
		makeView(context);
		makeBitmapImage();
		makeDialog();
	}
	
	private void makeBitmapImage() {
		ImageView hueBar = (ImageView) colorPickerView.findViewById(R.id.HueBar);
		hueBar.setImageBitmap(makeHueBitmap());
		hueBar.setOnTouchListener(new OnColorTouchListener(new OnHuePickerListener(this)));

		svBox = (ImageView) colorPickerView.findViewById(R.id.SVBox);
		svBox.setImageBitmap(makeSVBitmap(Color.YELLOW));
		svBox.setOnTouchListener(new OnColorTouchListener(new OnSVPickerListener(this)));
		
		previewBox = (TextView) colorPickerView.findViewById(R.id.previewBox);
	}

	private void makeView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		colorPickerView = (RelativeLayout) inflater.inflate(
				R.layout.color_picker, null);
	}

	private void makeDialog() {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.dialogTitle);

		builder.setPositiveButton(R.string.positive,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

		builder.setNegativeButton(R.string.negative,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

		builder.setView(colorPickerView);

		dialog = builder.create();
	}

	public AlertDialog getDialog() {
		return dialog;
	}

	private Bitmap makeHueBitmap() {
		int width = (int) (displaySize.getDisplayWidthInPixel() * 0.8);
		int height = (int) (displaySize.getDisplayHeightInPixel() * 0.05);

		Canvas canvas = new Canvas();

		hueBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		canvas.setBitmap(hueBitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);

		int[] colors = { Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN,
				Color.BLUE, Color.MAGENTA, Color.RED };
		float[] colors_position = { 0.0f, 0.17f, 0.34f, 0.51f, 0.68f, 0.85f,
				1.0f };

		LinearGradient shader = new LinearGradient(0, (float) (height * 0.5),
				width, (float) (height * 0.5), colors, colors_position,
				Shader.TileMode.CLAMP);
		paint.setShader(shader);

		canvas.drawRect(0, 0, width, height, paint);

		return hueBitmap;
	}

	private Bitmap makeSVBitmap(int selectedColor) {

		int width = (int) (displaySize.getDisplayWidthInPixel() * 0.8);
		int height = (int) (displaySize.getDisplayHeightInPixel() * 0.3);

		Canvas canvas = new Canvas();

		svBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		canvas.setBitmap(svBitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);

		Shader shaderSaturation = new LinearGradient(1, 1, width - 1, 1,
				Color.WHITE, selectedColor, TileMode.CLAMP);
		Shader shaderValue = new LinearGradient(1, 1, 1, height - 1, 0,
				Color.BLACK, TileMode.CLAMP);

		ComposeShader shader = new ComposeShader(shaderSaturation, shaderValue,
				PorterDuff.Mode.DARKEN);

		paint.setShader(shader);
		canvas.drawRect(1, 1, width - 1, height - 1, paint);

		return svBitmap;
	}

	@Override
	public void updatePreviewBox(int x, int y) {
		if(checkValidate(x, y, svBitmap)){
			previewBox.setBackgroundColor(svBitmap.getPixel(x, y));
		}
	}

	@Override
	public void updateSVBitmap(int x, int y) {
		if(checkValidate(x, y, hueBitmap)){
			svBox.setImageBitmap(makeSVBitmap(hueBitmap.getPixel(x, y)));
		}
	}
	
	private boolean checkValidate(int x, int y, Bitmap bitmap) {
		if(x > 0 && x < bitmap.getWidth() && y > 0 && y < bitmap.getHeight()){
			return true;
		}
		
		return false;
	}

}
