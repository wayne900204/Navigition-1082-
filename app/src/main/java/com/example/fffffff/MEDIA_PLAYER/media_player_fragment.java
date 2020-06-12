package com.example.fffffff.MEDIA_PLAYER;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fffffff.R;

public class media_player_fragment extends Fragment {
    Context contexts;
    public media_player_fragment(Context contexts){
        this.contexts = contexts;
    }
    TextView Media_Player_Tx;
    SeekBar seekBar;
    ImageButton media_BtPlay,media_BtPause,media_BtNext,media_BtPrevious;
    MediaPlayer mp;
    String str[] = {"Rescue Me","daydreamer","透明"};
    int music [] = {R.raw.rescueme,R.raw.daydreamer,R.raw.m3};
    int index = 0;
    Handler hd = new Handler();
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_player_fragment,container,false);
        seekBar = view.findViewById(R.id.seekBar);
        media_BtPlay = view.findViewById(R.id.media_player_play);
        media_BtPause = view.findViewById(R.id.media_player_pause);
        media_BtNext = view.findViewById(R.id.media_player_next);
        media_BtPrevious = view.findViewById(R.id.media_player_previous);
        Media_Player_Tx = view.findViewById(R.id.Media_Player_TX);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int position = seekBar.getProgress();
                mp.seekTo(position);
            }
        });
        media_BtPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if (index==-1){
                    index=2;
                }
                setPlay(index);

            }
        });

        media_BtPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp!=null){
                    if(mp.isPlaying()){
                        mp.pause();
                    }
                    else {
                        mp.start();
                    }
                }
            }
        });
        media_BtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlay(index);
            }
        });
        media_BtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if(index==3){
                    index=0;
                }
                setPlay(index);
            }
        });


        return view;
    }
    public void setPlay(int n){
        if (mp!=null){
            mp.stop();
            hd.removeCallbacks(update);
        }
        mp=MediaPlayer.create(contexts,music[n]);
        seekBar.setMax(mp.getDuration());
        Media_Player_Tx.setText("正在播放\n"+str[n]);
        mp.start();
        hd.post(update);
    }
    final Runnable update = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mp.getCurrentPosition());
            hd.postDelayed(update,1000);
        }
    };
}
