package com.example.martijncoomans.mbd2android;

import java.io.Serializable;

public class Product implements Serializable {
    public String name;
    public int id;

    public Product(String name, int id) {
        this.name = name;
        this.id = id;
    }
}