package com.example.demo_da1.Action;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo_da1.API.APIService;
import com.example.demo_da1.Adapter.Adapter;
import com.example.demo_da1.Object.News;
import com.example.demo_da1.R;
import com.example.demo_da1.RecyclerViewSwipeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {
    int pos;
    EditText edttk;
    ArrayList<News> arr=new ArrayList<>();
    ArrayList<News> arrtk=new ArrayList<>();
    String url="https://docbao.conveyor.cloud/api/tindaxems";
    Adapter adapter;
    RecyclerView recyclerView,recyclerViewTK;
    SwipeRefreshLayout swipeRefreshLayout,swipeRefreshLayoutTK;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history);
        new MyTask().execute(url);
        edttk=(EditText) findViewById(R.id.edtsearch);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayoutTK = findViewById(R.id.swipeRefreshtk);
        recyclerViewTK = findViewById(R.id.recyclerViewtk);
        //lv=(ListView) view.findViewById(R.id.lvtt);
        adapter = new Adapter(History.this,arr);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));
        recyclerViewTK.setLayoutManager(new LinearLayoutManager(History.this));
        //final String country = getCountry();
        RecyclerViewSwipeListener recyclerViewSwipeListener = new RecyclerViewSwipeListener(true) {

            @Override
            public void onSwipeLeft() {
                Xoa();
            }

            @Override
            public void onSwipeDown() {
                new MyTask().execute(url);
                adapter.notifyItemChanged(0);
            }
        };

        recyclerView.setOnFlingListener(recyclerViewSwipeListener);

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos=viewHolder.getAdapterPosition();
                Xoa();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyTask().execute(url);
            }
        });


        edttk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = edttk.getText().toString().toLowerCase();
                String str = s.toString().toLowerCase();
                arrtk=new ArrayList<>();
                if (!str.equals("")) {
                    for (int i = 0; i < arr.size(); i++) {
                        News n = arr.get(i);
                        String ten = n.title.toLowerCase().trim();
                        if (ten.contains(str)) {
                            arrtk.add(n);
                        }
                    }

                    if (arrtk.size() > 0) {
                        adapter = new Adapter(History.this, arrtk);
                        recyclerViewTK.setAdapter(adapter);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        swipeRefreshLayoutTK.setVisibility(View.VISIBLE);
                        recyclerViewTK.setLayoutManager(new LinearLayoutManager(History.this));

                    }
                    else {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                    }
                } else {

                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void Xoa(){
        APIService.apiService.sendDelete(arr.get(pos).getTitle()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new MyTask().execute(url);
                Toast.makeText(History.this, "Đã xóa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(History.this, "Không xóa đc", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void ParseNews(String content){
        arr.clear();
        try {
            JSONArray jsonArray=new JSONArray(content);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                String tieude, thoigian, anh, duongdan;
                tieude=object.getString("tieude");
                thoigian=object.getString("thoigian");
                duongdan=object.getString("duongdan");
                anh=object.getString("anh");
                arr.add(new News(tieude, duongdan, anh, thoigian));
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }
    public class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content=new StringBuilder();
            URL url=null;
            try {
                url=new URL(strings[0]);
                URLConnection urlConnection=url.openConnection();

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    content.append(line+"\n");
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParseNews(s);
        }
    }
}
