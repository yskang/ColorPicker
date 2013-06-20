package com.example.colorpicker.test;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.example.colorpicker.OnHuePickerListener;
import com.example.colorpicker.OnUpdateColorPicker;

import junit.framework.TestCase;

public class OnHuePickerListenerUnitTest extends TestCase {

	final static float x = 10;
	final static float y = 10;
	
	Mockery context = new Mockery();
	
	OnUpdateColorPicker colorPicker = context.mock(OnUpdateColorPicker.class);
	OnHuePickerListener onHuePickerListener = new OnHuePickerListener(colorPicker);
		
	public void testDrawFuntionSholdCalledWhenColorIsSelected(){
		context.checking(new Expectations(){{
			atLeast(1).of(colorPicker).updateSVBitmap((int)x, (int)y);
			atLeast(1).of(colorPicker).drawHueSeletionBar((int)x, (int)y);
		}});
		
		onHuePickerListener.onSelect(x, y);
		
		context.assertIsSatisfied();
	}

}
