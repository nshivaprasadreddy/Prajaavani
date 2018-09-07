package com.example.shiva.prajaavani;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiva.prajaavani.Models.Media.Media;
import com.example.shiva.prajaavani.Models.Posts.PostList;
import com.example.shiva.prajaavani.Models.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

   private List<PostList> listItem;
   private Context context;

    public MyAdapter(List<PostList> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.all_news_list, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
       final PostList postList = listItem.get(i);

       int id = postList.getId();
        final Document description = Jsoup.parse(postList.getExcerpt().getRendered());
        final Document body = Jsoup.parse(postList.getContent().getRendered());
        description.outputSettings().escapeMode(Entities.EscapeMode.base);
        viewHolder.textViewHead.setText(postList.getTitle().getRendered().toString());

        final Intent intent = new Intent(context,DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("title", postList.getTitle().getRendered().toString());

        Call<List<Media>> call = RetrofitClient.getmInstance().getApi().resposForImg(id);

        call.enqueue(new Callback<List<Media>>() {
            @Override
            public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                List<Media> pics = response.body();
                for (int i=0; i< pics.size(); i++){
                    String url = pics.get(i).getMediaDetails().getSizes().getThumbnail().getSourceUrl();
                    String url2 = pics.get(i).getGuid().getRendered();
                    Picasso.get().load(url).into(viewHolder.imageView);


                    intent.putExtra("imgurl",url2);



                }



                viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, postList.getTitle()+" clicked", Toast.LENGTH_LONG).show();

                        context.startActivity(intent);



                    }
                });
            }
            @Override
            public void onFailure(Call<List<Media>> call, Throwable t) {

            }
        });

        intent.putExtra("body",body.text());
       viewHolder.textViewDes.setText(description.text());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView textViewHead, textViewDes;
        public ImageView imageView;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.Heading);
            textViewDes = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
