package com.example.shiva.prajaavani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView textView,textView1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageView2);

        String title = getIntent().getStringExtra("title");
        String imgurl = getIntent().getStringExtra("imgurl");
        String body= getIntent().getStringExtra("body");

        textView.setText(title);
        textView1.setText(body);
        Picasso.get().load(imgurl).into(imageView);




    }
}
