package com.flipside;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipside.adapter.MainRecyclerAdapter;
import com.flipside.model.AllNews;
import com.flipside.model.NewsItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SportsNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportsNewsFragment extends Fragment {

    RecyclerView rvNews;
    MainRecyclerAdapter mainRecyclerAdapter;
    LinearLayoutManager layoutManager;

    FirebaseApp mApp;
    FirebaseDatabase mDatabase;

    private static final String TAG = "SportsNewsPage";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SportsNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SportsNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SportsNewsFragment newInstance(String param1, String param2) {
        SportsNewsFragment fragment = new SportsNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        rvNews = view.findViewById(R.id.newsRecyclerView);
        initFirebase();
        readDatabaseData();

        return view;
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
                    String subjectCategory = child.child("SubjectCategory").getValue(String.class);

                    if (subjectCategory.equals("ספורט")) {
                        String subjectTitle = child.child("subjectName").getValue(String.class);
                        Log.d(TAG, "subjectTitle " + subjectTitle);
                        String subjectPic = child.child("subjectPic").getValue(String.class);
                        Log.d(TAG, "subjectPic " + subjectPic);
                        List<NewsItem> newsItemList = new ArrayList<>();
                        for (DataSnapshot grandChild : child.child("News").getChildren()) {
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

        mainRecyclerAdapter = new MainRecyclerAdapter(this.getActivity(), allNewsList);
        layoutManager = new LinearLayoutManager(this.getContext());
        rvNews.setLayoutManager(layoutManager);
        rvNews.setAdapter(mainRecyclerAdapter);
    }
}