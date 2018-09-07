package com.example.shiva.prajaavani;

import com.example.shiva.prajaavani.Models.Media.Media;
import com.example.shiva.prajaavani.Models.Posts.PostList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("posts")
    Call<List<PostList>> getPostList();

    @GET("media")
    Call<List<Media>> resposForImg(@Query("parent") int id);
}
