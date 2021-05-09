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

public class HomeFragment extends Fragment {

    ArrayList<News> arr;
     View view;
     Adapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
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
        view= inflater.inflate(R.layout.fragment_home, container, false);

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
        new MyTask().execute("https://vtv.vn/suc-khoe.rss");
        arr = new ArrayList<>();
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
                new MyTask().execute("https://vtv.vn/suc-khoe.rss");
            }
        });
//        adapter = new CustemAdapter(getActivity(), R.layout.row_list_news, arr);
//        lv.setAdapter(adapter);
//        adapter2 = new CustemAdapter2(getActivity(), R.layout.row_list_news, arr);
//        lv.setAdapter(adapter2);


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent=new Intent(getActivity(),NewsDetail.class);
//                intent.putExtra("link",arr.get(position).getLink());
//                intent.putExtra("title",arr.get(position).getTitle());
//                intent.putExtra("image",arr.get(position).getImage());
//                intent.putExtra("time",arr.get(position).getTime());
//                startActivity(intent);
//            }
//        });
    }


}