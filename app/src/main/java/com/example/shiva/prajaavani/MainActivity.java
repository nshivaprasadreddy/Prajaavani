package com.example.shiva.prajaavani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.shiva.prajaavani.Models.Posts.PostList;
import com.example.shiva.prajaavani.Models.RetrofitClient;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<List<PostList>> call = RetrofitClient.getmInstance().getApi().getPostList();

        call.enqueue(new Callback<List<PostList>>() {
            @Override
            public void onResponse(Call<List<PostList>> call, Response<List<PostList>> response) {
                List<PostList> postLists = response.body();
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                adapter = new MyAdapter(postLists, getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<PostList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();
            }
        });


    }


}
