package com.mediausjt;

/**
 * Created by eric on 08/03/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;




public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

    private Context context;
    private List<DrawerItem> drawerItens;
    private int layoutResID;
    private MainActivity mainActivity;

    public CustomDrawerAdapter(Context context, int layoutResourceID,List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItens = listItems;
        this.layoutResID = layoutResourceID;
    }

    public void setMainActivity(MainActivity mainActivity){ // TODO Trocar pra fazer o casting do context pro mainActivity
        this.mainActivity = mainActivity;
    }
    public MainActivity getMainActivity(){
        return this.mainActivity;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.textlayout = (LinearLayout) view.findViewById(R.id.text_layout);
            //drawerHolder.switchLayout = (LinearLayout) view.findViewById(R.id.switch_layout);

            drawerHolder.itemName = (TextView) view.findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            //drawerHolder.iconSwitch = (ImageView) view.findViewById(R.id.drawer_switch_icon);
            //drawerHolder.itemNameSwitch = (TextView) view.findViewById(R.id.drawer_itemName_switch);
            //drawerHolder.aSwitch = (Switch) view.findViewById(R.id.drawer_switch);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerItem dItem = this.drawerItens.get(position);

        /*if(dItem.isSwitch()) {
            drawerHolder.textlayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.switchLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.iconSwitch.setImageDrawable(view.getResources().getDrawable(dItem.getImgID()));
            drawerHolder.itemNameSwitch.setText(dItem.getItemNome());
            drawerHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) //clicou e ficou true
                        getMainActivity().setAmi(true);
                    else
                        getMainActivity().setAmi(true);

                }
            });
        }else{*/
            drawerHolder.textlayout.setVisibility(LinearLayout.VISIBLE);
            //drawerHolder.switchLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgID()));
            drawerHolder.itemName.setText(dItem.getItemNome());
        //}
        return view;
    }



    private static class DrawerItemHolder {
        LinearLayout textlayout,switchLayout;
        TextView itemName,itemNameSwitch;
        ImageView icon,iconSwitch;
        //Switch aSwitch;
    }
}
