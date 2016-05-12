package me.aaronchan.ndpresentation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import me.aaronchan.ndpresentation.R;

public class ThreadLocalActivity extends AppCompatActivity {

    private static final String TAG = ThreadLocalActivity.class.getSimpleName();

    private ThreadLocal<String> mThreadLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mThreadLocal = new ThreadLocal<>();
        mThreadLocal.set("Main thread value");
        Log.d(TAG, "This is " + mThreadLocal.get());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new WorkerThread1().start();
        new WorkerThread2().start();
    }

    class WorkerThread1 extends Thread {

        @Override
        public void run() {
            super.run();

            mThreadLocal.set("Worker thread1 value.");
            Log.d(TAG, "This is " + mThreadLocal.get());
        }
    }

    class WorkerThread2 extends Thread {
        @Override
        public void run() {
            super.run();

            Log.d(TAG, "This is " + mThreadLocal.get());
        }
    }

}
