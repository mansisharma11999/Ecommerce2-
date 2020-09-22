package com.example.e_commerce.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.datapars;

public class order extends AppCompatActivity {
    TextView ordername;
    ImageView img1;
    Button bt1;
    EditText edt1,edt2,edt3,edt4,edt5,edt6,edt7,edt8,edt9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ordername=findViewById(R.id.textviewname);
        bt1=findViewById(R.id.buttonpayment);
        ordername.setText(RecyclerAdapter.ordername);
        img1=findViewById(R.id.img);
        img1.setImageResource(RecyclerAdapter.orderid);
        edt1=findViewById(R.id.edtorder1);
        edt2=findViewById(R.id.edt2);
        edt5=findViewById(R.id.editAdress2);
        edt6=findViewById(R.id.editcity);
        edt3=findViewById(R.id.edtstate);
        edt7=findViewById(R.id.editcountry);
        edt4=findViewById(R.id.edtpincode);
        edt8=findViewById(R.id.editphone);
        edt9=findViewById(R.id.editemail);
        edt1.setText(datapars.s);
        edt2.setText(datapars.s2);
        edt5.setText(datapars.s5);
        edt6.setText(datapars.s6);
        edt3.setText(datapars.s3);
        edt7.setText(datapars.s7);
        edt4.setText(datapars.s4);
        edt8.setText(datapars.s8);
        edt9.setText(datapars.s9);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt1.getText())) {
                    edt1.setError("Name is empty");
                } else if (TextUtils.isEmpty(edt2.getText())) {
                    edt2.setError("Address is empty");
                } else if (TextUtils.isEmpty(edt6.getText())) {
                    edt6.setError("City is empty");
                } else if (TextUtils.isEmpty(edt3.getText())) {
                    edt3.setError("State is empty");
                } else if (TextUtils.isEmpty(edt7.getText())) {
                    edt7.setError("Country is empty");
                } else if (TextUtils.isEmpty(edt4.getText())) {
                    edt4.setError("Pin-code is empty");
                } else if (TextUtils.isEmpty(edt8.getText())) {
                    edt8.setError("Phone is empty");
                } else if (TextUtils.isEmpty(edt9.getText())) {
                    edt9.setError("Email is empty");
                } else {
                    Intent i = new Intent(order.this, payments.class);
                    startActivity(i);
                }
            }
        });
    }
}