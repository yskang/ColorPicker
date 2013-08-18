package com.yskang.colorpicker;

import java.util.ArrayList;

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
import android.view.View;
import android.widget.Button;
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
    private final static float HEIGHT_RATIO = 0.4f;
    private final static float HUE_WIDTH_RATIO = 0.2f;
    private final static float SV_WIDTH_RATIO = 0.6f;
	private Paint svPaint;
	private Canvas svCanvas;
	private Shader shaderValue;
	private int selectedColor;
	private View view;
	private Button presetColorButton1;
	private Button presetColorButton2;
	private Button presetColorButton3;
	private Button presetColorButton4;
	private ArrayList<Integer> presetColors;
	private OnColorSelectedListener onColorPickerSelectedListener;
    private Paint paintBlackForHueMarker;
    private Paint paintWhiteForHueMarker;
    private Paint paintSelectedHueColorForHueMarker;
    private float hueMarkerR;

    public ColorPicker(Context context, int initialColor, OnColorSelectedListener onColorPickerSelectedListener, ArrayList<Integer> presetColors){
		this.context = context;
		this.onColorPickerSelectedListener = onColorPickerSelectedListener;
		this.presetColors = presetColors;

		displaySize = new DisplaySize(context);
		initViewSize();
		makeView(context);
		initPaints();
		setViews();
		updatePresetColor(initialColor);
		initPresetColors(presetColors);
		makeDialog();
		initPresetColorButtons();
	}
	
	private void initPresetColors(ArrayList<Integer> presetColors) {
		presetColorButton1 = (Button) colorPickerView.findViewById(R.id.presetButton_1);
		presetColorButton2 = (Button) colorPickerView.findViewById(R.id.presetButton_2);
		presetColorButton3 = (Button) colorPickerView.findViewById(R.id.presetButton_3);
		presetColorButton4 = (Button) colorPickerView.findViewById(R.id.presetButton_4);
		
		presetColorButton1.setBackgroundColor(presetColors.get(0));
		presetColorButton2.setBackgroundColor(presetColors.get(1));
		presetColorButton3.setBackgroundColor(presetColors.get(2));
		presetColorButton4.setBackgroundColor(presetColors.get(3));
	}

	private void initSelectedColor(int initialColor) {
		selectedColor = initialColor;
		updatePreviewBox();
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

        paintBlackForHueMarker = new Paint();
        paintBlackForHueMarker.setColor(Color.BLACK);
        paintBlackForHueMarker.setAntiAlias(true);
        paintBlackForHueMarker.setStyle(Paint.Style.FILL);

        paintWhiteForHueMarker = new Paint();
        paintWhiteForHueMarker.setColor(Color.WHITE);
        paintWhiteForHueMarker.setAntiAlias(true);
        paintWhiteForHueMarker.setStyle(Paint.Style.FILL);

        paintSelectedHueColorForHueMarker = new Paint();
        paintSelectedHueColorForHueMarker.setColor(selectedHue);
        paintSelectedHueColorForHueMarker.setAntiAlias(true);
        paintSelectedHueColorForHueMarker.setStyle(Paint.Style.FILL);

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

        hue_x = (int)(0.5 * (hueWidth-1));
        hue_y = (int)(hsv[0] * (hueHeight-1)/360);

		sv_x = (int)(hsv[1] * (svWidth-1));
		sv_y = (int)((svHeight-1) - hsv[2] * (svHeight-1));
	}

	private void initViewSize() {
		hueWidth = (int)(displaySize.getDisplayWidthInPixel() * HUE_WIDTH_RATIO);
		hueHeight = (int)(displaySize.getDisplayHeightInPixel() * HEIGHT_RATIO);
		svWidth = (int)(displaySize.getDisplayWidthInPixel() * SV_WIDTH_RATIO);
		svHeight = hueHeight;
        hueMarkerR = hueWidth/4;
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

	private void initPresetColorButtons() {
		presetColorButton1.setOnClickListener(new OnPresetColorButtonClickListener(this, presetColors.get(0)));
		presetColorButton2.setOnClickListener(new OnPresetColorButtonClickListener(this, presetColors.get(1)));
		presetColorButton3.setOnClickListener(new OnPresetColorButtonClickListener(this, presetColors.get(2)));
		presetColorButton4.setOnClickListener(new OnPresetColorButtonClickListener(this, presetColors.get(3)));
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
						onClickOk();
					}
				});

		builder.setNegativeButton(R.string.negative,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						onClickCancel();
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

		LinearGradient shader = new LinearGradient(hueWidth * 0.5f, hueMarkerR,
                hueWidth * 0.5f, hueHeight-hueMarkerR, colors, colors_position, Shader.TileMode.CLAMP);
		paint.setShader(shader);

		canvas.drawRect(hueWidth*0.5f + hueWidth*0.1f, hueMarkerR, hueWidth - hueWidth*0.1f, hueHeight-hueMarkerR, paint);

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

	private void updatePreviewBox() {
		previewBox.setBackgroundColor(selectedColor);
	}

	private int getSelectedColor() {
		return svBitmap.getPixel(sv_x, sv_y);
	}

	private void setSVSelectedPosition(int x, int y) {
		sv_x = x;
		sv_y = y;
	}

	public int getHueColor(){
		return hueBitmap.getPixel((int)(hueWidth*0.75f), hue_y);
	}
	
	private boolean checkValidate(int x, int y, float left, float top, float right, float bottom) {
        if(new RectF(left, top, right, bottom).contains(x, y)) return true;
        return false;
	}

	
	@Override
	public void updateHueBar(int x, int y) {
        if(checkValidate(x, y, hueWidth*0.5f + hueWidth*0.1f, hueMarkerR, hueWidth - hueWidth*0.1f, hueHeight-hueMarkerR)){
			setHueSelectedPosition(x, y);
			selectedHue = getHueColor();
			hueBar.setImageBitmap(drawSelectionMarkOnHueBitmap(hueBitmap));
			updateSVBox(sv_x, sv_y);
		}
	}

	@Override
	public void updateSVBox(int x, int y) {
		if(checkValidate(x, y, 0, 0, svWidth, svHeight)){
			setSVSelectedPosition(x, y);
			makeSVBitmap(selectedHue);
			selectedColor = getSelectedColor();
			svBox.setImageBitmap(drawSelectionBoxOnSVBitmap(svBitmap));
			updatePreviewBox();
		}
	}
	
	private Bitmap drawSelectionMarkOnHueBitmap(Bitmap hueBitmap) {
		hueBitmapCopy = Bitmap.createBitmap(hueBitmap);
		Canvas hueCanvas = new Canvas(hueBitmapCopy);

        paintSelectedHueColorForHueMarker.setColor(selectedHue);

        RectF rectFillBlackOuter = new RectF(hueMarkerR +(0.5f* hueMarkerR), hue_y-5, hueBitmap.getWidth(), hue_y+5);
        RectF rectFillWhite = new RectF(hueMarkerR +(0.5f* hueMarkerR), hue_y-4, hueBitmap.getWidth(), hue_y+4);
        RectF rectFillBlackInner = new RectF(hueMarkerR +(0.5f* hueMarkerR), hue_y-3, hueBitmap.getWidth(), hue_y+3);
        RectF rectFillSelectedHue = new RectF(hueMarkerR +(0.5f* hueMarkerR), hue_y-1, hueBitmap.getWidth(), hue_y+1);

        hueCanvas.drawCircle(hueMarkerR, hue_y, hueMarkerR, paintBlackForHueMarker);
        hueCanvas.drawRoundRect(rectFillBlackOuter, 10, 10, paintBlackForHueMarker);
        hueCanvas.drawCircle(hueMarkerR, hue_y, hueMarkerR -2, paintWhiteForHueMarker);
        hueCanvas.drawRoundRect(rectFillWhite, 10, 10, paintWhiteForHueMarker);
        hueCanvas.drawCircle(hueMarkerR, hue_y, hueMarkerR -4, paintBlackForHueMarker);
        hueCanvas.drawRoundRect(rectFillBlackInner, 10, 10, paintBlackForHueMarker);
        hueCanvas.drawRoundRect(rectFillSelectedHue, 10, 10, paintSelectedHueColorForHueMarker);
        hueCanvas.drawCircle(hueMarkerR, hue_y, hueMarkerR -6, paintSelectedHueColorForHueMarker);

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

	public void onClickOk() {
		onColorPickerSelectedListener.onSelected(selectedColor);
	}

    private void onClickCancel() {
        //TODO : add something to do when close color picker popup
    }

    @Override
	public void updatePresetColor(int color) {
		getInitialColorPosition(color);
		updateHueBar(hue_x, hue_y);
		initSelectedColor(color);
	}

}
