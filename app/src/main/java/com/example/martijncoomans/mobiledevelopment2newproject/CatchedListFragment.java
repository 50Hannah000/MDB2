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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CatchedListFragment extends ListFragment {
    private OnSelectedItemListener mListener;
    private CustomAdapter mAdapter;
    Context mContext;

    List<Pokemon> pokemons = new ArrayList<>();

    public interface OnSelectedItemListener {
        void onItemSelected(Pokemon pokemon);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();

        if (getActivity() instanceof OnSelectedItemListener) {
            mListener = (OnSelectedItemListener) getActivity();
        }

        mAdapter = new CustomAdapter(getActivity(), R.layout.list_row);
        setListAdapter(mAdapter);

        getCatchedPokemons();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        if (mListener != null) {
            mListener.onItemSelected(mAdapter.getItem(position));
        }
    }

    public void getCatchedPokemons() {
        File file = new File(mContext.getCacheDir(), "pokemon");
        if(file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                pokemons = StorageController.stringToList(readFromFileInputStream(fileInputStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
            Log.e("readFile", ex.getMessage(), ex);
        }finally
        {
            return retBuf.toString();
        }
    }
}

