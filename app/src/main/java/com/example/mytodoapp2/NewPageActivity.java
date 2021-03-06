package com.example.mytodoapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPageActivity extends AppCompatActivity {

    private EditText _etPageTitle;
    private EditText _etPageContent;
    DatabaseHelper _helper;

    InputMethodManager inputMethodManager;
    private ConstraintLayout newLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);

        _helper = new DatabaseHelper(NewPageActivity.this);

        _etPageTitle = findViewById(R.id.etPageTitle);
        _etPageContent = findViewById(R.id.etPageContent);

        //フォーカスを外す
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        newLayout = findViewById(R.id.new_layout);
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }

    //オプションメニュー add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean returnVal = true;

        int itemId = item.getItemId();
        if (itemId == R.id.pageOptionAdd) {
            save();
        } else {
            returnVal = super.onOptionsItemSelected(item);
        }

        return returnVal;
    }

    //保存
    public void save() {
        //時間
        DateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String updatedtime = df.format(date);

        //テキスト
        String pageTitle = _etPageTitle.getText().toString();
        String pageContent = _etPageContent.getText().toString();

        try(SQLiteDatabase db = _helper.getWritableDatabase()) {

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.PageList.COLUMN_DATE, updatedtime);
            cv.put(DatabaseContract.PageList.COLUMN_NAME_TITLE, pageTitle);
            cv.put(DatabaseContract.PageList.COLUMN_NAME_CONTENT, pageContent);

            db.insert(DatabaseContract.PageList.TABLE_NAME, null, cv);
        }
        finish();
    }

    //背景タップ
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputMethodManager.hideSoftInputFromWindow(newLayout.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        newLayout.requestFocus();
        return true;
    }
}