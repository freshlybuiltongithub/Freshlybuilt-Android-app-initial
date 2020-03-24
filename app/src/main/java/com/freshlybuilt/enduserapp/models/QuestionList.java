package com.freshlybuilt.enduserapp.models;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("guid")
    @Expose
    private Guid guid;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("excerpt")
    @Expose
    private Excerpt excerpt;
    @SerializedName("author")
    @Expose
    private Integer author;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @SerializedName("ping_status")
    @Expose
    private String pingStatus;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("question_category")
    @Expose
    private List<Object> questionCategory = null;
    @SerializedName("question_tag")
    @Expose
    private List<Integer> questionTag ;
    @SerializedName("eael_transient_elements")
    @Expose
    private List<Object> eaelTransientElements = null;
    @SerializedName("_cmplz_scanned_post")
    @Expose
    private String cmplzScannedPost;
    @SerializedName("count_items")
    @Expose
    private String countItems;
    @SerializedName("wtr-disable-reading-progress")
    @Expose
    private String wtrDisableReadingProgress;
    @SerializedName("wtr-disable-time-commitment")
    @Expose
    private String wtrDisableTimeCommitment;
    @SerializedName("_sq_post_keyword")
    @Expose
    private String sqPostKeyword;
    @SerializedName("_dont_share_post_linkedin")
    @Expose
    private String dontSharePostLinkedin;
    @SerializedName("rank_math_internal_links_processed")
    @Expose
    private String rankMathInternalLinksProcessed;
    @SerializedName("anspress-image")
    @Expose
    private String anspressImage;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public Guid getGuid() {
        return guid;
    }

    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Excerpt getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Object> getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(List<Object> questionCategory) {
        this.questionCategory = questionCategory;
    }

    public List<Integer> getQuestionTag() {
        return questionTag;
    }

    public void setQuestionTag(List<Integer> questionTag) {
        this.questionTag = questionTag;
    }

    public List<Object> getEaelTransientElements() {
        return eaelTransientElements;
    }

    public void setEaelTransientElements(List<Object> eaelTransientElements) {
        this.eaelTransientElements = eaelTransientElements;
    }

    public String getCmplzScannedPost() {
        return cmplzScannedPost;
    }

    public void setCmplzScannedPost(String cmplzScannedPost) {
        this.cmplzScannedPost = cmplzScannedPost;
    }

    public String getCountItems() {
        return countItems;
    }

    public void setCountItems(String countItems) {
        this.countItems = countItems;
    }

    public String getWtrDisableReadingProgress() {
        return wtrDisableReadingProgress;
    }

    public void setWtrDisableReadingProgress(String wtrDisableReadingProgress) {
        this.wtrDisableReadingProgress = wtrDisableReadingProgress;
    }

    public String getWtrDisableTimeCommitment() {
        return wtrDisableTimeCommitment;
    }

    public void setWtrDisableTimeCommitment(String wtrDisableTimeCommitment) {
        this.wtrDisableTimeCommitment = wtrDisableTimeCommitment;
    }

    public String getSqPostKeyword() {
        return sqPostKeyword;
    }

    public void setSqPostKeyword(String sqPostKeyword) {
        this.sqPostKeyword = sqPostKeyword;
    }

    public String getDontSharePostLinkedin() {
        return dontSharePostLinkedin;
    }

    public void setDontSharePostLinkedin(String dontSharePostLinkedin) {
        this.dontSharePostLinkedin = dontSharePostLinkedin;
    }

    public String getRankMathInternalLinksProcessed() {
        return rankMathInternalLinksProcessed;
    }

    public void setRankMathInternalLinksProcessed(String rankMathInternalLinksProcessed) {
        this.rankMathInternalLinksProcessed = rankMathInternalLinksProcessed;
    }

    public String getAnspressImage() {
        return anspressImage;
    }

    public void setAnspressImage(String anspressImage) {
        this.anspressImage = anspressImage;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
