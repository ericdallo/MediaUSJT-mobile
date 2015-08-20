package com.mediausjt;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class WeightFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{

    public static final String NOME_ITEM = "Peso";
    private final MainActivity mainActivity;
    private SeekBar seek1,seek2,seek3;
    private TextView tvseek1,tvseek2,tvseek3;
    private double peso1,peso2;
    private int media;
    private boolean podesalvar = false;
    private SharedPreferences prefs;
    private Typeface font,font2;


    public WeightFragment(){
        this.mainActivity = null;
    }

    @SuppressLint("ValidFragment")
    public WeightFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag3, container, false);

        prefs = getMainActivity().getPrefs();

        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aller.ttf");
        font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aller.ttf");


        peso1 = (double) prefs.getFloat("peso1", (float) 0.4);
        peso2 = (double) prefs.getFloat("peso2", (float) 0.6);
        media = prefs.getInt("media", 6);

        seek1 = (SeekBar) view.findViewById(R.id.seekbar1);
        seek1.setMax(90);
        seek1.setProgress((int) (peso1 * 100));
        seek1.setOnSeekBarChangeListener(this);

        seek2 = (SeekBar) view.findViewById(R.id.seekbar2);
        seek2.setMax(90);
        seek2.setProgress((int) (peso2 * 100));
        seek2.setOnSeekBarChangeListener(this);

        seek3 = (SeekBar) view.findViewById(R.id.seekbar3);
        seek3.setMax(90);
        seek3.setProgress(media * 10);
        seek3.setOnSeekBarChangeListener(this);

        tvseek1 = (TextView) view.findViewById(R.id.tvPeso1);
        tvseek1.setText("1º Semestre: 0." + seek1.getProgress() / 10);

        tvseek2 = (TextView) view.findViewById(R.id.tvPeso2);
        tvseek2.setText("2º Semestre: 0." + seek2.getProgress() / 10);

        tvseek3 = (TextView) view.findViewById(R.id.tvMedia);
        tvseek3.setText("" + seek3.getProgress() / 10);

        Button btmudar = (Button) view.findViewById(R.id.btMudarPeso);
        btmudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peso1 = 0.4;
                peso2 = 0.6;
                media = 6;
                seek1.setProgress(40);
                seek2.setProgress(60);
                seek3.setProgress(60);
                salvaPeso();
            }
        });
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int stepSize = 10;
        progress = (Math.round(progress/stepSize))*stepSize;
        seekBar.setProgress(progress);
        if(progress <= 10)
            seekBar.setProgress(10);

        if(seekBar == seek1){
            String speso = "0."+(seek1.getProgress()/10);

            if(Double.parseDouble(speso) >= 0.1){
                peso1 = Double.parseDouble(speso);
                tvseek1.setText("1º Semestre: " + peso1);
                mainActivity.setPeso1Setado("" + peso1);

                String speso2 = "0."+((100-seek1.getProgress())/10);
                peso2 = Double.parseDouble(speso2);
                tvseek2.setText("2º Semestre: " + peso2);
                seek2.setProgress(100-seek1.getProgress());
                podesalvar = true;
            }else
                podesalvar = false;


        }else if(seekBar == seek2){
            String speso2 = "0."+(seek2.getProgress()/10);

            if(Double.parseDouble(speso2) >= 0.1){
                peso2 = Double.parseDouble(speso2);
                tvseek2.setText("2º Semestre: " + peso2);
                mainActivity.setPeso2Setado("" + peso2);

                String speso = "0."+((100-seek2.getProgress())/10);
                peso1 = Double.parseDouble(speso);
                tvseek1.setText("1º Semestre: " + peso1);
                seek1.setProgress(100-seek2.getProgress());
                podesalvar = true;
            }else
                podesalvar = false;
        }else{

            if(seek3.getProgress()/10 >= 1){
                media = seek3.getProgress()/10;
                tvseek3.setText(""+media);
                podesalvar = true;
            }else
                podesalvar = false;

        }
    }

    private void salvaPeso() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("peso1", (float) peso1);
        editor.putFloat("peso2", (float)peso2);
        editor.putInt("media", media);
        editor.apply();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.setSecondaryProgress(seekBar.getProgress());
        if(podesalvar)
            salvaPeso();
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
