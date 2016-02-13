package com.appteam.nimbus;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utils {
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
    public static boolean checkData(String string){
        return  !string.isEmpty()&&string.trim().length()!=0;
    }
}
