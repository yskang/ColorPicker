package com.yskang.colorpicker;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.ArrayList;

import static android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorPicker implements OnSeekBarChangeListener, OnSVChangeListener, OnHueChangeListener {

	private Context context;
	private Dialog dialog;
	private RelativeLayout colorPickerView;
	private SVPicker svBox;
	private HuePicker hueBar;
	private GWLatticeBackgroundImageView previewBox;
	private int selectedColor;

    private ArrayList<GWLatticeBackgroundImageView> presetColorButtons = new ArrayList<GWLatticeBackgroundImageView>();

	private OnColorSelectedListener onColorPickerSelectedListener;
    private SeekBar alphaSeekBar;
    private int alpha = 255;

    public ColorPicker(Context context, int initialColor, OnColorSelectedListener onColorPickerSelectedListener, ArrayList<Integer> presetColors){
		this.context = context;
		this.onColorPickerSelectedListener = onColorPickerSelectedListener;
        selectedColor = initialColor;

		makeView(context);
		setViews(initialColor);
		initPresetColors();
        initOldColorBox(initialColor);
		makeDialog();
		initPresetColorButtons(presetColors);
	}

    private void initOldColorBox(int initialColor) {
        colorPickerView.findViewById(R.id.oldColorBox).setBackgroundColor(initialColor);
    }

    private void initPresetColors() {
		presetColorButtons.add((GWLatticeBackgroundImageView) colorPickerView.findViewById(R.id.presetButton_1));
		presetColorButtons.add((GWLatticeBackgroundImageView) colorPickerView.findViewById(R.id.presetButton_2));
		presetColorButtons.add((GWLatticeBackgroundImageView) colorPickerView.findViewById(R.id.presetButton_3));
		presetColorButtons.add((GWLatticeBackgroundImageView) colorPickerView.findViewById(R.id.presetButton_4));
		presetColorButtons.add((GWLatticeBackgroundImageView) colorPickerView.findViewById(R.id.presetButton_5));
	}


	private void setViews(int initialColor) {
		previewBox = (GWLatticeBackgroundImageView) colorPickerView.findViewById(R.id.previewBox);

        hueBar = (HuePicker) colorPickerView.findViewById(R.id.HueBar);
        hueBar.setOnHueChangeListener(this);

		svBox = (SVPicker) colorPickerView.findViewById(R.id.SVBox);
        svBox.setOnSVChangeListener(this);

        alphaSeekBar = (SeekBar) colorPickerView.findViewById(R.id.alphaSeekBar);
        alphaSeekBar.setMax(255);
        alphaSeekBar.setProgress(255);
        alphaSeekBar.setOnSeekBarChangeListener(this);

        hueBar.setInitialColor(initialColor);
        svBox.setInitialColor(initialColor);
        getInitialTPColor(initialColor);
    }

	public void initPresetColorButtons(ArrayList<Integer> presetColors) {
        for(int i = 0 ; i < presetColors.size() ; i++){
            presetColorButtons.get(i).setBackgroundColor(presetColors.get(i));
            presetColorButtons.get(i).setOnClickListener(new OnPresetColorButtonClickListener(this, presetColors.get(i), hueBar, svBox, alphaSeekBar));
        }
	}

	private void makeView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		colorPickerView = (RelativeLayout) inflater.inflate(
				R.layout.color_picker, null);
	}

	private void makeDialog() {
		Builder builder = new AlertDialog.Builder(context);

		builder.setPositiveButton(R.string.positive,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						onClickOk();
					}
				});

		builder.setNegativeButton(R.string.negative,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						onClickCancel();
					}
				});

		builder.setView(colorPickerView);

		dialog = builder.create();
	}

	public Dialog getDialog() {
		return dialog;
	}


	private void updatePreviewBox() {
        selectedColor = (selectedColor & 0x00FFFFFF) | (alpha<<24 );
		previewBox.setBackgroundColor(selectedColor);
	}


	public void onClickOk() {
		onColorPickerSelectedListener.onSelected(selectedColor);
	}

    private void onClickCancel() {
        //TODO : add something to do when close color picker popup
    }

    private void getInitialTPColor(int color) {
        alpha = (color & 0xFF000000) >>> 24;
        alphaSeekBar.setProgress(alpha);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean isFromUser) {
        alpha = i;
        updatePreviewBox();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onSVChanged(int svColor, boolean byUser) {
        if(byUser){
            selectedColor = svColor;
            updatePreviewBox();
        }else{
            updatePreviewBox();
        }

    }

    @Override
    public void onHueChanged(int hueColor, boolean byUser) {
        svBox.setBaseColor(hueColor, byUser);
    }

    public void setInitColor(int color) {
        selectedColor = color;
    }
}
