package com.example.mytodoapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper _helper;
    private Cursor _cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //追加ボタン
        FloatingActionButton fabNew = findViewById(R.id.fabNew);
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fabintent = new Intent(MainActivity.this, NewPageActivity.class);
                startActivity(fabintent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        selectDb();
    }

    //PageDetailActivityへ遷移
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            _cursor = (Cursor) parent.getItemAtPosition(position);

            int pageId = _cursor.getInt(0);
            String pageTitle = _cursor.getString(1);
            String pageContent = _cursor.getString(2);

            Intent intent = new Intent(MainActivity.this, PageDetailActivity.class);

            intent.putExtra("pageId", pageId);
            intent.putExtra("pageTitle", pageTitle);
            intent.putExtra("pageContent", pageContent);

            startActivity(intent);
        }
    }

    //データベースから取得
    public void selectDb() {
        _helper = new DatabaseHelper(MainActivity.this);

        SQLiteDatabase db = _helper.getWritableDatabase();

        String[] projection = {
                DatabaseContract.PageList._ID,
                DatabaseContract.PageList.COLUMN_NAME_TITLE,
                DatabaseContract.PageList.COLUMN_NAME_CONTENT
        };

        _cursor = db.query(
                DatabaseContract.PageList.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        String[] FROM = {DatabaseContract.PageList.COLUMN_NAME_TITLE};
        int[] TO = {android.R.id.text1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, _cursor, FROM, TO, 0);

        ListView lvPage = findViewById(R.id.lvPage);

        lvPage.setAdapter(adapter);

        lvPage.setOnItemClickListener(new ListItemClickListener());
    }
}