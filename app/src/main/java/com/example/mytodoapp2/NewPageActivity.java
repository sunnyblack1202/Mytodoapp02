package com.example.mytodoapp2;

import androidx.appcompat.app.AppCompatActivity;

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
            //TODO保存処理を
        } else {
            returnVal = super.onOptionsItemSelected(item);
        }

        return returnVal;
    }

    //一時的にbuttonから保存
    public void onSave(View view) {
        SQLiteDatabase db = _helper.getWritableDatabase();

        String pageTitle = _etPageTitle.getText().toString();
        String pageContent = _etPageContent.getText().toString();

        String sqlInsert = "INSERT INTO pagememos (title, content) VALUES (?, ?)";
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        stmt.bindString(1, pageTitle);
        stmt.bindString(2, pageContent);
        stmt.executeInsert();
    }

}