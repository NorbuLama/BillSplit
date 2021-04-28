package com.example.billsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button loginbtn;
    FirebaseAuth fireAuth;
    EditText useremail, userpassword;
    TextView register;
    ProgressBar pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        useremail = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginButton);
        register = findViewById(R.id.register);
        fireAuth = FirebaseAuth.getInstance();
        pbar = findViewById(R.id.progressBar2);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = useremail.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    useremail.setError("Email section is Empty!"); return;
                }
                if(TextUtils.isEmpty(password)){
                    userpassword.setError("Enter a valid password!"); return;
                }
                if(password.length()<=8){
                    userpassword.setError("Password must be 8 or more character!"); return;

                }
                pbar.setVisibility(View.VISIBLE);


                fireAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this, "login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(login.this, "Login Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));

            }
        });
    }
    public static boolean password(String s) {
        boolean valid = true;

        if(s.length() <= 8) {
            return false;
        } else {
            return true;
        }

    }

    public static boolean isEmailValid(String email) {
        if (email.contains("@") && email.contains(".com")) {
            return true;
        }
        return false;
    }
}