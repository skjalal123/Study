package com.example.study;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;


public class LogIn extends Activity {
    TextView register, forgetPassword;
    EditText email,resetEmail;
    EditText password;
    Button Log_In;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        /*--------------------------------------id--------------------------------------*/
        mAuth = FirebaseAuth.getInstance();
        Log_In =  findViewById(R.id.login);
        register =  findViewById(R.id.Creat_Account);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgetPassword=findViewById(R.id.Forget_password);
        resetEmail = findViewById(R.id.resetEmail);
        /*-----------------------------------Log In------------------------------------*/
        Log_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Ent_Email = email.getText().toString();
                String Ent_password = password.getText().toString();
                if(TextUtils.isEmpty(Ent_Email)){
                    email.setError("Enter Email");
                    email.requestFocus();
                }
                if(TextUtils.isEmpty(Ent_password)){
                    password.setError("Enter Password");
                    password.requestFocus();
                }

                if(!(Ent_Email.isEmpty()&&Ent_password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(Ent_Email, Ent_password)
                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(LogIn.this,
                                                "Sign In SuccessFul", Toast.LENGTH_LONG).show();
                                        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(homeIntent);
                                    } else {
                                        Toast.makeText(LogIn.this,
                                                "LogIn Failed " + Objects.requireNonNull(task.getException()).getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    }
                }
            });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),resetPassword.class));
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

}