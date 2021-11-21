package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class resetPassword extends AppCompatActivity {
    EditText emailbox;
    Button resetPassword;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        emailbox = findViewById(R.id.emailForReset);
        resetPassword = findViewById(R.id.resetPassword);
        mAuth = FirebaseAuth.getInstance();
        resetPassword.setOnClickListener(this::resetPassword);

    }

    private void resetPassword(View view) {
        String email = emailbox.getText().toString().trim();
        if(email.isEmpty())
        {
            emailbox.setError("enter email");
            emailbox.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailbox.setError("enter valid email");
            emailbox.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(resetPassword.this, "email sent successfully check teh email", Toast.LENGTH_SHORT).show();
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(resetPassword.this,signin.class));
                        }
                        else
                        {
                            Toast.makeText(resetPassword.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}