package com.example.talha.havadurumum;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by M.TaLha on 25.1.2017.
 */

public class dbyonetim extends SQLiteOpenHelper {
    final private String dbadi="KullaniciGirdi";
    final private String Kadi="Ad";
    final private String Ksoyadi="Soyad";


    public dbyonetim(Context context){
        super(context, "KullaniciGirdi" , null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table contacts " +
                        "("+Kadi+" nvarchar(50), "+Ksoyadi+" nvarchar(50))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertContact (String name, String soyad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Kadi, name);
        contentValues.put(Ksoyadi, soyad);
        db.insert("contacts", null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts ", null );
        return res;
    }
}
