package com.example.mytodoapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PageDetailActivity extends AppCompatActivity {

    private int pageId = 0;

    private EditText _etPageTitle;
    private EditText _etPageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        //情報の受け取り
        Intent intent = getIntent();

        pageId = intent.getIntExtra("pageId", 0);
        String pageTitle = intent.getStringExtra("pageTitle");
        String pageContent = intent.getStringExtra("pageContent");

        _etPageTitle = findViewById(R.id.etPageTitle);
        _etPageContent = findViewById(R.id.etPageContent);

        _etPageTitle.setText(pageTitle);
        _etPageContent.setText(pageContent);

        //戻るボタン
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //戻るボタン　オプションメニュー save delete
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        boolean returnVal = true;
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
            case R.id.pageOptionSave:
                //TODO保存処理
                break;
            case R.id.pageOptionDelete:
                //TODO削除
                break;
            default:
                returnVal = super.onOptionsItemSelected(item);
                break;
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

    //一時的にbuttonから保存
    public void onSave(View view) {
        DatabaseHelper helper = new DatabaseHelper(PageDetailActivity.this);

        String pageTitle = _etPageTitle.getText().toString();
        String pageContent = _etPageContent.getText().toString();

        try(SQLiteDatabase db = helper.getWritableDatabase()) {

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.PageList.COLUMN_NAME_TITLE, pageTitle);
            cv.put(DatabaseContract.PageList.COLUMN_NAME_CONTENT, pageContent);

            if (pageId == 0) {
                db.insert(DatabaseContract.PageList.TABLE_NAME, null, cv);
            } else {
                db.update(DatabaseContract.PageList.TABLE_NAME,
                        cv,
                        DatabaseContract.PageList._ID + " = ?",
                        new String[] {String.valueOf(pageId)});
            }

        }

        finish();
    }

    //削除
    public void onDelete(View view) {
        DatabaseHelper helper = new DatabaseHelper(PageDetailActivity.this);

        try (SQLiteDatabase db = helper.getWritableDatabase()){
            db.delete(DatabaseContract.PageList.TABLE_NAME,
                    DatabaseContract.PageList._ID + " = ? ",
                    new String[] {String.valueOf(pageId)});
        }

        finish();
    }

}