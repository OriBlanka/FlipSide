package com.flipside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flipside.adapter.MainRecyclerAdapter;
import com.flipside.model.AllNews;
import com.flipside.model.NewsItem;
import com.android.volley.Response;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsPage extends AppCompatActivity {

    Button logout;
    RecyclerView mainNewsRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;

    FirebaseApp mApp;
    FirebaseDatabase mDatabase;

    private static final String TAG = "NewsPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        initFirebase();
        readDatabaseData();
    }

    private void initFirebase() {
        mApp = FirebaseApp.getInstance();
        mDatabase = FirebaseDatabase.getInstance(mApp);
    }

    private void readDatabaseData() {
        DatabaseReference ref = mDatabase.getReference("NewSubjects");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<AllNews> allNewsList = new ArrayList<>();
                Log.d(TAG, "Snapshot received " + dataSnapshot.getValue());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Child node" + child.getValue());
                    String subjectTitle = child.child("subjectName").getValue(String.class);
                    Log.d(TAG, "subjectTitle " + subjectTitle);
                    String subjectPic = child.child("subjectPic").getValue(String.class);
                    Log.d(TAG, "subjectPic " + subjectPic);
                    List<NewsItem> newsItemList = new ArrayList<>();
                    for(DataSnapshot grandChild : child.child("News").getChildren()){
                        String newsName = grandChild.child("title").getValue(String.class);
                        Log.d(TAG, "newsName " + newsName);
                        String newsLink = grandChild.child("link").getValue(String.class);
                        Log.d(TAG, "newsLink " + newsLink);
                        String newsSummary = grandChild.child("summary").getValue(String.class);
                        Log.d(TAG, "newsSummary " + newsSummary);
                        String newsWebImg = grandChild.child("webName").getValue(String.class);
                        newsItemList.add(new NewsItem(1, newsName, newsSummary, newsLink, newsWebImg));
                    }
                    allNewsList.add(new AllNews(subjectTitle, subjectPic, newsItemList));
                }
                setMainNewsRecycler(allNewsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(eventListener);
    }

    private void setMainNewsRecycler(List<AllNews> allNewsList) {

        mainNewsRecycler = findViewById(R.id.mainRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainNewsRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allNewsList);
        mainNewsRecycler.setAdapter(mainRecyclerAdapter);
    }

}