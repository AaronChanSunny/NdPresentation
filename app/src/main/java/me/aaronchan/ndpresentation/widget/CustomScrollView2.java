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
public class CustomScrollView2 extends ScrollView {

    private static final String TAG = CustomScrollView2.class.getSimpleName();

    private CustomListView2 mList;

    public CustomScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.widget_custom_scroll2, null, false);

        addView(view);

        mList = (CustomListView2) view.findViewById(R.id.lv_sample);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                Arrays.asList(("A,B,C,D,F,X,Y,Z,H,I,J,K,M,N".split(","))));

        mList.setAdapter(adapter);
    }

}
