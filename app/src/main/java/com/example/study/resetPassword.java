package com.example.study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);

        final EditText resetEmail = findViewById(R.id.resetEmail);
        Button confirmEmail = findViewById(R.id.confirm_Email);
        mAuth = FirebaseAuth.getInstance();

        confirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = resetEmail.getText().toString();
                if(Email.isEmpty()){
                    resetEmail.setError("Enter Email");
                    resetEmail.requestFocus();
                }

                if(!Email.isEmpty()){
                    mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(resetPassword.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(resetPassword.this,"Password reset link has sent",Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
        });
    }
}
