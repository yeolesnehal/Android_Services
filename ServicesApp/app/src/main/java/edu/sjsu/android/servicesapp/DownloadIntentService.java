package edu.sjsu.android.servicesapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.MalformedURLException;

public class DownloadIntentService extends IntentService {
    final static int id = 12456789;

    public DownloadIntentService(){
        super("DownloadIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String[] url = intent.getStringArrayExtra("URLs");
        DownloadHelper helper = new DownloadHelper(getBaseContext());
        for (int index = 0; index < url.length; index++) {
            long downloadId = helper.downloadFile(url[index], "pdf_" + index + ".pdf");
            try {
                Thread.sleep(10000);
            }catch (InterruptedException exception) {
                exception.printStackTrace();
                Log.v("Error Message", "Could not download pdf_" + index + ".pdf file");
            }
        }
    }
}
