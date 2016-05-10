package me.aaronchan.ndpresentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import me.aaronchan.ndpresentation.util.ActionUtil;

/**
 * Created by Administrator on 2016/5/10.
 */
public class CustomButton extends Button {

    private static final String TAG = CustomButton.class.getSimpleName();

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent, action is " + ActionUtil.getAction(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent, action is " + ActionUtil.getAction(event.getAction()));
        return super.onTouchEvent(event);
    }
}
