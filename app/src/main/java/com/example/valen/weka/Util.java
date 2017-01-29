package com.example.valen.weka;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valen on 10. 01. 2017.
 */

public class Util {
    public static void Save(Data data){
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/data.app");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            PrintWriter pw = new PrintWriter(file);
            pw.println(gson.toJson(data)); //a is my object
            pw.close();
        } catch (IOException e) {
            System.out.println("Error save team!");
        }
    }

    public Data Load() {
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/data.app"));
            Data data = gson.fromJson(br, Data.class);
            br.close();

            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void SaveBitmap(Bitmap bitmapImage, String file_name) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS).getPath()+"/Images", file_name);

            fileOutputStream = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap LoadBitmap(String file_name) {
        FileInputStream inputStream = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS).getPath()+"/Images", file_name);

            inputStream = new FileInputStream(file);
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
