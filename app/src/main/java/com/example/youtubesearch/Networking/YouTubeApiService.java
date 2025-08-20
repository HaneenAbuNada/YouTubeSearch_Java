package com.example.youtubesearch.Networking;

import com.example.youtubesearch.Models.YouTubeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiService {
    @GET("search")
    Call<YouTubeSearchResponse> searchVideos(
            @Query("part") String part,
            @Query("type") String type,
            @Query("q") String query,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
}