package com.mediausjt.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mediausjt.Grade.Grade;

import java.util.ArrayList;
import java.util.List;

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

    public Cursor getGrade(int id) {
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

    public void dropAllGrades() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTAS_TABLE_NAME,null,null);
    }

    public List<Grade> getAllNotas(){
        List<Grade> gradeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from notas",null);
        res.moveToFirst();
        Grade grade;
        while(!res.isAfterLast()){
            grade = new Grade();
            grade.setId(res.getInt(res.getColumnIndex(NOTAS_COLUMN_ID)) );
            grade.setMatter(res.getString(res.getColumnIndex(NOTAS_COLUMN_MATERIA)));
            grade.setValue(res.getString(res.getColumnIndex(NOTAS_COLUMN_NOTA)));
            gradeList.add(grade);
            res.moveToNext();
        }
        return gradeList;
    }
}
