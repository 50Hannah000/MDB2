package com.example.martijncoomans.mbd2android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailFragment extends Fragment {

    private TextView name;
    private ImageView image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail, container, false);

        name = view.findViewById(R.id.product_detail_name);
        image = view.findViewById(R.id.product_detail_image);

        if (getArguments() != null && getArguments().containsKey("product")) {
            setProduct((Product) getArguments().getSerializable("product"));
        }

        return view;
    }

    public void setProduct(Product product) {
       name.setText(product.name);
       new DownloadImageTask(image).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + product.id + ".png");
    }
}
