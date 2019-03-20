package edu.sjsu.android.servicesapp;

import android.os.AsyncTask;

public class DownloadTask extends AsyncTask<String, Integer, Long> {


    @Override
    protected Long doInBackground(String... url) {
        int count = url.length;
        return 0L;
    }
}
