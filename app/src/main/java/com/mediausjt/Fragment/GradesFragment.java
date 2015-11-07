package com.mediausjt.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mediausjt.Adapter.GradeExpandAdapter;
import com.mediausjt.Database.DBHelper;
import com.mediausjt.Grade.Grade;
import com.mediausjt.R;
import com.mediausjt.Util.MediaConfig;
import com.mediausjt.util.MediaDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class GradesFragment extends Fragment {

    public final static String NOME_ITEM = "Notas";
    private DBHelper meudb;
    private SparseArray<Grade> notas = new SparseArray<>();

    private List<Grade> gradeList;
    private GradeExpandAdapter expandAdapter;

    @Bind(R.id.tv_grade_title)
    TextView tvGradeTitle;
    @Bind(R.id.list_view)
    ExpandableListView listview;

    @Bind(R.id.bt_drop_all)
    FloatingActionButton btDropAll;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.grades_frag, container, false);
        ButterKnife.bind(this, rootView);

        meudb = new DBHelper(rootView.getContext());

        gradeList = meudb.getAllNotas();
        convertToSparseArray(gradeList);

        expandAdapter = new GradeExpandAdapter(notas);
        listview.setAdapter(expandAdapter);

        MediaConfig.addFirstFontTo(tvGradeTitle);

        if (gradeList.isEmpty()){
            btDropAll.hide();
        }

        return rootView;
    }

    @OnClick(R.id.bt_drop_all)
    public void dropAllGrades(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.deletarTodasNotas).setPositiveButton(R.string.sim, (dialog, id) -> {
            gradeList.clear();
            expandAdapter.notifyDataSetChanged();

            meudb.dropAllGrades();

            MediaConfig.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AverageFragment()).commit();

            MediaDialog.showSnack(rootView.getRootView(), R.string.grades_removed_message, Snackbar.LENGTH_LONG);
        }).setNegativeButton(R.string.nao, (dialog1, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle("Excluir Todas as Notas ?");
        dialog.show();
    }

    public void convertToSparseArray(List<Grade> gradeList) {
        for (int i = 0; i < gradeList.size(); i++) {
            Grade grade = new Grade();
            grade.setId(gradeList.get(i).getId());
            grade.setMatter(gradeList.get(i).getMatter());
            grade.setValue(gradeList.get(i).getValue());

            notas.append(i, grade);
        }
    }

}
