package com.example.kot7;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MediaActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_activity);

        videoView = findViewById(R.id.vidik);

        // Устанавливаем путь к видео
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vidik);
        videoView.setVideoURI(videoUri);

        // Добавляем контроллер для видео
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Обработчик ошибок
        videoView.setOnErrorListener((mp, what, extra) -> {
            Log.e("VideoView", "Ошибка воспроизведения: " + what + ", " + extra);
            return true;
        });

        // Начинаем воспроизведение видео
        videoView.start();
    }
}