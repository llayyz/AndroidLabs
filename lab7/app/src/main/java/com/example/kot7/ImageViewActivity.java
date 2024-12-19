package com.example.kot7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_activity);

        imageView = findViewById(R.id.imageView);

        // Получаем путь к изображению из Intent
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imagePath");

        if (imagePath == null || imagePath.isEmpty()) {
            Toast.makeText(this, "Ошибка: путь к изображению не передан", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("ImageViewActivity", "Image Path: " + imagePath);

        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            Toast.makeText(this, "Ошибка: файл не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Загружаем изображение в полноэкранный вид
        Glide.with(this)
                .load(imageFile)
                .into(imageView);
    }
}
