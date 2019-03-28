package ru.android_2019.citycam.webcams;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import ru.android_2019.citycam.Callbacks;
import ru.android_2019.citycam.CityCamActivity;
import ru.android_2019.citycam.model.Cam;

public class CamTask extends AsyncTask<URL, Integer, Cam> {
    
    @SuppressLint("StaticFieldLeak")
    private CityCamActivity cityCamActivity;
    private Cam cam;
    private Callbacks callbacks;

    public CamTask(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void attachActivity(CityCamActivity cityCamActivity) {
        this.cityCamActivity = cityCamActivity;
        updateView();
    }

    private void updateView() {

    }

    @Override
    protected Cam doInBackground(URL... urls) {
        Cam cam = null;
        int amount = urls.length;
        for (int i=0; i < amount; i++) {
            try {
                cam = getCamByURL(urls[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        return cam;
    }

    private Cam getCamByURL(URL url) throws IOException {
        Cam cam = null;
        HttpURLConnection connection = null;
        InputStream input = null;
        try {
            connection = NewConnection.getConnection(url);
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException();
            }
            input = connection.getInputStream();
            List<Cam> cams = JsonParser.getAllCams(input, "UTF-8");
            cam = cams != null && !cams.isEmpty() ? cams.get(new Random().nextInt(cams.size())) : null;
        }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
            if (input != null) {
                input.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return cam;
    }

    @Override
    protected void onPostExecute(Cam res) {
        callbacks.onPostExecute(res);
        callbacks.onProgressUpdate(100);
    }

    @Override
    protected void onProgressUpdate(Integer... percent) {
        super.onProgressUpdate(percent);
        callbacks.onProgressUpdate(percent[0]);
    }
}
