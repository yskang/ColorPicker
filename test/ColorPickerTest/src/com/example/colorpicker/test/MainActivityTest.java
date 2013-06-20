package com.example.colorpicker.test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.colorpicker.MainActivity;
import com.example.colorpicker.R;
import com.jayway.android.robotium.solo.Solo;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	private Solo solo;
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testMainActivity() {
		solo.assertCurrentActivity("Check on first activity", MainActivity.class);
	}
	
	public void testTextIsCorrect(){
		assertNotNull(solo.getText("Click button to start ColorPicker dialog"));
	}
	
	public void testButtonTextIsCorrect(){
		assertNotNull(solo.getButton("Start ColorPicker"));
	}
	
	public void testStartButtonCanStartNewDialog(){
		solo.clickOnButton(0);
		assertEquals("Failed to open dialog","ColorPicker", solo.getText(0).getText());
	}
	
	public void testIsTheHueBar(){
		solo.clickOnButton(0);
		assertEquals(R.id.HueBar, solo.getView(R.id.HueBar).getId());
	}

}
