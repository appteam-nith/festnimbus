package com.appteam.nimbus;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class Waiting {
    public static class SaveImage extends AsyncTask<Void,Void,Void>{
    private     Bitmap bitmap;
     private    String string;

        public SaveImage(Bitmap bitmap, String string) {
            this.bitmap = bitmap;
            this.string = string;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Utils.SaveImage(bitmap,string);
           return null;
        }
    }
    public static class GetImage extends AsyncTask<Void,Void,Bitmap>{
      private String string;
        private ImageView imageView;
        public GetImage( String string,ImageView imageView) {
            this.string = string;
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return Utils.GetImage(string);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

    }
}
