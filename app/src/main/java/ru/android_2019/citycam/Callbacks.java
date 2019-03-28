package ru.android_2019.citycam;

import ru.android_2019.citycam.model.Cam;

public interface Callbacks {
        void onProgressUpdate(int percent);
        void onPostExecute(Cam cam);
}
