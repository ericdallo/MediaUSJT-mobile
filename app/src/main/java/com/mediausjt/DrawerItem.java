package com.mediausjt;
/**
 * Created by eric on 08/03/15.
 */

public class DrawerItem {

    String itemNome;
    int imgID;
    //boolean isSwitch;


    public DrawerItem(String nome, int ID) {
        super();
        itemNome = nome;
        this.imgID = ID;
    }

   /* public DrawerItem(String nome,int ID,boolean isSwitch) {
        super();
        this.itemNome = nome;
        this.isSwitch = isSwitch;
        this.imgID = ID;
    }*/

    public String getItemNome() {
        return itemNome;
    }
    public void setItemNome(String nome) {
        this.itemNome = nome;
    }
    public int getImgID() {
        return imgID;
    }
    public void setImgID(int imgID) {
        this.imgID = imgID;
    }
    /*
    public boolean isSwitch(){
        return this.isSwitch;
    }*/

}