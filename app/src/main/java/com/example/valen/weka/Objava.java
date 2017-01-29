package com.example.valen.weka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by valen on 10. 01. 2017.
 */

public class Objava {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("drive")
    @Expose
    private String drive;
    @SerializedName("power")
    @Expose
    private int power;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("upgradeability")
    @Expose
    private int upgradeability;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("image")
    @Expose
    private String image;

    public Objava(String name, String material, String drive, int power, int price, int upgradeability) {
        this.name = name;
        this.material = material;
        this.drive = drive;
        this.power = power;
        this.price = price;
        this.upgradeability = upgradeability;
        this.image="";
    }

    public Objava(String name, String material, String drive, int power, int price, int upgradeability, String image) {
        this.name = name;
        this.material = material;
        this.drive = drive;
        this.power = power;
        this.price = price;
        this.upgradeability = upgradeability;
        this.image = image;
    }

    public Objava(String name, String material, String drive, int power, int price) {
        this.name = name;
        this.material = material;
        this.drive = drive;
        this.power = power;
        this.price = price;
        this.upgradeability = 0;
        this.rating = 0;
        this.image = "";
    }

    public Objava() {
        this.name = "";
        this.material = "";
        this.drive = "";
        this.power = 0;
        this.price = 0;
        this.upgradeability = 0;
        this.rating = 0;
        this.image = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setUpgradeability(int upgradeability){ this.upgradeability = upgradeability; }

    public int getUpgradeability(){ return upgradeability; }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
