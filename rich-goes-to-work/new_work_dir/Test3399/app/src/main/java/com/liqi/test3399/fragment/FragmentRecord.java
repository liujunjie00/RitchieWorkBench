package com.liqi.test3399.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liqi.test3399.R;

import java.io.File;
import java.io.IOException;

public class FragmentRecord extends Fragment{
    private Context context;
    private View layout_r;
    private View layout_s;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    boolean stopOrRestar = true;
    boolean ssr = true;
    int queue = 0;
    StringBuilder stringBuilder = new StringBuilder();
    Handler handler2 = new Handler(Looper.myLooper());
    Handler handler1 = new Handler(Looper.myLooper());
    Handler handler3 = new Handler(Looper.myLooper());

    public FragmentRecord(int recording_fragment) {
        super(recording_fragment);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public FragmentRecord() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.recording_btn1);
        layout_r = view.findViewById(R.id.recording_layout);
        layout_s = view.findViewById(R.id.r_speak_layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
            }
        });

       // handler2.postDelayed(runnable1,1*1000);
       handler3.post(runnable3);
    }

    Runnable runnable1  = new Runnable() {
        @Override
        public void run() {
            int delayMIll = 0;
            if (stopOrRestar){
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                File file =new File("/sdcard/Test3399/");

                if (queue>9){
                    queue=0;
                }
                queue++;
                @SuppressLint("SdCardPath") File file_mp3 = new File(file.getPath() ,queue+"ssstext.mp3");

                if (!file.exists()){
                    file.mkdir();
                }
                if (file_mp3.exists()){
                    Log.d("liujunjie","删除文件 "+stopOrRestar+file_mp3.getPath());
                    file_mp3.delete();
                }
                mediaRecorder.setOutputFile(file_mp3.getPath());

                Log.d("liujunjie","开始录音 "+stopOrRestar+file_mp3.getPath());
                layout_r.setVisibility(View.VISIBLE);
                layout_s.setVisibility(View.GONE);


                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

               // prepor = false;
                delayMIll = 5*1000;
                stopOrRestar =false;
            }else {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                stopOrRestar = true;
                handler1.post(runnable2);

                delayMIll =  8*1000;
            }
                handler2.postDelayed(runnable1,delayMIll);

        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            if (mediaRecorder!=null){
                mediaRecorder.stop();
                mediaPlayer.release();
                mediaPlayer =null;
            }

            layout_r.setVisibility(View.GONE);
            layout_s.setVisibility(View.VISIBLE);
            mediaPlayer = new MediaPlayer();
            File file = new File("/sdcard/Test3399/"+queue+"ssstext.mp3");
            Log.d("liujunjie",file.getPath());
            Uri myUri = Uri.fromFile(file);
            try {
                mediaPlayer.setDataSource(context, myUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            if (mediaRecorder!=null){
                mediaRecorder.stop();
                mediaPlayer.release();
                mediaPlayer =null;
            }
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.starluyin);
            mediaPlayer.start();
            handler2.postDelayed(runnable1,5*1000);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaPlayer != null){
            mediaPlayer.release();
        }

    }

}
