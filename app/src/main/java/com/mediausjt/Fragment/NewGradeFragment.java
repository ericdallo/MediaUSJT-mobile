package com.mediausjt.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mediausjt.Database.DBHelper;
import com.mediausjt.R;
import com.mediausjt.Util.MaskedInput;
import com.mediausjt.Util.MediaConfig;
import com.mediausjt.util.FragmentUtil;
import com.mediausjt.util.MediaDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewGradeFragment extends Fragment {

    @Bind(R.id.et_matter)
    EditText etMatter;
    @Bind(R.id.et_new_grade)
    EditText etNewGrade;

    private DBHelper dbHelper;
    private View rootView;
    private String idToUpdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_grade_frag, container, false);

        ButterKnife.bind(this, rootView);

        dbHelper =  new DBHelper(MediaConfig.getActivity());
        etMatter.setFocusable(true);

        showInfoToUpdate();

        //mascara
        MaskedInput maskedInput = new MaskedInput(this, "#.#", etNewGrade);
        etNewGrade.addTextChangedListener(maskedInput.insert());

        return rootView;
    }

    private void showInfoToUpdate() {
        idToUpdate = MediaConfig.getPreference("idToUpdate", "");
        int gradeColor = MediaConfig.getPreference("gradeColor",R.color.black);

        if (!idToUpdate.isEmpty()) {
            Cursor rs = dbHelper.getGrade(Integer.parseInt(idToUpdate));
            rs.moveToFirst();
            String matterName = rs.getString(rs.getColumnIndex(DBHelper.NOTAS_COLUMN_MATERIA));
            String grade = rs.getString(rs.getColumnIndex(DBHelper.NOTAS_COLUMN_NOTA));
            if (!rs.isClosed()) {
                rs.close();
            }
            etMatter.setText(matterName);
            etNewGrade.setText(grade);
            etNewGrade.setTextColor(gradeColor);

            MediaConfig.removePreference("idToUpdate");
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


    @OnClick(R.id.bt_persist_grade)
    public void persistGrade(View view) {
        String matterName = etMatter.getText().toString();
        Double matterValue = Double.parseDouble(etNewGrade.getText().toString());

        if (matterName.equals("") || matterValue < 0.0 || matterValue > 10.0) {
            MediaDialog.showSnack(view, R.string.invalid_new_grade, Snackbar.LENGTH_LONG);
        } else {

            if (!idToUpdate.isEmpty()) {
                MediaConfig.goToAverage();
                boolean success = dbHelper.atualizarNota(Integer.parseInt(idToUpdate), matterName, matterValue.toString());

                if (success) {
                    MediaDialog.showSnack(rootView, R.string.updated, Snackbar.LENGTH_LONG);
                } else {
                    MediaDialog.showSnack(rootView, R.string.not_updated, Snackbar.LENGTH_LONG);
                }
            } else {
                FragmentUtil.showLogic();
                boolean success = dbHelper.inserirNota(matterName, matterValue.toString());
                if (success) {
                    MediaDialog.showSnack(FragmentUtil.getRootView(),R.string.saved_grade,Snackbar.LENGTH_LONG);
                } else {
                    MediaDialog.showSnack(FragmentUtil.getRootView(), R.string.unsaved_grade, Snackbar.LENGTH_LONG);
                }
            }

        }
    }
}
