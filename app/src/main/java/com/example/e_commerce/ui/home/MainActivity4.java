package com.example.e_commerce.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_commerce.R;

public class MainActivity4 extends AppCompatActivity {
    ListView listView;
    int[] image={
            R.drawable.accessories,
            R.drawable.clothes,
            R.drawable.shoe,
            R.drawable.food,
    };
    String[] name={
            "ACCESSORIES",
            "CLOTHES",
            "SHOES",
            "FOOD",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        listView=findViewById(R.id.list_item);
        customAdaptor customAdaptor=new customAdaptor();
        listView.setAdapter(customAdaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent i = new Intent(MainActivity4.this, Accesories.class);
                    startActivity(i);
                }
                else if(position==1){
                    Intent i = new Intent(MainActivity4.this, Clothes.class);
                    startActivity(i);
                }
                else if(position==2){
                    Intent i = new Intent(MainActivity4.this, Shoes.class);
                    startActivity(i);
                }
                else if(position==3){
                    Intent i = new Intent(MainActivity4.this, Food.class);
                    startActivity(i);
                }

            }
        });
    };
    class customAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.activity_custom_layout,null);
            ImageView imageView=view.findViewById(R.id.imageView);
            TextView textView=view.findViewById(R.id.textview);
            imageView.setImageResource(image[position]);
            textView.setText(name[position]);
            return view;
        }
    }
}