package edu.sjsu.android.servicesapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    DownloadBindService mService;
    boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText pdfURL1 = (EditText)findViewById(R.id.enterPdfFile1);
        EditText pdfURL2 = (EditText)findViewById(R.id.enterPdfFile2);
        EditText pdfURL3 = (EditText)findViewById(R.id.enterPdfFile3);
        EditText pdfURL4 = (EditText)findViewById(R.id.enterPdfFile4);
        EditText pdfURL5 = (EditText)findViewById(R.id.enterPdfFile5);

        pdfURL1.setText("https://www.cisco.com/web/about/ac79/docs/innov/IoE.pdf");
        pdfURL2.setText("http://www.cisco.com/web/about/ac79/docs/innov/IoE_Economy.pdf");
        pdfURL3.setText("http://www.cisco.com/web/strategy/docs/gov/everything-for-cities.pdf");
        pdfURL4.setText("http://www.cisco.com/web/offer/gist_ty2_asset/Cisco_2014_ASR.pdf");
        pdfURL5.setText("http://www.cisco.com/web/offer/emear/38586/images/Presentations/P3.pdf");

    }

    public void startDownload(View view){
        Log.v("Before", "*********Inside startDownload() method*********");
        haveStoragePermission();
        Log.v("After", "*********After have storage permission***********");
        Intent intent = new Intent(getBaseContext(), DownloadIntentService.class);

        try{
            String[] url = new String[5];
            url[0] = "https://www.cisco.com/web/about/ac79/docs/innov/IoE.pdf";
            url[1] = "http://www.cisco.com/web/about/ac79/docs/innov/IoE_Economy.pdf";
            url[2] = "http://www.cisco.com/web/strategy/docs/gov/everything-for-cities.pdf";
            url[3] = "http://www.cisco.com/web/offer/gist_ty2_asset/Cisco_2014_ASR.pdf";
            url[4] = "http://www.cisco.com/web/offer/emear/38586/images/Presentations/P3.pdf";
            intent.putExtra("URLs", url);
            startService(intent);
            if(mBound){
                mService.downloadFiles(intent);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            DownloadBindService.DownloadBinder binder = (DownloadBindService.DownloadBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error", "You have permission");
                return true;
            } else {

                Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.e("Permission error", "You already have the permission");
            return true;
        }
    }
}
