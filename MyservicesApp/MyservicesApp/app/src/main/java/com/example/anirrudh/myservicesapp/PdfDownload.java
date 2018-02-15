package com.example.anirrudh.myservicesapp;

/**
 * Created by susheel on 10/20/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PdfDownload extends AppCompatActivity {

    public static final String downloadLink1 = "https://www.cisco.com/web/about/ac79/docs/innov/IoE.pdf";
    public static final String downloadLink2 = "http://www.cisco.com/web/about/ac79/docs/innov/IoE_Economy.pdf";
    public static final String downloadLink3 = "http://www.cisco.com/web/strategy/docs/gov/everything-for-cities.pdf";
    public static final String downloadLink4 = "http://www.cisco.com/web/offer/gist_ty2_asset/Cisco_2014_ASR.pdf";
    public static final String downloadLink5 = "http://www.cisco.com/web/offer/emear/38586/images/Presentations/P3.pdf";
    static String currDownloadLink;

    public static final String fileName1 = "IoE.pdf";
    public static final String fileName2 = "IoE_Economy.pdf";
    public static final String fileName3 = "everything-for-cities.pdf";
    public static final String fileName4 = "Cisco_2014_ASR.pdf";
    public static final String fileName5 = "P3.pdf";
    static String currFileName;

    /*
    0   => not started
    1   => downloading
    -1  => failed
    2   => downloaded
    */

    public static int file1Flag = 0;
    public static int file2Flag = 0;
    public static int file3Flag = 0;
    public static int file4Flag = 0;
    public static int file5Flag = 0;
    static int currFileFlag;

    static TextView textView1;
    static TextView textView2;
    static TextView textView3;
    static TextView textView4;
    static TextView textView5;
    static TextView currTextView;

    static String downloadPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerReceiver(receiver, new IntentFilter(
                MyService.NOTIFICATION));
        textView1 = (TextView) findViewById(R.id.textViewFile1);
        textView2 = (TextView) findViewById(R.id.textViewFile2);
        textView3 = (TextView) findViewById(R.id.textViewFile3);
        textView4 = (TextView) findViewById(R.id.textViewFile4);
        textView5 = (TextView) findViewById(R.id.textViewFile5);
        refreshAllTextViews();
        downloadPath = getFilesDir().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAllTextViews();
        registerReceiver(receiver, new IntentFilter(
                MyService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void downloadFile (View view) {
        Toast.makeText(this, "Starting all downloads !", Toast.LENGTH_SHORT).show();
        startDownloadService(downloadLink1, fileName1);
        file1Flag = 1;
        refreshTextView(1);

        startDownloadService(downloadLink2, fileName2);
        file2Flag = 1;
        refreshTextView(2);

        startDownloadService(downloadLink3, fileName3);
        file3Flag = 1;
        refreshTextView(3);

        startDownloadService(downloadLink4, fileName4);
        file4Flag = 1;
        refreshTextView(4);

        startDownloadService(downloadLink5, fileName5);
        file5Flag = 1;
        refreshTextView(5);
    }

    private void startDownloadService (String link, String file) {
        Intent intent = new Intent(getBaseContext(), MyService.class);
        intent.putExtra("urlpath", link);
        intent.putExtra("filename", file);
        startService(intent);
    }

    private void refreshTextView (int x) {
        if (x > 5 || x < 1) {
            System.out.println("refreshTextView wrong parameter, returning");
            return;
        }

        if (x == 1) {
            currFileFlag = file1Flag;
            currTextView = textView1;
            currFileName = fileName1;
            currDownloadLink = downloadLink1;
        } else if (x == 2) {
            currFileFlag = file2Flag;
            currTextView = textView2;
            currFileName = fileName2;
            currDownloadLink = downloadLink2;
        } else if (x == 3) {
            currFileFlag = file3Flag;
            currTextView = textView3;
            currFileName = fileName3;
            currDownloadLink = downloadLink3;
        } else if (x == 4) {
            currFileFlag = file4Flag;
            currTextView = textView4;
            currFileName = fileName4;
            currDownloadLink = downloadLink4;
        } else {
            currFileFlag = file5Flag;
            currTextView = textView5;
            currFileName = fileName5;
            currDownloadLink = downloadLink5;
        }

        if (currFileFlag == 0) currTextView.setText("PDF" + x + " File Location: " + currDownloadLink);
        else if (currFileFlag == 1) currTextView.setText(currFileName + " is downloading...");
        else if (currFileFlag == 2) currTextView.setText(currFileName + " downloaded at " + downloadPath);
        else if (currFileFlag == -1) currTextView.setText(currFileName + " downloaded failed!");
        else currTextView.setText("unknown internal error");
    }

    private void refreshAllTextViews () {
        refreshTextView(1);
        refreshTextView(2);
        refreshTextView(3);
        refreshTextView(4);
        refreshTextView(5);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int x;
            if (bundle != null) {
                String filename = bundle.getString("file");
                System.out.println("BroadcastReceiver -> onReceive -> Filename -> " + filename);
                if (filename.equals(fileName1)) x=1;
                else if (filename.equals(fileName2)) x=2;
                else if (filename.equals(fileName3)) x=3;
                else if (filename.equals(fileName4)) x=4;
                else x=5;
                refreshTextView(x);
            }
        }
    };
}