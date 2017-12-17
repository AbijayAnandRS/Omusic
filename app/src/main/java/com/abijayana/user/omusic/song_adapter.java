package com.abijayana.user.omusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 16-12-2017.
 */

public class song_adapter extends ArrayAdapter<songs> {
    Context context;
    int resource;
    ArrayList<songs> objects;


    public song_adapter(Context context, int resource, ArrayList<songs> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoldera vw=new ViewHoldera();
        if(convertView==null){
            LayoutInflater lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=lf.inflate(resource,null);
            vw.iv=(ImageView)convertView.findViewById(R.id.imagevwpic);
            vw.tv1=(TextView)convertView.findViewById(R.id.textView4);
            vw.tv2=(TextView)convertView.findViewById(R.id.textView5);


            convertView.setTag(vw);



        }
        else  vw=(ViewHoldera)convertView.getTag();
        Picasso.with(getContext()).load(objects.get(position).getImgUrl()).into(vw.iv);
        String r=objects.get(position).getNme();
        String t="";int i;
        for(i=0;i<10&&i<r.length();i++){t=t+r.charAt(i);}
        if(i<r.length())t=t+"...";


        vw.tv1.setText(t);
        t="";
        r=objects.get(position).getAuthors();
        for(i=0;i<10;i++){t=t+r.charAt(i);}
        if(i<r.length())t=t+"...";
        vw.tv2.setText(t);


        vw.tv2.setText(objects.get(position).getAuthors());


        return convertView;
    }




    public class ViewHoldera{
        ImageView iv;
        TextView tv1;
        TextView tv2;


    }
}
