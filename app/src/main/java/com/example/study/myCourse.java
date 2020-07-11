package com.example.study;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class myCourse extends AppCompatActivity {
    TextView Course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_course);

        noCourse();
    }

    public void noCourse(){
        Course = findViewById(R.id.Course);
        Course.setText("No Course Available");
    }

    public void myCourseList(){

    }
}