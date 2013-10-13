package com.yskang.colorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

public class SVPicker extends ImageView{
    private int panelWidth = 1;
    private int panelHeight = 1;
    private DisplaySize displaySize;

    private ArrayList<OnSVChangeListener> listeners = new ArrayList<OnSVChangeListener>();

    private Bitmap svBitmap;
    private Bitmap svBitmapCopy;
    private float selectionMarkerR;
    private int baseColor = Color.GREEN;
    private Shader shaderValue;
    private int sv_x;
    private int sv_y;
    private int selectedColor;

    private ColorPickerPaint paintShop = ColorPickerPaint.getInstance();
    private Paint svPaint = new Paint();
    private int initialColor;

    public SVPicker(Context context) {
        super(context);
    }

    public SVPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplaySize displaySize = new DisplaySize(context);
        initPaints();
    }

    public SVPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = 0;
        int heightSize = 0;

        switch (heightMode){
            case MeasureSpec.UNSPECIFIED:
                heightSize = heightMeasureSpec;
                break;
            case MeasureSpec.AT_MOST:
                heightSize = panelHeight;
                break;
            case MeasureSpec.EXACTLY:
                heightSize = MeasureSpec.getSize(heightMeasureSpec);
                break;
        }

        switch(widthMode) {
            case MeasureSpec.UNSPECIFIED:
                widthSize = widthMeasureSpec;
                break;
            case MeasureSpec.AT_MOST:
                widthSize = panelWidth;
                break;
            case MeasureSpec.EXACTLY:
                widthSize = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        panelWidth = getMeasuredWidth();
        panelHeight = getMeasuredHeight();
        selectionMarkerR = displaySize.getPixel(15);
        initPosition(initialColor);
        makeSVPanelBitmap();
        updateSelectedColor(sv_x, sv_y);
        drawSelectionMarkerOnSVBitmap();
        setImageBitmap(svBitmapCopy);
    }

    private void initPosition(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        sv_x = (int)(hsv[1] * (panelWidth - selectionMarkerR -1));
        sv_y = (int)((panelHeight- selectionMarkerR-1) - hsv[2] * (panelHeight- selectionMarkerR-1) + selectionMarkerR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setImageBitmap(svBitmapCopy);
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            {
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();

                updateSelectedColor(x, y);
                notifyToListeners(drawSelectionMarkerOnSVBitmap(), true);
                break;
            }

            default:
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private void updateSelectedColor(int x, int y) {
        if(x < selectionMarkerR) x = (int)selectionMarkerR;
        if(x > panelWidth - selectionMarkerR) x = (int)(panelWidth - selectionMarkerR -1);
        if(y < (selectionMarkerR)) y = (int)selectionMarkerR;
        if(y > panelHeight - (selectionMarkerR)) y = (int)(panelHeight - selectionMarkerR -1);

        sv_x = x;
        sv_y = y;

        selectedColor = svBitmap.getPixel(x, y);
    }

    private Bitmap makeSVPanelBitmap() {
        Canvas svCanvas = new Canvas();
        svBitmap = Bitmap.createBitmap(panelWidth, panelHeight, Bitmap.Config.ARGB_8888);
        svCanvas.setBitmap(svBitmap);
        Shader shaderSaturation = new LinearGradient(selectionMarkerR, 0, panelWidth-selectionMarkerR, 0,
                Color.WHITE, baseColor, Shader.TileMode.CLAMP);
        ComposeShader shader = new ComposeShader(shaderSaturation, shaderValue, PorterDuff.Mode.DARKEN);
        svPaint.setShader(shader);
        svCanvas.drawRect(selectionMarkerR, selectionMarkerR, panelWidth - selectionMarkerR, panelHeight - selectionMarkerR, svPaint);
        return svBitmap;
    }

    private int drawSelectionMarkerOnSVBitmap() {
        svBitmapCopy = Bitmap.createBitmap(svBitmap);
        Canvas canvas = new Canvas(svBitmapCopy);

        paintShop.paintSelectedColorForMarker.setColor(selectedColor);

        canvas.drawCircle(sv_x, sv_y, selectionMarkerR, paintShop.paintBlackFill);
        canvas.drawCircle(sv_x, sv_y, selectionMarkerR -2, paintShop.paintWhiteFill);
        canvas.drawCircle(sv_x, sv_y, selectionMarkerR -4, paintShop.paintBlackFill);
        canvas.drawCircle(sv_x, sv_y, selectionMarkerR -6, paintShop.paintSelectedColorForMarker);

        return selectedColor;
    }

    private void initPaints() {
        displaySize = new DisplaySize(getContext());
        shaderValue = new LinearGradient(0, selectionMarkerR, 0, panelHeight - selectionMarkerR, Color.TRANSPARENT,
                Color.BLACK, Shader.TileMode.CLAMP);
        svPaint.setAntiAlias(true);
    }

    private void notifyToListeners(int color, boolean byUser){
        for(OnSVChangeListener listener : listeners){
            listener.onSVChanged(color, byUser);
        }
    }

    public void setOnSVChangeListener(OnSVChangeListener listener) {
        listeners.add(listener);
    }

    public void setBaseColor(int baseColor, boolean byUser){
        this.baseColor = baseColor;
        initPaints();
        makeSVPanelBitmap();
        updateSelectedColor(sv_x, sv_y);
        drawSelectionMarkerOnSVBitmap();
        notifyToListeners(selectedColor, byUser);
    }

    public void setUpdateInitialColor(int baseColor, int color){
        this.baseColor = baseColor;
        makeSVPanelBitmap();
        initPosition(color);
        updateSelectedColor(sv_x, sv_y);
        drawSelectionMarkerOnSVBitmap();
        notifyToListeners(selectedColor, false);
    }

    public void setInitialColor(int initialColor) {
        this.initialColor = initialColor;
        this.baseColor = getBaseColor(initialColor);
    }

    private int getBaseColor(int initColor){
        float[] hsv = new float[3];
        Color.colorToHSV(initColor, hsv);

        hsv[1] = 1;
        hsv[2] = 1;

        return Color.HSVToColor(hsv);
    }

}
