package me.aaronchan.ndpresentation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.aaronchan.ndpresentation.R;
import me.aaronchan.ndpresentation.service.SimpleService;

public class IntentServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
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

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleService.actionStart(IntentServiceActivity.this, "#0");
                SimpleService.actionStart(IntentServiceActivity.this, "#1");
                SimpleService.actionStart(IntentServiceActivity.this, "#2");
                SimpleService.actionStart(IntentServiceActivity.this, "#3");
                SimpleService.actionStart(IntentServiceActivity.this, "#4");
            }
        });
    }

}
