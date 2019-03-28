package ru.android_2019.citycam.webcams;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import ru.android_2019.citycam.model.Cam;

import static android.content.ContentValues.TAG;

public class NewConnection {

    private static final String KEY_HEADER_NAME = "X-RapidAPI-Key";
    private static final String RAPID_API_KEY = "0faed0ab1emshcfb6420045a8f75p19e252jsn9b309eb036ca";

    public static HttpURLConnection getConnection (URL url) throws IOException {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty(KEY_HEADER_NAME, RAPID_API_KEY);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private NewConnection(URL url) {

    }
}
