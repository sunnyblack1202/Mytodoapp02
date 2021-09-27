package com.example.mytodoapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        //情報の受け取り
        Intent intent = getIntent();

        String pageTitle = intent.getStringExtra("pageTitle");
        String pageContent = intent.getStringExtra("pageContent");

        TextView tvPageTitle = findViewById(R.id.tvPageTitle);
        TextView tvPageContent = findViewById(R.id.tvPageContent);

        tvPageTitle.setText(pageTitle);
        tvPageContent.setText(pageContent);

        //戻るボタン
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //戻るボタン
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        boolean returnVal = true;
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else {
            returnVal = super.onOptionsItemSelected(item);
        }
        return returnVal;
    }

    //オプションメニュー save delete
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_saveanddelete, menu);
        return true;
    }
}