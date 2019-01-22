package com.example.martijncoomans.mobiledevelopment2newproject;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PokemonListFragment extends ListFragment {
    private OnSelectedItemListener mListener;
    private CustomAdapter mAdapter;

    List<Pokemon> pokemons = new ArrayList<>();

    public interface OnSelectedItemListener {
        void onItemSelected(Pokemon pokemon);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof OnSelectedItemListener) {
            mListener = (OnSelectedItemListener) getActivity();
        }

        mAdapter = new CustomAdapter(getActivity(), R.layout.list_row);
        setListAdapter(mAdapter);

        getAllPokemons();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        if (mListener != null) {
            mListener.onItemSelected(mAdapter.getItem(position));
        }
    }

    public void getAllPokemons() {
        pokemons.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://pokeapi.co/api/v2/pokemon/?limit=151", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonProducts = response.getJSONArray("results");

                    for(int i=0;i<jsonProducts.length();i++) {
                        JSONObject jsonProduct = jsonProducts.getJSONObject(i);
                        String url = jsonProduct.getString("url");
                        String[] segments = url.split("/");
                        int id = Integer.parseInt(segments[segments.length-1]);
                        Pokemon pokemon = new Pokemon(jsonProduct.getString("name"), id, null);

                        pokemons.add(pokemon);
                    }
                    mAdapter.addAll(pokemons);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("getAllPokemons()","Error :" + error.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
