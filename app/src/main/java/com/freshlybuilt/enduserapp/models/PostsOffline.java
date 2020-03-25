package com.freshlybuilt.enduserapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PostsOffline")
public class PostsOffline {
    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "excerpt")
    private String excerpt;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public PostsOffline(String name, String title, String excerpt, String url, String thumbnail) {

        this.name = name;
        this.title = title;
        this.excerpt = excerpt;
        this.url = url;
        this.thumbnail = thumbnail;
    }

}
