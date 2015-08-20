package com.mediausjt.Fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mediausjt.Application.MainActivity;
import com.mediausjt.Util.MaskedInput;
import com.mediausjt.Grade.NewGradeActivity;
import com.mediausjt.R;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AverageFragment extends Fragment {

    public static final String NOME_ITEM = "Calcular";

    private final MainActivity mainActivity;

    private EditText nota1;
    private EditText nota2;
    private EditText precisa1;

    @SuppressLint("ValidFragment")
    public AverageFragment(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public AverageFragment(){
        this.mainActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag1, container, false);

        //pega a fonte
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aller.ttf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/onramp.ttf");

        nota1 = (EditText) view.findViewById(R.id.etNota1);
        nota1.setBackgroundColor(0x00000000);
        nota1.setTypeface(font2);

        nota2 = (EditText) view.findViewById(R.id.etNota2);
        nota2.setBackgroundColor(0x00000000);
        nota2.setTypeface(font2);

        EditText resp1 = (EditText) view.findViewById(R.id.etResp1);
        resp1.setBackgroundColor(0x00000000);
        resp1.setTypeface(font2);

        precisa1 = (EditText) view.findViewById(R.id.etPrecisa1);
        precisa1.setBackgroundColor(0x00000000);
        precisa1.setTypeface(font2);

        TextView tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
        tvTitulo.setTypeface(font);

        TextView tvNota1 = (TextView) view.findViewById(R.id.tvNota1);
        tvNota1.setTypeface(font);

        TextView tvNota2 = (TextView) view.findViewById(R.id.tvNota2);
        tvNota2.setTypeface(font);

        Button btSalvar = (Button) view.findViewById(R.id.btSalvar);
        btSalvar.setTypeface(font);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {  // SALVAR NOTA

                String sprecisa1 = precisa1.getText().toString();

                if (sprecisa1.equals("")) {
                    Toast.makeText(getActivity(),"Calcule uma nota mínima a ser cadastrada", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", 0);
                    Intent intent = new Intent(getActivity(),NewGradeActivity.class);
                    intent.putExtras(dataBundle);
                    intent.putExtra("notaMinima", sprecisa1);
                    int corNotaMinima;
                    if(precisa1.getCurrentTextColor() == R.color.Vermelho){
                        corNotaMinima =  R.color.Vermelho;
                    }else{
                        corNotaMinima = R.color.Verde;
                    }
                    intent.putExtra("materia",corNotaMinima);
                    startActivity(intent);
                    limpaCampoPrecisa();
                    nota1.setText("");

                }
            }

        });

        //mascara
        final MaskedInput maskedInput = new MaskedInput(this, "#.#", nota1);
        nota1.addTextChangedListener(maskedInput.insert());
        nota1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_0)
                    maskedInput.Aperto0(true);
                else
                    maskedInput.Aperto0(false);
                return false;
            }
        });
        final MaskedInput maskedInput2 = new MaskedInput(this, "#.#", nota2);

        nota2.addTextChangedListener(maskedInput2.insert());
        nota2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_0)
                    maskedInput2.Aperto0(true);
                else
                    maskedInput2.Aperto0(false);
                return false;
            }
        });


        return view;
    }

    public void calculaMedia() {
        String snota1;
        double n1;

        String snota2;
        double n2;

        try {
            snota1 = nota1.getText().toString();

            snota2 = nota2.getText().toString();

            SharedPreferences prefs = getMainActivity().getPrefs();

            double dpeso1 = (double)(prefs.getFloat("peso1",(float)0.4));
            double dpeso2 = (double)(prefs.getFloat("peso2",(float)0.6));
            int mediacurso = prefs.getInt("media",6);

            if (!(snota1.equals("")) && !(snota2.equals(""))) {

                limpaCampoPrecisa();
                n1 = Double.parseDouble(snota1);
                n2 = Double.parseDouble(snota2);

                double media = (n1 * dpeso1) + (n2 * dpeso2);
                String fmedia = media + "";

                EditText etResp1 = (EditText) mainActivity.findViewById(R.id.etResp1);
                // fmedia = arredonda(fmedia);
                etResp1.setText(fmedia);
                if (media < mediacurso)
                    Colore(R.color.Vermelho, etResp1);
                else
                    Colore(R.color.Verde, etResp1);

            } else if (!(snota1.equals(""))) {
                limpaCampoMedia();
                n1 = Double.parseDouble(snota1);
                double precisa = (mediacurso - (n1 * dpeso1)) / dpeso2; //TODO ARRUMAR CONTA
                String fprecisa = precisa + "";

                EditText etPrecisa1 = (EditText) mainActivity.findViewById(R.id.etPrecisa1);
                etPrecisa1.setText(fprecisa);
                if (precisa <= mediacurso)
                    Colore(0xFF13C300, etPrecisa1);
                else
                    Colore(0xFFFF0000, etPrecisa1);

            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "Informações incorretas...";
            Context con = mainActivity.getApplicationContext();
            //int tempo = 4000;
            Toast to = Toast.makeText(con, msg,Toast.LENGTH_SHORT);
            to.show();
        }

    }

    public void limpaCampoMedia() {
        EditText etMedia1 = (EditText) mainActivity.findViewById(R.id.etResp1);
        etMedia1.setText("");
    }

    public void limpaCampoPrecisa() {
        EditText etPrecisa1 = (EditText) mainActivity.findViewById(R.id.etPrecisa1);
        etPrecisa1.setText("");
    }

    public void Colore(int cor, EditText et) {
        et.setTextColor(cor);
    }

    public MainActivity getMainActivity() {
        return this.mainActivity;
    }
    public EditText getEdit2() {
        return nota2;
    }
    public EditText getEdit1() {
        return nota1;
    }
}
