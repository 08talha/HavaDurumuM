package com.example.talha.havadurumum;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView yer, havaDetayi, havaninDurumu, derece, nemOrani, ulkeKodu,fonksiyonel;
    OmdbService omdbService;
    HavaDurumu hvDrumu;
    ImageView imgIcon;
    String  yeri;
    String ukodunu;
    String havadetayi;
    String havanindurumu;
    String derecee;
    double nemorani;
    String API_KEY = "a26d26aa2d7b42b27524a3fe389b136f";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1905;
    String mesaj;
    String mesajl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        if (!checkAndRequestPermissions()) {
            return;
        }
        final MsgDb mbdata = new MsgDb(getApplicationContext());


        /*mbdata.insertContact("Hava güzel hazırlan fırla sahiller seni bekler :D",
                "Ara ara göz at dışarıya okul tatil olsun diye de dua et :D",
                "Yağmur yağayi yağmur, yaylanun çimenine, Al şemsuyeni yanuna…",
                "Yol neredeyse yok eğlenecek bir şeyde yok gibi kardeş.");

        mbdata.insertContact("Hava güzel hazırlan fırla sahiller seni bekler :D",
                "Ara ara göz at dışarıya okul tatil olsun diye de dua et :D",
                "Yağmur yağayi yağmur, yaylanun çimenine, Al şemsuyeni yanuna…",
                "Yol neredeyse yok eğlenecek bir şeyde yok gibi kardeş.");*/

        //konum veri tipleri
        final String gpsAcildi = "GPS Acıldı";
        final String gpsKapatildi = "GPS Kapalı";


        boolean havaDrumuinsert = preferences.getBoolean("havaDrumuinsert",false);
        if(!havaDrumuinsert){
            mbdata.insertContact("Hava güzel hazırlan fırla sahiller seni bekler :D",
                    "Ara ara göz at dışarıya okul tatil olsun diye de dua et :D",
                    "Yağmur yağayi yağmur, yaylanun çimenine, Al şemsuyeni yanuna…",
                    "Yol neredeyse yok eğlenecek bir şeyde yok gibi kardeş.");

            mbdata.insertContact("Hava güzel çık gez Sahiller seni bekler :D",
                    "Ara ara göz at dışarıya okul tatil olsun diye de dua et :D",
                    "Yağmur rahmettir. Gelen rahmete bak da huzur bulalım:)",
                    "Sis Bastırdı dağın ardı gözükmez oldu...");
            mbdata.insertContactKonum(48.992433,27.748428);
            editor.putBoolean("havaDrumuinsert", true);
            editor.commit();
        }


        Button btn = (Button) findViewById(R.id.KonumAl);
        fonksiyonel=(TextView)findViewById(R.id.fonksiyonel);
        yer = (TextView) findViewById(R.id.yer);
        ulkeKodu = (TextView) findViewById(R.id.UlkeKodu);
        havaninDurumu = (TextView) findViewById(R.id.havaninDurumu);
        derece = (TextView) findViewById(R.id.derece);
        havaDetayi = (TextView) findViewById(R.id.havaDetayi);
        nemOrani = (TextView) findViewById(R.id.nemOrani);

        final double[] latitude = new double[1]; // latitude
        final double[] longitude = new double[1];
        final boolean isNetworkEnabled = false;



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long UZAKLIK_DEGISIMI = 20;
                final long SURE = 1000 * 0;

                Location location;

                boolean isNetworkEnabled = false;

                LocationManager konumYoneticisi = (LocationManager) getSystemService(LOCATION_SERVICE);
                isNetworkEnabled = konumYoneticisi
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                LocationListener konumDinleyicisi = new LocationListener() {
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        //Toast.makeText(getApplicationContext(), gpsAcildi, Toast.LENGTH_SHORT).show();
                        fonksiyonel.setText(gpsAcildi);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        //Toast.makeText(getApplicationContext(), gpsKapatildi, Toast.LENGTH_SHORT).show();
                        fonksiyonel.setText(gpsKapatildi);
                    }

                    @Override
                    public void onLocationChanged(Location loc) {
                        latitude[0] = loc.getLatitude();
                        longitude[0]= loc.getLongitude();
                        mbdata.updateContact(1,latitude[0],longitude[0]);


                    }
                };

                Criteria kriter = new Criteria();
                kriter.setAccuracy(Criteria.ACCURACY_COARSE);
                kriter.setAltitudeRequired(false);
                kriter.setSpeedRequired(false);
                kriter.setPowerRequirement(Criteria.POWER_MEDIUM);
                kriter.setCostAllowed(false);
                String bilgiSaglayici = konumYoneticisi.getBestProvider(kriter, true);
                if (bilgiSaglayici == null) {
                    List<String> bilgiSaglayicilar =
                            konumYoneticisi.getAllProviders();
                    for (String tempSaglayici : bilgiSaglayicilar) {
                        if (konumYoneticisi.isProviderEnabled(tempSaglayici))
                            bilgiSaglayici = tempSaglayici;
                    }
                }

                if (isNetworkEnabled) {
                    fonksiyonel.setText("isNetworkEnabled ");
                    konumYoneticisi.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, konumDinleyicisi);
                    if (konumYoneticisi != null) {
                        location = konumYoneticisi.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            fonksiyonel.setText("location boş ");
                            latitude[0] = location.getLatitude();
                            longitude[0] = location.getLongitude();
                            mbdata.updateContact(1,latitude[0],longitude[0]);

                        }
                    }
                }else
                    konumYoneticisi.requestLocationUpdates(bilgiSaglayici, SURE, UZAKLIK_DEGISIMI, konumDinleyicisi);
                mbdata.updateContact(1,latitude[0],longitude[0]);
                havaDrumuCagirma(latitude[0],longitude[0]);
                mesajl=mesajGet();
                fonksiyonel.setText(mesajl);

            }
        });
        Cursor rs = mbdata.getDataKonum(1,"latitude");
        rs.moveToFirst();
        latitude[0] = rs.getDouble(rs.getColumnIndex(mbdata.CONTACTS_COLUMN_lat));

        Cursor rs1 = mbdata.getDataKonum(1,"longitude");
        rs1.moveToFirst();
        longitude[0] = rs1.getDouble(rs1.getColumnIndex(mbdata.CONTACTS_COLUMN_lon));



        havaDrumuCagirma(latitude[0],longitude[0]);

    }
    public void havaDrumuCagirma(double latitude,double longitude) {
        omdbService = ServiceBaglanti.connectHava().create(OmdbService.class);
        Call<HavaDurumu> havDurumu = omdbService.getSyss(latitude, longitude, "metric", API_KEY);
        havDurumu.enqueue(havaDrumuCallBack);
    }
    private Callback<HavaDurumu> havaDrumuCallBack=new Callback<HavaDurumu>(){

        @Override

        public void onResponse(Call<HavaDurumu> call, Response<HavaDurumu> response) {

            yer = (TextView) findViewById(R.id.yer);
            ulkeKodu = (TextView) findViewById(R.id.UlkeKodu);
            havaninDurumu = (TextView) findViewById(R.id.havaninDurumu);
            derece = (TextView) findViewById(R.id.derece);
            havaDetayi = (TextView) findViewById(R.id.havaDetayi);
            nemOrani = (TextView) findViewById(R.id.nemOrani);
            fonksiyonel=(TextView)findViewById(R.id.fonksiyonel);
            if(response.isSuccessful()){
                hvDrumu=response.body();
                final MsgDb mbdata = new MsgDb(getApplicationContext());
                List<Weather> list = new ArrayList<>();
                yeri=hvDrumu.getName();
                ukodunu =hvDrumu.getSys().getCountry();
                derecee= String.valueOf((int) hvDrumu.getMain().getTemp());

                havadetayi=hvDrumu.getWeather().get(0).getMain();
                nemorani=hvDrumu.getMain().getHumidity();
                Date simdikiZaman = new Date();
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String simdikizaman;
                simdikizaman=df.format(simdikiZaman);
                Cursor rs = mbdata.getDataKonum(1,"latitude");
                rs.moveToFirst();
                double latitude = rs.getDouble(rs.getColumnIndex(mbdata.CONTACTS_COLUMN_lat));

                mesajl=mesajGet();

                fonksiyonel.setText(mesajl);
            }
            else{
                Log.e("MainActivity", "Response received but request not successful. Response: " + response.raw());
            }
            yer.setText(yeri);
            derece.setText(derecee);
            ulkeKodu.setText(ukodunu);
            havaninDurumu.setText(havanindurumu);
            havaDetayi.setText(havadetayi);
            nemOrani.setText("nem %"+nemorani);
        }

        @Override
        public void onFailure(Call<HavaDurumu> call, Throwable t) {
            Log.e("MainActivity", "Request error!");
        }
    };
    public String mesajGet(){
        fonksiyonel=(TextView)findViewById(R.id.fonksiyonel);
        imgIcon=(ImageView)findViewById(R.id.imageView);

        final MsgDb mbdata = new MsgDb(getApplicationContext());

        if(havadetayi.equals("Clear") && Integer.parseInt(derecee)>20) {
            Cursor rs = mbdata.getData(1, "gunesli");

            rs.moveToFirst();

            mesaj = rs.getString(rs.getColumnIndex(mbdata.CONTACTS_COLUMN_Gunesli));
            imgIcon.setImageResource(R.mipmap.gunes);

        }
        else if(havadetayi.equals("Rain" )) {
            Cursor rs = mbdata.getData(1,  "yagmurlu");

            rs.moveToFirst();

            mesaj = rs.getString(rs.getColumnIndex(mbdata.CONTACTS_COLUMN_Yahmurlu));
            imgIcon.setImageResource(R.mipmap.yagmurlu);
        }
        else if(havadetayi.equals("Snow")) {
            Cursor rs = mbdata.getData(1, "karli");

            rs.moveToFirst();

            mesaj = rs.getString(rs.getColumnIndex(mbdata.CONTACTS_COLUMN_Karli));
            imgIcon.setImageResource(R.mipmap.karyagislii);

        }else if(havadetayi.equals("Clouds")){
            Cursor rs = mbdata.getData(1, "sisli");

            rs.moveToFirst();

            mesaj = rs.getString(rs.getColumnIndex(mbdata.CONTACTS_COLUMN_Sisli));
            imgIcon.setImageResource(R.mipmap.bulutlu);
        }else if(havadetayi.equals("     ")){
            imgIcon.setImageResource(R.mipmap.rain);

        }
        else{
            mesaj="hava durumuna yönelik mesajı en kısa sürede ekliyeceğiz ";
        }

        return mesaj;
    }


    private boolean checkAndRequestPermissions(){
        int permissionINTERNET = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET);
        int permissionACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
        int permissionACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionWRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionACCESS_WIFI_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_WIFI_STATE);
        int permissionCHANGE_WIFI_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CHANGE_WIFI_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionINTERNET != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
        }
        if (permissionACCESS_NETWORK_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (permissionACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionINTERNET != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
        }

        if (permissionWRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionACCESS_WIFI_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (permissionCHANGE_WIFI_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

}
