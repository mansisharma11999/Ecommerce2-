package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignin;
    private FirebaseAuth firebaseAuth;
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    Button phonelogin;
    TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() !=null){

            finish();

            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);

        }

        editTextEmail=findViewById(R.id.edittextemail);
        editTextPassword=findViewById(R.id.edittextpassword);
        buttonSignin= findViewById(R.id.buttonsign);
        //textViewSignup=findViewById(R.id.textviewsignup);
        phonelogin=findViewById(R.id.phonesignin);
        forget=findViewById(R.id.forgetpass);
        phonelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login2.this,phonelogin.class);
                startActivity(i);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forgetemailid=editTextEmail.getText().toString().trim();

                FirebaseAuth.getInstance().sendPasswordResetEmail(forgetemailid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login2.this,"Email sent", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(Login2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        progressDialog =new ProgressDialog(this);

        buttonSignin.setOnClickListener(this);
        //textViewSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==buttonSignin){
            userlogin();
        }
        /*if (v==textViewSignup){
            finish();

            startActivity(new Intent(this,MainActivity3.class));

        }*/



    }
    private void userlogin() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(editTextEmail.getText())) {
            editTextEmail.setError("Email is Required");
        } else if (TextUtils.isEmpty(editTextPassword.getText())) {
            editTextPassword.setError("Password is Required");
        } else {
            progressDialog.setMessage("signinginplease wait");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();


                    if (task.isSuccessful()) {

                        finish();
                        startActivity(new Intent(getApplicationContext(), Main2Activity.class));

                    }


                }
            });
        }
    }
}