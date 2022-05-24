package com.liqi.test3399.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liqi.test3399.R;

public class FragmentVideo extends Fragment implements MediaPlayer.OnPreparedListener {
    Context context;
    MediaPlayer mediaPlayer;
    public FragmentVideo(int video_fragment) {
        super(video_fragment);
    }

    public FragmentVideo() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VideoView videoView = view.findViewById(R.id.video1);
        MediaController mediaController = new MediaController(context);
        String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.vo;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.setMediaController(mediaController);
        videoView.start();
        /*mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(0f,0f);*/


        /*videoView.setOnCompletionListener(MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                // TODO Auto-generated method stub
                mPlayer.start();
                mPlayer.setLooping(true);
                mPlayer.setVolume(0f,0f);
            }
        });*/


    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //mediaPlayer.setVolume(0f,0f);
        mediaPlayer.start();
    }

}
