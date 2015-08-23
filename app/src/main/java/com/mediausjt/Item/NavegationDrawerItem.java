package com.mediausjt.Item;

public class NavegationDrawerItem {

    String itemNome;
    int imgID;

    public NavegationDrawerItem(String nome, int ID) {
        itemNome = nome;
        this.imgID = ID;
    }

    public String getItemNome() {
        return itemNome;
    }
    public int getImgID() {
        return imgID;
    }

}
