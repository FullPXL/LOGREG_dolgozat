package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBaseHelper extends SQLiteOpenHelper {
    public  static final  String DB_NAME= "felhasznalo.db";
    public  static final int  DB_VERSION = 1;

    public static final String FELHASZNALO_TABLE = "felhasznalo";
    public static final String COL_ID = "id";
    public static final String COL_EMAIL = "email";
    public static final String COL_FELHNEV = "felhnev";
    public static final String COL_JELSZO = "jelszo";
    public static final String COL_TELJESNEV = "teljesnev";

    public DBaseHelper(Context context) {super (context, DB_NAME, null, DB_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + FELHASZNALO_TABLE + " ("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " VARCHAR(255) NOT NULL, " +
                COL_FELHNEV + " VARCHAR(255) NOT NULL, " +
                COL_JELSZO + " VARCHAR(255) NOT NULL, " +
                COL_TELJESNEV + " VARCHAR(255) NOT NULL " +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + FELHASZNALO_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor adatLekerdezes(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+FELHASZNALO_TABLE,null);
    }

    public Cursor belepo(String nev, String jelszo){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT "+ COL_TELJESNEV +" FROM "+FELHASZNALO_TABLE +" WHERE (" + COL_FELHNEV + " = ? OR " +COL_EMAIL + " = ?) AND " + COL_JELSZO +" = ?", new String[]{nev, nev, jelszo});
    }

    public boolean emailEllenorzes(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + FELHASZNALO_TABLE + " WHERE " + COL_EMAIL + " = ?", new String[]{email});
        return result.getCount() == 1;
    }

    public boolean felhasznaloEllenorzes(String felhasznalo, String jelszo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + FELHASZNALO_TABLE + " WHERE (" + COL_FELHNEV + " = ? OR " +COL_EMAIL + " = ?) AND " + COL_JELSZO +" = ?", new String[]{felhasznalo, felhasznalo, jelszo});
        return result.getCount() == 1;
    }



    public boolean adatRogzites(String email, String fnev, String jelszo, String tnev) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_FELHNEV, fnev);
        values.put(COL_JELSZO, jelszo);
        values.put(COL_TELJESNEV, tnev);
        return db.insert(FELHASZNALO_TABLE, null, values) != -1;

    }
}
