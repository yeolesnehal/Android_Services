package edu.sjsu.android.servicesapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class DownloadBindService extends Service {

    private final IBinder binder = new DownloadBinder();

    public class DownloadBinder extends Binder {
        DownloadBindService getService() {
            return DownloadBindService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void downloadFiles(Intent intent) {
        Toast.makeText(getBaseContext(), "Starting Download from Bind Service", Toast.LENGTH_SHORT).show();
        String[] url = intent.getStringArrayExtra("URLs");
        DownloadHelper helper = new DownloadHelper(getBaseContext());
        for (int index = 0; index < url.length; index++) {
            helper.downloadFile(url[index], "pdf_" + index + ".pdf");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException exception) {

            }
        }
    }
}
