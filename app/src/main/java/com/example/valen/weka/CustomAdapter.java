package com.example.valen.weka;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by valen on 10. 01. 2017.
 */

class CustomAdapter extends ArrayAdapter<Objava>{
    public CustomAdapter(Context context, ArrayList<Objava> objave) {
        super(context, R.layout.list_layout, objave);
    }

    Util util = new Util();

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_layout, parent, false);

        Objava objava = getItem(position);
        TextView tv_Price = (TextView)customView.findViewById(R.id.tv_price1);
        TextView tv_rating = (TextView)customView.findViewById(R.id.tv_rating);
        TextView tv_name = (TextView)customView.findViewById(R.id.textView2);
        ImageView img = (ImageView)customView.findViewById(R.id.type_icon);

        tv_Price.setText("Price: " + String.valueOf(objava.getPrice()));
        tv_rating.setText("Rating: " + String.valueOf(objava.getRating()));
        tv_name.setText(objava.getName());

        if(objava.getImage().equals("")) {
            if (objava.getDrive().equals("Spr"))
                img.setImageResource(R.drawable.spring_icon);
            if (objava.getDrive().equals("AEG"))
                img.setImageResource(R.drawable.aeg_icon);
            if (objava.getDrive().equals("Gas"))
                img.setImageResource(R.drawable.gas_icon);
        }
        else{
            Bitmap image = util.LoadBitmap(objava.getImage());
            img.setImageBitmap(image);
        }
        return customView;
    }

}
