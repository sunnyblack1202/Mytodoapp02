package com.example.mytodoapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PageDetailActivity extends AppCompatActivity {

    private int _pageId = -1;
    private String _pageTitle;

    private EditText _etPageTitle;
    private EditText _etPageContent;

    InputMethodManager inputMethodManager;
    private ConstraintLayout detailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail);

        //情報の受け取り
        Intent intent = getIntent();

        _pageId = intent.getIntExtra("pageId", 0);
        String updatedtime = intent.getStringExtra("updatedtime"); //更新時間
        _pageTitle = intent.getStringExtra("pageTitle");
        String pageContent = intent.getStringExtra("pageContent");

        TextView tvUpdatedtime = findViewById(R.id.tvlastupdated);
        _etPageTitle = findViewById(R.id.etPageTitle);
        _etPageContent = findViewById(R.id.etPageContent);

        tvUpdatedtime.setText(updatedtime);
        _etPageTitle.setText(_pageTitle);
        _etPageContent.setText(pageContent);

        //戻るボタン
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //フォーカスを外す
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        detailLayout = findViewById(R.id.detail_layout);
    }

    //戻るボタン　オプションメニュー save delete
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        boolean returnVal = true;
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home: //戻る
                finish();
                break;
            case R.id.pageOptionSave: //保存
                save();
                break;
            case R.id.pageOptionDelete: //削除
                deleteClick();
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

    //保存
    public void save() {
        DatabaseHelper helper = new DatabaseHelper(PageDetailActivity.this);
        //時間
        DateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String updatedtime = df.format(date);

        //テキスト
        String pageTitle = _etPageTitle.getText().toString();
        String pageContent = _etPageContent.getText().toString();

        try(SQLiteDatabase db = helper.getWritableDatabase()) {

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.PageList.COLUMN_DATE, updatedtime);
            cv.put(DatabaseContract.PageList.COLUMN_NAME_TITLE, pageTitle);
            cv.put(DatabaseContract.PageList.COLUMN_NAME_CONTENT, pageContent);

            if (_pageId == 0) {
                db.insert(DatabaseContract.PageList.TABLE_NAME, null, cv);
            } else {
                db.update(DatabaseContract.PageList.TABLE_NAME,
                        cv,
                        DatabaseContract.PageList._ID + " = ?",
                        new String[] {String.valueOf(_pageId)});
            }
        }
        finish();
    }

    //削除
    public void deleteClick() {
        DeleteConfirmDialogFragment dialogFragment = new DeleteConfirmDialogFragment();

        Bundle args = new Bundle();
        args.putString("pageTitle", _pageTitle);
        args.putInt("pageId", _pageId);
        dialogFragment.setArguments(args);

        dialogFragment.show(getSupportFragmentManager(), "DeleteConfirmDialogFragment");
    }

    //背景タップ
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputMethodManager.hideSoftInputFromWindow(detailLayout.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        detailLayout.requestFocus();
        return true;
    }
}