package com.abijayana.user.omusic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class imgadpter extends RecyclerView.Adapter<imgadpter.MyViewHolder> {

    List<songs> list;
    Context context;

    int resource;
    View itemView;
    SharedPreferences sp1;
    SharedPreferences sp2;
    SharedPreferences sp3;
    SharedPreferences sp4;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;ImageView img;

        public MyViewHolder(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.imagevwpic);
            tv1=(TextView)view.findViewById(R.id.textView4);
            tv2=(TextView)view.findViewById(R.id.textView5);

        }
    }
    public imgadpter(List<songs> list,int resource,Context context) {
        this.list = list;
        this.resource=resource;
        this.context=context;
        sp1=context.getSharedPreferences("SSD",Context.MODE_PRIVATE);
        sp2=context.getSharedPreferences("DDS", Context.MODE_PRIVATE);
        sp3=context.getSharedPreferences("ASD",Context.MODE_PRIVATE);
        sp4=context.getSharedPreferences("ASB",Context.MODE_PRIVATE);




    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);


        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.with(holder.itemView.getContext()).load(list.get(position).getImgUrl()).into(holder.img);
        holder.tv1.setText(list.get(position).getNme());
        holder.tv2.setText(list.get(position).getAuthors());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(sp1,list.get(position).getNme());
                edit(sp2,list.get(position).getAuthors());
                edit(sp3,list.get(position).getImgUrl());
                edit(sp4,list.get(position).getUrl());
                Intent i=new Intent(context,Music.class);
                context.startActivity(i);



            }
        });



    }
    @Override
    public int getItemCount() {
        return list.size();
    }
public void edit(SharedPreferences spa,String value){
    SharedPreferences.Editor ed=spa.edit();
    ed.putString("HAI",value);
    ed.commit();



}




}
