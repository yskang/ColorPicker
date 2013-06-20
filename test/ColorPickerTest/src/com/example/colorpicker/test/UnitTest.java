package com.example.colorpicker.test;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.view.MotionEvent;

import com.example.colorpicker.OnColorPickerListener;
import com.example.colorpicker.OnColorTouchListener;

public class UnitTest extends TestCase {

	Mockery context = new Mockery();

	OnColorPickerListener onHuePickerListener = context
			.mock(OnColorPickerListener.class);
	OnColorTouchListener onColorTouchListener = new OnColorTouchListener(
			onHuePickerListener);
	final static float x = 10;
	final static float y = 10;

	public void testSelectionBarMustSeenByTouchTheHueBar() {

		MotionEvent motionEvent = MotionEvent.obtain(1, 1,
				MotionEvent.ACTION_DOWN, x, y, 0);

		context.checking(new Expectations() {
			{
				atLeast(1).of(onHuePickerListener).onSelect(x, y);
			}
		});

		onColorTouchListener.onTouch(null, motionEvent);

		context.assertIsSatisfied();
	}

}
