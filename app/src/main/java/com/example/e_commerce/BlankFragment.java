package com.example.e_commerce;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String IMAGE_DIRECTORY_NAME ="hello camera" ;
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9;
    Button bt,default1;
    DatabaseReference mdatabase;
    private Uri fileUri;
    TextView s1,s2,s3,s4,s5,s6,s7,s8;
    Button choose,upload;
    ImageView image;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2)
    {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        ed1=view.findViewById(R.id.edit1);
        ed2=view.findViewById(R.id.editAdress1);
        ed5=view.findViewById(R.id.editAdress2);
        ed6=view.findViewById(R.id.editcity);
        ed3=view.findViewById(R.id.editstate);
        ed7=view.findViewById(R.id.editcountry);
        ed4=view.findViewById(R.id.editpincode);
        ed8=view.findViewById(R.id.editphone);
        ed9=view.findViewById(R.id.editemail);
        bt=view.findViewById(R.id.buttonsave);
        default1=view.findViewById(R.id.defaultt);
        s1=view.findViewById(R.id.star1);
        s2=view.findViewById(R.id.star2);
        s3=view.findViewById(R.id.star3);
        s4=view.findViewById(R.id.star4);
        s5=view.findViewById(R.id.star5);
        s6=view.findViewById(R.id.star6);
        s7=view.findViewById(R.id.star7);
        s8=view.findViewById(R.id.star8);
        choose=view.findViewById(R.id.choosebutton);
        upload=view.findViewById(R.id.uploadbutton);
        image=view.findViewById(R.id.imageprofile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ed1.getText())){
                    ed1.setError("Name is Mandatory");
                }
                else if(TextUtils.isEmpty(ed2.getText())){
                    ed2.setError("Address is Mandatory");
                }
                else if(TextUtils.isEmpty(ed6.getText())){
                    ed6.setError("City is Mandatory");
                }
                else if(TextUtils.isEmpty(ed7.getText())){
                    ed7.setError("Country is Mandatory");
                }
                else if(TextUtils.isEmpty(ed4.getText())){
                    ed4.setError("Pin-code is Mandatory");
                }
                else if(TextUtils.isEmpty(ed8.getText())){
                    ed8.setError("Phone is Mandatory");
                }
                else if(TextUtils.isEmpty(ed9.getText())){
                    ed9.setError("E-mail is Mandatory");
                }
                else if(TextUtils.isEmpty(ed3.getText())){
                    ed3.setError("State is Mandatory");
                }
                else {
                    datapars.s = ed1.getText().toString();
                    datapars.s2 = ed2.getText().toString();
                    datapars.s5 = ed5.getText().toString();
                    datapars.s6 = ed6.getText().toString();
                    datapars.s3 = ed3.getText().toString();
                    datapars.s7 = ed7.getText().toString();
                    datapars.s4 = ed4.getText().toString();
                    datapars.s8 = ed8.getText().toString();
                    datapars.s9 = ed9.getText().toString();
                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment

        default1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Name");
                DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Email");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String finalname = dataSnapshot.getValue().toString();
                        //Toast.makeText(getContext(),fianlname, Toast.LENGTH_SHORT).show();
                        ed1.setText(finalname);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String finalemail=dataSnapshot.getValue().toString();
                        ed9.setText(finalemail);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
   return view;
    }
    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(
                                    intent,
                                    "Select Image from here..."),
                            PICK_IMAGE_REQUEST);
                }
    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(getContext().getContentResolver(), filePath);
              bitmap=rotate(bitmap,90);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
        if (requestCode==1){
            //Toast.makeText(getContext(), "ftgghgyfghfgf", Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }
    private void uploadImage ()
    {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String user = FirebaseAuth.getInstance().getUid();
            // Defining the child of storageReference
            StorageReference ref = storageReference.child(user);
            // adding listeners on upload
            // or failure of image

            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getContext(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }
    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
        private static File getOutputMediaFile(int type) {
            // External sdcard location
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    IMAGE_DIRECTORY_NAME);
            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory");
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            File mediaFile;
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            } else {
                return null;
            }
            return mediaFile;
        }

        public Uri getOutputMediaFileUri(int type) {
            return Uri.fromFile(getOutputMediaFile(type));
        }
}