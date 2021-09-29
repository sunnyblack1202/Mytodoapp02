package com.example.mytodoapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NewPageActivity extends AppCompatActivity {

    private EditText _etPageTitle;
    private EditText _etPageContent;
    DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);

        _helper = new DatabaseHelper(NewPageActivity.this);

        _etPageTitle = findViewById(R.id.etPageTitle);
        _etPageContent = findViewById(R.id.etPageContent);
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
        String pageTitle = _etPageTitle.getText().toString();
        String pageContent = _etPageContent.getText().toString();

        try(SQLiteDatabase db = _helper.getWritableDatabase()) {

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.PageList.COLUMN_NAME_TITLE, pageTitle);
            cv.put(DatabaseContract.PageList.COLUMN_NAME_CONTENT, pageContent);

            db.insert(DatabaseContract.PageList.TABLE_NAME, null, cv);
        }
        finish();
    }
}