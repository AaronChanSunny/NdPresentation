package me.aaronchan.ndpresentation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import me.aaronchan.ndpresentation.R;

public class HandlerCreationActivity extends AppCompatActivity {

    private static final String TAG = HandlerCreationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleThread("#SimpleThread").start();
            }
        });
    }

    class SimpleThread extends Thread {

        private Handler mHandler;

        public SimpleThread(String threadName) {
            super(threadName);

            Log.d(TAG, "Current thread is " + Thread.currentThread().getName());
        }

        @Override
        public void run() {
            Log.d(TAG, "Current thread is " + Thread.currentThread().getName());

            mHandler = new Handler();

            super.run();
        }
    }


}
