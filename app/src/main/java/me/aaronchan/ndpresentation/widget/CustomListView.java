package me.aaronchan.ndpresentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/5/12.
 */
public class CustomListView extends ListView {

    private static final String TAG = CustomListView.class.getSimpleName();

    private int mLastY = 0;
    private boolean mReachedTop = false;
    private boolean mReachedBottom =false;

    public CustomListView(Context context, AttributeSet attrs) {
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
}
