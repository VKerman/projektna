package com.example.valen.weka;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.*;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.TestInstances;
import weka.core.converters.ConverterUtils.DataSource;

import static com.example.valen.weka.R.id.activity_second;


public class MainActivity extends AppCompatActivity{
    ArrayList<Objava> objave = new ArrayList<>();
    Global glob = new Global();
    Util util = new Util();
    NaiveBayes nb = new NaiveBayes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // /data/user/0/com.example.valen.weka/files


        glob = (Global)getApplication();



        isStoragePermissionGranted();
        test();
        glob.nb = nb;

        //glob.data = new Data();

        String rootDataDir = getFilesDir().toString();



        /*
        Objava ob1 = new Objava("M4", "Poly", "AEG", 380, 150, 8);
        Objava ob2 = new Objava("1911", "Steel", "Gas", 350, 150, 8);
        //glob.objave.add(ob1);
        //glob.objave.add(ob2);
        glob.data.objave = new ArrayList<>();
        glob.data.objave.add(ob1);
        glob.data.objave.add(ob2);
*/
        //util.Save(glob.data);
        glob.data = util.Load();
        //glob.objave = glob.data.objave;

        populateList();

        FloatingActionButton FAB = (FloatingActionButton)findViewById(R.id.floatingActionButton2);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        util.Save(glob.data);
    }

    public void populateList(){
        ListAdapter listAdapter = new CustomAdapter(this, glob.data.objave);
        ListView myList = (ListView) findViewById(R.id.myList);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Objava objava = glob.data.objave.get(position);
                //Gson gson = new Gson();
                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                intent.putExtra("index", position);
                //intent.putExtra("test", "test2");
                startActivity(intent);
            }
        });
        myList.setAdapter(listAdapter);
    }

    public void WriteToFile(String data){
        final File path =
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS
                );

        if(!path.exists()){
            path.mkdirs();
        }

        final File file = new File(path, "test.txt");

        try{
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter outWriter = new OutputStreamWriter(fOut);
            outWriter.append(data);
            outWriter.close();
            fOut.flush();
            fOut.close();
            Toast.makeText(this, "save succsesfull", Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            Log.e("Exception", "File write failed: " + e.toString());
            Toast.makeText(this, "save failed", Toast.LENGTH_LONG).show();
        }
    }

    public void test2(){

        try {
            Instances data = new Instances(new BufferedReader(new FileReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/airsoft.arff"))));

            data.setClassIndex(data.numAttributes()-1);

            NaiveBayes nb = new NaiveBayes();

            nb.buildClassifier(data);
            Toast.makeText(this, "Build OK", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
            //e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            //e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        populateList();
    }

    public void Add_click(View view){
        Intent intent = new Intent(getBaseContext(), AddActivity.class);
        startActivity(intent);
    }

    public void test(){
        Instances data = null;
        try
        {
            data = new Instances(new BufferedReader(
                    new FileReader(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOCUMENTS + "/airsoft.arff"))));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        if(data!=null) {
            data.setClassIndex(data.numAttributes() - 1);
            glob.instances = data;
            //NaiveBayes nb = new NaiveBayes();
            try {
                nb.buildClassifier(data);
                Evaluation eval = new Evaluation(data);
                eval.evaluateModel(nb, data);

                //Toast.makeText(this, "correct: " + eval.correct() + "\nfalse: " + eval.incorrect(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(this, "data is null", Toast.LENGTH_LONG).show();

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show();
            //Log.v(TAG,"Permission is granted");
            return true;
        }
    }


}
