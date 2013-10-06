package com.example.colorpicker.test;

import org.jmock.Expectations;
import org.jmock.Mockery;

import junit.framework.TestCase;

public class OnSVPickerListenerUnitTest extends TestCase {

	Mockery context = new Mockery();
	OnUpdateColorPicker colorPicker = context.mock(OnUpdateColorPicker.class);
	OnSVPicker onSVPickerListener = new OnSVPicker(colorPicker);
	
	private final static float x = 10;
	private final static float y = 10;
	
	public void testPreviewColorBoxShouldUpdateAndMarkDisplayedWhenUserTouchSVColorBox() throws Exception {
		context.checking(new Expectations(){{
			atLeast(1).of(colorPicker).updateSVBox((int)x, (int)y);
		}});
		
		onSVPickerListener.onSelect(x, y);
		
		context.assertIsSatisfied();
	}
}
