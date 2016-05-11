package me.aaronchan.ndpresentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import me.aaronchan.ndpresentation.util.ActionUtil;

/**
 * Created by Administrator on 2016/5/11.
 */
public class CustomView5 extends View {

    private static final String TAG = CustomView5.class.getSimpleName();

    public CustomView5(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent, action is " + ActionUtil.getAction(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }
}
