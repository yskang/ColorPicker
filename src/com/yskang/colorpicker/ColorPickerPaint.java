package com.yskang.colorpicker;

import android.graphics.Color;
import android.graphics.Paint;

public class ColorPickerPaint {
    public static ColorPickerPaint instance;

    public Paint paintBlackFill;
    public Paint paintWhiteFill;
    public Paint paintSelectedColorForMarker;
    public Paint paintSelectedHueColorForHueMarker;
    public Paint paintBlack = new Paint();
    public Paint paintWhite = new Paint();

    public static ColorPickerPaint getInstance(){
        if(instance == null){
            instance = new ColorPickerPaint();
        }
        return instance;
    }

    private ColorPickerPaint() {
        paintBlack.setColor(Color.BLACK);
        paintBlack.setAntiAlias(true);
        paintBlack.setStrokeWidth(2);
        paintBlack.setStyle(Paint.Style.STROKE);

        paintWhite.setColor(Color.WHITE);
        paintWhite.setAntiAlias(true);
        paintWhite.setStrokeWidth(1);
        paintWhite.setStyle(Paint.Style.STROKE);

        paintBlackFill = new Paint();
        paintBlackFill.setColor(Color.BLACK);
        paintBlackFill.setAntiAlias(true);
        paintBlackFill.setStyle(Paint.Style.FILL);

        paintWhiteFill = new Paint();
        paintWhiteFill.setColor(Color.WHITE);
        paintWhiteFill.setAntiAlias(true);
        paintWhiteFill.setStyle(Paint.Style.FILL);

        paintSelectedColorForMarker = new Paint();
        paintSelectedColorForMarker.setAntiAlias(true);
        paintSelectedColorForMarker.setStyle(Paint.Style.FILL);

        paintSelectedHueColorForHueMarker = new Paint();
        paintSelectedHueColorForHueMarker.setAntiAlias(true);
        paintSelectedHueColorForHueMarker.setStyle(Paint.Style.FILL);
    }
}
