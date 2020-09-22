package com.example.e_commerce.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class confirmorder extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    private static int cart_count = 0;
    FloatingActionButton fab;
    Context mContext;
    String[] productNames = null;
    static List<String> list = new ArrayList<String>();
    static ArrayList<Integer> productImages = new ArrayList<Integer>();
    int count = 0;
    Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        //productImages.clear();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("odername");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Map<String,Object> td= (Map<String, Object>) dataSnapshot.getValue();
                // List<Object> values= (List<Object>) td.values();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot childdatasnapshot : dataSnapshot.getChildren()) {

                        count = count + 1;

                        list.add(childdatasnapshot.getKey());
                        String a = String.valueOf(count);


                        productImages.add(Integer.parseInt(childdatasnapshot.getValue().toString()));


                    }
                    //Toast.makeText(confirmorder.this, list.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(confirmorder.this, productImages.toString(), Toast.LENGTH_SHORT).show();
                    //productNames= list.toArray(new String[0]);
                    Products product;
                    List<Products> listdata = new ArrayList<>();

                    String length = String.valueOf(list.size());
                   // Toast.makeText(confirmorder.this, length, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < list.size(); i++) {
                        product = new Products(list.get(i), productImages.get(i));

                        listdata.add(product);
                    }
                    //Toast.makeText(confirmorder.this, listdata.toString(), Toast.LENGTH_SHORT).show();

                    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                    mRecyclerView.setHasFixedSize(true);

                    GridLayoutManager mLayoutManager = new GridLayoutManager(confirmorder.this,2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new RecyclerAdapter(confirmorder.this,listdata);
                    mRecyclerView.setAdapter(mAdapter);



                    //Toast.makeText(confirmorder.this, productImages.toString(), Toast.LENGTH_SHORT).show();
                } else {

                    //Toast.makeText(confirmorder.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(confirmorder.this, list.toString(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        list.clear();




    }
}