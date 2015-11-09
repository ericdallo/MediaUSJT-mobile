package com.mediausjt.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mediausjt.R;
import com.mediausjt.adapter.GradeAdapter;
import com.mediausjt.grade.Grade;
import com.mediausjt.util.MediaConfig;
import com.mediausjt.util.MediaDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class GradesFragment extends Fragment {

    private List<Grade> gradeList;
    private GradeAdapter adapter;

    @Bind(R.id.bt_drop_all)
    FloatingActionButton btDropAll;
    @Bind(R.id.rv_grades)
    RecyclerView recyclerView;

    @Bind(R.id.lt_no_grades)
    LinearLayout ltNoGrades;
    @Bind(R.id.lt_grades)
    RelativeLayout ltGrades;

    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.grades_frag, container, false);
        ButterKnife.bind(this, rootView);

        gradeList = MediaConfig.getDB().getAllNotas();

        if(gradeList.isEmpty()){
            showGradesNotFound();
        }else{
            adapter = new GradeAdapter(rootView.getContext(),gradeList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());


            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new SlideInLeftAnimator());
            recyclerView.setAdapter(adapter);

            if (gradeList.isEmpty()){
                btDropAll.hide();
            }
        }

        return rootView;
    }

    private void showGradesNotFound() {
        ltGrades.setVisibility(View.GONE);
        ltNoGrades.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_drop_all)
    public void dropAllGrades(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.deletarTodasNotas).setPositiveButton(R.string.sim, (dialog, id) -> {
            gradeList.clear();
            adapter.notifyDataSetChanged();

            MediaConfig.getDB().dropAllGrades();

            MediaConfig.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AverageFragment()).commit();

            MediaDialog.showSnack(rootView.getRootView(), R.string.grades_removed_message, Snackbar.LENGTH_LONG);
        }).setNegativeButton(R.string.nao, (dialog1, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle("Excluir Todas as Notas ?");
        dialog.show();
    }

}
