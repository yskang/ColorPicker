package com.example.colorpicker.test;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.SeekBar;

import com.jayway.android.robotium.solo.Solo;
import com.yskang.colorpicker.GWLatticeBackgroundImageView;
import com.yskang.colorpicker.example.MainActivity;
import com.yskang.colorpicker.example.R;

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
		assertEquals("Failed to open dialog", solo.getString(com.yskang.colorpicker.R.string.previewTitle), solo.getText(0).getText());
	}
	
	public void testIsTheHueBar(){
		solo.clickOnButton(0);
		assertEquals(R.id.HueBar, solo.getView(R.id.HueBar).getId());
	}

	public void testSetInitialColor_1(){
		solo.clickOnButton(0);
        GWLatticeBackgroundImageView previewBoxView = (GWLatticeBackgroundImageView) solo.getView(R.id.previewBox);
		assertEquals(Color.rgb(55, 128, 128), previewBoxView.getSettingColor());
	}
	
	public void testSetInitialColor_2(){
		solo.clickOnButton(1);
        GWLatticeBackgroundImageView previewBoxView = (GWLatticeBackgroundImageView) solo.getView(R.id.previewBox);
		assertEquals(Color.MAGENTA, previewBoxView.getSettingColor());
	}

	public void testSelectedColorShouldUpdateButtonColor() throws Exception {
		solo.clickOnButton(1);
		solo.clickOnView(solo.getView(R.id.SVBox));

        GWLatticeBackgroundImageView previewBoxView = (GWLatticeBackgroundImageView) solo.getView(R.id.previewBox);
        int selectedColor = previewBoxView.getSettingColor();

		solo.clickOnButton(1);

        ColorDrawable startColorPickerButton = (ColorDrawable) solo.getView(R.id.startColorPicker_2).getBackground();
		assertEquals(selectedColor, startColorPickerButton.getColor());
	}
	
	public void testMainActivityCanInitialPresetColors() throws Exception {
		solo.clickOnButton(0);

        GWLatticeBackgroundImageView view1 = (GWLatticeBackgroundImageView) solo.getView(R.id.presetButton_1);
        GWLatticeBackgroundImageView view2 = (GWLatticeBackgroundImageView) solo.getView(R.id.presetButton_2);
        GWLatticeBackgroundImageView view3 = (GWLatticeBackgroundImageView) solo.getView(R.id.presetButton_3);
        GWLatticeBackgroundImageView view4 = (GWLatticeBackgroundImageView) solo.getView(R.id.presetButton_4);
        GWLatticeBackgroundImageView view5 = (GWLatticeBackgroundImageView) solo.getView(R.id.presetButton_5);

		assertEquals(Color.BLUE, view1.getSettingColor());
		assertEquals(Color.CYAN, view2.getSettingColor());
		assertEquals(Color.GREEN, view3.getSettingColor());
		assertEquals(Color.RED, view4.getSettingColor());
		assertEquals(Color.RED, view5.getSettingColor());
	}
	
	public void testSelectedColorShouldUpdatedByPresetButton() throws Exception {
		solo.clickOnButton(0);
		solo.clickOnImageButton(3);

        GWLatticeBackgroundImageView previewBoxView = (GWLatticeBackgroundImageView) solo.getView(R.id.previewBox);
		
		assertEquals(Color.RED, previewBoxView.getSettingColor());
	}

    public void testAlphaSetting() throws Exception {
        solo.clickOnButton(0);
        solo.clickOnImageButton(3);
        solo.setProgressBar(0,50);

        GWLatticeBackgroundImageView previewBoxView = (GWLatticeBackgroundImageView) solo.getView(R.id.previewBox);
        previewBoxView.getSettingColor();

        assertEquals(Color.argb(50, 255, 0, 0), previewBoxView.getSettingColor());
    }


    public void testSelectedColorTpValueShouldUpdatedByPresetButton() throws Exception {
        solo.clickOnButton(0);

        solo.setProgressBar(0, 30);

        solo.clickOnImageButton(3);

        GWLatticeBackgroundImageView previewBox = (GWLatticeBackgroundImageView) solo.getView(R.id.previewBox);

        assertEquals(Color.argb(255, 255, 0, 0), previewBox.getSettingColor());
        assertEquals(255, ((SeekBar) solo.getView(R.id.alphaSeekBar)).getProgress());
    }
}
