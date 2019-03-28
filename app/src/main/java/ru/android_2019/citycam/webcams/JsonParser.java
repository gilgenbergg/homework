package ru.android_2019.citycam.webcams;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.android_2019.citycam.model.Cam;

class JsonParser {
    public static List<Cam> getAllCams(InputStream input, String decoding) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(input, decoding));
        List <Cam> cams = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();
            if(key.equals("result")) {
                cams = decodeResult(reader);
            }
            else if(key.equals("status")) {
                String status = reader.nextString();
                if(!status.equals("OK")){
                    throw  new IOException("Bad status");
                }
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return cams;
    }

    private static List<Cam> decodeResult(JsonReader reader) throws IOException {
        reader.beginObject();
        List <Cam> cams = new ArrayList<>();
        while (reader.hasNext()) {
            String key = reader.nextName();
            if(key.equals("webcams")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    cams.add(getCam(reader));
                }
                reader.endArray();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return cams;
    }

    private static Cam getCam(JsonReader reader) throws IOException {
        Integer id = 0;
        String title = null;
        URL imageURL = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();
            if(key.equals("id")) {
                id = Integer.parseInt(reader.nextString());
            }
            else if(key.equals("title")) {
                title = reader.nextString();
            }
            else if(key.equals("image")) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String innerKey = reader.nextName();
                    if (innerKey.equals("current")) {
                        imageURL = getImageURL(reader);
                    }
                    else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Cam(id, title, imageURL);
    }

    private static URL getImageURL(JsonReader reader) throws IOException {
        URL url = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();
            if(key.equals("preview")) {
                String imageUrl = reader.nextString();
                url = new URL(imageUrl);
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return url;
    }
}
