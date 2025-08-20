package com.example.youtubesearch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubesearch.Adapter.VideoAdapter;
import com.example.youtubesearch.Models.Video;
import com.example.youtubesearch.Models.YouTubeSearchResponse;
import com.example.youtubesearch.Networking.YouTubeApiService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ProgressBar loadingProgressBar;
    private VideoAdapter videoAdapter;
    private final List<Video> videoList = new ArrayList<>();

    private static final String API_KEY = "AIzaSyAEk7F_bbhTFUWxwJXDn5fzxviwCJYk7EY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        RecyclerView resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(videoList);
        resultsRecyclerView.setAdapter(videoAdapter);

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                return;
            }
            searchVideos(query);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchVideos(String query) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        videoList.clear();
        videoAdapter.notifyDataSetChanged();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeApiService service = retrofit.create(YouTubeApiService.class);
        Call<YouTubeSearchResponse> call = service.searchVideos(
                "snippet",
                "video",
                query,
                25,
                API_KEY
        );

        call.enqueue(new Callback<YouTubeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<YouTubeSearchResponse> call, @NonNull Response<YouTubeSearchResponse> response) {
                loadingProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<YouTubeSearchResponse.Item> items = response.body().getItems();
                    if (items == null || items.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (YouTubeSearchResponse.Item item : items) {
                        String title = item.getSnippet().getTitle();
                        String description = item.getSnippet().getDescription();
                        String channelTitle = item.getSnippet().getChannelTitle();
                        String thumbnailUrl = item.getSnippet().getThumbnails().getMedium().getUrl();
                        String publishTime = item.getSnippet().getPublishedAt();

                        videoList.add(new Video(title, description, channelTitle, thumbnailUrl, publishTime));
                    }
                    videoAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<YouTubeSearchResponse> call, @NonNull Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}