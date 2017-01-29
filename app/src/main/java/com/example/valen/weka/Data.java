package com.example.valen.weka;

import java.util.ArrayList;

/**
 * Created by valen on 11. 01. 2017.
 */

public class Data {
    public ArrayList<Objava> objave;

    public Data(ArrayList<Objava> objave) {
        this.objave = objave;
    }

    public Data() {
this.objave = new ArrayList<>();
    }
}
