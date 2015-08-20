package com.mediausjt.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.mediausjt.Application.MainActivity;
import com.mediausjt.Database.DBHelper;
import com.mediausjt.Fragment.AverageFragment;
import com.mediausjt.Grade.Grade;
import com.mediausjt.Grade.NewGradeActivity;
import com.mediausjt.R;

import java.io.Serializable;


/**
 * Created by eric on 10/03/15.
 */
public class CustomExpandAdapter extends BaseExpandableListAdapter implements View.OnClickListener,Serializable {

    private final SparseArray<Grade> notas;
    public LayoutInflater inflater;
    public Activity activity;
    TextView tvNota,tvMateria;
    private int grupoAtual;
    private MainActivity mainActivity;


    public CustomExpandAdapter(Activity act, SparseArray<Grade> notas,MainActivity mainActivity) {
        activity = act;
        this.notas = notas;
        inflater = act.getLayoutInflater();
        this.mainActivity = mainActivity;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return notas.get(groupPosition).getNota();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listrow_details, parent, false);
        }
        Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/aller.ttf");

        tvMateria = (TextView) v.findViewById(R.id.textView1);
        tvNota = (TextView) v.findViewById(R.id.textView2);


        tvMateria.setText("Grade Mínima");

        String nota = notas.get(groupPosition).getNota();
        if(Double.parseDouble(nota) > 6.0)
            tvNota.setTextColor(v.getResources().getColor(R.color.Vermelho));
        else
            tvNota.setTextColor(v.getResources().getColor(R.color.Verde));

        tvNota.setText(nota);

        Button btEditar = (Button) v.findViewById(R.id.btEditarMateria);
        btEditar.setOnClickListener(this);
        btEditar.setTag(groupPosition);
        Button btExcluir = (Button) v.findViewById(R.id.btExcluirMateria);
        btExcluir.setTag(groupPosition);
        btExcluir.setOnClickListener(this);

        btEditar.setOnClickListener(this);
        btExcluir.setOnClickListener(this);

        btEditar.setTypeface(font);
        btExcluir.setTypeface(font);

        return v;
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        Grade grade = (Grade) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(grade.getMateria());
        ((CheckedTextView) convertView).setChecked(isExpanded);
        grupoAtual = groupPosition;

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    @Override
    public void onClick(View v) {
        if(v == v.findViewById(R.id.btEditarMateria)){
            Intent intent = new Intent(activity,NewGradeActivity.class);
            intent.putExtra("materia", notas.get(grupoAtual).getMateria());
            intent.putExtra("notaMinima",tvNota.getText().toString());
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", notas.get((int)v.getTag()).getId());
            intent.putExtras(dataBundle);

            activity.startActivity(intent);
            activity.getFragmentManager().beginTransaction().replace(R.id.content_frame,new AverageFragment(mainActivity)).commit();
        }else if(v == v.findViewById(R.id.btExcluirMateria)){

            final DBHelper meudb = new DBHelper(v.getContext());
            final View mView = v;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.deletarNota).setPositiveButton(R.string.sim,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    //int pos = (int)mView.getTag(); // CAGADA
                    //A POSICAO DEVE SER O ID DO OBJETO NO BD E NAO A POSICAO NA LISTA
                    int position = notas.get((int)mView.getTag()).getId();
                    meudb.deletarNota(position);
                    notifyDataSetChanged();
                    Toast.makeText(activity,"Grade Excluida", Toast.LENGTH_SHORT).show();

                    activity.recreate();
                }
            }).setNegativeButton(R.string.nao,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // cancelou o dialogo
                }
            });
            AlertDialog d = builder.create();
            d.setTitle("Você tem certeza?");
            d.show();
        }
    }

}
