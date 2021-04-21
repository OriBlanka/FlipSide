package com.flipside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.flipside.adapter.MainRecyclerAdapter;
import com.flipside.model.AllNews;
import com.flipside.model.NewsItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class NewsPage extends AppCompatActivity {

    Button logout;
    RecyclerView mainNewsRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;

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

        List<NewsItem> newsItemList1 = new ArrayList<>();
        newsItemList1.add(new NewsItem(1, "מירי רגב: נחפש מתווה חדש לנתבג. בני גנץ: שיקולים פוליטיים"));
        newsItemList1.add(new NewsItem(1, "תחקיר מביש שמעליב את העיתונות"));
        newsItemList1.add(new NewsItem(1, "מחדל סגירת השמים: מאעכרים, קומבינות ופרוטקציות כדי להיכנס לישראל"));
        newsItemList1.add(new NewsItem(1, "מה הסיפור של נתניהו עם טיסות?"));

        List<NewsItem> newsItemList2 = new ArrayList<>();
        newsItemList2.add(new NewsItem(1, "דרעי: מודה לבורא עולם על ביטול האישומים השקריים נגדי"));
        newsItemList2.add(new NewsItem(1, "עורך דין אורי קורב על תיק דרעי: ההר הוליד עכבר"));
        newsItemList2.add(new NewsItem(1, "עבירות מס במקום שוחד: דרעי יואשם בכפוף לשימוע"));
        newsItemList2.add(new NewsItem(1, "סופן של עבירות המס המיוחסות לאריה דרעי - מאסר"));

        List<NewsItem> newsItemList3 = new ArrayList<>();
        newsItemList3.add(new NewsItem(1, "זה כמעט נגמר באסון: שוטר מעיף מפגין על רכב נוסע"));
        newsItemList3.add(new NewsItem(1, "מפגינים על מות אהוביה סנדק תועדו מכים שוטרים בירושלים: תשרפו אותם"));
        newsItemList3.add(new NewsItem(1, "מפגינים על מות אהוביה סנדק: תשרפו אותם"));
        newsItemList3.add(new NewsItem(1, "המחאה על מותו של אהוביה סנדק: מפגינים תקפו שוטרים, 34 נעצרו"));

        List<NewsItem> newsItemList4 = new ArrayList<>();
        newsItemList4.add(new NewsItem(1, "ג'וס ווידן איים לפגוע בקריירה של גל גדות בצילומי ליגת הצדק"));
        newsItemList4.add(new NewsItem(1, "במאי סרט ליגת הצדק התבטא באופן מעליב מול גל גדות: נמאס לי ממנה, היא צריכה לשתוק"));
        newsItemList4.add(new NewsItem(1, "ליגה ללא צדק: האם הבמאי של הסרט התעלל בגל גדות"));
        newsItemList4.add(new NewsItem(1, "דרמה בהוליווד: במאי ליגת הצדק איים לפגוע בקריירה של גל גדות"));


        List<AllNews> allNewsList = new ArrayList<>();
        allNewsList.add(new AllNews("תחקיר סגירת נתבג", R.drawable.airport_closing_topic_pic, newsItemList1));
        allNewsList.add(new AllNews("פרשת שר הפנים דרעי", R.drawable.arie_deryi_topic_pic, newsItemList2));
        allNewsList.add(new AllNews("מותו של אהוביה סנדק", R.drawable.hahuvya_topic_pic, newsItemList3));
        allNewsList.add(new AllNews("הבמאי לשחקנית גל גדות", R.drawable.gal_gadot_topic_pic, newsItemList4));

        setMainNewsRecycler(allNewsList);
    }

    private void setMainNewsRecycler(List<AllNews> allNewsList) {

        mainNewsRecycler = findViewById(R.id.mainRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainNewsRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allNewsList);
        mainNewsRecycler.setAdapter(mainRecyclerAdapter);
    }
}