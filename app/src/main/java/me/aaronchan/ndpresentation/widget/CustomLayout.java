package me.aaronchan.ndpresentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import me.aaronchan.ndpresentation.util.ActionUtil;

/**
 * Created by Administrator on 2016/5/10.
 */
public class CustomLayout extends FrameLayout {
    private static final String TAG = CustomLayout.class.getSimpleName();

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent, action is " + ActionUtil.getAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "onInterceptTouchEvent, action is " + ActionUtil.getAction(event.getAction()));
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent, action is " + ActionUtil.getAction(event.getAction()));
        return super.onTouchEvent(event);
    }
}
