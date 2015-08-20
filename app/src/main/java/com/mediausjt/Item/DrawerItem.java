package com.mediausjt.Item;

public class DrawerItem {

    String itemNome;
    int imgID;
    //boolean isSwitch;

    public DrawerItem(String nome, int ID) {
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
    public void setItemNome(String nome){
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
