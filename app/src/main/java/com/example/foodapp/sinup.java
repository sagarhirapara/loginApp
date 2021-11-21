package com.example.foodapp;

import static java.util.logging.Logger.global;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sinup extends AppCompatActivity {
    TextView loginAccount;
    FirebaseDatabase database;
    DatabaseReference dref;
    Button register;
    EditText usernamebox,passwordbox,mobilebox,emailbox;
    FirebaseAuth authDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinup);

        getSupportActionBar().setTitle("Sign up");

//        already have account
        loginAccount = findViewById(R.id.LoginAccount);
        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sinup.this,signin.class);
                startActivity(intent);
            }
        });

//        database connection
        database = FirebaseDatabase.getInstance("https://fir-practice-5b909-default-rtdb.firebaseio.com");
        dref = database.getReference("users");
        authDatabase = FirebaseAuth.getInstance();

//        get the register button
        register = findViewById(R.id.signin);
        register.setOnClickListener(this::runCode);



    }

    private void runCode(View view) {
//        String text = "sagar Hirapara";
//        dref.child("user1").child("username").setValue(text);
//        dref.child("user1").child("mobile").setValue("9825035725");
//        dref.child("user1").child("password").setValue("9825035725");
//        dref.child("user1").child("email").setValue("hiraparasagar3@gmail.com");
//
//        dref.child("user2").child("username").setValue(text);
//        dref.child("user2").child("mobile").setValue("9825035725");
//        dref.child("user2").child("password").setValue("9825035725");
//        dref.child("user2").child("email").setValue("hiraparasagar3@gmail.com");
//
//
//
//        dref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String s = dataSnapshot.child("user1").child("username").getValue().toString();
//                Toast.makeText(sinup.this, s, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        usernamebox = findViewById(R.id.emailsignin);
        mobilebox = findViewById(R.id.mobileNumber);
        emailbox = findViewById(R.id.email);
        passwordbox = findViewById(R.id.passwordsignin);

        String username = usernamebox.getText().toString();
        String mobile = mobilebox.getText().toString();
        String email = emailbox.getText().toString();
        String password = passwordbox.getText().toString();

        if(username.isEmpty())
        {
            usernamebox.setError("please enter the username");
            usernamebox.requestFocus();
            return;
        }
        if(mobile.isEmpty())
        {
            mobilebox.setError("please enter the mobile number");
            mobilebox.requestFocus();
            return;
        }
        if(mobile.length() !=10)
        {
            mobilebox.setError("mobile  number must be 10 digit");
            mobilebox.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            emailbox.setError("please enter the email");
            emailbox.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailbox.setError("please enter the valid email address");
            emailbox.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordbox.setError("please enter the password");
            passwordbox.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            passwordbox.setError("length is greater than 6");
            passwordbox.requestFocus();
            return;
        }
        final int flag = 0;
        authDatabase.fetchSignInMethodsForEmail(email).
                addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean x = task.getResult().getSignInMethods().isEmpty();


                        if(!x)
                        {
                            Toast.makeText(sinup.this, "Email id already exits", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        authDatabase.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            user p = new user(username,mobile,email,password);
                            dref.child(authDatabase.getCurrentUser().getUid()).setValue(p).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                            Toast.makeText(sinup.this, "registered successful", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(sinup.this, "registration is failed", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(sinup.this, "failed to register", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}