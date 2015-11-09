package com.mediausjt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mediausjt.R;
import com.mediausjt.grade.Grade;
import com.mediausjt.holder.GradeViewHolder;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeViewHolder>{

    private final Context context;
    private final List<Grade> gradeList;

    public GradeAdapter(Context context, List<Grade> gradeList) {
        this.context = context;
        this.gradeList = gradeList;
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.grade_list_item, parent, false);

        return new GradeViewHolder(this,view,gradeList);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        Grade grade = gradeList.get(position);

        holder.getTvMatterName().setText(grade.getMatter());
        holder.getTvRequiredGrade().setText(grade.getValue());
        holder.setColor();
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }
}
