package com.mediausjt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mediausjt.item.NavegationDrawerItem;
import com.mediausjt.R;
import com.mediausjt.util.MediaConfig;

import java.util.List;

public class CustomDrawerAdapter extends ArrayAdapter<NavegationDrawerItem> {

    private List<NavegationDrawerItem> drawerItens;
    private int layoutResID;

    public CustomDrawerAdapter(int layoutResourceID, List<NavegationDrawerItem> listItems) {
        super(MediaConfig.getActivity(), layoutResourceID, listItems);
        this.drawerItens = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = MediaConfig.getActivity().getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.textlayout = (LinearLayout) view.findViewById(R.id.text_layout);

            drawerHolder.itemName = (TextView) view.findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        NavegationDrawerItem dItem = this.drawerItens.get(position);
        drawerHolder.textlayout.setVisibility(LinearLayout.VISIBLE);
        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getId()));
        drawerHolder.itemName.setText(dItem.getName());
        return view;
    }

    private static class DrawerItemHolder {
        LinearLayout textlayout;
        TextView itemName;
        ImageView icon;
    }
}
