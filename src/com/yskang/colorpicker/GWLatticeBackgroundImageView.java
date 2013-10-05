package com.yskang.colorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class GWLatticeBackgroundImageView extends ImageButton {

    private int viewHeight = 1;
    private int viewWidth = 1;
    private int settingColor = Color.TRANSPARENT;
    private Bitmap imageBitmap;

    public GWLatticeBackgroundImageView(Context context) {
        super(context);
    }

    public GWLatticeBackgroundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GWLatticeBackgroundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
                heightSize = viewHeight;
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
                widthSize = viewWidth;
                break;
            case MeasureSpec.EXACTLY:
                widthSize = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    public void setBackgroundColor(int color) {
        settingColor = color;
        makeImageBitmap();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        if(viewWidth <= 0) viewWidth = 1;
        if(viewHeight <= 0) viewHeight = 1;
        setImageBitmap(makeImageBitmap());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setImageBitmap(imageBitmap);
    }

    public int getSettingColor(){
        return settingColor;
    }

    private Bitmap makeImageBitmap() {
        imageBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(imageBitmap);

        drawGrayWhiteLattice(canvas, 5);

        Paint colorPaint = new Paint();
        colorPaint.setColor(settingColor);
        canvas.drawRect(0, 0, viewWidth, viewHeight, colorPaint);

        return imageBitmap;
    }

    private void drawGrayWhiteLattice(Canvas canvas, int cellSizeDp) {
        DisplaySize displaySize = new DisplaySize(getContext());
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int rectSize = displaySize.getPixel(cellSizeDp);


        Paint grayPaint = new Paint();
        Paint whitePaint = new Paint();
        grayPaint.setColor(Color.LTGRAY);
        whitePaint.setColor(Color.WHITE);

        for(int i=0 ; i<(width/rectSize + 1) ; i++){
            for(int j=0 ; j<(height/rectSize)+1 ; j++){
                Rect rect = new Rect(i*rectSize, j*rectSize, rectSize + i*rectSize, rectSize + j*rectSize);
                if((i+j)%2 == 0){
                    canvas.drawRect(rect, grayPaint);
                }else{
                    canvas.drawRect(rect, whitePaint);
                }
            }
        }
    }
}
