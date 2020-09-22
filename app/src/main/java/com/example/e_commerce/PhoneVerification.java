package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_commerce.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {
    private String verificationID;
   private FirebaseAuth mAuth;
   private ProgressBar progressBar;
   private EditText editText;
   private Button signin;
   FirebaseAuth firebaseAuth;
    String loginame;
    String loginemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        mAuth=FirebaseAuth.getInstance();
        editText=findViewById(R.id.otp);
        signin=findViewById(R.id.signin);
        String phonenumber=getIntent().getStringExtra("phonenumber");
         loginame=getIntent().getStringExtra("Name");
         loginemail=getIntent().getStringExtra("Email");
        sendVerificationcode(phonenumber);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editText.getText().toString().trim();
                if (code.isEmpty() ||code.length()<6){
                    editText.setError("Enter code....");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID,code);
        signinwithcredential(credential);
    }
    private void signinwithcredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()){
                 Intent i=new Intent(PhoneVerification.this,Main2Activity.class);
                // Bundle bundle=new Bundle();
                 //bundle.putString("name",loginame);
                 //bundle.putString("Email",loginemail);
                 //HomeFragment hm=new HomeFragment();
                 //hm.setArguments(bundle);
                 startActivity(i);
             }
             else{
                 Toast.makeText(PhoneVerification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
             }
            }
        });

    }
    private void sendVerificationcode(String phonenumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallback);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if (code!=null){
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    public String getName(){

        return loginame;
    }

}