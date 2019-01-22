package com.example.martijncoomans.mobiledevelopment2newproject;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokemonDetailFragment extends Fragment {

    private TextView name;
    private TextView id;
    private ImageView image;
    private Button button;
    private Pokemon currentPokemon;
    private List<Pokemon> pokemons = new ArrayList<>();
    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pokemon_detail, container, false);

        id = view.findViewById(R.id.pokemon_detail_id);
        name = view.findViewById(R.id.pokemon_detail_name);
        image = view.findViewById(R.id.pokemon_detail_image);

        if (getArguments() != null && getArguments().containsKey("pokemon")) {
            setPokemon((Pokemon) getArguments().getSerializable("pokemon"));
        }

        if(currentPokemon.isCatched) {
            button = view.findViewById(R.id.catch_button);
            button.setText("Release");
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    currentPokemon.image = image.getDrawingCache();
                    releasePokemon(currentPokemon);
                }
            });
        } else {
            button = view.findViewById(R.id.catch_button);
            button.setText("Catch");
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    currentPokemon.image = image.getDrawingCache();
                    catchPokemon(currentPokemon);
                }
            });
        }


        return view;
    }

    public void catchPokemon(Pokemon pokemon){
        Random rand = new Random();
        Toast toast = null;
        if(rand.nextInt(4) == 0) {
            toast = Toast.makeText(mContext, "You caught the pokemon!", Toast.LENGTH_SHORT);
            pokemons = StorageController.getPokemons(mContext);

            pokemon.isCatched = true;

            pokemons.add(pokemon);

            StorageController.setPokemons(mContext, pokemons);
        } else {
            toast = Toast.makeText(mContext, "Pokemon escaped!", Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void releasePokemon(Pokemon pokemon) {
        pokemons = StorageController.getPokemons(mContext);

        Pokemon removeP = null;

        for(Pokemon p : pokemons){
            if(p.id == pokemon.id) {
                removeP = p;
            }
        }

        pokemons.remove(removeP);

        StorageController.setPokemons(mContext, pokemons);
    }

    public void setPokemon(Pokemon pokemon) {
        currentPokemon = pokemon;
        id.setText("#" + pokemon.id);
        name.setText(pokemon.name);
        new DownloadImageTask(image).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.id + ".png");
    }
}

