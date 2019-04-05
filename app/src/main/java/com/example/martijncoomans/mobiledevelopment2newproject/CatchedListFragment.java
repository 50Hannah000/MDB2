package com.example.martijncoomans.mobiledevelopment2newproject;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class CatchedListFragment extends ListFragment {
    private OnSelectedItemListener mListener;
    private CustomAdapter mAdapter;
    private Context mContext;

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

    //function which gets all pokemons from store
    public void getCatchedPokemons() {
        pokemons.clear();
        pokemons = StorageController.getPokemons(mContext);
        mAdapter.addAll(pokemons);
        mAdapter.notifyDataSetChanged();
    }
}

