package com.mediausjt.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mediausjt.R;
import com.mediausjt.util.MediaConfig;
import com.mediausjt.listener.SeekBarListener;
import com.mediausjt.util.MediaDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeightFragment extends Fragment {

    @Bind(R.id.seekbar_first_grade)
    SeekBar seekbarFirstGrade;

    @Bind(R.id.seekbar_second_grade)
    SeekBar seekbarSecondGrade;
    @Bind(R.id.seekbar_average)
    SeekBar seekbarAverage;
    @Bind(R.id.tv_change_first_grade)
    TextView tvFirstGrade;

    @Bind(R.id.tv_change_second_grade)
    TextView tvSecondGrade;
    @Bind(R.id.tv_change_average)
    TextView tvAverage;

    public static final String NOME_ITEM = "Peso";
    private SeekBarListener seekBarListener;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.weight_frag, container, false);
        ButterKnife.bind(this, rootView);

        float firstGradeWeight = MediaConfig.getPreference("peso1", 0.4f);
        float secondGradeWeight = MediaConfig.getPreference("peso2", 0.6f);
        int averageWeight = MediaConfig.getPreference("media", 6);

        seekBarListener = new SeekBarListener.Builder()
                .withFirstWeight(firstGradeWeight)
                .withSecondWeight(secondGradeWeight)
                .withAverageWeight(averageWeight)
                .withTextViewFirst(tvFirstGrade)
                .withTextViewSecond(tvSecondGrade)
                .withTextViewAverage(tvAverage)
                .withSeekBarFirstGrade(seekbarFirstGrade)
                .withSeekBarSecondGrade(seekbarSecondGrade)
                .withSeekBarAverageGrade(seekbarAverage)
                .build();

        setupSeekbar(seekbarFirstGrade, firstGradeWeight, false);
        setupSeekbar(seekbarSecondGrade, secondGradeWeight, false);
        setupSeekbar(seekbarAverage, averageWeight, true);

        tvFirstGrade.setText("1º Semestre: 0." + seekbarFirstGrade.getProgress() / 10);
        tvSecondGrade.setText("2º Semestre: 0." + seekbarSecondGrade.getProgress() / 10);
        tvAverage.setText("" + seekbarAverage.getProgress() / 10);

        return rootView;
    }

    @OnClick(R.id.bt_reset_weight)
    public void resetWeight() {
        MediaDialog.showSnack(rootView, R.string.reset_message, Snackbar.LENGTH_LONG);
        seekbarFirstGrade.setProgress(40);
        seekbarSecondGrade.setProgress(60);
        seekbarAverage.setProgress(60);

        MediaConfig.resetWeight();
    }

    private void setupSeekbar(SeekBar seekbar, double value, boolean type) {
        seekbar.setMax(90);
        double multiplier = type ? 10 : 100;
        seekbar.setProgress((int) (value * multiplier));
        seekbar.setOnSeekBarChangeListener(seekBarListener);
    }
}
