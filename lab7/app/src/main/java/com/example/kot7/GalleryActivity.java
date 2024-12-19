package com.example.kot7;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<File> imageFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Сетка из 3 колонок

        // Загружаем изображения
        imageFiles = loadImagesFromStorage();
        imageAdapter = new ImageAdapter(imageFiles, this);
        recyclerView.setAdapter(imageAdapter);
    }

    private List<File> loadImagesFromStorage() {
        List<File> imageFiles = new ArrayList<>();
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CameraXApp");

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".jpg")) {  // Проверяем, что это изображение
                        imageFiles.add(file);
                    }
                }
            }
        } else {
            Toast.makeText(this, "Изображений не найдено", Toast.LENGTH_SHORT).show();
        }
        return imageFiles;
    }
}
