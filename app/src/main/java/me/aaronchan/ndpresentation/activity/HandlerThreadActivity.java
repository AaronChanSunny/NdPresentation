package me.aaronchan.ndpresentation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import me.aaronchan.ndpresentation.R;

public class HandlerThreadActivity extends AppCompatActivity {

    private static final String TAG = HandlerThreadActivity.class.getSimpleName();
    private static final int MSG = 0;

    private Handler mHandler;
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();

        initView();
    }

    private void initData() {
        mCount = 0;

        HandlerThread worker = new HandlerThread("#WorkerThread");
        worker.start();

        mHandler = new Handler(worker.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == MSG) {
                    Log.d(TAG, "Received msg #" +
                            msg.arg1 + " in thread " +
                            Thread.currentThread().getName());
                }
            }
        };
    }

    private void initView() {
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = mHandler.obtainMessage();
                msg.what = MSG;
                msg.arg1 = mCount;

                mHandler.sendMessage(msg);

                mCount++;
            }
        });
    }

}
