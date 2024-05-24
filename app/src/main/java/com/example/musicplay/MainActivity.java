package com.example.musicplay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    MediaPlayer player;

    int index = 0;
    Handler handler;
    Runnable runnable;

    CircleImageView imageView;
    ObjectAnimator rotationAnimator;

    SeekBar seakbarTimer;

    ImageButton btnPlay,btnNext,btnPre,btnPlaylist,btnLoop;
    TextView txtName,tvCurrentTime,tvMaxTime,tvSingerName,tvCheckIsLoop;
    LinearLayout mLinearLayoutMainApp;
    List<Song> currentPlaylist;
    int []backgrounds = {R.drawable.backgound_color_gradient,R.drawable.backgound_color_gradient1,R.drawable.backgound_color_gradient2,R.drawable.backgound_color_gradient3};
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        int indexSong = getIntent().getIntExtra("index_song", -1);
        if(!PlaylistActivity.currentPlaylistName.isEmpty() && indexSong != -1){
            if(PlaylistActivity.currentPlaylistName.equals("Tất cả bài hát")){
                currentPlaylist = PlaylistActivity.getPlaylist();
            }else{
                currentPlaylist = PlaylistActivity.getSongToCategory(PlaylistActivity.currentPlaylistName);
            }
            index = indexSong;

        }else{
            currentPlaylist = PlaylistActivity.getPlaylist();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player != null)
            player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(player != null)
            player.start();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player = null;
    }

    private void initView(){
        mLinearLayoutMainApp = findViewById(R.id.linear_main_layout);
         btnPlay = findViewById(R.id.btnPlay);
         btnNext = findViewById(R.id.btnNext);
         btnPre = findViewById(R.id.btnPre);
        btnPlaylist = findViewById(R.id.btn_list);
        btnLoop = findViewById(R.id.btn_loop);
        tvCheckIsLoop = findViewById(R.id.tv_custom_is_loop);
        txtName = findViewById(R.id.txtNameSong);
        tvSingerName = findViewById(R.id.tv_singerName);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        imageView = findViewById(R.id.profile_image);
        tvMaxTime = findViewById(R.id.tv_max_time);
        seakbarTimer = findViewById(R.id.sb_timer);
        rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        rotationAnimator.setDuration(10000); // Thời gian một vòng quay (2 giây trong ví dụ)
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE); // Lặp vô hạn
        rotationAnimator.setInterpolator(new LinearInterpolator());

        handler = new Handler();


        runnable = new Runnable() {
            @Override
            public void run() {
                if(player != null && player.isPlaying()){
                    tvCurrentTime.setText(formatTime(player.getCurrentPosition()));
                    handler.postDelayed(this,1000);

                    seakbarTimer.setProgress(player.getCurrentPosition());
                    player.setOnCompletionListener(mediaPlayer -> {
                        if(tvCheckIsLoop.getVisibility() == View.VISIBLE){
                            playMusic();
                        }
                        else{
                            if(player == null) return;
                            index++;
                            if(index == currentPlaylist.size()){
                                index = 0;
                            }

                            player.stop();
                            player.release();
                            handler.removeCallbacks(runnable);
                            playMusic();
                            txtName.setText(currentPlaylist.get(index).getName());
                            tvSingerName.setText(currentPlaylist.get(index).getNameSinger());
                            btnPlay.setImageResource(R.drawable.ic_pause_64);
                        }


                    });
                }

            }
        };
        seakbarTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    int seekPosition = seakbarTimer.getProgress();
                    player.seekTo(seekPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(view -> {
            if(player == null){

                this.playMusic();
                txtName.setText(currentPlaylist.get(index).getName());
                btnPlay.setImageResource(R.drawable.ic_pause_64);
                return;
            }
            if(!player.isPlaying()){
                player.start();
                handler.post(runnable);
                btnPlay.setImageResource(R.drawable.ic_pause_64);
                rotationAnimator.resume();


            }else {
                player.pause();
                handler.removeCallbacks(runnable);
                btnPlay.setImageResource(R.drawable.ic_play_64);
                rotationAnimator.pause();
            }
        });


        btnNext.setOnClickListener(view -> {
            if(player == null) return;
            index++;
            if(index == currentPlaylist.size()){
                index = 0;
            }

            player.stop();
            player.release();
            player = null;
            handler.removeCallbacks(runnable);
            this.playMusic();
            txtName.setText(currentPlaylist.get(index).getName());
            btnPlay.setImageResource(R.drawable.ic_pause_64);

        });

        btnPre.setOnClickListener(view -> {
            if(player == null) return;
            if(index == 0){
                index = currentPlaylist.size();
            }else{
                index--;
                player.stop();
                player.release();
                player = null;
                handler.removeCallbacks(runnable);
                this.playMusic();
                txtName.setText(currentPlaylist.get(index).getName());
                btnPlay.setImageResource(R.drawable.ic_pause_64);
            }


        });

        btnPlaylist.setOnClickListener(view -> {
            Intent i = new Intent(this, PlaylistActivity.class);
            this.startActivity(i);
        });

        btnLoop.setOnClickListener(view -> tvCheckIsLoop.setVisibility((tvCheckIsLoop.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE)));

    }



    private void playMusic(){
        player = MediaPlayer.create(this,currentPlaylist.get(index).getResource());
        imageView.setImageResource(currentPlaylist.get(index).getImage());
        btnPlay.setImageResource(R.drawable.ic_pause_64);
        txtName.setText(currentPlaylist.get(index).getName());
        tvSingerName.setText(currentPlaylist.get(index).getNameSinger());
        randomBackround();
        seakbarTimer.setMax(player.getDuration());
        tvMaxTime.setText(formatTime(player.getDuration()));
        player.start();
        handler.post(runnable);
        rotationAnimator.start();
    }

    public static String formatTime(int duration) {
        int minutes = (duration / 1000) / 60;
        int seconds = (duration / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    public void randomBackround(){
        Random random = new Random();
        int i = random.nextInt(4);
        mLinearLayoutMainApp.setBackgroundResource(backgrounds[i]);
    }






}