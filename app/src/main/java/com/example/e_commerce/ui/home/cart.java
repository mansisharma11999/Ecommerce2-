package com.example.e_commerce.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class cart extends AppCompatActivity
{
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    private static int cart_count=0;
    FloatingActionButton fab;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setTitle("Cart");
        String[] productNames= RecyclerAdapter.list.toArray(new String[0]);
        ArrayList<Integer> productImages=RecyclerAdapter.list2;

        Products product;
        List<Products> listdata=new ArrayList<>();
        if (mContext instanceof Accesories) {
            for (int i = 0; i < 10; i++) {
                product = new Products(productNames[i], productImages.get(i));
                listdata.add(product);
            }
        }
        else if(mContext instanceof Clothes)
        {
            for (int i = 0; i < 10; i++) {
                product = new Products(productNames[i], productImages.get(i));
                listdata.add(product);
            }
;
        }
        else if(mContext instanceof Shoes)
        {
            for (int i = 0; i < 10; i++) {
                product = new Products(productNames[i], productImages.get(i));
                listdata.add(product);
            }

        }
        else if(mContext instanceof Food)
        {
            for (int i = 0; i < 10; i++) {
                product = new Products(productNames[i], productImages.get(i));
                listdata.add(product);
            }
        }
        else
            {
            for (int i = 0; i <productImages.size(); i++) {
                product = new Products(productNames[i], productImages.get(i));
                listdata.add(product);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(this,listdata);
        mRecyclerView.setAdapter(mAdapter);


    }
}