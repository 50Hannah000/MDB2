package com.example.martijncoomans.mobiledevelopment2newproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StorageController {
    private SharedPreferences sharedPreferences;
    private List<Pokemon> pokemon;

    public StorageController() {

    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("pokemon", Context.MODE_PRIVATE);
    }

    public static List<Pokemon> getPokemons(Context context) {

        String data = getPrefs(context).getString("catchedPokemon", null);
        return data != null ? stringToList(data) : stringToList("");
    }

    public static void setPokemons(Context context, List<Pokemon> list) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("catchedPokemon", listToString(list));
        editor.commit();
    }

    public static String listToString(List<Pokemon> list) {
        String string = "";

        for(int i = 0; i < list.size(); i++) {

            string += list.get(i).id + "/column/" + list.get(i).name + "/column/" + "/row/";
        }
        Log.i("string", string);
        return string;
    }

    public static List<Pokemon> stringToList(String string) {
        List<Pokemon> list = new ArrayList<Pokemon>();
        if(string != "") {
            String[] pokemons = string.split("/row/");
            for(int i = 0; i < pokemons.length; i++) {
                String[] attributes = pokemons[i].split("/column/");

                list.add(new Pokemon(attributes[1], Integer.parseInt(attributes[0]), null));
            }
        }
        return list;
    }

    private static String convertToBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;
    }

    private static Bitmap decodeFromBase64ToBitmap(String encodedImage) {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }
}
