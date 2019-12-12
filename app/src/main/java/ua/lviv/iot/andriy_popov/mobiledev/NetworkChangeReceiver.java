package ua.lviv.iot.andriy_popov.mobiledev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if(checkInternet(context))
        {
            Toast.makeText(context, "Network Available Do operations", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Network NOT Available Do operations", Toast.LENGTH_LONG).show();
        }

    }

    boolean checkInternet(Context context) {

        if (isNetworkAvailable(context)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}