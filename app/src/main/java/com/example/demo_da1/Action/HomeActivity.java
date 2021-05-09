package com.example.demo_da1.Action;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.demo_da1.Adapter.Adapter;
import com.example.demo_da1.Object.News;
import com.example.demo_da1.R;
import com.example.demo_da1.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    EditText edttk;
    ArrayList<News> arr,arrtk;

RelativeLayout relativeLayout;
    Adapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        getSupportActionBar().hide();
        Init2();

        relativeLayout=(RelativeLayout) findViewById(R.id.actionbar);
        arr=new ArrayList<>();
        new MyTask().execute("https://vtv.vn/suc-khoe/me-va-be.rss");
        new MyTask().execute("https://vtv.vn/suc-khoe/dinh-duong.rss");
        new MyTask().execute("https://vtv.vn/suc-khoe/tieu-diem.rss");
        new MyTask().execute("https://vtv.vn/suc-khoe.rss");
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshhome);
        recyclerView = findViewById(R.id.recyclerViewhome);

        adapter = new Adapter(HomeActivity.this,arr);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));





        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyTask().execute("https://vtv.vn/suc-khoe/me-va-be.rss");
                new MyTask().execute("https://vtv.vn/suc-khoe/dinh-duong.rss");
                new MyTask().execute("https://vtv.vn/suc-khoe/tieu-diem.rss");
                new MyTask().execute("https://vtv.vn/suc-khoe.rss");
            }
        });
        edttk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = edttk.getText().toString().toUpperCase();
                String tieude = s.toString().toUpperCase();
                arrtk=new ArrayList<>();
                if (!tieude.equals("")) {
                    for (int i = 0; i < arr.size(); i++) {
                        News news = arr.get(i);
                        String ten = news.title.toUpperCase().trim();
                        if (ten.contains(tieude)) {
                            arrtk.add(news);
                        }
                    }
                    if (arrtk.size() > 0) {
                        viewPager.setVisibility(View.GONE);
                        adapter = new Adapter(HomeActivity.this, arrtk);
                        recyclerView.setAdapter(adapter);
                       swipeRefreshLayout.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    }
                    else {
                        swipeRefreshLayout.setVisibility(View.GONE);
                       viewPager.setVisibility(View.VISIBLE);
                    }
                } else {
                    swipeRefreshLayout.setVisibility(View.GONE);
                   viewPager.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        relativeLayout.setVisibility(View.VISIBLE);

                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.tieudiem).setChecked(true);
                        relativeLayout.setVisibility(View.VISIBLE);

                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.video).setChecked(true);
                        relativeLayout.setVisibility(View.VISIBLE);

                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.food).setChecked(true);
                        relativeLayout.setVisibility(View.VISIBLE);

                        break;

                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.person).setChecked(true);
                       // relativeLayout=(RelativeLayout) findViewById(R.id.actionbar);
                        relativeLayout.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tieudiem:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.video:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.food:
                        viewPager.setCurrentItem(3);
                        break;

                    case R.id.person:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
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

    public void Init2(){
        viewPager=(ViewPager)findViewById(R.id.viewpg);
        edttk=(EditText) findViewById(R.id.edtsearch);

    }
}
