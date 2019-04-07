package com.example.martijncoomans.mobiledevelopment2newproject.Views;

import android.app.Fragment;
import android.app.FragmentManager;
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

import com.example.martijncoomans.mobiledevelopment2newproject.CustomAdapter;
import com.example.martijncoomans.mobiledevelopment2newproject.DownloadImageTask;
import com.example.martijncoomans.mobiledevelopment2newproject.Pokemon;
import com.example.martijncoomans.mobiledevelopment2newproject.R;
import com.example.martijncoomans.mobiledevelopment2newproject.StorageController;

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
    private FragmentManager fragmentManager = getFragmentManager();

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
        if(pokemon.isCatched != true) {
            if(rand.nextInt(4) == 0) {
                toast = Toast.makeText(mContext, "You caught the pokemon!", Toast.LENGTH_SHORT);
                pokemons = StorageController.getPokemons(mContext);

                pokemon.isCatched = true;

                pokemons.add(pokemon);

                StorageController.setPokemons(mContext, pokemons);
            } else {
                toast = Toast.makeText(mContext, "Pokemon escaped!", Toast.LENGTH_SHORT);
            }
        }
        else {
            button.setBackgroundColor(5555);
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
    }

    public void setPokemon(Pokemon pokemon) {
        currentPokemon = pokemon;
        id.setText("#" + pokemon.id);
        name.setText(pokemon.name);
        new DownloadImageTask(image).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.id + ".png");
    }
}

