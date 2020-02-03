
package com.freshlybuilt.enduserapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailImages {

    @SerializedName("full")
    @Expose
    private Thumbnail full;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("medium")
    @Expose
    private Thumbnail medium;
    @SerializedName("1536x1536")
    @Expose
    private Thumbnail _1536x1536;
    @SerializedName("2048x2048")
    @Expose
    private Thumbnail _2048x2048;
    @SerializedName("alm-thumbnail")
    @Expose
    private Thumbnail almThumbnail;
    @SerializedName("hestia-blog")
    @Expose
    private Thumbnail hestiaBlog;
    @SerializedName("rpwe-thumbnail")
    @Expose
    private Thumbnail rpweThumbnail;
    @SerializedName("_nx_notification_thumb")
    @Expose
    private Thumbnail nxNotificationThumb;

    public Thumbnail getFull() {
        return full;
    }

    public void setFull(Thumbnail full) {
        this.full = full;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Thumbnail getMedium() {
        return medium;
    }

    public void setMedium(Thumbnail medium) {
        this.medium = medium;
    }

    public Thumbnail get1536x1536() {
        return _1536x1536;
    }

    public void set1536x1536(Thumbnail _1536x1536) {
        this._1536x1536 = _1536x1536;
    }

    public Thumbnail get2048x2048() {
        return _2048x2048;
    }

    public void set2048x2048(Thumbnail _2048x2048) {
        this._2048x2048 = _2048x2048;
    }

    public Thumbnail getAlmThumbnail() {
        return almThumbnail;
    }

    public void setAlmThumbnail(Thumbnail almThumbnail) {
        this.almThumbnail = almThumbnail;
    }

    public Thumbnail getHestiaBlog() {
        return hestiaBlog;
    }

    public void setHestiaBlog(Thumbnail hestiaBlog) {
        this.hestiaBlog = hestiaBlog;
    }

    public Thumbnail getRpweThumbnail() {
        return rpweThumbnail;
    }

    public void setRpweThumbnail(Thumbnail rpweThumbnail) {
        this.rpweThumbnail = rpweThumbnail;
    }

    public Thumbnail getNxNotificationThumb() {
        return nxNotificationThumb;
    }

    public void setNxNotificationThumb(Thumbnail nxNotificationThumb) {
        this.nxNotificationThumb = nxNotificationThumb;
    }

}
