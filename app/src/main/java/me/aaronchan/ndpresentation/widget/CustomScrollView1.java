package me.aaronchan.ndpresentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;

import java.util.Arrays;

import me.aaronchan.ndpresentation.R;

/**
 * Created by Administrator on 2016/5/12.
 */
public class CustomScrollView1 extends ScrollView {

    private static final String TAG = CustomScrollView1.class.getSimpleName();

    private CustomListView1 mList;
    private int mLastY = 0;

    public CustomScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.widget_custom_scroll1, null, false);

        addView(view);

        mList = (CustomListView1) view.findViewById(R.id.lv_sample);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                Arrays.asList(("A,B,C,D,F,X,Y,Z,H,I,J,K,M,N".split(","))));

        mList.setAdapter(adapter);
    }

    /**
     * 滑动冲突#外部拦截法
     * @param ev
     * @return
     */
    //@Override
   /* public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted =false;

        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastY;
                if (mList.isReachedTop() && deltaY > 0 ||
                        mList.isReachedBottom() && deltaY < 0) {
                    intercepted = true;
                } else {
                    intercepted =false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }

        mLastY = y;

        Log.d(TAG, "Action " + ActionUtil.getAction(ev.getAction()) + " intercepted ? " + intercepted);

        super.onInterceptTouchEvent(ev);

        return intercepted;
    }*/

}
