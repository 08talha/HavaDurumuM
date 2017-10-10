package com.example.talha.havadurumum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by M.TaLha on 1.2.2017.
 */

public class MsgDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Dbmsg.db";

    //tablo adları
    public static final String CONTACTS_TABLE_NAME = "db20";
    public static final String CONTACTS_TABLE_Konum="konum";
    public static final String CONTACTS_TABLE_NAME2 = "db30";
    public static final String CONTACTS_TABLE_NAME3 = "db35";
    public static final String CONTACTS_TABLE_NAME4 = "db40";

    public static final String CONTACTS_TABLE_Veriler = "GhavaDurumu";

    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_Gunesli = "gunesli";
    public static final String CONTACTS_COLUMN_Karli = "karli";
    public static final String CONTACTS_COLUMN_Yahmurlu = "yagmurlu";
    public static final String CONTACTS_COLUMN_Sisli = "sisli";
    public static final String CONTACTS_COLUMN_lat = "latitude";
    public static final String CONTACTS_COLUMN_lon = "longitude";

    public static final String CONTACTS_COLUMN_Derece = "derece";
    public static final String CONTACTS_COLUMN_Yeri = "yeri";
    public static final String CONTACTS_COLUMN_Ukodunu = "Ukodunu";
    public static final String CONTACTS_COLUMN_havadetayi = "havadetayi";
    public static final String CONTACTS_COLUMN_nemorani = "nemorani";
    public static final String CONTACTS_COLUMN_dt= "dTarih";

    public static String CONTACTS_TABLE_UPdate="konum";
    public MsgDb(Context context){
        super(context, DATABASE_NAME , null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE "+CONTACTS_TABLE_NAME +
                        "(id integer primary key, gunesli text,karli text,yagmurlu text, sisli text)"
        );
        db.execSQL(
                "CREATE TABLE "+CONTACTS_TABLE_NAME2 +
                        "(id integer primary key, gunesli text,karli text,yagmurlu text, sisli text)"
        );
        db.execSQL(
                "CREATE TABLE "+CONTACTS_TABLE_NAME3 +
                        "(id integer primary key, gunesli text,karli text,yagmurlu text, sisli text)"
        );
        db.execSQL(
                "CREATE TABLE "+CONTACTS_TABLE_NAME4 +
                        "(id integer primary key, gunesli text,karli text,yagmurlu text, sisli text)"
        );
        db.execSQL(
                "CREATE TABLE "+CONTACTS_TABLE_Konum +
                        "(id integer,latitude double,longitude double)"
        );
       /*db.execSQL(
                "CREATE TABLE "+CONTACTS_TABLE_UPdate +
                        "(id integer,derece double,yeri text,Ukodunu text,havadetayi text,nemorani text,dTarih text)"
        );*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.e(CONTACTS_COLUMN_lat, "Error is here");
        Log.e(CONTACTS_COLUMN_lon, "Error is here");

        db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_TABLE_UPdate);
        onCreate(db);

    }
    public boolean insertContact (String gunesli, String karli, String yagmurlu, String sisli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gunesli", gunesli);
        contentValues.put("karli", karli);
        contentValues.put("yagmurlu", yagmurlu);
        contentValues.put("sisli", sisli);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;

    }

    public boolean insertContact2 (String gunesli, String karli, String yagmurlu, String sisli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gunesli", gunesli);
        contentValues.put("karli", karli);
        contentValues.put("yagmurlu", yagmurlu);
        contentValues.put("sisli", sisli);
        db.insert(CONTACTS_TABLE_NAME2, null, contentValues);
        return true;

    }
    public boolean insertContact3 (String gunesli, String karli, String yagmurlu, String sisli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gunesli", gunesli);
        contentValues.put("karli", karli);
        contentValues.put("yagmurlu", yagmurlu);
        contentValues.put("sisli", sisli);
        db.insert(CONTACTS_TABLE_NAME3, null, contentValues);
        return true;

    }
    public boolean insertContact4 (String gunesli, String karli, String yagmurlu, String sisli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gunesli", gunesli);
        contentValues.put("karli", karli);
        contentValues.put("yagmurlu", yagmurlu);
        contentValues.put("sisli", sisli);
        db.insert(CONTACTS_TABLE_NAME4, null, contentValues);
        return true;
    }
    public boolean insertContactKonum(double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 1);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        db.insert(CONTACTS_TABLE_Konum, null, contentValues);
        return true;
    }

    public Cursor getData(int id,String drm) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+ drm +" from "+CONTACTS_TABLE_NAME+" where id="+id, null );
        return res;
    }
    public Cursor getData2(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res2 =  db.rawQuery( "select * from "+CONTACTS_TABLE_NAME2+" where id="+id+"", null );
        return res2;
    }
    public Cursor getData3(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res3 =  db.rawQuery( "select * from "+CONTACTS_TABLE_NAME3+" where id="+id+"", null );
        return res3;
    }
    public Cursor getData4(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res4 =  db.rawQuery( "select * from "+CONTACTS_TABLE_NAME4+" where id="+id+"", null );
        return res4;
    }
    public Cursor getDataKonum(int id,String drm) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resk =  db.rawQuery( "select "+drm+" from "+CONTACTS_TABLE_Konum+" where id="+id+"", null );
        return resk;
    }

    public boolean updateContact (int id, double latitude,double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery="UPDATE "+CONTACTS_TABLE_Konum+" SET  "+CONTACTS_COLUMN_ID+"= "+id+
                ", "+CONTACTS_COLUMN_lat+"= "+latitude+", "+CONTACTS_COLUMN_lon+"= "+longitude;
        db.execSQL(updateQuery);

        return true;
    }




}
