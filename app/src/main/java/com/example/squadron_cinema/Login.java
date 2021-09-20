package com.example.squadron_cinema;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity{

    //declaring variables
    private EditText loginpswd,loginmail;
    private Button buttonlogin;
    private TextView registerhere, forgotpassword;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //hiding the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();  //hiding the actionbar


        setContentView(R.layout.activity_login);


        mAuth           = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null){
                    Intent i = new Intent(Login.this, Myprofile.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        //initializing all variables
        registerhere    = findViewById(R.id.registerhere);
        loginmail       = findViewById(R.id.loginmail);
        loginpswd       = findViewById(R.id.loginpswd);
        forgotpassword  = findViewById(R.id.forgotpassword);
        buttonlogin     = findViewById(R.id.buttonlogin);


        //onclick listener to register here
        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        //onclick listener to login button
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = loginmail.getText().toString().trim();
                final String pswd = loginpswd.getText().toString().trim();

                //validating whether login field are empty or not
                if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(pswd)){
                    Toast.makeText(Login.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mAuth.signInWithEmailAndPassword(Email, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this, Myprofile.class);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }*/
}
