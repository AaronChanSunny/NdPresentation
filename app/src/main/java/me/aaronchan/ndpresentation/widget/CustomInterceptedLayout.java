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
public class CustomInterceptedLayout extends FrameLayout {

    private static final String TAG = CustomInterceptedLayout.class.getSimpleName();

    public CustomInterceptedLayout(Context context, AttributeSet attrs) {
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

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }

        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent, action is " + ActionUtil.getAction(event.getAction()));
        return super.onTouchEvent(event);
    }
}
