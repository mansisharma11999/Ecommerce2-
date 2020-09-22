package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class phonelogin extends AppCompatActivity {
    EditText phonenumber;
    Button verifybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
        phonenumber=findViewById(R.id.edittextphonenumber);
        verifybutton=findViewById(R.id.buttonverify);
        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=phonenumber.getText().toString();
                if (number.isEmpty() || number.length()<10){
                    phonenumber.setError("valid number is required");
                    phonenumber.requestFocus();
                    return;



                }
                String phonenumber="+91"+number;
                Intent j=new Intent(phonelogin.this,PhoneVerification.class);
                j.putExtra("phonenumber",phonenumber);
                startActivity(j);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent i=new Intent(this,Main2Activity.class);
            startActivity(i);
        }
    }
}