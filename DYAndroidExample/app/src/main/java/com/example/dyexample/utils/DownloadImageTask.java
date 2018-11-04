package com.example.dyexample.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView mImageview;

    public DownloadImageTask(ImageView imageview) {
        this.mImageview = imageview;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap imageBitmap = null;

        try {

            InputStream in = new java.net.URL(urldisplay).openStream();
            imageBitmap = BitmapFactory.decodeStream(in);

        } catch (Exception e) {

        }

        return imageBitmap;
    }

    protected void onPostExecute(Bitmap result) {

        mImageview.setImageBitmap(result);
    }
}