package me.aaronchan.ndpresentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/5/12.
 */
public class CustomListView2 extends ListView {

    private static final String TAG = CustomListView2.class.getSimpleName();

    private int mLastY = 0;
    private boolean mReachedTop = false;
    private boolean mReachedBottom =false;

    public CustomListView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mReachedTop = firstVisibleItem == 0;
                mReachedBottom = visibleItemCount + firstVisibleItem == totalItemCount;
            }
        });
    }

    public boolean isReachedBottom() {
        return mReachedBottom;
    }

    public boolean isReachedTop() {
        return mReachedTop;
    }

    /**
     * 滑动冲突#内部拦截法
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastY;
                if (mReachedTop && deltaY > 0 ||
                        mReachedBottom && deltaY < 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
