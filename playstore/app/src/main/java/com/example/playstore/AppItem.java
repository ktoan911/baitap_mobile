package com.example.playstore;

public class AppItem {
    private String name;
    private String category;
    private float rating;
    private int sizeMB;
    private int iconResId;

    public AppItem(String name, String category, float rating, int sizeMB, int iconResId) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.sizeMB = sizeMB;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getRating() {
        return rating;
    }

    public int getSizeMB() {
        return sizeMB;
    }

    public int getIconResId() {
        return iconResId;
    }
}
