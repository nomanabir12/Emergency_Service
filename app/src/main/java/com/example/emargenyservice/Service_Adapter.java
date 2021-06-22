package com.example.emargenyservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emargenyservice.model.Service_model;

import java.util.List;

public class Service_Adapter extends RecyclerView.Adapter<Service_Adapter.myview> {
    private List<Service_model> data;

    public Service_Adapter(List<Service_model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_image,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        holder.name.setText("Name : "+data.get(position).getName());
        holder.address.setText("Address : "+data.get(position).getAddress());
        holder.phone.setText("Phone Number : "+data.get(position).getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alet=new AlertDialog.Builder(v.getContext());
                alet.setTitle("Details");
                final String name=data.get(position).getName();
                final String adress=data.get(position).getAddress();
                final String phone=data.get(position).getPhone();
                String message="Name: "+name+"\nAddress : "+adress+"\nPhoneNumber : "+phone+"\nStay Safe";
                alet.setMessage(message);
                alet.setPositiveButton("Call Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String number=phone;

                            String s="tel:"+number;
                            Intent intent=new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(s));
                            v.getContext().startActivity(intent);



                    }
                }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alet.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView name,phone,address;
        public myview(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            phone=itemView.findViewById(R.id.phone);
        }
    }
}
