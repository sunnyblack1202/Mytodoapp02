package com.example.mytodoapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Map<String, String>> _pageList;

    private static final String[] FROM = {"title"};
    private static final int[] TO = {android.R.id.text1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvPage = findViewById(R.id.lvPage);

        _pageList = createPageList();

        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, _pageList,
                android.R.layout.simple_list_item_1, FROM, TO);

        lvPage.setAdapter(adapter);

        lvPage.setOnItemClickListener(new ListItemClickListener());

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
}