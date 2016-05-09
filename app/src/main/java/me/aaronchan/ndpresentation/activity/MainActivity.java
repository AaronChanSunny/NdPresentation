package me.aaronchan.ndpresentation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.aaronchan.ndpresentation.R;
import me.aaronchan.ndpresentation.adapter.ItemAdapter;
import me.aaronchan.ndpresentation.model.Item;

public class MainActivity extends AppCompatActivity {

    private List<Item> mItems;
    private RecyclerView mRvItem;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();

        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up selector_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mRvItem = (RecyclerView) findViewById(R.id.rv_item);
        mRvItem.setLayoutManager(new LinearLayoutManager(this));
        mRvItem.setAdapter(mAdapter);
    }

    private void initData() {
        mItems = new ArrayList<>();

        mItems.add(new Item(true, "Android Async Framework"));
        mItems.add(new Item(false,
                "Handler Without Looper",
                "Create handler in thread without " + "looper throw exception.",
                HandlerCreationActivity.class));
        mItems.add(new Item(false,
                "HandlerThread Sample",
                "A simple HandlerThread example.",
                HandlerThreadActivity.class));
        mItems.add(new Item(false,
                "AsyncTask Sample",
                "A async task example show serial tasks.",
                AsyncTaskActivity.class));
        mItems.add(new Item(false,
                "IntentService Sample",
                "A intent service example.",
                IntentServiceActivity.class));
        mItems.add(new Item(true, "Android Touch System"));
        mItems.add(new Item(false,
                "Touch Event Ignore",
                "A text view in frame layout.",
                TouchIgnoreActivity.class));
        mItems.add(new Item(false,
                "Touch Event Consumed",
                "A button in frame layout.",
                TouchConsumedActivity.class));
        mItems.add(new Item(false,
                "Touch Event Intercepted",
                "A button in scroll view.",
                TouchInterceptedActivity.class));

        mAdapter = new ItemAdapter(mItems);
    }
}
