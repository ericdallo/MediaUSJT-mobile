package com.mediausjt.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mediausjt.R;
import com.mediausjt.util.MaskedInput;
import com.mediausjt.util.MediaConfig;
import com.mediausjt.util.FragmentUtil;
import com.mediausjt.util.MediaDialog;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogicFragment extends Fragment{

    private static final int GREEN = Color.rgb(19, 168, 0);

    @Bind(R.id.tvTitulo)
    TextView tvTitulo;

    @Bind(R.id.tv_first_grade)
    TextView tvFirstGrade;
    @Bind(R.id.et_first_grade)
    EditText etFirstGrade;

    @Bind(R.id.tv_second_grade)
    TextView tvSecondGrade;
    @Bind(R.id.et_second_grade)
    EditText etSecondGrade;

    @Bind(R.id.et_required)
    EditText etRequired;

    @Bind(R.id.et_average_result)
    EditText etAverageResult;

    @Bind(R.id.bt_save)
    FloatingActionButton btSave;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.logic_frag, container, false);
        ButterKnife.bind(this,rootView);

        prepareFontsAndBackgrounds();

        setMasks();

        btSave.hide();

        return rootView;
    }

    @OnClick(R.id.bt_save)
    public void saveGrade() {
        String requiredValue = etRequired.getText().toString();
        String averageValue = etAverageResult.getText().toString();

        if (!averageValue.equals("")) {
            MediaDialog.showSnack(rootView, R.string.only_minimal, Snackbar.LENGTH_LONG);
        } else if (requiredValue.equals("")) {
            MediaDialog.showSnack(rootView, R.string.calculate_minimal, Snackbar.LENGTH_LONG);
        } else {
            EditText etNewGrade = (EditText) MediaConfig.getActivity().findViewById(R.id.et_new_grade);
            etNewGrade.setText(etRequired.getText().toString());
            etNewGrade.setTextColor(etRequired.getCurrentTextColor());

            cleanNeed();
            etFirstGrade.setText("");
            FragmentUtil.showNewGrade();
        }
    }

    public void calculate() {
        String firstGrade = etFirstGrade.getText().toString();
        float firstValue;

        String secondGrade = etSecondGrade.getText().toString();
        float secondValue;

        float firstWeight = MediaConfig.getPreference("peso1", 0.4f);
        float secondWeight = MediaConfig.getPreference("peso2", 0.6f);
        int configuredAverage = MediaConfig.getPreference("media", 6);

        try {
            if (!firstGrade.isEmpty() && !secondGrade.isEmpty()) {
                cleanNeed();
                firstValue = Float.valueOf(firstGrade);
                secondValue = Float.valueOf(secondGrade);

                Float finalAverage = (firstValue * firstWeight) + (secondValue * secondWeight);

                //TODO Arredondamento
                etAverageResult.setText(finalAverage.toString());
                if (finalAverage < configuredAverage) {
                    changeColor(Color.RED, etAverageResult);
                } else {
                    changeColor(GREEN, etAverageResult);
                }
                btSave.hide();
            } else if (!(firstGrade.equals(""))) {
                cleanAverage();
                firstValue = Float.valueOf(firstGrade);
                Float requiredValue = (configuredAverage - (firstValue * firstWeight)) / secondWeight; //TODO Validar conta

                etRequired.setText(requiredValue.toString());
                if (requiredValue <= configuredAverage) {
                    changeColor(GREEN, etRequired);
                } else {
                    changeColor(Color.RED, etRequired);
                }
                btSave.show();
            }
        } catch (Exception e) {
            cleanAverage();
            cleanNeed();
            MediaDialog.showSnack(rootView, R.string.invalid_info, Snackbar.LENGTH_LONG);
        }

    }

    private void prepareFontsAndBackgrounds() {
        MediaConfig.addFirstFontTo(tvTitulo);
        MediaConfig.addFirstFontTo(tvFirstGrade);
        MediaConfig.addFirstFontTo(tvSecondGrade);

        MediaConfig.addSecondFontTo(etFirstGrade);
        MediaConfig.addSecondFontTo(etSecondGrade);
        MediaConfig.addSecondFontTo(etAverageResult);
        MediaConfig.addSecondFontTo(etRequired);

        etFirstGrade.setBackgroundColor(0x00000000);
        etSecondGrade.setBackgroundColor(0x00000000);
        etAverageResult.setBackgroundColor(0x00000000);
        etRequired.setBackgroundColor(0x00000000);
    }

    private void setMasks() {
        final MaskedInput maskedInput = new MaskedInput(this,"#.#", etFirstGrade);
        etFirstGrade.addTextChangedListener(maskedInput.insert());
        etFirstGrade.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_0) {
                maskedInput.pressed(true);
            } else {
                maskedInput.pressed(false);
            }
            return false;
        });

        /*final MaskedInput maskedInput2 = new MaskedInput(this, "#.#", etSecondGrade);
        etSecondGrade.addTextChangedListener(maskedInput2.insert());
        etSecondGrade.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_0) {
                maskedInput2.pressed(true);
            } else {
                maskedInput2.pressed(false);
            }
            return false;
        });*/
    }

    public void cleanAverage() {
        etAverageResult.setText("");
    }

    public void cleanNeed() {
        etRequired.setText("");
    }

    public void changeColor(int color, EditText editText) {
        editText.setTextColor(color);
    }

    public EditText getSecondGradeEditText() {
        return etSecondGrade;
    }

    public EditText getFirstGradeEditText() {
        return etFirstGrade;
    }

    public void hideButton() {
        btSave.hide();
    }
}
