package com.example.valen.weka;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddActivity extends AppCompatActivity {

    Global glob = new Global();
    Util util = new Util();
    ImageView image;
    String file_name;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        image = (ImageView) findViewById(R.id.imageView3);

        setSpinners();

        glob = (Global)getApplication();


        FloatingActionButton FAB = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        FAB.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.editText);
                String name = text.getText().toString();

                String material = "Poly";
                Spinner spinner1 = (Spinner)findViewById(R.id.mat_spinner);
                switch (spinner1.getSelectedItemPosition()) {
                    case 0:
                        material = "Poly";
                        break;
                    case 1:
                        material = "Alu";
                        break;
                    case 2:
                        material = "Steel";
                        break;
                }

                String drive ="";
                Spinner spinner2 = (Spinner)findViewById(R.id.drive_spinner);
                switch (spinner2.getSelectedItemPosition()) {
                    case 0:
                        drive = "AEG";
                        break;
                    case 1:
                        drive = "Spr";
                        break;
                    case 2:
                        drive = "Gas";
                        break;
                }


                text = (EditText)findViewById(R.id.editText4);
                String power = text.getText().toString();
                int powerInt = Integer.parseInt(power); //try catch

                text = (EditText)findViewById(R.id.editText5);
                String price = text.getText().toString();
                int priceInt = Integer.parseInt(price); //try catch

                text = (EditText)findViewById(R.id.editText6);
                String upgradeability = text.getText().toString();
                int upgradeabilityInt = Integer.parseInt(upgradeability)-1; //try catch



                Objava novaobjava = new Objava(name,material,drive,powerInt,priceInt,upgradeabilityInt, file_name);
                glob.data.objave.add(novaobjava);
                util.Save(glob.data);
                util.SaveBitmap(bitmap, file_name);

                finish();
            }
        });

    }

    public void onImageClick(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 20){
                Uri uri = data.getData();

                InputStream imageStream;

                try {
                    imageStream = getContentResolver().openInputStream(uri);

                    bitmap = BitmapFactory.decodeStream(imageStream);

                    image.setImageBitmap(bitmap);

                    file_name=uri.getLastPathSegment();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setSpinners(){
        Spinner spinner = (Spinner)findViewById(R.id.mat_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.material_array, R.layout.my_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner)findViewById(R.id.drive_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.drive_array, R.layout.my_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }
}
