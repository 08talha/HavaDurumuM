package com.example.talha.havadurumum.Bildirim;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by talha on 14.08.2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
        Log.d("TOKEN Verildi", token);
    }

    private void registerToken(String token){

    }
}
