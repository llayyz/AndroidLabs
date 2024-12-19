package com.example.mpis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    int likes = 0;
    int dislikes = 0;
    private EditText editTextQuery;
    private ImageView imageView;
    private String imageUrl = "";
    private boolean isClicked = false;
    private static final String ACCESS_KEY = "DOXWcT4YnaP03i3a7qZbUX5ZrWIE3xJB3i01KYHf_-Y"; // Замените на ваш ключ доступа

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = findViewById(R.id.searchButton);
        ImageButton likeButton = findViewById(R.id.likeButton);
        ImageButton dislikeButton = findViewById(R.id.dislikeButton);
        TextView likesCount = findViewById(R.id.likesCount);
        TextView dislikesCount = findViewById(R.id.dislikesCount);

        Button downloadButton = findViewById(R.id.downloadButton);
        Button visitWebSiteButton = findViewById(R.id.visitWebSiteButton);


        editTextQuery = findViewById(R.id.editTextQuery);
        imageView = findViewById(R.id.imageView);

        likeButton.setOnClickListener(v -> {
            if(isClicked == false) {
                likes++;
                likesCount.setText("Likes : " + likes);
                Toast.makeText(MainActivity.this, "Вы поставили лайк", Toast.LENGTH_SHORT).show();
                isClicked = true;
            }
            else{
                Toast.makeText(MainActivity.this, "Вы уже ставили оценку", Toast.LENGTH_SHORT).show();
            }
        });

        dislikeButton.setOnClickListener(v -> {
            if(isClicked == false) {
                dislikes++;
                dislikesCount.setText("Dislike: " + dislikes);
                Toast.makeText(MainActivity.this, "Вы поставили дизлайк", Toast.LENGTH_SHORT).show();
                isClicked = true;
            }
            else{
                Toast.makeText(MainActivity.this, "Вы уже ставили оценку", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(v -> {
            String query = editTextQuery.getText().toString();
            if (!query.isEmpty()) {
                searchImage(query);
                isClicked = false;
            } else {
                Toast.makeText(MainActivity.this, "Введите запрос для поиска", Toast.LENGTH_SHORT).show();
            }
        });

        downloadButton.setOnClickListener(v -> {
                if (!imageUrl.isEmpty()) {
                    downloadImage(imageUrl);
                    Toast.makeText(MainActivity.this, "Скачивание не реализовано", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Нет доступного изображения для скачивания", Toast.LENGTH_SHORT).show();
                }
        });

        visitWebSiteButton.setOnClickListener(v ->{

                if (!imageUrl.isEmpty()) {
                    Uri webpage = Uri.parse(imageUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Нет доступной ссылки для перехода", Toast.LENGTH_SHORT).show();
                }
        });
    }

    private void searchImage(String query) {
        new Thread(() -> {
            try {
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                String url = "https://api.unsplash.com/search/photos?query=" + encodedQuery + "&client_id=" + ACCESS_KEY;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray results = jsonObject.getJSONArray("results");

                    if (results.length() > 0) {
                        imageUrl = results.getJSONObject(0).getJSONObject("urls").getString("regular");
                        loadImageFromUrl(imageUrl);
                    } else {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Изображение не найдено", Toast.LENGTH_SHORT).show());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void loadImageFromUrl(String urlString) {
        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                runOnUiThread(() -> imageView.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


    private void downloadImage(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Скачивание изображения");
        request.setDescription("Загрузка изображения с Unsplash");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloaded_image.jpg");

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            Toast.makeText(this, "Скачивание начато", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка инициализации загрузчика", Toast.LENGTH_SHORT).show();
        }
    }


}