package com.appteam.nimbus;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    private static final String NAME_FOLDER="Nimbus";
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
    public static boolean checkData(String string){
        return  !string.isEmpty()&&string.trim().length()!=0;

    }
    public static void SaveImage(Bitmap bitmap, String filename) {
        File folder= Environment.getExternalStoragePublicDirectory(NAME_FOLDER);
        if(!folder.exists()) {
            Log.d("folder created","folder created");
            folder.mkdir();
        }

        File file=new File(folder,filename+".png");
        if (file.exists()){
            return;
        }
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Log.d("FileSave","File "+file.getName()+" has saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap GetImage(String filename){

        File file=new File(Environment.getExternalStoragePublicDirectory(NAME_FOLDER),filename+".png");
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(),options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,100,100);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath(),options);
        Log.d("SuccessFile","Success in getting "+file.getName());
        return bitmap;
    }
    public static boolean check(String filename){
        File file=new File(Environment.getExternalStoragePublicDirectory(NAME_FOLDER),filename+".png");
        if(file.exists())
            return true;
        return false;
    }
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
