package com.example.musicplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    RecyclerView rcvPlaylist;
    SongAdapter songAdapter;
    Spinner spnCategory;
    CategoryAdapter categoryAdapter;
    public static Song  []songs;
    public static String currentPlaylistName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        songs = new Song[]{
                new Song("Yêu Đi", R.drawable.yeu_di, R.raw.yeu_di,"Châu Khải Phong","Nhạc trẻ"),
                new Song("Giữ Em Thật Lâu", R.drawable.giu_em_that_lau, R.raw.giu_em_that_lau,"Naod","Nhạc trẻ"),
                new Song("Đừng Bắt Em Phải Quên", R.drawable.dung_bat_em_phai_quen, R.raw.dung_bat_em_phai_quen,"Miu Lê","Nhạc trẻ"),
                new Song("Nấu Ăn Cho Em",R.drawable.nau_an_cho_em,R.raw.nau_an_cho_em,"Đen, PiaLinh","Nhạc rap"),
                new Song("Từng Quen",R.drawable.tung_quen,R.raw.tung_quen,"Wren Evans, Itsnk","Nhạc rap"),
                new Song("Ngõ Chạm",R.drawable.ngo_cham,R.raw.ngo_cham,"Biggdaddy, Emily","Nhạc rap"),
                new Song("Vaicaunoicokhiennguoithaydoi",R.drawable.vaicaunoicokhiennguoithaydoi,R.raw.vaicaunoicokhiennguoithaydoi,"GREY D, tlinh","Nhạc rap"),
                new Song("Cung Bậc Sầu",R.drawable.cung_bac_sau,R.raw.cung_bac_sau,"Mr. Siro","Nhạc trẻ"),
                new Song("Một Ngày Chẳng Nắng", R.drawable.mot_ngay_chang_nang,R.raw.mot_ngay_chang_nang, "Pháo","Nhạc rap"),
                new Song("Nếu Lúc Đó", R.drawable.neu_luc_do, R.raw.neu_luc_do,"Tlinh","Nhạc trẻ"),




        };

        categories.add("Tất cả bài hát");
        categories.add("Nhạc rap");
        categories.add("Nhạc trẻ");




        rcvPlaylist = findViewById(R.id.rcv_playlist);
        songAdapter = new SongAdapter(this);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        spnCategory = findViewById(R.id.spn_category);
        categoryAdapter = new CategoryAdapter();
        spnCategory.setAdapter(categoryAdapter);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(categories.get(i).equals("Tất cả bài hát")){
                    songAdapter.setData(getPlaylist());
                }else{
                    songAdapter.setData(getSongToCategory(categories.get(i)));
                }
                currentPlaylistName = categories.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rcvPlaylist.setLayoutManager(linearLayout);

        rcvPlaylist.setAdapter(songAdapter);







    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(songAdapter != null){
            songAdapter.release();
        }
    }

    public static List<Song> getPlaylist(){
        return new ArrayList<>(Arrays.asList(songs));
    }

    public static List<Song> getSongToCategory(String cate){
        List<Song> songTmp = new ArrayList<>();
        for(Song c : songs){
            if(c.getCategory().equals(cate)){
                songTmp.add(c);
            }
        }
        return songTmp;
    }

    private final List<String> categories = new ArrayList<>();
    public class CategoryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int i) {
            return categories.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String c = categories.get(i);
            View rowView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_category, null);
            TextView tv = rowView.findViewById(R.id.tv_name_category);
            tv.setText(c);
            return rowView;
        }
    }
}