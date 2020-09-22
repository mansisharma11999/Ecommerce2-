package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private EditText editTextEmail;
    Button signinwithphone;
    private EditText editTextPassword,editname;
    private Button buttonSignup;
    private Button textViewSignin;
    private ProgressDialog progressDialog;
    private String my_email;
    ImageButton img;

    private FirebaseAuth firebaseAuth;
    private static final int RC_SIGN_IN = 9001;
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        firebaseAuth = FirebaseAuth.getInstance();
        img = findViewById(R.id.imagebutton);
        signinwithphone = findViewById(R.id.signupwithphone);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInwithGoogle();
            }
        });
        if (firebaseAuth.getCurrentUser() != null) {

            finish();

            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);

        }

        editTextEmail = findViewById(R.id.edittextemail);
        editTextPassword = findViewById(R.id.editpassword);
        textViewSignin = findViewById(R.id.textviewsignin);
        editname=findViewById(R.id.namedit);


        buttonSignup = findViewById(R.id.buttonsignup);

        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
       signinwithphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity3.this,phonenumber.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == buttonSignup) {
            if (TextUtils.isEmpty(editname.getText())){
                editname.setError("Name is Mandatory");
            }
            else if (TextUtils.isEmpty(editTextEmail.getText())){
                editTextEmail.setError("Email is Mandatory");

            }
            else if (TextUtils.isEmpty(editTextPassword.getText())){
                editTextPassword.setError("Password is Mandatory");

            }else {


                registerUser();
            }

        }
        if (v == textViewSignin) {

            startActivity(new Intent(this, Login2.class));

        }
    }

    private void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();


        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();


        }

        progressDialog.setMessage("Registering please wait");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            sendVerificationEmail();


                            finish();
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));


                        } else {

                            Toast.makeText(MainActivity3.this, "Password should be at least 6 characters", Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();

                    }
                });
    }

    protected void signInwithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(MainActivity3.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();


//                String userData = gson.toJson(user);
//                      EPreferenceManager.getSingleton().setUserdata(getActivity(),userData);
                firebaseAuthWithGoogle(acct);
            } else {
                Toast.makeText(MainActivity3.this, result.getStatus().toString()
                        , Toast.LENGTH_SHORT).show();
                ;
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) MainActivity3.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity3.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity3.this, "Successfully Registered",
                                    Toast.LENGTH_SHORT).show();
                            sendVerificationEmail();
                            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                            startActivity(intent);

                        }
                    }
                });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity

                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);

                            overridePendingTransition(0, 0);


                        }
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }
}



