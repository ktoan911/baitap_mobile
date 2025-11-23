package com.example.playstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuggestedAdapter extends RecyclerView.Adapter<SuggestedAdapter.ViewHolder> {
    private List<AppItem> apps;

    public SuggestedAdapter(List<AppItem> apps) {
        this.apps = apps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggested, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppItem app = apps.get(position);
        holder.appIcon.setImageResource(app.getIconResId());
        holder.appName.setText(app.getName());
        holder.appCategory.setText(app.getCategory());
        holder.appRating.setText(String.format("%.1f", app.getRating()));
        holder.appSize.setText(String.format("%d MB", app.getSizeMB()));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appCategory;
        TextView appRating;
        TextView appSize;

        ViewHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.appIcon);
            appName = itemView.findViewById(R.id.appName);
            appCategory = itemView.findViewById(R.id.appCategory);
            appRating = itemView.findViewById(R.id.appRating);
            appSize = itemView.findViewById(R.id.appSize);
        }
    }
}
