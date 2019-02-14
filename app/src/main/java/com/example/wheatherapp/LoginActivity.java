package com.example.wheatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Email;
    private EditText Password;
    private Button Login;
    private Button Register;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = (EditText) findViewById(R.id.editText);
        Password = (EditText) findViewById(R.id.editText2);
        Login = (Button) findViewById(R.id.btn);
        Login.setOnClickListener(this);
        Register = (Button) findViewById(R.id.register);
        Register.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        pro = new ProgressDialog(this);
        /*if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
            startActivity(intent);
        }*/

    }

    @Override
    public void onClick(View v) {
        if(v==Register){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        if(v==Login){
            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
                return;
            }
            pro.setMessage("Logging in...");
            pro.show();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pro.dismiss();
                        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                        startActivity(intent);
                    }
                    else{
                        pro.dismiss();
                        Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
