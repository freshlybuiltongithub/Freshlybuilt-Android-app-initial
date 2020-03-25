package com.freshlybuilt.enduserapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts {
    @SerializedName("url")
    @Expose
    private String url;



    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_plain")
    @Expose
    private String titlePlain;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("excerpt")
    @Expose
    private String excerpt;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("thumbnail_images")
    @Expose
    private ThumbnailImages thumbnailImages;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlePlain() {
        return titlePlain;
    }

    public void setTitlePlain(String titlePlain) {
        this.titlePlain = titlePlain;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public ThumbnailImages getThumbnailImages() {
        return thumbnailImages;
    }

    public void setThumbnailImages(ThumbnailImages thumbnailImages) { this.thumbnailImages = thumbnailImages; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
