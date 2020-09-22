package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class phonenumber extends AppCompatActivity {

    EditText phonenumber;
    Button verifybutton;
    EditText loginname,loginemail;
    DatabaseReference mDatabase;
    String ed1,ed2;
    static  phonenumber phonenumber1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumber);
        phonenumber=findViewById(R.id.edittextphonenumber);
        verifybutton=findViewById(R.id.buttonverify);
        loginname=findViewById(R.id.name);
        loginemail=findViewById(R.id.email);
        phonenumber1=this;

        verifybutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String number=phonenumber.getText().toString();
                mDatabase = FirebaseDatabase.getInstance().getReference();

                ed1=loginname.getText().toString();
                ed2=loginemail.getText().toString();

                if(TextUtils.isEmpty(loginname.getText())){
                    loginname.setError("Name is Mandatory");
                }
                else if (number.isEmpty() || number.length()<10){
                    phonenumber.setError("Valid Number is required");
                    phonenumber.requestFocus();
                    return;
                }else
                {


                String phonenumber="+91"+number;
                Toast.makeText(phonenumber.this, "OTP SENT", Toast.LENGTH_SHORT).show();
                Intent j=new Intent(phonenumber.this,PhoneVerification.class);
                j.putExtra("phonenumber",phonenumber);
                //j.putExtra("Name",ed1);
                //j.putExtra("Email",ed2);
                startActivity(j);
                }
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
    public String getname(){
        return ed1;

    }
    public String getemail(){
        return ed2;
    }
    public static phonenumber getInstance(){

        return phonenumber1;
    }
}
