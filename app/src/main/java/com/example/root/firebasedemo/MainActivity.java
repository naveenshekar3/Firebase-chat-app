package com.example.root.firebasedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {

    private EditText mNameEt;
    private EditText mEmailEt;
    private EditText mPasswordEt;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        
        mNameEt=(EditText)findViewById(R.id.name_et);
        mEmailEt=(EditText)findViewById(R.id.email_et);
        mPasswordEt=(EditText)findViewById(R.id.password_et);
    }

    public void login(View view) 
    {
        mAuth.createUserWithEmailAndPassword(mEmailEt.getText().toString(),mPasswordEt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    gotoChat();
                }else
                {
                    //Toast.makeText(MainActivity.this, "User not created", Toast.LENGTH_SHORT).show();
                    mAuth.signInWithEmailAndPassword(mEmailEt.getText().toString(),mPasswordEt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                gotoChat();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void gotoChat()
    {
        Intent intent=new Intent(MainActivity.this,ChatActivity.class);
        intent.putExtra("myname",mNameEt.getText().toString());
        startActivity(intent);
    }
}
