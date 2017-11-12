package com.example.feroz.randomnews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by feroz on 16-Jan-16.
 */
public class CheckInternet {

        private Context _context;

        public CheckInternet(Context context){
            this._context = context;
        }

        public boolean isOK(){

                ConnectivityManager connectivityManager
                        = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();

        }

}
