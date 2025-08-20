package com.example.youtubesearch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.youtubesearch.Models.Video;
import com.example.youtubesearch.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private final List<Video> videoList;

    public VideoAdapter(List<Video> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.titleTextView.setText(video.getTitle());
        holder.descriptionTextView.setText(video.getDescription());
        holder.channelTextView.setText(video.getChannelTitle());
        holder.publishTimeTextView.setText(formatPublishTime(video.getPublishTime()));

        Glide.with(holder.thumbnailImageView.getContext())
                .load(video.getThumbnailUrl())
                .into(holder.thumbnailImageView);
    }

    private String formatPublishTime(String publishedAt) {
        if (publishedAt == null) return "";
        return "Published: " + publishedAt.substring(0, 10);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView, channelTextView, publishTimeTextView;
        ImageView thumbnailImageView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.videoTitle);
            descriptionTextView = itemView.findViewById(R.id.videoDescription);
            channelTextView = itemView.findViewById(R.id.videoChannel);
            publishTimeTextView = itemView.findViewById(R.id.videoPublishTime);
            thumbnailImageView = itemView.findViewById(R.id.videoThumbnail);
        }
    }
}