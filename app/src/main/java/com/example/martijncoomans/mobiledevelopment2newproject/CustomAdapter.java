package com.example.martijncoomans.mobiledevelopment2newproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class CustomAdapter extends ArrayAdapter<Pokemon> {

    public CustomAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, parent, false);
        }
        Pokemon pokemon = (Pokemon) getItem(position);
        TextView idText = convertView.findViewById(R.id.id);
        TextView nameText = convertView.findViewById(R.id.name);
        ImageView image = convertView.findViewById(R.id.image);

        if(pokemon != null) {
            idText.setText("#" + pokemon.id);
            nameText.setText(pokemon.name);
            new DownloadImageTask(image).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.id + ".png");
        }

        return convertView;
    }

}
