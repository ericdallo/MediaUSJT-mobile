package com.mediausjt.Grade;
/**
 * Created by eric on 09/03/15.
 */
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mediausjt.Database.DBHelper;
import com.mediausjt.Util.MaskedInput;
import com.mediausjt.R;

import java.io.Serializable;


public class NewGradeActivity extends ActionBarActivity implements Serializable{
    private DBHelper meudb;
    private TextView tvmateria,tvnota;
    private EditText etmateria,etnota;
    private int id_To_Update = 0;
    private Typeface font,font2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_nota);

        //pega a fonte
        font = Typeface.createFromAsset(getAssets(), "fonts/aller.ttf");
        font2 = Typeface.createFromAsset(getAssets(), "fonts/onramp.ttf");

        tvmateria = (TextView) findViewById(R.id.tvMateria);
        tvmateria.setTypeface(font);

        tvnota = (TextView) findViewById(R.id.tvNotaCadastro);
        tvnota.setTypeface(font);

        etmateria = (EditText) findViewById(R.id.etMateria);
        etnota = (EditText) findViewById(R.id.etNotaCadastro);;
        etnota.setTypeface(font2);

        String materiaDoPrecisa = "";
        if(getIntent().getExtras().getString("materia") != null)
            materiaDoPrecisa = getIntent().getExtras().getString("materia");

        String notaDoPrecisa = getIntent().getExtras().getString("notaMinima");

        etmateria.setText(materiaDoPrecisa);
        etmateria.setFocusable(true);
        etnota.setText(notaDoPrecisa);

        meudb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = meudb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(DBHelper.NOTAS_COLUMN_MATERIA)); //TODO Resolver erro excluir nota
                String emai = rs.getString(rs.getColumnIndex(DBHelper.NOTAS_COLUMN_NOTA));
                if (!rs.isClosed())
                    rs.close();

                etmateria.setText(nam);
                etnota.setText(emai);
            }
        }

        //mascara
        MaskedInput maskedInput = new MaskedInput(this,"#.#",etnota);
        etnota.addTextChangedListener(maskedInput.insert());
    }


    public void salvaNota(View view) {
        String snomeMateria = etmateria.getText().toString();

        EditText nota = (EditText) findViewById(R.id.etNotaCadastro);
        double inota = Double.parseDouble(nota.getText().toString());

        //Toast.makeText(getApplicationContext(),inota+"",Toast.LENGTH_SHORT).show();

        if (snomeMateria.equals("") || inota < 0.0 || inota > 10.0  ) {
            Toast.makeText(getApplicationContext(),"Informe o nome da matéria ou corrija a nota", Toast.LENGTH_SHORT).show();
        }else{
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int Value = extras.getInt("id");
                if (Value > 0) {
                    if (meudb.atualizarNota(id_To_Update, snomeMateria, inota+""))
                        Toast.makeText(getApplicationContext(), "Atualizado",Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(getApplicationContext(),"Não Atualizado", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    if (meudb.inserirNota(snomeMateria, inota + ""))
                        Toast.makeText(getApplicationContext(), "Grade Salva",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Grade não Salva",Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        }
    }

}

