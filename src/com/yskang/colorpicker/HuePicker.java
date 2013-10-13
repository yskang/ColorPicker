package com.yskang.colorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

public class HuePicker extends ImageView{

    private int panelHeight = 1;
    private int panelWidth = 1;
    private float positionMarkerR;
    private DisplaySize displaySize;
    private int initialColor = Color.GREEN;
    private Bitmap hueBitmapCopy;
    private Bitmap hueBitmap;
    private int selectedHue;
    private LinearGradient shaderValue;
    private float hue_y;
    private float hue_x;
    private ArrayList<OnHueChangeListener> listeners = new ArrayList<OnHueChangeListener>();
    private ColorPickerPaint paintShop = ColorPickerPaint.getInstance();

    public HuePicker(Context context) {
        super(context);
    }

    public HuePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplaySize displaySize = new DisplaySize(context);
    }

    public HuePicker(Context context, AttributeSet attrs, int defStyle) {
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

        initPaints();
        initPosition();
        makeHueBitmap();
        updateSelectedColor((int)hue_x, (int)hue_y);
        drawSelectionMarkOnHueBitmap(hueBitmap);
        setImageBitmap(hueBitmapCopy);
        notifyToListeners(selectedHue, false);
    }

    private void initPosition() {
        float[] hsv = new float[3];
        Color.colorToHSV(initialColor, hsv);

        hue_x = (int)(0.7 * (panelWidth-1));
        hue_y = (int)(hsv[0] * (panelHeight-2*positionMarkerR)/360 + positionMarkerR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setImageBitmap(hueBitmapCopy);
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

                if(x < positionMarkerR*2 + displaySize.getPixel(5)) x = (int)(positionMarkerR*2 + displaySize.getPixel(5));
                if(x > panelWidth-displaySize.getPixel(5)) x = (int)(panelWidth-displaySize.getPixel(5)-1);
                if(y < (positionMarkerR)) y = (int) positionMarkerR;
                if(y > panelHeight - (positionMarkerR)) y = (int)(panelHeight - positionMarkerR -1);

                updateSelectedColor(x, y);
                notifyToListeners(drawSelectionMarkOnHueBitmap(x, y), true);
                break;
            }

            default:
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private void updateSelectedColor(int x, int y) {
        selectedHue = hueBitmap.getPixel(x, y);
    }

    private int drawSelectionMarkOnHueBitmap(int x, int y) {
        hue_x = x;
        hue_y = y;
        return drawSelectionMarkOnHueBitmap(hueBitmap);
    }

    private Bitmap makeHueBitmap() {
        Canvas canvas = new Canvas();

        hueBitmap = Bitmap.createBitmap(panelWidth, panelHeight,
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(hueBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int[] colors = { Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN,
                Color.BLUE, Color.MAGENTA, Color.RED };
        float[] colors_position = { 0.0f, 0.17f, 0.34f, 0.51f, 0.68f, 0.85f,
                1.0f };

        LinearGradient shader = new LinearGradient(panelWidth * 0.5f, positionMarkerR,
                panelWidth * 0.5f, panelHeight- positionMarkerR, colors, colors_position, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        canvas.drawRect(positionMarkerR*2 + displaySize.getPixel(5), positionMarkerR, panelWidth-displaySize.getPixel(5), panelHeight - positionMarkerR, paint);

        return hueBitmap;
    }

    private int drawSelectionMarkOnHueBitmap(Bitmap hueBitmap) {
        hueBitmapCopy = Bitmap.createBitmap(hueBitmap);
        Canvas hueCanvas = new Canvas(hueBitmapCopy);

        paintShop.paintSelectedHueColorForHueMarker.setColor(selectedHue);

        RectF rectFillBlackOuter = new RectF(positionMarkerR +(0.5f* positionMarkerR), hue_y-5, hueBitmap.getWidth(), hue_y+5);
        RectF rectFillWhite = new RectF(positionMarkerR +(0.5f* positionMarkerR), hue_y-4, hueBitmap.getWidth(), hue_y+4);
        RectF rectFillBlackInner = new RectF(positionMarkerR +(0.5f* positionMarkerR), hue_y-3, hueBitmap.getWidth(), hue_y+3);
        RectF rectFillSelectedHue = new RectF(positionMarkerR +(0.5f* positionMarkerR), hue_y-1, hueBitmap.getWidth(), hue_y+1);

        hueCanvas.drawCircle(positionMarkerR, hue_y, positionMarkerR, paintShop.paintBlackFill);
        hueCanvas.drawRoundRect(rectFillBlackOuter, 10, 10, paintShop.paintBlackFill);
        hueCanvas.drawCircle(positionMarkerR, hue_y, positionMarkerR -2, paintShop.paintWhiteFill);
        hueCanvas.drawRoundRect(rectFillWhite, 10, 10, paintShop.paintWhiteFill);
        hueCanvas.drawCircle(positionMarkerR, hue_y, positionMarkerR -4, paintShop.paintBlackFill);
        hueCanvas.drawRoundRect(rectFillBlackInner, 10, 10, paintShop.paintBlackFill);
        hueCanvas.drawRoundRect(rectFillSelectedHue, 10, 10, paintShop.paintSelectedHueColorForHueMarker);
        hueCanvas.drawCircle(positionMarkerR, hue_y, positionMarkerR -6, paintShop.paintSelectedHueColorForHueMarker);

        return selectedHue;
    }

    private void initPaints() {
        displaySize = new DisplaySize(getContext());
        positionMarkerR = displaySize.getPixel(15);
        shaderValue = new LinearGradient(0, positionMarkerR, 0, panelHeight - positionMarkerR, Color.TRANSPARENT,
                Color.BLACK, Shader.TileMode.CLAMP);
    }

    private void notifyToListeners(int color, boolean byUser){
        for(OnHueChangeListener listener : listeners){
            listener.onHueChanged(color, byUser);
        }
    }

    public void setOnHueChangeListener(OnHueChangeListener listener) {
        listeners.add(listener);
    }

    public void setInitialColor(int initialColor) {
        this.initialColor = initialColor;
    }

    public int getBaseColor() {
        return selectedHue;
    }

    public void setUpdateInitialColor(int initialColor) {
        this.initialColor = initialColor;

        initPosition();
        makeHueBitmap();
        updateSelectedColor((int)hue_x, (int)hue_y);
        drawSelectionMarkOnHueBitmap(hueBitmap);
        setImageBitmap(hueBitmapCopy);
        notifyToListeners(selectedHue, false);
    }
}
