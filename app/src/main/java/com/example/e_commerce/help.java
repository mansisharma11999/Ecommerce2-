package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_commerce.ui.home.confirmorder;

public class help extends AppCompatActivity {

    Button order,profilee,customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        order=findViewById(R.id.orders);
        profilee=findViewById(R.id.refund);
        customer=findViewById(R.id.customer);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(help.this, confirmorder.class);
                startActivity(i);
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(help.this,customer.class);
                startActivity(i);
            }
        });

    }
}