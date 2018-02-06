package com.ucb.appin.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * Created by Juan choque on 2/4/2018.
 */

public class UtilInmo {
    private Context context;

    public UtilInmo() {
    }

    public UtilInmo(Context context) {
        this.context = context;
    }

    public String getNumberPhone() {
        String number = null;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            number = telephonyManager.getSimSerialNumber();
        }

        return number;
    }

}

