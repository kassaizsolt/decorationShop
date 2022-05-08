package com.example.decorationshop;

public class Product {
    private String id;
    private String name;
    private int amount;
    private float stars;
    private int price;

    public Product(){

    }

    public Product(String id, String name, int amount, float stars, int price) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.stars = stars;
        this.price = price;
    }

    public Product(String name, int amount, float stars, int price) {
        this.name = name;
        this.amount = amount;
        this.stars = stars;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}