package com.example.todeolho.myapplication.classes;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by alan_ on 20/06/2016.
 */
public class Util {

    public static boolean VerificaConexaoInternet(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            String LogSync = null;
            String LogToUserTitle = null;
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return true;
            } else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
