package com.example.colorpicker.test;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.view.MotionEvent;

import com.yskang.colorpicker.OnColorPickerListener;
import com.yskang.colorpicker.OnColorTouch;

public class OnColorTouchListenerUnitTest extends TestCase {

	Mockery context = new Mockery();

	OnColorPickerListener onHuePickerListener = context
			.mock(OnColorPickerListener.class);
	OnColorTouch onColorTouchListener = new OnColorTouch(
			onHuePickerListener);
	final static float x = 10;
	final static float y = 10;

	public void testTheonSelectMethodShouldCalledByTouchEventDown() {

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
	
	public void testTheonSelectMethodShouldCalledByTouchEventUp() {
		
		MotionEvent motionEvent = MotionEvent.obtain(1, 1,
				MotionEvent.ACTION_UP, x, y, 0);
		
		context.checking(new Expectations() {
			{
				atLeast(1).of(onHuePickerListener).onSelect(x, y);
			}
		});
		
		onColorTouchListener.onTouch(null, motionEvent);
		
		context.assertIsSatisfied();
	}
	
	public void testTheonSelectMethodShouldCalledByTouchEventMove() {
		
		MotionEvent motionEvent = MotionEvent.obtain(1, 1,
				MotionEvent.ACTION_MOVE, x, y, 0);
		
		context.checking(new Expectations() {
			{
				atLeast(1).of(onHuePickerListener).onSelect(x, y);
			}
		});
		
		onColorTouchListener.onTouch(null, motionEvent);
		
		context.assertIsSatisfied();
	}

	public void testTheonSelectMethodShouldNotCalledByTouchEvent() {
		
		MotionEvent motionEvent = MotionEvent.obtain(1, 1,
				MotionEvent.ACTION_OUTSIDE, x, y, 0);
		
		context.checking(new Expectations() {
			{
				never(onHuePickerListener).onSelect(x, y);
			}
		});
		
		onColorTouchListener.onTouch(null, motionEvent);
		
		context.assertIsSatisfied();
	}

}
