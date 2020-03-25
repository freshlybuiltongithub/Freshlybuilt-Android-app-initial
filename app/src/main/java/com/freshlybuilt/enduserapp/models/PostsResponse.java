package com.freshlybuilt.enduserapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PostsResponse {
 private String status;
 private float count;
 private float count_total;
 private float pages;
 @SerializedName("posts")
 @Expose
 private List<Posts> posts = null;

// List< Object > posts = new ArrayList< Object >();


 // Getter Methods 

 public String getStatus() {
  return status;
 }

 public float getCount() {
  return count;
 }

 public float getCount_total() {
  return count_total;
 }

 public float getPages() {
  return pages;
 }

 public List<Posts> getPosts() { return posts; }









 // Setter Methods 

 public void setStatus(String status) {
  this.status = status;
 }

 public void setCount(float count) {
  this.count = count;
 }

 public void setCount_total(float count_total) {
  this.count_total = count_total;
 }

 public void setPages(float pages) {
  this.pages = pages;
 }

 public void setPosts(List<Posts> posts) { this.posts = posts; }



}