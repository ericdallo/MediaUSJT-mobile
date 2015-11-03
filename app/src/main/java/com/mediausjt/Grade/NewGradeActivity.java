package com.mediausjt.Grade;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mediausjt.Database.DBHelper;
import com.mediausjt.R;
import com.mediausjt.Util.MaskedInput;
import com.mediausjt.Util.MediaConfig;
import com.mediausjt.util.MediaDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewGradeActivity extends AppCompatActivity {

    @Bind(R.id.tv_matter)
    TextView tvMatter;
    @Bind(R.id.etMatter)
    EditText etMatter;
    @Bind(R.id.tv_new_grade)
    TextView tvNewGrade;
    @Bind(R.id.et_new_grade)
    EditText etNewGrade;

    private DBHelper dbHelper = new DBHelper(this);
    private int idToUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grade);
        ButterKnife.bind(this);

        MediaConfig.addFirstFontTo(tvMatter);
        MediaConfig.addFirstFontTo(tvNewGrade);
        MediaConfig.addSecondFontTo(etNewGrade);

        String requiredGrade = getIntent().getExtras().getString("notaMinima");

        etMatter.setFocusable(true);
        etNewGrade.setText(requiredGrade);
        etNewGrade.setTextColor(getIntent().getIntExtra("matterColor", R.color.AzulMedio));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("id");
            if (id > 0) {
                Cursor rs = dbHelper.getGrade(id);
                idToUpdate = id;
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(DBHelper.NOTAS_COLUMN_MATERIA));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.NOTAS_COLUMN_NOTA));
                if (!rs.isClosed()){
                    rs.close();
                }

                etMatter.setText(nam);
                etNewGrade.setText(emai);
            }
        }
        //mascara
        MaskedInput maskedInput = new MaskedInput(this, "#.#", etNewGrade);
        etNewGrade.addTextChangedListener(maskedInput.insert());
    }

    @OnClick(R.id.bt_persist_grade)
    public void persistGrade(View view) {
        String snomeMateria = etMatter.getText().toString();

        double inota = Double.parseDouble(etNewGrade.getText().toString());

        if (snomeMateria.equals("") || inota < 0.0 || inota > 10.0) {
            MediaDialog.showSnack(view,R.string.invalid_new_grade, Snackbar.LENGTH_LONG);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int Value = extras.getInt("id");
                if (Value > 0) {
                    if (dbHelper.atualizarNota(idToUpdate, snomeMateria, inota + "")) {
                        Toast.makeText(getApplicationContext(), getString(R.string.updated),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.not_updated),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (dbHelper.inserirNota(snomeMateria, inota + "")) {
                        Toast.makeText(getApplicationContext(), getString(R.string.saved_grade),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.unsaved_grade),Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        }
    }

}

