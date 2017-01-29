package com.example.valen.weka;

import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by valen on 10. 01. 2017.
 */

public class Global extends Application{
    @SerializedName("objave")
    @Expose
    public ArrayList<Objava> objave;
    public Data data;
    public NaiveBayes nb;
    public Instances instances;

    public Global(){
        this.objave = new ArrayList<>();
        this.data = new Data();
    }

    public void Save(Global global){

        try
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/data.app");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            PrintWriter pw = new PrintWriter(file);
            pw.println(gson.toJson(global));
            pw.close();
            Toast.makeText(this, "save complete", Toast.LENGTH_LONG).show();
        }
        catch (FileNotFoundException e)
        {
            //Toast.makeText(this, "save failed", Toast.LENGTH_LONG);
            e.printStackTrace();
        }


    }

    public Global Load(){
        Global glob = new Global();
        try {

            File file2 = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/data.app");

            FileInputStream fstream = new FileInputStream(file2);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuffer sb = new StringBuffer();
            String strLine;

            while ((strLine = br.readLine()) != null)
                sb.append(strLine).append('\n');

            br.close();
            Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
            //Gson gson2 = new Gson();
            glob = gson2.fromJson(sb.toString(), Global.class);

            if (glob == null)
                System.out.println("Error: fromJson Format error");
            else {
                System.out.println("Load complete");
//                System.out.println(glob.osebe + "\n\n" + glob.objave + "\n\n" + glob.lokacije);
            }

        }
        catch (IOException e) {
            System.out.println("Error load Team");
        }
        return glob;
    }

}
