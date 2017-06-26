package com.example.peo.tspb;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Obl on 2017/6/26.
 * com.example.peo.tspb
 */

public class ToastUtils {
    private static Toast toast;

    public static void showQuckToast(Context context, Object msg) {
        setToast(context, msg+"");
    }

    public static void setToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
