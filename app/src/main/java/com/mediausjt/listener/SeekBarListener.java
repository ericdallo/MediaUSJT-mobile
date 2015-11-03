package com.mediausjt.listener;

import android.widget.SeekBar;
import android.widget.TextView;

import com.mediausjt.Util.MediaConfig;

public class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

    private boolean canSave = false;
    private Float firstGradeWeight;
    private Float secondGradeWeight;
    private int averageWeight;
    private TextView tvFirstGrade;
    private TextView tvSecondGrade;
    private TextView tvAverage;
    private SeekBar seekbarFirstGrade;
    private SeekBar seekbarSecondGrade;
    private SeekBar seekbarAverage;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int stepSize = 10;
        progress = (Math.round(progress / stepSize)) * stepSize;
        seekBar.setProgress(progress);
        if (progress <= 10) {
            seekBar.setProgress(10);
        }

        if (seekBar == seekbarFirstGrade) {
            String speso = "0." + (seekBar.getProgress() / 10);

            if (Double.parseDouble(speso) >= 0.1) {
                firstGradeWeight = Float.valueOf(speso);
                tvFirstGrade.setText("1º Semestre: " + firstGradeWeight);
                //mediaActivity.setPeso1Setado("" + peso1);

                String speso2 = "0." + ((100 - seekbarFirstGrade.getProgress()) / 10);
                secondGradeWeight = Float.valueOf(speso2);
                tvSecondGrade.setText("2º Semestre: " + secondGradeWeight);
                seekbarSecondGrade.setProgress(100 - seekbarFirstGrade.getProgress());
                canSave = true;
            } else
                canSave = false;


        } else if (seekBar == seekbarSecondGrade) {
            String speso2 = "0." + (seekbarSecondGrade.getProgress() / 10);

            if (Double.parseDouble(speso2) >= 0.1) {
                secondGradeWeight = Float.valueOf(speso2);
                tvSecondGrade.setText("2º Semestre: " + secondGradeWeight);
                //mediaActivity.setPeso2Setado("" + peso2);

                String speso = "0." + ((100 - seekbarSecondGrade.getProgress()) / 10);
                firstGradeWeight = Float.valueOf(speso);
                tvFirstGrade.setText("1º Semestre: " + firstGradeWeight);
                seekbarFirstGrade.setProgress(100 - seekbarSecondGrade.getProgress());
                canSave = true;
            } else
                canSave = false;
        } else {

            if (seekbarAverage.getProgress() / 10 >= 1) {
                averageWeight = seekbarAverage.getProgress() / 10;
                tvAverage.setText("" + averageWeight );
                canSave = true;
            } else
                canSave = false;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.setSecondaryProgress(seekBar.getProgress());
        if (canSave) {
            MediaConfig.savePreference("peso1",firstGradeWeight);
            MediaConfig.savePreference("peso2", secondGradeWeight);
            MediaConfig.savePreference("media", averageWeight);
        }
    }

    public void setFirstGradeWeight(float firstGradeWeight) {
        this.firstGradeWeight = firstGradeWeight;
    }

    public void setSecondGradeWeight(float secondGradeWeight) {
        this.secondGradeWeight = secondGradeWeight;
    }

    public void setAverageWeight(int averageWeight) {
        this.averageWeight = averageWeight;
    }

    public void setTvFirstGrade(TextView tvFirstGrade) {
        this.tvFirstGrade = tvFirstGrade;
    }

    public void setTvSecondGrade(TextView tvSecondGrade) {
        this.tvSecondGrade = tvSecondGrade;
    }

    public void setTvAverage(TextView tvAverage) {
        this.tvAverage = tvAverage;
    }

    public void setSeekbarFirstGrade(SeekBar seekbarFirstGrade) {
        this.seekbarFirstGrade = seekbarFirstGrade;
    }

    public void setSeekbarSecondGrade(SeekBar seekbarSecondGrade) {
        this.seekbarSecondGrade = seekbarSecondGrade;
    }

    public void setSeekbarAverage(SeekBar seekbarAverage) {
        this.seekbarAverage = seekbarAverage;
    }

    public static class Builder {

        private SeekBarListener seekBar = new SeekBarListener();

        public Builder withFirstWeight(float firstGradeWeight) {
            seekBar.setFirstGradeWeight(firstGradeWeight);
            return this;
        }

        public Builder withSecondWeight(float secondGradeWeight) {
            seekBar.setSecondGradeWeight(secondGradeWeight);
            return this;
        }

        public Builder withAverageWeight(int averageWeight) {
            seekBar.setAverageWeight(averageWeight);
            return this;
        }

        public Builder withTextViewFirst(TextView textView) {
            seekBar.setTvFirstGrade(textView);
            return this;
        }

        public Builder withTextViewSecond(TextView textView) {
            seekBar.setTvSecondGrade(textView);
            return this;
        }

        public Builder withTextViewAverage(TextView textView) {
            seekBar.setTvAverage(textView);
            return this;
        }

        public Builder withSeekBarFirstGrade(SeekBar seekBar) {
            this.seekBar.setSeekbarFirstGrade(seekBar);
            return this;
        }

        public Builder withSeekBarSecondGrade(SeekBar seekBar) {
            this.seekBar.setSeekbarSecondGrade(seekBar);
            return this;
        }

        public Builder withSeekBarAverageGrade(SeekBar seekBar) {
            this.seekBar.setSeekbarAverage(seekBar);
            return this;
        }

        public SeekBarListener build() {
            return this.seekBar;
        }
    }
}
