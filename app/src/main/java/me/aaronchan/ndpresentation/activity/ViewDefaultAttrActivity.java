package me.aaronchan.ndpresentation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.aaronchan.ndpresentation.R;

public class ViewDefaultAttrActivity extends AppCompatActivity {

    private static final String TAG = ViewDefaultAttrActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_default_attr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View view = new View(this);
        TextView textView = new TextView(this);
        Button button = new Button(this);

        Log.d(TAG, "view#click is " + view.isClickable() +
                ", view#longClick is " + view.isLongClickable());
        Log.d(TAG, "textView#click is " + textView.isClickable() +
                ", textView#longClick is " + textView.isLongClickable());
        Log.d(TAG, "button#click is " + button.isClickable() +
                ", button#longClick is " + button.isLongClickable());
    }

}
