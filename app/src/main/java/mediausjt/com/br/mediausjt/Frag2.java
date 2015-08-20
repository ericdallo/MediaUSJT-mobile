package mediausjt.com.br.mediausjt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class Frag2 extends Fragment {

    public final static String NOME_ITEM = "Notas";

    DBHelper meudb;
    SparseArray<Nota> notas = new SparseArray<Nota>();
    private Main main;

    @SuppressLint("ValidFragment")
    public Frag2(Main main) {
        this.main = main;
    }
    public Frag2(){
        this.main = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag2, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aller.ttf");

        TextView tvTitulo = (TextView)view.findViewById(R.id.tvTituloNota);
        tvTitulo.setTypeface(font);

        final Context c = getActivity().getApplicationContext();

        meudb = new DBHelper(c);
        final ArrayList<Nota> array_list = meudb.getAllNotas();
        transformaSparseArray(array_list);

        final CustomExpandAdapter expandAdapter = new CustomExpandAdapter(getActivity(),notas,main);

        Button b = (Button) view.findViewById(R.id.btDeletarTudo);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.deletarTodasNotas).setPositiveButton(R.string.sim,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        array_list.clear();
                        expandAdapter.notifyDataSetChanged();
                        meudb.deletarTodasNotas();
                        Toast.makeText(getActivity(), "Notas excluidas",Toast.LENGTH_LONG).show();
                        getActivity().recreate(); //TODO arrumar para voltar para o menu principal ou atualizar notas
                    }
                }).setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "Nenhuma nota excluida", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog d = builder.create();
                d.setTitle("Excluir Todas as Notas ?");
                d.show();
            }
        });

        //ListView listview = (ListView) view.findViewById(R.id.listView1);
        ExpandableListView listview = (ExpandableListView) view.findViewById(R.id.listView);
        listview.setAdapter(expandAdapter);

        return view;
    }

    public void transformaSparseArray(ArrayList<Nota> array_list) {
        for(int i = 0; i< array_list.size();i++){
           Nota nota = new Nota(array_list.get(i).getId(),array_list.get(i).getMateria(),array_list.get(i).getNota());
            notas.append(i, nota);
        }
    }

}
