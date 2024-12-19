package com.example.kot4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kot4.model.JSONPlaceholder;
import com.example.kot4.model.Post;
import com.example.kot4.model.PostAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class    MainActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public TextView emptyList;
    public RecyclerView postsView;
    public Button loadBtn, authorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        postsView = findViewById(R.id.postsView);
        postsView.setHasFixedSize(true);
        postsView.setLayoutManager(new LinearLayoutManager(this));

        loadBtn = findViewById(R.id.loadBtn);
        authorBtn = findViewById(R.id.authorBtn);

        progressBar = findViewById(R.id.progressBar);
        emptyList = findViewById(R.id.emptyList);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                postsView.setVisibility(View.GONE);
                emptyList.setVisibility(View.GONE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JSONPlaceholder jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
                Call<List<Post>> call = jsonPlaceholder.getPost();
                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                        progressBar.setVisibility(View.GONE);
                        if (!response.isSuccessful()) {
                            showAlertDialog("Error!", String.valueOf(response.code()));
                            return;
                        }

                        List<Post> postList = response.body();
                        PostAdapter postAdapter = new PostAdapter(MainActivity.this, postList);
                        postsView.setAdapter(postAdapter);
                        postsView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable throwable) {
                        showAlertDialog("Error!", throwable.getMessage());
                    }
                });
            }
        });

        authorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Разработал", getString(R.string.author));
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}