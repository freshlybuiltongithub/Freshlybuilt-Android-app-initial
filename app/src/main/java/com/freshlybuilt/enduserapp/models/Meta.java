package com.freshlybuilt.enduserapp.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("_bbp_topic_count")
    @Expose
    private Integer bbpTopicCount;
    @SerializedName("_bbp_reply_count")
    @Expose
    private Integer bbpReplyCount;
    @SerializedName("_bbp_total_topic_count")
    @Expose
    private Integer bbpTotalTopicCount;
    @SerializedName("_bbp_total_reply_count")
    @Expose
    private Integer bbpTotalReplyCount;
    @SerializedName("_bbp_voice_count")
    @Expose
    private Integer bbpVoiceCount;
    @SerializedName("_bbp_anonymous_reply_count")
    @Expose
    private Integer bbpAnonymousReplyCount;
    @SerializedName("_bbp_topic_count_hidden")
    @Expose
    private Integer bbpTopicCountHidden;
    @SerializedName("_bbp_reply_count_hidden")
    @Expose
    private Integer bbpReplyCountHidden;
    @SerializedName("_bbp_forum_subforum_count")
    @Expose
    private Integer bbpForumSubforumCount;
    @SerializedName("spay_email")
    @Expose
    private String spayEmail;

    public Integer getBbpTopicCount() {
        return bbpTopicCount;
    }

    public void setBbpTopicCount(Integer bbpTopicCount) {
        this.bbpTopicCount = bbpTopicCount;
    }

    public Integer getBbpReplyCount() {
        return bbpReplyCount;
    }

    public void setBbpReplyCount(Integer bbpReplyCount) {
        this.bbpReplyCount = bbpReplyCount;
    }

    public Integer getBbpTotalTopicCount() {
        return bbpTotalTopicCount;
    }

    public void setBbpTotalTopicCount(Integer bbpTotalTopicCount) {
        this.bbpTotalTopicCount = bbpTotalTopicCount;
    }

    public Integer getBbpTotalReplyCount() {
        return bbpTotalReplyCount;
    }

    public void setBbpTotalReplyCount(Integer bbpTotalReplyCount) {
        this.bbpTotalReplyCount = bbpTotalReplyCount;
    }

    public Integer getBbpVoiceCount() {
        return bbpVoiceCount;
    }

    public void setBbpVoiceCount(Integer bbpVoiceCount) {
        this.bbpVoiceCount = bbpVoiceCount;
    }

    public Integer getBbpAnonymousReplyCount() {
        return bbpAnonymousReplyCount;
    }

    public void setBbpAnonymousReplyCount(Integer bbpAnonymousReplyCount) {
        this.bbpAnonymousReplyCount = bbpAnonymousReplyCount;
    }

    public Integer getBbpTopicCountHidden() {
        return bbpTopicCountHidden;
    }

    public void setBbpTopicCountHidden(Integer bbpTopicCountHidden) {
        this.bbpTopicCountHidden = bbpTopicCountHidden;
    }

    public Integer getBbpReplyCountHidden() {
        return bbpReplyCountHidden;
    }

    public void setBbpReplyCountHidden(Integer bbpReplyCountHidden) {
        this.bbpReplyCountHidden = bbpReplyCountHidden;
    }

    public Integer getBbpForumSubforumCount() {
        return bbpForumSubforumCount;
    }

    public void setBbpForumSubforumCount(Integer bbpForumSubforumCount) {
        this.bbpForumSubforumCount = bbpForumSubforumCount;
    }

    public String getSpayEmail() {
        return spayEmail;
    }

    public void setSpayEmail(String spayEmail) {
        this.spayEmail = spayEmail;
    }

}
