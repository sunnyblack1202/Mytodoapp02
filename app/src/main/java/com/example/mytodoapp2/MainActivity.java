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

    //リストの中身作り
    private List<Map<String, String>> createPageList() {
        List<Map<String, String>> pageList = new ArrayList<>();

        Map<String, String> page = new HashMap<>();
        page.put("title", "あかさたな");
        page.put("content", "あいうえおかきくけこさしすせそたちつてとなにぬねの");
        pageList.add(page);

        page = new HashMap<>();
        page.put("title", "はまやらわ");
        page.put("content","はひふえほまみむめもやゆよらりるれろわをん");
        pageList.add(page);

        page = new HashMap<>();
        page.put("title", "やゆよ");
        page.put("content","よ");
        pageList.add(page);

        return pageList;
    }

    //PageDetailActivityへ遷移
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);

            String pageTitle = item.get("title");
            String pageContent = item.get("content");

            Intent intent = new Intent(MainActivity.this, PageDetailActivity.class);
            intent.putExtra("pageTitle", pageTitle);
            intent.putExtra("pageContent", pageContent);

            startActivity(intent);
        }
    }

    public void selectDb() {
        _helper = new DatabaseHelper(MainActivity.this);

        SQLiteDatabase db = _helper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DatabaseContract.PageList.COLUMN_NAME_TITLE,
                DatabaseContract.PageList.COLUMN_NAME_CONTENT
        };

        Cursor cursor = db.query(
                DatabaseContract.PageList.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        //List<String> records = new ArrayList<>();

        String[] FROM = {DatabaseContract.PageList.COLUMN_NAME_TITLE};
        int[] TO = {android.R.id.text1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, cursor, FROM, TO, 0);

        ListView lvPage = findViewById(R.id.lvPage);

        lvPage.setAdapter(adapter);

        //lvPage.setOnItemClickListener(new ListItemClickListener());
    }
}