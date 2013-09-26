package com.example.colorpicker.test;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.graphics.Color;
import android.test.ActivityInstrumentationTestCase2;

import com.yskang.colorpicker.ColorPicker;
import com.yskang.colorpicker.OnColorSelectedListener;
import com.yskang.colorpicker.example.MainActivity;

public class OnColorSelectedListenerTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	public OnColorSelectedListenerTest() {
		super(MainActivity.class);
	}

	Mockery context = new Mockery();
	OnColorSelectedListener onColorSelectedListener = context
			.mock(OnColorSelectedListener.class);
	private ColorPicker colorPicker;
	private ArrayList<Integer> presetColors = new ArrayList<Integer>();

	@Override
	protected void setUp() throws Exception {
		presetColors.add(Color.BLUE);
		presetColors.add(Color.BLUE);
		presetColors.add(Color.BLUE);
		presetColors.add(Color.BLUE);
		presetColors.add(Color.BLUE);
		colorPicker = new ColorPicker(getActivity(), Color.WHITE,
				onColorSelectedListener, presetColors);
		super.setUp();
	}

	public void testOnColorSelectedListener() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(onColorSelectedListener).onSelected(-1);
			}
		});

		colorPicker.onClickOk();
		context.assertIsSatisfied();
	}

}
