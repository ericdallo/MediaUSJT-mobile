package com.mediausjt.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mediausjt.Grade.Grade;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NotasDB.db";
    public static final String NOTAS_TABLE_NAME = "notas";
    public static final String NOTAS_COLUMN_ID = "id";
    public static final String NOTAS_COLUMN_MATERIA = "materia";
    public static final String NOTAS_COLUMN_NOTA = "nota";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table notas (id integer primary key, materia text,nota text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notas");
        onCreate(db);
    }

    public boolean inserirNota(String materia,String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("materia", materia);
        contentValues.put("nota", nota);

        db.insert("notas", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from notas where id=" + id + "",null);

        return res;
    }


    public boolean atualizarNota(Integer id, String materia, String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("materia", materia);
        contentValues.put("nota", nota);
        db.update("notas", contentValues, "id = ? ",new String[] { Integer.toString(id) });
        return true;
    }

    public Integer deletarNota(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("notas", "id = ? ", new String[] { Integer.toString(id) }); //TODO Arrumar o mais 1
    }

    public void deletarTodasNotas() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTAS_TABLE_NAME,null,null);
    }

    public ArrayList<Grade> getAllNotas(){
        ArrayList<Grade> array_list = new ArrayList<Grade>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from notas",null);
        res.moveToFirst();
        Grade grade;
        while(!res.isAfterLast()){
            grade = new Grade(res.getInt(res.getColumnIndex(NOTAS_COLUMN_ID)),res.getString(res.getColumnIndex(NOTAS_COLUMN_MATERIA)),res.getString(res.getColumnIndex(NOTAS_COLUMN_NOTA)));
            array_list.add(grade);
            res.moveToNext();
        }
        return array_list;
    }
}
