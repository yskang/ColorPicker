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
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ColorPicker implements OnUpdateColorPicker{

	private Context context;
	private AlertDialog dialog;
	private RelativeLayout colorPickerView;
	private DisplaySize displaySize;
	private Bitmap hueBitmap;
	private Bitmap svBitmap;
	private ImageView svBox;
	private ImageView hueBar;
	private TextView previewBox;
	private int hue_x;
	private int hue_y;
	private int selectedHue;
	private int sv_x;
	private int sv_y;
	private Bitmap hueBitmapCopy;
	private Paint paintBlack;
	private Paint paintWhite;
	private int hueWidth;
	private int hueHeight;
	private int svWidth;
	private int svHeight;
	private final static float WIDTH_RATIO = 0.8f;
	private final static float HUE_HEIGHT_RATIO = 0.05f;
	private final static float SV_HEIGHT_RATIO = 0.6f;
	private Paint svPaint;
	private Canvas svCanvas;
	private Shader shaderValue;
	
	public ColorPicker(Context context, int initialColor) {
		this.context = context;

		displaySize = new DisplaySize(context);
		makeView(context);
		getInitialColorPosition(initialColor);
		initPaints();
		setViews();
		updateHueBar(hue_x, hue_y);
		makeDialog();
	}
	
	private void initPaints() {
		paintBlack = new Paint();
		paintWhite = new Paint();
		svPaint = new Paint();

		paintBlack.setColor(Color.BLACK);
		paintBlack.setAntiAlias(true);
		paintBlack.setStrokeWidth(2);
		paintBlack.setStyle(Paint.Style.STROKE);
		
		paintWhite.setColor(Color.WHITE);
		paintWhite.setAntiAlias(true);
		paintWhite.setStrokeWidth(1);
		paintWhite.setStyle(Paint.Style.STROKE);

		svPaint.setAntiAlias(true);
		
		svCanvas = new Canvas();

		svBitmap = Bitmap.createBitmap(svWidth, svHeight,
				Bitmap.Config.ARGB_8888);
		svCanvas.setBitmap(svBitmap);
		
		shaderValue = new LinearGradient(1, 1, 1, svHeight, 0,
				Color.BLACK, TileMode.CLAMP);

	}

	private void getInitialColorPosition(int color) {
		float[] hsv = new float[3];
		Color.colorToHSV(color, hsv);
		selectedHue = color;
		
		hueWidth = (int)(displaySize.getDisplayWidthInPixel()*WIDTH_RATIO);
		hueHeight = (int)(displaySize.getDisplayHeightInPixel() * HUE_HEIGHT_RATIO);
		svWidth = hueWidth;
		svHeight = (int)(displaySize.getDisplayWidthInPixel()*SV_HEIGHT_RATIO);
		
		hue_x = (int)(hsv[0]*(hueWidth-1)/360);
		hue_y = (int)(0.5 * (hueHeight-1));

		sv_x = (int)(hsv[1] * (svWidth-1));
		sv_y = (int)((svHeight-1) - hsv[2] * (svHeight-1));
	}

	private void setViews() {
		hueBitmap = makeHueBitmap();
		
		hueBar = (ImageView) colorPickerView.findViewById(R.id.HueBar);
		hueBar.setImageBitmap(hueBitmap);
		
		hueBar.getLayoutParams().height = hueBitmap.getHeight();
		hueBar.getLayoutParams().width = hueBitmap.getWidth();

		hueBar.setOnTouchListener(new OnColorTouch(new OnHuePicker(this)));

		svBox = (ImageView) colorPickerView.findViewById(R.id.SVBox);
		makeSVBitmap(selectedHue);
		svBox.setImageBitmap(svBitmap);
		
		svBox.getLayoutParams().height = svBitmap.getHeight();
		svBox.getLayoutParams().width = svBitmap.getWidth();
		
		svBox.setOnTouchListener(new OnColorTouch(new OnSVPicker(this)));
		
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
		Canvas canvas = new Canvas();

		hueBitmap = Bitmap.createBitmap(hueWidth, hueHeight,
				Bitmap.Config.ARGB_8888);
		canvas.setBitmap(hueBitmap);

		Paint paint = new Paint();
		paint.setAntiAlias(true);

		int[] colors = { Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN,
				Color.BLUE, Color.MAGENTA, Color.RED };
		float[] colors_position = { 0.0f, 0.17f, 0.34f, 0.51f, 0.68f, 0.85f,
				1.0f };

		LinearGradient shader = new LinearGradient(0, (float) (hueHeight * 0.5),
				hueWidth, (float) (hueHeight * 0.5), colors, colors_position,
				Shader.TileMode.CLAMP);
		paint.setShader(shader);

		canvas.drawRect(0, 0, hueWidth, hueHeight, paint);

		return hueBitmap;
	}

	private void makeSVBitmap(int selectedColor) {
		Shader shaderSaturation = new LinearGradient(1, 1, svWidth, 1,
				Color.WHITE, selectedColor, TileMode.CLAMP);

		ComposeShader shader = new ComposeShader(shaderSaturation, shaderValue,
				PorterDuff.Mode.DARKEN);

		svPaint.setShader(shader);
		svCanvas.drawRect(0, 0, svWidth, svHeight, svPaint);
	}

	private void updatePreviewBox(int color) {
		previewBox.setBackgroundColor(color);
	}

	private int getSelectedColor() {
		return svBitmap.getPixel(sv_x, sv_y);
	}

	private void setSVSelectedPosition(int x, int y) {
		sv_x = x;
		sv_y = y;
	}

	public int getHueColor(){
		return hueBitmap.getPixel(hue_x, hue_y);
	}
	
	private boolean checkValidate(int x, int y, Bitmap bitmap) {
		if(x >= 0 && x <= bitmap.getWidth()-1 && y >= 0 && y <= bitmap.getHeight()-1){
			return true;
		}
		return false;
	}

	
	@Override
	public void updateHueBar(int x, int y) {
		if(checkValidate(x, y, hueBitmap)){
			setHueSelectedPosition(x, y);
			selectedHue = getHueColor();
			hueBar.setImageBitmap(drawSelectionBoxOnHueBitmap(hueBitmap));
			updateSVBox(sv_x, sv_y);
		}
	}

	@Override
	public void updateSVBox(int x, int y) {
		if(checkValidate(x, y, this.svBitmap)){
			int selectedColor;
			setSVSelectedPosition(x, y);
			makeSVBitmap(selectedHue);
			selectedColor = getSelectedColor();
			svBox.setImageBitmap(drawSelectionBoxOnSVBitmap(svBitmap));
			updatePreviewBox(selectedColor);
		}
	}
	
	private Bitmap drawSelectionBoxOnHueBitmap(Bitmap hueBitmap) {
		hueBitmapCopy = Bitmap.createBitmap(hueBitmap);
		Canvas hueCanvas = new Canvas(hueBitmapCopy);
		
		RectF rectBlack = new RectF((int)(hue_x-5), 0.f, (int)(hue_x+5), (int)hueBitmap.getHeight());
		RectF rectWhite = new RectF((int)(hue_x-4), 1.f, (int)(hue_x+6), (int)hueBitmap.getHeight());
		
		hueCanvas.drawRoundRect(rectWhite, 10, 10, paintWhite);
		hueCanvas.drawRoundRect(rectBlack, 10, 10, paintBlack);

		return hueBitmapCopy;
	}

	private Bitmap drawSelectionBoxOnSVBitmap(Bitmap svBitmap) {
		Canvas canvas = new Canvas(svBitmap);

		canvas.drawCircle(sv_x, sv_y, 10, paintBlack);
		canvas.drawCircle(sv_x, sv_y, 9, paintWhite);
		canvas.drawCircle(sv_x, sv_y, 12, paintWhite);
		return svBitmap;
	}
	
	private void setHueSelectedPosition(int x, int y) {
		hue_x = x;
		hue_y = y;
	}

}
