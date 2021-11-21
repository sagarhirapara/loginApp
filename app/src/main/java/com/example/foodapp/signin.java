package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class signin extends AppCompatActivity {
    TextView createAccount , forgetPassword;
    EditText emailbox,passwordbox;
    Button loginbox;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
//        change the toolbar title
        getSupportActionBar().setTitle("sign in ");

        createAccount = findViewById(R.id.LoginAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin.this,sinup.class);
                startActivity(intent);
            }
        });
        loginbox =findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
        loginbox.setOnClickListener(this::runcode);
        emailbox = findViewById(R.id.emailsignin);
        passwordbox = findViewById(R.id.passwordsignin);
        forgetPassword = findViewById(R.id.forgetPasword);
        forgetPassword.setOnClickListener(this::forgetPassword);



    }

    private void forgetPassword(View view) {
        startActivity(new Intent(signin.this,resetPassword.class));
    }

    private void runcode(View view) {
     String email = emailbox.getText().toString();
     String password = passwordbox.getText().toString();

     if(email.isEmpty()){
         emailbox.setError("email is required");
         emailbox.requestFocus();
         return;
     }
     if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
     {
         emailbox.setError("please enter valid email");
         emailbox.requestFocus();
         return;
     }
     if(password.isEmpty())
     {
         passwordbox.setError("password is required");
         passwordbox.requestFocus();
         return;
     }

     mAuth.signInWithEmailAndPassword(email,password).
             addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful())
                     {
                         startActivity(new Intent(signin.this,main.class));
                     }
                     else
                     {
                         Toast.makeText(signin.this, "failed to login", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
    }

}