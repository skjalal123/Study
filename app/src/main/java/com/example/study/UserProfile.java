package com.example.study;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference reference;
    TextView Name,ActiveCount,CompleteCount,Email,Mobile,SignOut,Gender;
    LinearLayout Setting,Share;
    CircleImageView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        /*---------------------------Id---------------------------------------*/
        Name = findViewById(R.id.Name);
        Gender = findViewById(R.id.Gender);
        ActiveCount = findViewById(R.id.ActiveCount);
        CompleteCount = findViewById(R.id.CompleteCount);
        Email = findViewById(R.id.Email);
        Mobile = findViewById(R.id.Mobile);
        profile = (CircleImageView) findViewById(R.id.profile);

        /*---------------------------Buttons---------------------------------------*/
        Setting = findViewById(R.id.Setting);
        Share = findViewById(R.id.Share);
        SignOut = findViewById(R.id.SignOut);

        /*---------------------------Main Program---------------------------------------*/
        userProfile();

        SignOut.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(UserProfile.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SettingPage.class));
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Share = new Intent(Intent.ACTION_SEND);
                Share.setType("text/plain");
                Share.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                Share.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(Share, "choose one"));
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    public void userProfile(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String User = user.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        reference = mDatabase.getReference().child("Student").child(User);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Name.setText(dataSnapshot.child("Name").getValue().toString());
                    Email.setText(dataSnapshot.child("Email").getValue().toString());
                    Mobile.setText(dataSnapshot.child("MobileNo").getValue().toString());
                    Gender.setText(dataSnapshot.child("Gender").getValue().toString());
                    Picasso.get().load(dataSnapshot.child("Image").getValue().toString()).into(profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfile.this,"Thank you",Toast.LENGTH_LONG).show();
            }
        });
    }
}

