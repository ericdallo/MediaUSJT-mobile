package com.mediausjt.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.mediausjt.Database.DBHelper;
import com.mediausjt.Fragment.AverageFragment;
import com.mediausjt.Grade.Grade;
import com.mediausjt.Grade.NewGradeActivity;
import com.mediausjt.R;
import com.mediausjt.Util.MediaConfig;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomExpandAdapter extends BaseExpandableListAdapter {

    @Nullable @Bind(R.id.tv_matter_list_item)
    TextView tvMatterListItem;
    @Nullable @Bind(R.id.tv_grade_list_item)
    TextView tvGradeListItem;

    @Nullable @Bind(R.id.bt_edit_matter)
    Button btEditar;
    @Nullable @Bind(R.id.bt_delete_matter)
    Button btExcluir;

    private final SparseArray<Grade> notas;

    public CustomExpandAdapter(SparseArray<Grade> notas) {
        this.notas = notas;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return notas.get(groupPosition).getValue();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) MediaConfig.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listrow_details, parent, false);
        }
        ButterKnife.bind(this,view);

        tvMatterListItem.setText("Nota Mínima");

        String nota = notas.get(groupPosition).getValue();
        if(Double.parseDouble(nota) > 6.0){
            tvGradeListItem.setTextColor(view.getResources().getColor(R.color.red));
        }else{
            tvGradeListItem.setTextColor(view.getResources().getColor(R.color.green));
        }

        tvGradeListItem.setText(nota);

        btEditar.setTag(groupPosition);
        btExcluir.setTag(groupPosition);

        MediaConfig.addFirstFontTo(btEditar);
        MediaConfig.addFirstFontTo(btExcluir);

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return notas.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return notas.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View childView, ViewGroup parent) {
        if (childView == null) {
            childView = MediaConfig.getActivity().getLayoutInflater().inflate(R.layout.listrow_group, parent, false);
        }
        Grade grade = (Grade) getGroup(groupPosition);
        ((CheckedTextView) childView).setText(grade.getMatter());
        ((CheckedTextView) childView).setChecked(isExpanded);

        return childView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @OnClick(R.id.bt_edit_matter)
    public void editMatter(View view){
        Intent intent = new Intent(MediaConfig.getActivity(),NewGradeActivity.class);
        intent.putExtra("notaMinima", tvGradeListItem.getText().toString());
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", notas.get((int) view.getTag()).getId());
        intent.putExtras(dataBundle);
        MediaConfig.getActivity().startActivity(intent);
        MediaConfig.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new AverageFragment()).commit();
    }

    @OnClick(R.id.bt_delete_matter)
    public void deleteMatter(View view){
        final DBHelper meudb = new DBHelper(MediaConfig.getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(MediaConfig.getActivity());
        builder.setMessage(R.string.delete_grade).setPositiveButton(R.string.sim, (dialog, id) -> {
            int position = notas.get((int) view.getTag()).getId();
            meudb.deletarNota(position);
            notifyDataSetChanged();
            Toast.makeText(MediaConfig.getActivity(), "Nota Excluida", Toast.LENGTH_SHORT).show();

            MediaConfig.getActivity().recreate();
        }).setNegativeButton(R.string.nao,(dialog1, which) -> {});

        AlertDialog d = builder.create();
        d.setTitle(MediaConfig.getActivity().getString(R.string.delete_sure));
        d.show();
    }
}
