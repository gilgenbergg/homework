package ru.android_2019.citycam.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;

public class Cam {
    private long id;
    private String title;
    private URL imageUrl;
    private Bitmap bitmap;

    public Cam(long id, String title, URL imageUrl) throws IOException {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.bitmap = BitmapFactory.decodeStream(this.imageUrl.openStream());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId () {
        return id;
    }

    public Bitmap getImage() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
