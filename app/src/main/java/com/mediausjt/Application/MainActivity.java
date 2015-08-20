package com.mediausjt.Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mediausjt.Adapter.CustomDrawerAdapter;
import com.mediausjt.Item.DrawerItem;
import com.mediausjt.Fragment.AverageFragment;
import com.mediausjt.Fragment.GradesFragment;
import com.mediausjt.Fragment.WeightFragment;
import com.mediausjt.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence drawerTitulo;
    private CharSequence title;
    private CustomDrawerAdapter adapter;

    private List<DrawerItem> infoList;

    private SharedPreferences prefs;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.contains("PrimeiraVez")) {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Como Calcular");
                alertDialog.setMessage(getString(R.string.msg1));
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("PrimeiraVez", true);
                                editor.apply();
                            }
                        });
                alertDialog.setIcon(R.drawable.icon_alert_t);
                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),getString(R.string.msg1), Toast.LENGTH_LONG).show();
            }
        }

        // Inicializando o drawer

        infoList = new ArrayList<DrawerItem>();
        title = drawerTitulo = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.RIGHT); //TODO mudar segundo parametro

        // adiciona o item no arraylist
        infoList.add(new DrawerItem("Média", R.drawable.calculator));
        infoList.add(new DrawerItem("Notas", R.drawable.notes));
        infoList.add(new DrawerItem("Peso", R.drawable.pesos));
        infoList.add(new DrawerItem("Informações", R.drawable.info));
        infoList.add(new DrawerItem("Contato", R.drawable.contato));

        adapter = new CustomDrawerAdapter(this, R.layout.custom_list_drawer,infoList);
        adapter.setMainActivity(this);

        drawerList.setAdapter(adapter);

        //add o listener no drawer
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        if(toolbar != null)
            setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitulo);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        if(savedInstanceState == null)
            selectItem(0);
    }

    public void selectItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        boolean isFrag;

        switch (position) {
            case 0:
                fragment = new AverageFragment(this);
                bundle.putString(AverageFragment.NOME_ITEM, infoList.get(position).getItemNome());
                isFrag = true;
                break;
            case 1:
                fragment = new GradesFragment(this);
                bundle.putString(GradesFragment.NOME_ITEM, infoList.get(position).getItemNome());
                isFrag = true;
                break;
            case 2:
                fragment = new WeightFragment(this);
                bundle.putString(WeightFragment.NOME_ITEM, infoList.get(position).getItemNome());
                isFrag = true;
                break;
            case 3:   //Informacoes
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Como Calcular");
                    alertDialog.setMessage(getString(R.string.msg1));
                    alertDialog.setInverseBackgroundForced(true);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    alertDialog.setIcon(R.drawable.icon_alert_t);
                    alertDialog.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.msg1), Toast.LENGTH_LONG).show();
                }
                isFrag = false;
                break;
            case 4: // CONtato
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Entre em Contato");
                    alertDialog.setMessage(getString(R.string.msg2));
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    alertDialog.setIcon(R.drawable.icon_alert_t);
                    alertDialog.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.msg2), Toast.LENGTH_LONG).show();
                }
                isFrag = false;
                break;
            default:
                isFrag = false;
                break;
        }
        if(isFrag){
            fragment.setArguments(bundle);
            FragmentManager fmanager = getFragmentManager();
            fmanager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            drawerList.setItemChecked(position, true);
            setTitle(infoList.get(position).getItemNome());
        }



        drawerLayout.closeDrawer(drawerList);
    }


    @Override
    public void setTitle(CharSequence title){
        this.title = title;
        getSupportActionBar().setTitle(this.title);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Como Calcular");
            alertDialog.setMessage(getString(R.string.msg1));
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setIcon(R.drawable.icon_alert_t);
            alertDialog.show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.msg1), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // passa a configuracao pro drawerTootle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }


}
