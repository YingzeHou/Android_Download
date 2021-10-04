package com.example.lab_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;
    private TextView downloadText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        downloadText = findViewById(R.id.textDownload);
    }

    public void mockFileDownloader(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading......");
            }
        });
        for(int downloadProgress = 0; downloadProgress<=100; downloadProgress+=10){
            if(stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("start");
                        downloadText.setText("");
                    }
                });
                return;
            }
            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    downloadText.setText("Download Progress: "+ finalDownloadProgress +"%");
                }
            });
            Log.d(TAG,"Download Progress: "+downloadProgress+"%");
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
                downloadText.setText("");
            }
        });
    }

    class ExampleRunnable implements Runnable{

        @Override
        public void run() {
            mockFileDownloader();
        }
    }
    public void startDownload(View view){
        ExampleRunnable exampleRunnable = new ExampleRunnable();
        new Thread(exampleRunnable).start();
    }

    public void stopDownload(View view){
        stopThread=true;
    }
}