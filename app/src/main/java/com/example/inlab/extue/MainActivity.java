package com.example.inlab.extue;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private File sdCard = Environment.getExternalStorageDirectory();
    private File song = new File(sdCard.getAbsolutePath() + "/Music/Song.ogg");
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mediaPlayer.reset();
            Log.i("MyApp", song.getAbsolutePath());
            mediaPlayer.setDataSource(song.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button playButton = (Button) findViewById(R.id.playButton);
        Button pauseButton = (Button) findViewById(R.id.pauseButton);
        Button stopButton = (Button) findViewById(R.id.stopButton);
        textView = (TextView) findViewById(R.id.textView);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                textView.setText("Playing");
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                textView.setText("Paused");
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                textView.setText("Stopped");
                try {
                    mediaPlayer.prepare();
                    textView.setText("Ready");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        textView.setText("Stopped");
        try {
            mp.prepare();
            textView.setText("Ready");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
