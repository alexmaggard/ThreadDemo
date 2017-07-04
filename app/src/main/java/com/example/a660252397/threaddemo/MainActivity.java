package com.example.a660252397.threaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /***************************************************
    *        Handler responsible for updating the UI
    *******************************************************/
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TextView msgText = (TextView) findViewById(R.id.textView);
            msgText.setText("I'm Done Processing");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*******************************************************
     *        second runnable thread, runs in background
     *******************************************************/
    public void clickMe(View view) {
        //second thread to run in background
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 10000;
                while (System.currentTimeMillis() < futureTime) {
                    synchronized (this) {                                       //syncronized is for multithreads
                        try {
                            wait(futureTime - System.currentTimeMillis());
                        } catch (Exception e) {

                        }
                    }
                }
                //call handler
                handler.sendEmptyMessage(0); //0 is successful, 1 is fail
            }
        }; //needs a semicolon because its a runnable

        //put all your code in your thread and start it
        Thread myThread = new Thread(r);
        myThread.start();

    }
}
