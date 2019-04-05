package com.example.martijncoomans.mobiledevelopment2newproject;

import android.graphics.Bitmap;
import java.io.Serializable;

public class Pokemon implements Serializable {
    public String name;
    public int id;
    public Bitmap image;
    public boolean isCatched;

    public Pokemon(Pokemon pokemon) {
        this.name = pokemon.name;
        this.id = pokemon.id;
        this.image = pokemon.image;
        this.isCatched = pokemon.isCatched;
    }

    public Pokemon(String name, int id, Bitmap image, boolean isCatched) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.isCatched = isCatched;
    }
}
