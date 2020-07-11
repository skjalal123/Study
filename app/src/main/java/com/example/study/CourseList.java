package com.example.study;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class CourseList extends AppCompatActivity {
    ListView Course;
    String Author,Link,Name,Profile,Price,Rating;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Bundle Extra;
    ArrayList<String> nameList;
    String Data;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        Course = findViewById(R.id.CourseList);
        RecyclerView recyclerView = findViewById(R.id.CourseList1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CourseList.this);
        recyclerView.setLayoutManager(layoutManager);


        Extra = getIntent().getExtras();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nameList= null;
            } else {
                nameList= extras.getStringArrayList("Position");
            }
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Data= null;
            } else {
                Data= extras.getString("Data");
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), simple_list_item_1, nameList);
        Course.setAdapter(arrayAdapter);


        Course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Data(Data, nameList.get(position));
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.WishList:
                        Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myCourse:
                        startActivity(new Intent(getApplicationContext(), myCourse.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.search_button:
                        Toast.makeText(getApplicationContext(), "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myAccount:
                        firebaseAuth.addAuthStateListener(authStateListener);
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null) {
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
                    finish();
                }
                if (firebaseUser != null) {
                    Intent i = new Intent(getApplicationContext(), UserProfile.class);
                    startActivity(i);
                    finish();
                }
            }
        };

    public void Data(String Data, String nameList1){

        Source source = Source.CACHE;
        db.collection(Data).document(nameList1).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()){
                    for (Map.Entry<String, Object> data : documentSnapshot.getData().entrySet()){
                        if(String.valueOf(data.getKey()).equals("Author")) { Author= data.getValue().toString(); }
                        if(String.valueOf(data.getKey()).equals("Link")) {Link= data.getValue().toString();}
                        if(String.valueOf(data.getKey()).equals("Name")) {Name= data.getValue().toString();}
                        if(String.valueOf(data.getKey()).equals("Profile")) { Profile = data.getValue().toString();}
                        if(String.valueOf(data.getKey()).equals("Price")) { Price = data.getValue().toString();}
                        if(String.valueOf(data.getKey()).equals("Rating")) { Rating = data.getValue().toString();}
                    }
                    System.out.println(Name);
                    System.out.println(Profile);
                    System.out.println(Author);
                    System.out.println(Price);
                    System.out.println(Rating);
                    System.out.println(Link);


//                    RecyclerView.Adapter myAdapter;
//                    ArrayList<recylcer> people = new ArrayList<>();
//                    people.add(new recylcer(Profile,Name,Author,Integer.parseInt(Price) , Float.parseFloat(Rating)));
//                    myAdapter = new recyclerAdapter(CourseList.this,people);
//                    recyclerView.setAdapter(myAdapter);
                    Intent i = new Intent(getApplicationContext(),VideoPlayer.class);
                    i.putExtra("link", Link);
                    i.putExtra("author", Author);
                    i.putExtra("title", Name);
                    i.putExtra("profile",Profile);
                    i.putExtra("Price",Price);
                    i.putExtra("Rating",Rating);
                    startActivity(i);
                }

            }
        });

    }
}
