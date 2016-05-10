package me.aaronchan.ndpresentation.util;

import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/5/10.
 */
public class ActionUtil {

    public static String getAction(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            default:
                return String.valueOf(action);
        }
    }
}
