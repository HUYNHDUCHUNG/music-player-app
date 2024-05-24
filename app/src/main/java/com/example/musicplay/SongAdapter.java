package com.example.musicplay;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends  RecyclerView.Adapter<SongAdapter.SongViewHolder>{


    private Context mContext;
    private List<Song> mListSong;

    public SongAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Song> list){
        this.mListSong = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,parent,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = mListSong.get(position);
        if(song == null)
            return;
        holder.imvSong.setImageResource(song.getImage());
        holder.tvSongName.setText(song.getName());
        holder.tvSingerName.setText(song.getNameSinger());
        holder.tvSongTime.setText(song.getSongTime(mContext));


        holder.layout_item.setOnClickListener(view -> {
            Intent i = new Intent(mContext, MainActivity.class);
            i.putExtra("index_song",position);
            mContext.startActivity(i);
        });
    }


    public void release(){
        mContext = null;
    }



    @Override
    public int getItemCount() {
        if(mListSong != null)
            return mListSong.size();
        return 0;
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder{

        private final RelativeLayout layout_item;
        private final ImageView imvSong;
        private final TextView tvSongName,tvSingerName,tvSongTime;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.relative_item_song);
            imvSong = itemView.findViewById(R.id.imv_song);
            tvSongName = itemView.findViewById(R.id.tv_song_name);
            tvSingerName = itemView.findViewById(R.id.tv_singer_name);
            tvSongTime = itemView.findViewById(R.id.tv_song_time);
        }
    }

}
