package com.example.talha.havadurumum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by M.TaLha on 3.12.2016.
 */

public class GirdiSayfasi extends AppCompatActivity   {
    EditText adetxt2,soyadetxt3,dgmetxt4;
    Button kydtbtn,dgstrbtn;
    TextView hobitxt1,adtxt2,soyadtxt3,dgmtxt4,textView3;
    CheckBox futbol,voleybol,basketbol,onlineOyun,gezmek,kitapOkumak,kampYapmak;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.soru_ekrani);
        kydtbtn=(Button) findViewById(R.id.button4);

        adetxt2=(EditText)findViewById(R.id.editText5);
        soyadetxt3=(EditText)findViewById(R.id.editText6);
        dgmetxt4=(EditText)findViewById(R.id.editText7);




        final String[][] kayit = {{" "}};

        hobitxt1 = (TextView) findViewById(R.id.textView6);
        adtxt2 = (TextView) findViewById(R.id.textView2);
        soyadtxt3 = (TextView) findViewById(R.id.textView4);
        dgmtxt4 = (TextView) findViewById(R.id.textView5);

        final MsgDb mbdata = new MsgDb(getApplicationContext());



        kydtbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();

                String veriler=" "+ adetxt2.getText()+" "+soyadetxt3.getText()+" "+dgmetxt4.getText();
                kayit[0] =veriler.split(" ");
                adetxt2.setEnabled(false);
                soyadetxt3.setEnabled(false);
                dgmetxt4.setEnabled(false);


                Intent girdisaydasinagit = new Intent(GirdiSayfasi.this, MainActivity.class);
                startActivity(girdisaydasinagit);
                editor.putBoolean("GirdiSayfasi", true);
                editor.commit();
                mbdata.insertContact("Hava güzel hazırlan fırla sahiller seni bekler :D",
                        "Ara ara göz at dışarıya okul tatil olsun diye de dua et :D",
                        "Yağmur yağayi yağmur, yaylanun çimenine, Al şemsuyeni yanuna…",
                        "Yol neredeyse yok eğlenecek bir şeyde yok gibi kardeş.");


            }
        });
        dgstrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hobitxt1.setText("");
                adtxt2.setText("");
                soyadtxt3.setText("");
                dgmtxt4.setText("");
                adetxt2.setEnabled(true);
                soyadetxt3.setEnabled(true);
                dgmetxt4.setEnabled(true);

            }
        });




    }
}
