package com.example.demo_da1.Action;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_da1.API.APIService;
import com.example.demo_da1.Object.PostNews;
import com.example.demo_da1.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetail extends AppCompatActivity  {
    PostNews p;
    String tieude,duongdan,anh,thoigian;
    ArrayList<PostNews>arr;
    int pos;
    WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_newsdetail);
        arr=new ArrayList<>();
        webView=(WebView) findViewById(R.id.webview);
        Intent intent=getIntent();


        duongdan=intent.getStringExtra("link");
        tieude=intent.getStringExtra("title");
        thoigian=intent.getStringExtra("time");
        anh=intent.getStringExtra("image");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(duongdan);
        webView.setWebViewClient(new WebViewClient());
        //Toast.makeText(this,anh+"",Toast.LENGTH_SHORT).show();
        sendPostHistory();
    }

    public void Share() {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+ duongdan));
//        startActivity(browserIntent);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharebody=duongdan;
        intent.putExtra(Intent.EXTRA_TEXT,sharebody);
        startActivity(Intent.createChooser(intent,"Chia sẻ với"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.yeuthich:
                //code xử lý khi bấm menu1
                sendPost();
                break;
            case R.id.chiase:
                //code xử lý khi bấm menu2
                Share();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void sendPost() {
        p=new PostNews(tieude, duongdan, anh, thoigian);
        APIService.apiService.sendPostLuus(p).enqueue(new Callback<PostNews>() {
            @Override
            public void onResponse(Call<PostNews> call, Response<PostNews> response) {
                Toast.makeText(NewsDetail.this, "Thêm vào yêu thích", Toast.LENGTH_SHORT).show();

                PostNews result=response.body();
                if (result!=null){
                    arr.add(p);
                    // finish();
                }
            }
            @Override
            public void onFailure(Call<PostNews> call, Throwable t) {
                Toast.makeText(NewsDetail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendPostHistory() {
        p=new PostNews(tieude, duongdan, anh, thoigian);
        APIService.apiService.sendPosts(p).enqueue(new Callback<PostNews>() {
            @Override
            public void onResponse(Call<PostNews> call, Response<PostNews> response) {
                Toast.makeText(NewsDetail.this, "Thêm vào lịch sử xem", Toast.LENGTH_SHORT).show();

                PostNews result=response.body();
                if (result!=null){
                    // finish();
                    arr.add(result);
                }
            }
            @Override
            public void onFailure(Call<PostNews> call, Throwable t) {
                Toast.makeText(NewsDetail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
