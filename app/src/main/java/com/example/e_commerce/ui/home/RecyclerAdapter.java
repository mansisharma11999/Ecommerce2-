package com.example.e_commerce.ui.home;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.MainActivity;
import com.example.e_commerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    List<Products> productsList;
    Context mContext;
    final static List<String> list = new ArrayList<String>();
    final static ArrayList<Integer> list2 = new ArrayList<Integer>();
    static  String ordername="";
    static Integer orderid;


    public RecyclerAdapter() {

    }

    public RecyclerAdapter(Context mContext, List<Products> productsList) {
        this.mContext = mContext;
        this.productsList = productsList;

    }


    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext instanceof Accesories) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            return new MyViewHolder(view);
        }else if(mContext instanceof Clothes){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            return new MyViewHolder(view);
        }else if(mContext instanceof Shoes){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            return new MyViewHolder(view);
        }else if(mContext instanceof Food){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            return new MyViewHolder(view);
        }
        else if(mContext instanceof confirmorder){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout3, parent, false);
            return new MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout2, parent, false);
            return new MyViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.productName.setText(productsList.get(position).getProductName());
        Picasso.with(mContext).load(productsList.get(position).getProductImageId()).centerCrop().resize(400,400).into(holder.productImage);
        holder.productImage.setImageResource(productsList.get(position).getProductImageId());
        holder.addRemoveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!productsList.get(position).isAddedTocart())
                {

                    if(mContext instanceof Accesories)
                    {
                        productsList.get(position).setAddedTocart(true);
                        holder.addRemoveBt.setText("Remove");
                        String Name=productsList.get(position).getProductName();
                        Integer nm=productsList.get(position).getProductImageId();

                        list.add(Name);
                        list2.add(nm);
                        if (mContext instanceof Accesories) {
                            ((AddorRemoveCallbacks) mContext).onAddProduct();
                        }
                    }
                    else if(mContext instanceof Clothes){
                        productsList.get(position).setAddedTocart(true);
                        holder.addRemoveBt.setText("Remove");
                        String Name=productsList.get(position).getProductName();
                        Integer nm=productsList.get(position).getProductImageId();

                        list.add(Name);
                        list2.add(nm);
                        if (mContext instanceof Clothes) {
                            ((AddorRemoveCallbacks) mContext).onAddProduct();
                        }

                    }
                    else if(mContext instanceof Shoes){
                        productsList.get(position).setAddedTocart(true);
                        holder.addRemoveBt.setText("Remove");
                        String Name=productsList.get(position).getProductName();
                        Integer nm=productsList.get(position).getProductImageId();

                        list.add(Name);
                        list2.add(nm);
                        if (mContext instanceof Shoes) {
                            ((AddorRemoveCallbacks) mContext).onAddProduct();
                        }

                    }
                    else if(mContext instanceof Food){
                        productsList.get(position).setAddedTocart(true);
                        holder.addRemoveBt.setText("Remove");
                        String Name=productsList.get(position).getProductName();
                        Integer nm=productsList.get(position).getProductImageId();

                        list.add(Name);
                        list2.add(nm);
                        if (mContext instanceof Food) {
                            ((AddorRemoveCallbacks) mContext).onAddProduct();
                        }

                    }

                }
                else
                {
                    if (mContext instanceof Accesories) {
                    productsList.get(position).setAddedTocart(false);
                    holder.addRemoveBt.setText("Add");
                    } else if(mContext instanceof Clothes){
                    productsList.get(position).setAddedTocart(false);
                    holder.addRemoveBt.setText("Add");
                    }else if(mContext instanceof Shoes){
                        productsList.get(position).setAddedTocart(false);
                        holder.addRemoveBt.setText("Add");
                    }else if(mContext instanceof Food){
                        productsList.get(position).setAddedTocart(false);
                        holder.addRemoveBt.setText("Add");
                    }
                    String Name=productsList.get(position).getProductName();
                    Integer nm=productsList.get(position).getProductImageId();

                    list.remove(Name);
                    list2.remove(nm);

                    ((AddorRemoveCallbacks)mContext).onRemoveProduct();
                }
                if (mContext instanceof cart){
                     ordername=productsList.get(position).getProductName();
                       orderid=productsList.get(position).getProductImageId();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    Intent i=new Intent(mContext,order.class);
                    Map<String,Object> taskmap=new HashMap<>();
                    //Toast.makeText(mContext, "hELLO", Toast.LENGTH_SHORT).show();
                    taskmap.put(ordername,orderid.toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).child("odername").updateChildren(taskmap);
                    //mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).child("oderid").setValue(orderid.toString());
                    mContext.startActivity(i);

                }
                if (mContext instanceof confirmorder){
                    final String Name=productsList.get(position).getProductName();
                    Integer nm=productsList.get(position).getProductImageId();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("odername");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot childdatasnapshot : dataSnapshot.getChildren()) {
                                    String key=childdatasnapshot.getKey();
                                    String value=childdatasnapshot.getValue().toString();

                                    if (key.equals(Name)){

                                        dataSnapshot.getRef().child(key).removeValue();
                                        Intent i=new Intent(mContext,confirmorder.class);
                                        mContext.startActivity(i);
                                        break;

                                    }
                                }
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName;
        Button addRemoveBt;

        public MyViewHolder(View itemView) {
            super(itemView);
            productImage=(ImageView) itemView.findViewById(R.id.productImageView);
            productName=(TextView) itemView.findViewById(R.id.productNameTv);
            addRemoveBt=(Button)itemView.findViewById(R.id.addButton);
        }
    }
    public void filterlist(ArrayList<String> filteredlist){


    }

}


