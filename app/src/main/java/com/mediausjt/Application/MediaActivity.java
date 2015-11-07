package com.mediausjt.Application;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mediausjt.Adapter.CustomDrawerAdapter;
import com.mediausjt.Fragment.AverageFragment;
import com.mediausjt.Fragment.GradesFragment;
import com.mediausjt.Fragment.WeightFragment;
import com.mediausjt.Item.NavegationDrawerItem;
import com.mediausjt.R;
import com.mediausjt.Util.MediaConfig;
import com.mediausjt.util.FragmentUtil;
import com.mediausjt.util.MediaDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MediaActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.left_drawer)
    ListView drawerList;

    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitulo;
    private CharSequence title;

    private List<NavegationDrawerItem> infoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MediaConfig(this);

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (MediaConfig.notContainsPreference("PrimeiraVez")) {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Como Calcular");
                alertDialog.setMessage(getString(R.string.how_to_message));
                alertDialog.setButton("OK", (dialog, which) -> {
                    MediaConfig.savePreference("PrimeiraVez", true);
                });
                alertDialog.setIcon(R.drawable.icon_alert_info);
                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.how_to_message), Toast.LENGTH_LONG).show();
            }
        }

        title = drawerTitulo = getTitle();

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);

        // adiciona o item no arraylist
        infoList.add(new NavegationDrawerItem("Média", R.drawable.calculator));
        infoList.add(new NavegationDrawerItem("Notas", R.drawable.notes));
        infoList.add(new NavegationDrawerItem("Peso", R.drawable.pesos));
        infoList.add(new NavegationDrawerItem("Informações", R.drawable.info));
        infoList.add(new NavegationDrawerItem("Contato", R.drawable.contato));

        CustomDrawerAdapter adapter = new CustomDrawerAdapter(R.layout.custom_list_drawer, infoList);

        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener((parent, view, position, id) -> selectItem(position));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitulo);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    public void selectItem(int position) {
        Fragment fragment = null;

        boolean isFrag;
        switch (position) {
            case 0:
                fragment = new AverageFragment();
                isFrag = true;
                break;
            case 1:
                fragment = new GradesFragment();
                isFrag = true;
                break;
            case 2:
                fragment = new WeightFragment();
                isFrag = true;
                break;
            case 3:   //Informacoes
                MediaDialog.showDialog(this, getString(R.string.how_to_title), getString(R.string.how_to_message));
                isFrag = false;
                break;
            case 4: // Contato
                MediaDialog.showDialog(this, getString(R.string.contact_title), getString(R.string.contact_message));
                isFrag = false;
                break;
            default:
                isFrag = false;
                break;
        }
        if (isFrag) {
            FragmentManager fmanager = getSupportFragmentManager();
            fmanager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            drawerList.setItemChecked(position, true);
            setTitle(infoList.get(position).getName());
        }

        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(this.title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        MediaDialog.showDialog(this, getString(R.string.how_to_title), getString(R.string.how_to_message));
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(FragmentUtil.isEditGrade){
            MediaConfig.goToAverage();
            FragmentUtil.isEditGrade = false;
            return;
        }
        if(FragmentUtil.isLogic){
            super.onBackPressed();
        }
        FragmentUtil.showLogic();
    }
}
