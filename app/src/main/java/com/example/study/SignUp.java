package com.example.study;
import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignUp extends Activity {
    EditText name,email,password,mobile;
    String Name, Email, Password, Mobile,gender, Image;
    RadioButton genderMale,genderFemale;
    Button register;
    private String downloadImageUrlres;
    private CircleImageView profile;
    private TextView profileChangeTextBtnres;
    StorageReference storageProfilePrictureRefres;
    ProgressBar loading;
    private Uri imageUrires;
    private FirebaseAuth mAuth;
    DatabaseReference RootRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        storageProfilePrictureRefres = FirebaseStorage.getInstance().getReference().child("Profile pictures");
        name = findViewById(R.id.Name);
        email = findViewById(R.id.Enter_Email);
        password = findViewById(R.id.Enter_Password);
        mobile = findViewById(R.id.Enter_Mobile);
        genderMale =  findViewById(R.id.male);
        genderFemale =  findViewById(R.id.female);
        register = findViewById(R.id.Register);
        profileChangeTextBtnres = (TextView)findViewById(R.id.upload);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.INVISIBLE);
        profile = findViewById(R.id.profile);
        mAuth = FirebaseAuth.getInstance();


        RootRef = FirebaseDatabase.getInstance().getReference();


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(imageUrires)
                        .setAspectRatio(1, 1)
                        .start(SignUp.this);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Name = name.getText().toString().trim();
                Email = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                Mobile = mobile.getText().toString().trim();
                gender="";

                if (Name.isEmpty()){
                    name.setError("Enter Your Name");
                    name.requestFocus();
                }
                if (Email.isEmpty()){
                    email.setError("Enter Your Name");
                    email.requestFocus();
                }
                if (Password.isEmpty()){
                    password.setError("Enter Your Name");
                    password.requestFocus();
                }
                if (Mobile.isEmpty()){
                    mobile.setError("Enter Your Name");
                    mobile.requestFocus();
                }
                if(genderMale.isChecked()){
                    gender="Male";
                }
                if(genderFemale.isChecked()){
                    gender="Female";
                }

                if (!(Name.isEmpty()&&Email.isEmpty()&&Password.isEmpty()&&Mobile.isEmpty() && gender.isEmpty())){
                    loading.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(SignUp.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        updateImage();

                                        DataBase information = new DataBase(Name,
                                                Email,
                                                gender,
                                                Mobile);
                                                RootRef.child("Student")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(SignUp.this, "Registration Complete", Toast.LENGTH_LONG).show();
                                                loading.setVisibility(View.GONE);
                                                Intent intent = new Intent(SignUp.this, LogIn.class);
                                                startActivity(intent);
                                                finish();
                                             }
                                        });
                                    }
                                    else{
                                        loading.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this,"SignUp Unsuccessful",
                                                Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                }
            }



        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUrires = result.getUri();

            profile.setImageURI(imageUrires);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SignUp.this, SignUp.class));
            finish();
        }
    }

    private void updateImage() {

        if (imageUrires != null)
        {
            final StorageReference fileRef = storageProfilePrictureRefres
                    .child(Mobile + ".jpg");

            final UploadTask uploadTask = fileRef.putFile(imageUrires);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    String message = e.toString();
                    Toast.makeText(SignUp.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(SignUp.this, "User Profile Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                        {
                            if (!task.isSuccessful())
                            {
                                throw task.getException();
                            }

                            downloadImageUrlres = fileRef.getDownloadUrl().toString();
                            return fileRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                downloadImageUrlres = task.getResult().toString();

                            }
                            else
                            {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(SignUp.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }

}