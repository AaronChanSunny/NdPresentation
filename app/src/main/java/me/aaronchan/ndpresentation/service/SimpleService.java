package me.aaronchan.ndpresentation.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Administrator on 2016/5/9 0009.
 */
public class SimpleService extends IntentService {

    private static final String TAG = SimpleService.class.getSimpleName();
    private static final String KEY_TAG = SimpleService.class.getName() + ".KEY_TAG";

    public static void actionStart(Context context, String tag) {
        Intent intent = new Intent(context, SimpleService.class);
        intent.putExtra(KEY_TAG, tag);

        context.startService(intent);
    }

    public SimpleService() {
        super("SimpleService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SystemClock.sleep(2000);

        Log.d(TAG, "Task in " + Thread.currentThread().getName() + " " + intent.getStringExtra(KEY_TAG) + " done.");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
