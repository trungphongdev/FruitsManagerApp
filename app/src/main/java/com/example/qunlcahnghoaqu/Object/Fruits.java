package com.example.qunlcahnghoaqu.Object;

public class Fruits {
    private String nameFruit;
    private String idFruit;
    private int count;
    private float price;
    private byte[] img;
    private String category;
    private String producer;
    public Fruits(String idFruit , String nameFruit, int count , float price , byte[] data , String category, String producer) {
        this.idFruit = idFruit;
        this.nameFruit = nameFruit;
        this.count = count;
        this.price = price;
        this.img = data;
        this.category = category;
        this.producer = producer;
    }
    public void setIdFruit(String idFruit) {
        this.idFruit = idFruit;
    }
    public String getIdFruit() {
        return  this.idFruit;
    }

    public String getNameFruit() {
        return nameFruit;
    }

    public void setNameFruit(String nameFruit) {
        this.nameFruit = nameFruit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
