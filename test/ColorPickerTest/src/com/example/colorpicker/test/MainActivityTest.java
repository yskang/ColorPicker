package com.example.colorpicker.test;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.test.ActivityInstrumentationTestCase2;

import com.yskang.colorpicker.example.MainActivity;
import com.yskang.colorpicker.example.R;
import com.jayway.android.robotium.solo.Solo;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super(MainActivity.class);
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

	public void testSetInitialColor_1(){
		ColorDrawable colorDrawable;
		solo.clickOnButton(0);
		colorDrawable = (ColorDrawable)solo.getView(R.id.previewBox).getBackground();
		assertEquals(Color.rgb(55, 128, 128), colorDrawable.getColor());
	}
	
	public void testSetInitialColor_2(){
		ColorDrawable colorDrawable;
		solo.clickOnButton(1);
		colorDrawable = (ColorDrawable)solo.getView(R.id.previewBox).getBackground();
		assertEquals(Color.MAGENTA, colorDrawable.getColor());
	}

	public void testSelectedColorShouldUpdateButtonColor() throws Exception {
		solo.clickOnButton(1);
		solo.clickOnView(solo.getView(R.id.SVBox));
		ColorDrawable colorDrawable = (ColorDrawable)solo.getView(R.id.previewBox).getBackground();
		int selectedColor = colorDrawable.getColor();
		
		solo.clickOnButton(5);
		colorDrawable = (ColorDrawable) solo.getView(R.id.startColorPicker_2).getBackground();
		assertEquals(selectedColor, colorDrawable.getColor());
	}
	
	public void testMainActivityCanInitialPresetColors() throws Exception {
		ColorDrawable presetButtonDrawable1;
		ColorDrawable presetButtonDrawable2;
		ColorDrawable presetButtonDrawable3;
		ColorDrawable presetButtonDrawable4;
		
		solo.clickOnButton(0);
		
		presetButtonDrawable1 = (ColorDrawable) solo.getView(R.id.presetButton_1).getBackground();
		presetButtonDrawable2 = (ColorDrawable) solo.getView(R.id.presetButton_2).getBackground();
		presetButtonDrawable3 = (ColorDrawable) solo.getView(R.id.presetButton_3).getBackground();
		presetButtonDrawable4 = (ColorDrawable) solo.getView(R.id.presetButton_4).getBackground();
		
		assertEquals(Color.BLUE, presetButtonDrawable1.getColor());
		assertEquals(Color.CYAN, presetButtonDrawable2.getColor());
		assertEquals(Color.GREEN, presetButtonDrawable3.getColor());
		assertEquals(Color.RED, presetButtonDrawable4.getColor());
	}
	
	public void testSelectedColorShouldUpdatedByPresetButton() throws Exception {
		solo.clickOnButton(0);
		solo.clickOnButton(3);

		ColorDrawable previewViewDrawable;
		previewViewDrawable = (ColorDrawable) solo.getView(R.id.previewBox).getBackground();
		
		assertEquals(Color.RED, previewViewDrawable.getColor());
	}

    public void testAlhpaSetting() throws Exception {
        solo.clickOnButton(0);
        solo.clickOnButton(3);

        solo.setProgressBar(0,50);

        ColorDrawable previewViewDrawable;
        previewViewDrawable = (ColorDrawable) solo.getView(R.id.previewBox).getBackground();

        assertEquals(Color.argb(50,255,0,0),previewViewDrawable.getColor());
    }
}
