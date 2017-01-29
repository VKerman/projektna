package com.example.valen.weka;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Enumeration;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.TestInstances;

public class SecondActivity extends AppCompatActivity {

    Objava objava;
    Global glob;
    int tmp;
    TextView rating;
    Button btn;
    ImageButton ibtn;
    Bitmap image;
    Util util = new Util();
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        glob = (Global)getApplication();

        imageView = (ImageView) findViewById(R.id.imageView);
        /*btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(btnListener);*/

        ibtn = (ImageButton)findViewById(R.id.imageButton);
        ibtn.setOnClickListener(btnListener);

        Intent intent = getIntent();
        Gson gson = new Gson();
        tmp = intent.getIntExtra("index",-1);
        objava = glob.data.objave.get(tmp);
        //objava = gson.fromJson(tmp, Objava.class);
        //Toast.makeText(this, tmp, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, objava.getName(), Toast.LENGTH_SHORT).show();

        TextView name = (TextView)findViewById(R.id.tv2_name);
        name.setText(objava.getName());

        TextView material = (TextView)findViewById(R.id.tv2_mat);
        material.setText(objava.getMaterial());

        TextView drive = (TextView)findViewById(R.id.tv2_drive);
        String tmp_string = objava.getDrive();
        if(tmp_string.equals("Spr"))
            drive.setText("Spring");
        if(tmp_string.equals("AEG"))
            drive.setText("Electric");
        if(tmp_string.equals("Gas"))
            drive.setText("Green gas");

        TextView power = (TextView)findViewById(R.id.tv2_pow);
        power.setText(objava.getPower()+"");

        TextView price = (TextView)findViewById(R.id.tv2_price);
        price.setText(objava.getPrice()+"€");

        TextView upgradeability = (TextView)findViewById(R.id.tv2_upgr);
        upgradeability.setText(objava.getUpgradeability()+"");

        rating = (TextView)findViewById(R.id.tv2_rate);
        rating.setText(objava.getRating()+"");

        image = util.LoadBitmap(objava.getImage());

        imageView.setImageBitmap(image);

        if(objava.getRating() > 0){
            ibtn.setVisibility(View.INVISIBLE);
        }
        else
            ibtn.setVisibility(View.VISIBLE);
    }


    private View.OnClickListener btnListener = new View.OnClickListener()
    {

        public void onClick(View v)
        {

            Instance in2 = glob.instances.firstInstance();
            Instance test = new DenseInstance(in2);


            Instance instance = new DenseInstance(6);
            instance.setValue(in2.attribute(0), objava.getMaterial());
            instance.setValue(in2.attribute(1), objava.getDrive());
            instance.setValue(in2.attribute(2), objava.getPower());
            instance.setValue(in2.attribute(3), objava.getPrice());
            instance.setValue(in2.attribute(4), objava.getUpgradeability());
            instance.setValue(in2.attribute(5), objava.getRating());

            instance.setDataset(glob.instances);

            //Toast.makeText(getApplicationContext(), test.toString(), Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), instance.toString(), Toast.LENGTH_LONG).show();

            try {
                double rat = glob.nb.classifyInstance(instance);
                objava.setRating((int)rat);
                glob.data.objave.set(tmp, objava);
                rating.setText(objava.getRating()+"");
                ibtn.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "Rating = " + rat, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    };
}
