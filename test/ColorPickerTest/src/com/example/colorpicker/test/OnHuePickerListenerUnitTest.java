package com.example.colorpicker.test;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.example.colorpicker.OnHuePicker;
import com.example.colorpicker.OnUpdateColorPicker;

import junit.framework.TestCase;

public class OnHuePickerListenerUnitTest extends TestCase {

	final static float x = 10;
	final static float y = 10;
	
	Mockery context = new Mockery();
	
	OnUpdateColorPicker colorPicker = context.mock(OnUpdateColorPicker.class);
	OnHuePicker onHuePickerListener = new OnHuePicker(colorPicker);
		
	public void testDrawFuntionSholdCalledWhenColorIsSelected(){
		context.checking(new Expectations(){{
			atLeast(1).of(colorPicker).updateHueBar((int)x, (int)y);
		}});
		
		onHuePickerListener.onSelect(x, y);
		
		context.assertIsSatisfied();
	}

}
