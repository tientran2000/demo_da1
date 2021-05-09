package com.example.demo_da1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo_da1.Object.News;
import com.example.demo_da1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustemAdapter extends ArrayAdapter<News> {
    Context context;
    int resource;
    ArrayList<News> arr;

    public CustemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arr= (ArrayList<News>) objects;
    }




    @Override
    public int getCount() {
        return arr.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        ImageView img;
        TextView txtTitle;
        TextView time;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row=inflater.inflate(R.layout.row_list_news,null);
//        //ánh xạ
//        ImageView anhtt = (ImageView) row.findViewById(R.id.anhtrangchu);
//        TextView tieudett=(TextView) row.findViewById(R.id.txttieude);
//        TextView thoigian=(TextView) row.findViewById(R.id.thoigian);
//
//        News tt=arr.get(position);
//        tieudett.setText(tt.title);
//        thoigian.setText(tt.time);
//        if (!tt.image.equals("")){
//        //Picasso.get().load(tt.image).into(anhtt);}
//        Picasso.with(context).load(tt.getImage()).into(holder.imgThumbnail);

        ViewHolder holder;
        if(convertView==null){
            holder=new CustemAdapter.ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resource,null);
            holder.txtTitle=(TextView) convertView.findViewById(R.id.txttieude);
            holder.img=(ImageView) convertView.findViewById(R.id.anhtrangchu);
            holder.time=(TextView) convertView.findViewById(R.id.thoigian);
            convertView.setTag(holder);
        }else {
            holder=(CustemAdapter.ViewHolder) convertView.getTag();
        }
        News news=arr.get(position);
        holder.txtTitle.setText(news.getTitle());
        holder.time.setText(news.getTime());
        Picasso.with(context).load(news.getImage()).into(holder.img);

        return convertView;
    }
//    public void sortTintuc(String s){
//        s=s.toUpperCase();
//        int k=0;
//        for (int i=0;i<arr.size();i++){
//            News a=arr.get(i);
//            String ten=a.title.toUpperCase();
//            if(ten.equals(s)){
//                arr.set(k,a);
//                k++;
//            }
//        }
//        notifyDataSetChanged();
//    }
}
