package ru.android_2019.citycam.webcams;

import android.net.Uri;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Константы для работы с Webcams API
 */
public final class Webcams {

    // Зарегистрируйтесь на http://ru.webcams.travel/developers/
    // и вставьте сюда ваш devid
    private static final String DEV_ID = "Ваш devid";
    private static final String BASE_URL = "https://webcamstravel.p.rapidapi.com/webcams/";

    private static final String PARAM_DEVID = "devid";
    private static final String PARAM_METHOD = "method";
    private static final String PARAM_LAT = "lat";
    private static final String PARAM_LON = "lng";
    private static final String PARAM_FORMAT = "format";

    //rapidAPI params
    private static final String PARAM_LANG = "lang";
    private static final String LANG = "en";
    private static final String PARAM_SHOW = "show";
    private static final String SHOW = "webcams:image,location,statistics";

    private static final String METHOD_NEARBY = "list/nearby";
    private static final String RADIUS = "50";

    private static final String FORMAT_JSON = "json";

    /**
     * Возвращает URL для выполнения запроса Webcams API для получения
     * информации о веб-камерах рядом с указанными координатами в формате JSON.
     */
    public static URL createNearbyUrl(double latitude, double longitude)
            throws MalformedURLException {
        Uri uri = Uri.parse(BASE_URL+METHOD_NEARBY+"="+Double.toString(latitude)+","+Double.toString(longitude)+","+RADIUS).buildUpon()
                .appendQueryParameter(PARAM_LANG, LANG)
                .appendQueryParameter(PARAM_SHOW, SHOW)
                .build();
        return new URL(uri.toString());
    }

    private Webcams() {}
}
