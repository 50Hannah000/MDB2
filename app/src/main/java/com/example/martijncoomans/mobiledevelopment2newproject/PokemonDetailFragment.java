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
    private List<Pokemon> pokemons;
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
        button = view.findViewById(R.id.catch_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPokemon.image = image.getDrawingCache();
                catchPokemon(currentPokemon);
            }
        });

        if (getArguments() != null && getArguments().containsKey("pokemon")) {
            setPokemon((Pokemon) getArguments().getSerializable("pokemon"));
        }

        return view;
    }

    public void catchPokemon(Pokemon pokemon){
        Random rand = new Random();
        if(rand.nextInt(4) == 0) {
            File file = new File(mContext.getCacheDir(), "pokemon");
            if(file.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    pokemons = StorageController.stringToList(readFromFileInputStream(fileInputStream));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            pokemons.add(pokemon);

            StorageController.setPokemons(mContext, pokemons);
        }
    }

    private String readFromFileInputStream(FileInputStream fileInputStream)
    {
        StringBuffer retBuf = new StringBuffer();

        try {
            if (fileInputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String lineData = bufferedReader.readLine();
                while (lineData != null) {
                    retBuf.append(lineData);
                    lineData = bufferedReader.readLine();
                }
            }
        }catch(IOException ex)
        {
            Log.e("IOException", ex.getMessage(), ex);
        }finally
        {
            return retBuf.toString();
        }
    }

    private void writeDataToFile(File file, String data)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            this.writeDataToFile(fileOutputStream, data);
            fileOutputStream.close();
        }catch(FileNotFoundException ex)
        {
            Log.e("FileNotFound", ex.getMessage(), ex);
        }catch(IOException ex)
        {
            Log.e("IOException", ex.getMessage(), ex);
        }
    }

    private void writeDataToFile(FileOutputStream fileOutputStream, String data)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
        }catch(FileNotFoundException ex)
        {
            Log.e("FileNotFound", ex.getMessage(), ex);
        }catch(IOException ex)
        {
            Log.e("IOException", ex.getMessage(), ex);
        }
    }

    public void setPokemon(Pokemon pokemon) {
        currentPokemon = pokemon;
        id.setText("#" + pokemon.id);
        name.setText(pokemon.name);
        new DownloadImageTask(image).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.id + ".png");
    }
}

