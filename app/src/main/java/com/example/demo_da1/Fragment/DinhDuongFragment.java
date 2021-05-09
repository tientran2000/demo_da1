package com.example.demo_da1.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo_da1.Adapter.Adapter;
import com.example.demo_da1.Object.News;
import com.example.demo_da1.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class DinhDuongFragment extends Fragment {
//    CustemAdapter adapter;
//    ListView lv;
    ArrayList<News> arr;
    View view;
    Adapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    public DinhDuongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_dinh_duong, container, false);
        Init();
        return view;
    }
    class MyTask extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
//
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements element = document.select("item");
                News tt = null;
                for (Element elm : element) {
                    tt = new News();
                    tt.setTitle(elm.select("title").text().replaceAll("&#34;", "\""));
                    tt.setImage(Jsoup.parse(elm.select("description").text()).select("img").attr("src"));
                    tt.setLink(elm.select("link").text());
                    tt.setTime(elm.select("pubDate").text());
                    arr.add(tt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return arr;
        }

    }

    public void Init(){
        arr = new ArrayList<>();
        new MyTask().execute("https://vtv.vn/suc-khoe/dinh-duong.rss");
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerView);
        //lv=(ListView) view.findViewById(R.id.lvtt);
        adapter = new Adapter(getActivity(),arr);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //final String country = getCountry();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyTask().execute("https://vtv.vn/suc-khoe/dinh-duong.rss");
            }
        });
    }
}