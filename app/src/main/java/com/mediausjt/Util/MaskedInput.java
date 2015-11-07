package com.mediausjt.Util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mediausjt.Fragment.LogicFragment;
import com.mediausjt.Fragment.NewGradeFragment;

public class MaskedInput {

    private LogicFragment averageFragment;
    private NewGradeFragment newGradeActivity;
    private String mask, actualString;
    private EditText editText;
    private boolean isPressed;

    public MaskedInput(LogicFragment averageFragment, String mask, EditText editText) {
        this.averageFragment = averageFragment;
        this.mask = mask;
        this.editText = editText;
        this.newGradeActivity = null;
    }

    public MaskedInput(NewGradeFragment newGradeFragment, String mask, EditText editText) {
        this.averageFragment = null;
        this.mask = mask;
        this.editText = editText;
        this.newGradeActivity = newGradeFragment;
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public TextWatcher insert() {
        return new TextWatcher() {
            boolean isUptodate;
            String antigo = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                actualString = s.toString();
                String str = unmask(s.toString());
                String mascara = "";
                if (isUptodate) {
                    antigo = str;
                    isUptodate = false;
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
                isUptodate = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {                                                               // TODO validar nullpointer
                    if (editable.toString().equals("1.0") && isPressed) {
                        mask = "##.0";
                    } else {
                        mask = "#.#";
                    }
                    int tam = editText.getText().toString().length();
                    if (averageFragment != null) {
                        if (tam >= 1) {
                            averageFragment.calculate();
                        } else {
                            if (averageFragment.getSecondGradeEditText().getText().toString().length() == 0 && averageFragment.getFirstGradeEditText().getText().toString().length() != 0) {
                                averageFragment.cleanAverage();
                                averageFragment.calculate();
                            } else {
                                averageFragment.cleanNeed();
                                averageFragment.hideButton();
                            }
                        }
                    }
                    if ((tam == 3 && !(actualString.equals("1.0"))) || tam == 4) {
                        // esconde teclado
                        downKeyboard();
                    }
                } catch (NullPointerException e) {
                    Log.e("Exception", "Error on after text changed mask input");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        };

    }

    private void downKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) MediaConfig.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(MediaConfig.getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }

    public void pressed(boolean pressed) {
        this.isPressed = pressed;
    }
}
