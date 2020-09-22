package com.example.e_commerce.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.e_commerce.Login2;
import com.example.e_commerce.PhoneVerification;
import com.example.e_commerce.R;
import com.example.e_commerce.phonenumber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        listView=root.findViewById(R.id.list);
       // String Name=setArguments().getString("Name");
        //String Email=setArguments().getString("Email");

        phonenumber activitty=new phonenumber();
         //String Name=phonenumber.getInstance().getname();
         //String Email=phonenumber.getInstance().getemail();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Toast.makeText(getContext(), mDatabase.toString(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(getContext(), Name, Toast.LENGTH_SHORT).show();

        //mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Name").setValue(Name);
        //mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Email").setValue(Email);
        final String values[]={"COLLECTIONS","CART","ORDERS"};
        ArrayAdapter adapter=new ArrayAdapter(getContext(), simple_list_item_1, Arrays.asList(values));
        listView.setAdapter(adapter);
        List<String> fruits_list = new ArrayList<String>(Arrays.asList(values));


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1,fruits_list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.parseColor("#fe00fb"));

                // Generate ListView Item using TextView
                return view;
            }
        };


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position==0) {
                Intent i = new Intent(getContext(), MainActivity4.class);
                startActivity(i);
            }
                else if(position==1){
                    Intent i=new Intent(getContext(),cart.class);
                    startActivity(i);
                }
                else if(position==2){
                    Intent i=new Intent(getContext(),confirmorder.class);
                    startActivity(i);
                }
            }
        });
        return root;

    }

}