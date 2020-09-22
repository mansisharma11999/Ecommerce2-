package com.example.e_commerce.ui.home;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.e_commerce.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Food extends AppCompatActivity implements AddorRemoveCallbacks {

    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    String[] productNames={"Food","Fruits"};
    int[] productImages={R.drawable.food,R.drawable.fruits};
    private static int cart_count=0;
    FloatingActionButton fab;
    EditText edsearch;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportActionBar().setTitle("Food");

        /*fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        Products product;
        List<Products> listdata=new ArrayList<>();
        for(int i=0;i<2;i++)
        {
            product = new Products(productNames[i],productImages[i]);
            listdata.add(product);
        }
        edsearch=findViewById(R.id.edittextsearch);
        edsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(this,listdata);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(Converter.convertLayoutToImage(Food.this,cart_count,R.drawable.google));
        //MenuItem menuItem2 = menu.findItem(R.id.notification_action);
        //menuItem2.setIcon(Converter.convertLayoutToImage(Food.this,2,R.drawable.ic_launcher_foreground));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.cart_action:
                Intent i =new Intent(Food.this,cart.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddProduct() {
        cart_count++;
        invalidateOptionsMenu();
        Snackbar.make((CoordinatorLayout)findViewById(R.id.parentlayout), "Added to cart !!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();
        Snackbar.make((CoordinatorLayout)findViewById(R.id.parentlayout), "Removed from cart !!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
    public void filter(String text){


        List<Products> filteredlist=new ArrayList<>();;
        for(int i=0;i<2;i++){
            Products item = new Products(productNames[i],productImages[i]);

            if (item.getProductName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);

                mAdapter = new RecyclerAdapter(this,filteredlist);
                mRecyclerView.setAdapter(mAdapter);
                //notifyAll();
            }
        }



    }



}