package com.example.martijncoomans.mobiledevelopment2newproject;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokemonDetailFragment extends Fragment {

    private TextView name;
    private TextView id;
    private ImageView image;
    private Button button;
    private Pokemon currentPokemon;
    private CustomAdapter mAdapter;
    private List<Pokemon> pokemons = new ArrayList<>();
    private Context mContext;

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

        button = view.findViewById(R.id.catch_button);

        button.setText(currentPokemon.isCatched ? "Release" : "Catch");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPokemon.image = ((BitmapDrawable)image.getDrawable()).getBitmap();
                if(currentPokemon.isCatched){
                    releasePokemon(currentPokemon);
                } else {
                    catchPokemon(currentPokemon);
                }
            }
        });


        return view;
    }

    public void catchPokemon(Pokemon pokemon){
        Random rand = new Random();
        Toast toast = null;
        if(pokemon.isCatched != true) {
            if(rand.nextInt(4) == 0) {
                toast = Toast.makeText(mContext, "You caught the pokemon!", Toast.LENGTH_SHORT);
                pokemons = StorageController.getPokemons(mContext);

                //creates copy of the pokemon
                Pokemon tmpPokemon = new Pokemon(pokemon);

                //sets isCatched true on the copy
                tmpPokemon.isCatched = true;

                boolean test = pokemon.isCatched;

                pokemons.add(tmpPokemon);

                StorageController.setPokemons(mContext, pokemons);
            } else {
                toast = Toast.makeText(mContext, "Pokemon escaped!", Toast.LENGTH_SHORT);
            }
        }
        else {
            toast = Toast.makeText(mContext, "U already caught this pokemon!", Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void releasePokemon(Pokemon pokemon) {
        pokemons = StorageController.getPokemons(mContext);
        Toast toast = Toast.makeText(mContext, "Sorry, today is not a good day to release a pokemon!", Toast.LENGTH_SHORT);
        Pokemon removeP = null;

        for(Pokemon p : pokemons){
            if(p.id == pokemon.id) {
                removeP = p;
                toast = Toast.makeText(mContext, "You released the pokemon!", Toast.LENGTH_SHORT);
            }
        }

        pokemons.remove(removeP);
        toast.show();

        StorageController.setPokemons(mContext, pokemons);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame
                        , new CatchedListFragment())
                .remove(this)
                .commit();
    }

    public void setPokemon(Pokemon pokemon) {
        currentPokemon = pokemon;
        id.setText("#" + pokemon.id);
        name.setText(pokemon.name);
        new DownloadImageTask(image).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.id + ".png");
    }
}

