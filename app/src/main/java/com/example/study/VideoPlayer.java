package com.example.study;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class VideoPlayer extends Activity {

    private VideoView video;
    TextView Title,Description,Author;
    ListView Course;
    Bundle Extra;
    String Link,title, author;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer);
        video = findViewById(R.id.Video_Player);
        Title = findViewById(R.id.title);
        Description = findViewById(R.id.Description);
        Author = findViewById(R.id.Author);
        Course = findViewById(R.id.CourseList);


        Extra = getIntent().getExtras();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Link= null;
                title = null;
                author = null;
            } else {
                Link= extras.getString("link");
                title = extras.getString("title");
                author = extras.getString("author");
            }
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            playVideo(title,Link,author);
        }
        else{
            Intent homeIntent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(homeIntent);
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.WishList:
                        Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myCourse:
                        startActivity(new Intent(getApplicationContext(),myCourse.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.search_button:
                        Toast.makeText(getApplicationContext(), "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myAccount:
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void playVideo(String VideoTitle,String Descrip,String AuthorName,String Link){
        Title.setText(VideoTitle);
        Description.setText(Descrip);
        Author.setText(AuthorName);

        MediaController controller = new MediaController(this);
        video.setMediaController(controller);
        controller.setAnchorView(video);
        Uri uri = Uri.parse(Link);
        video.setVideoURI(uri);
        video.requestFocus();
        video.start();
    }

    public void playVideo(String VideoTitle, String Link, String author){
        Title.setText(VideoTitle);
        Author.setText(author);
        MediaController controller = new MediaController(this);
        video.setMediaController(controller);
        controller.setAnchorView(video);
        Uri uri = Uri.parse(Link);
        video.setVideoURI(uri);
        video.requestFocus();
        video.start();
    }
}
