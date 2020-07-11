package com.example.study;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSetting extends Activity {

    EditText name, email, password, mobile;
    String Name, Email, Mobile, Gender,gender;
    RadioButton genderMale, genderFemale;
    Button Update;
    CircleImageView profile;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting);

        //profile = findViewById(R.id.User_Image);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.Enter_Email);
        password = findViewById(R.id.Enter_Password);
        mobile = findViewById(R.id.Enter_Mobile);
        genderMale = findViewById(R.id.male);
        genderFemale = findViewById(R.id.female);
        Update = findViewById(R.id.Update);
        mAuth = FirebaseAuth.getInstance();

        userProfile();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
                Intent i = new Intent(getApplicationContext(), UserProfile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }


    public void userProfile() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String User = user.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        reference = mDatabase.getReference().child("Student").child(User);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    name.setText(dataSnapshot.child("Name").getValue().toString());
                    email.setText(dataSnapshot.child("Email").getValue().toString());
                    mobile.setText(dataSnapshot.child("MobileNo").getValue().toString());
                    //Picasso.get().load("Image").into(profile);
                    gender = dataSnapshot.child("Gender").getValue().toString();
                    if(gender.equals("Male")){
                        genderMale.setChecked(true);
                        genderFemale.setChecked(false);
                    }
                    if(gender.equals("Female")){
                        genderFemale.setChecked(true);
                        genderMale.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void UpdateData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String User = user.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        reference = mDatabase.getReference().child("Student").child(User);

        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Mobile = mobile.getText().toString().trim();
        Gender = "";

        if (Name.isEmpty()) {
            name.setError("Enter Your Name");
            name.requestFocus();
        }
        if (Email.isEmpty()) {
            email.setError("Enter Your Name");
            email.requestFocus();
        }
        if (Mobile.isEmpty()) {
            mobile.setError("Enter Your Name");
            mobile.requestFocus();
        }
        if (genderMale.isChecked()) {
            Gender = "Male";
        }
        if (genderFemale.isChecked()) {
            Gender = "Female";
        }


        if (!(Name.isEmpty() && Email.isEmpty() && Mobile.isEmpty() && Gender.isEmpty())) {
            DataBase information = new DataBase(Name,
                    Email,
                    Gender,
                    Mobile);
            FirebaseDatabase.getInstance().getReference("Student")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Data Updated",
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}