package com.mediausjt;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by eric on 13/03/15.
 */

public class Mask{

    private AverageFragment frag;
    private CadastraNota cad;
    private String mask,stringAtual;
    private EditText ediTxt;
    private boolean aperto;

    public Mask(AverageFragment frag,String mask,EditText ediTxt){
        this.frag = frag;
        this.mask = mask;
        this.ediTxt = ediTxt;
        this.cad = null;
    }

    public Mask(CadastraNota cad,String mask,EditText ediTxt){
        this.frag = null;
        this.mask = mask;
        this.ediTxt = ediTxt;
        this.cad = cad;
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public TextWatcher insert() {
        return new TextWatcher() {
            boolean taAtualizando;
            String antigo = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before,int count) {

                stringAtual = s.toString();
                String str = unmask(s.toString());
                String mascara = "";
                if (taAtualizando) {
                    antigo = str;
                    taAtualizando = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > antigo.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                taAtualizando = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length())   ;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("1.0") && aperto) {
                    mask = "##.0";
                    //abaixaTeclado();
                }else {
                    mask = "#.#";
                }

                int tam = ediTxt.getText().toString().length();
                if(frag != null) {
                    if (tam >= 1)
                        frag.calculaMedia();
                    else {
                        if (frag.getEdit2().getText().toString().length() == 0 &&
                                frag.getEdit1().getText().toString().length() != 0) {
                            frag.limpaCampoMedia();
                            frag.calculaMedia();
                        } else
                            frag.limpaCampoPrecisa();
                    }
                }
                if((tam == 3 && !(stringAtual.equals("1.0"))) || tam == 4) {
                    // esconde teclado
                    abaixaTeclado();
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        };

    }

    private void abaixaTeclado() {
        if(frag!=null){
            final InputMethodManager inputMethodManager = (InputMethodManager) frag.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(frag.getView().getWindowToken(), 0);
        }else{
            final InputMethodManager inputMethodManager = (InputMethodManager) cad.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(cad.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void Aperto0(boolean aperto) {
        this.aperto = aperto;
    }
}
