package com.example.study;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;


public class MainActivity extends Activity {
    SliderLayout sliderLayout;
    ListView CourseList;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFireStore;
    ArrayList<String> nameList = new ArrayList<>();
    HashMap<String, List<String>> key = new HashMap<>();
    String Author,Link,Name,Profile,Price,Rating;

    String[] list1 = {"Item Songs","Romantic Songs","Time Pass"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        CourseList =findViewById(R.id.CourseList);

        sliderLayout = findViewById(R.id.ImageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(1);
        setSliderViews();

        mFireStore = FirebaseFirestore.getInstance();


//--------------------------------------Course Click Listener--------------------------------------------
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), simple_list_item_1, list1);
        CourseList.setAdapter(arrayAdapter);
        CourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Course(list1[position]);
                Toast.makeText(getApplicationContext(),list1[position],Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigation();


    }

    //-------------------------------------------Bottom Navigation------------------------

    public void BottomNavigation(){
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
                        Toast.makeText(MainActivity.this, "Wish List", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myCourse:
                        try{
                            startActivity(new Intent(getApplicationContext(),myCourse.class));}catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        overridePendingTransition(0,0);
                        break;
                    case R.id.search_button:
                        Toast.makeText(MainActivity.this, "Search Button", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myAccount:
                        firebaseAuth.addAuthStateListener(authStateListener);
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
    }
//------------------------------Slide View-------------------------------------------

    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            DefaultSliderView sliderView;
            sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.shahrukh_khan);
                    sliderView.setDescription("Shahrukh Khan");
                    break;
                case 1:
                    sliderView.setImageUrl("https://res.cloudinary.com/dyd911kmh/image/upload/f_auto,q_auto:best/v1582562373/ML_vs_AI_ph9sbt.png");
                    sliderView.setDescription("Machine Learning");
                    break;
                case 2:
                    sliderView.setImageUrl("https://www.dataquest.io/wp-content/uploads/2019/05/what-is-data-science.jpg");
                    sliderView.setDescription("Data Science");
                    break;
                case 3:
                    sliderView.setImageUrl("https://5.imimg.com/data5/QQ/CT/AO/GLADMIN-25397883/selection-064-500x500.png");
                    sliderView.setDescription("JAVA Programming");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            sliderLayout.addSliderView(sliderView);
        }
    }

    //------------------------------Check User Status------------------------

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


    //-------------------------Course List-----------------------------
    public void Course(final String data){
        CollectionReference db = FirebaseFirestore.getInstance().collection(data);

        db.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        nameList.add(id);
                    }
                }

                Intent i = new Intent(getApplicationContext(),CourseList.class);
                i.putExtra("Data",data);
                i.putExtra("Position", nameList);
                startActivity(i);
                nameList.clear();
            }
        });

    }


    //--------------------------------------Back Button--------------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

}

