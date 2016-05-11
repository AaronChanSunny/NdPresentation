package me.aaronchan.ndpresentation.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MessengerService extends Service {

    public static final int SAY_HELLO = 0;

    private static final String TAG = MessengerService.class.getSimpleName();

    private final Messenger mMessenger = new Messenger(new IncomineHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    /**
     * 处理客户端发来的消息
     */
    class IncomineHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAY_HELLO:
                    Log.d(TAG, "Messenger thread #" + Thread.currentThread().getName());
                    Log.d(TAG, "Hi, client.");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

}
