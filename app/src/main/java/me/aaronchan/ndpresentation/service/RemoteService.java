package me.aaronchan.ndpresentation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import me.aaronchan.ndpresentation.IRemoteCalculator;

/**
 * Created by Administrator on 2016/5/12.
 */
public class RemoteService extends Service {

    private static final String TAG = RemoteService.class.getSimpleName();

    private IRemoteCalculator.Stub mBinder = new IRemoteCalculator.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            Log.d(TAG, "Current thread is " + Thread.currentThread().getName() + " onPlus");
            return a + b;
        }

        @Override
        public int minus(int a, int b) throws RemoteException {
            return a - b;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Current thread is " + Thread.currentThread().getName() + " onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }
}
