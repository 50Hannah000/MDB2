package com.example.martijncoomans.mobiledevelopment2newproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class StorageController {

    public StorageController() {  }

    //function to get pokemon from the SharedPreferences
    public static List<Pokemon> getPokemons(Context context) {
        String data = getPrefs(context).getString("catchedPokemon", null);
        return data != null ? stringToList(data) : stringToList("");
    }

    //function to set pokemon in the SharedPreferences
    public static void setPokemons(Context context, List<Pokemon> list) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("catchedPokemon", listToString(list));
        editor.commit();
    }

    //function to get the catched pokemon part of the SharedPreferences
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("catchedPokemon", Context.MODE_PRIVATE);
    }

    //function to create a string from the list of pokemons
    private static String listToString(List<Pokemon> list) {
        String string = "";

        for(int i = 0; i < list.size(); i++) {

            string += list.get(i).id + "/column/" + list.get(i).name + "/column/" + list.get(i).isCatched + "/column/" + "/row/";
        }
        Log.i("string", string);
        return string;
    }

    //function to create a list of pokemon from string
    private static List<Pokemon> stringToList(String string) {
        List<Pokemon> list = new ArrayList<Pokemon>();
        if(string != "") {
            String[] pokemons = string.split("/row/");
            for(int i = 0; i < pokemons.length; i++) {
                String[] attributes = pokemons[i].split("/column/");
                list.add(new Pokemon(attributes[1], Integer.parseInt(attributes[0]), null, Boolean.parseBoolean(attributes[2])));
            }
        }
        return list;
    }
}
