package ru.android_2019.citycam;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.net.MalformedURLException;

import ru.android_2019.citycam.model.Cam;
import ru.android_2019.citycam.model.City;
import ru.android_2019.citycam.webcams.CamTask;
import ru.android_2019.citycam.webcams.Webcams;

/**
 * Экран, показывающий веб-камеру одного выбранного города.
 * Выбранный город передается в extra параметрах.
 */
public class CityCamActivity extends AppCompatActivity implements Callbacks {

    /**
     * Обязательный extra параметр - объект City, камеру которого надо показать.
     */
    public static final String EXTRA_CITY = "city";

    private City city;

    private ImageView camImageView;
    private ProgressBar progressView;

    private CamTask camTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        city = getIntent().getParcelableExtra(EXTRA_CITY);
        if (city == null) {
            Log.w(TAG, "City object not provided in extra parameter: " + EXTRA_CITY);
            finish();
        }

        setContentView(R.layout.activity_city_cam);
        camImageView = (ImageView) findViewById(R.id.cam_image);
        progressView = (ProgressBar) findViewById(R.id.progress);

        getSupportActionBar().setTitle(city.name);

        progressView.setVisibility(View.VISIBLE);

        // Проверка: запущен ли таск
        if (savedInstanceState != null) {
            camTask = (CamTask) getLastCustomNonConfigurationInstance();
        }
        if (camTask == null) {
            try{
                camTask = new CamTask(this);
                camTask.execute(Webcams.createNearbyUrl(city.latitude,city.longitude));
            } catch (MalformedURLException e){
                Log.w(TAG, "Cannot create URL with lat : "+city.latitude+" and lng : "+city.longitude);
                this.finish();
            }
        } else {
            // Связь разнее запущенного таска с текущим Activity
            camTask.attachActivity(this);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return this.camTask;
    }

    private static final String TAG = "CityCam";

    @Override
    public void onProgressUpdate(int percent) {
        if (percent == 100) {
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPostExecute(final Cam cam) {
        if (cam == null) {
            progressView.setVisibility(View.INVISIBLE);
        }
        else {
            camImageView.setImageBitmap(cam.getImage());
        }
    }
}
