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

public class Register extends AppCompatActivity {
    EditText fullName, useremail, userpassword, password2;
    Button registerButton;
    FirebaseAuth fireAuth;
    ProgressBar progressBar;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.fullName);
        useremail = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);


        login = findViewById(R.id.textView4);
        registerButton = findViewById(R.id.registerButton);
        fireAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        if(fireAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = useremail.getText().toString().trim();
                String password = userpassword.getText().toString().trim();
                String confirmPassword = password2.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    useremail.setError("Email section is Empty!"); return;
                }
                if(TextUtils.isEmpty(password)){
                    userpassword.setError("Enter a valid password!"); return;
                }
                if(password.length()<=8){
                    userpassword.setError("Password must be 8 or more character!"); return;

                }
//                if(password.equals(confirmPassword)){
//                    confirmPassword.setError("passwoprd do not match!");return;;
//
//                }
                progressBar.setVisibility(View.VISIBLE);

                fireAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Account Registered!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Error creating account" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
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