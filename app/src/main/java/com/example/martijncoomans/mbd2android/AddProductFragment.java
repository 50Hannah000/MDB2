package com.example.martijncoomans.mbd2android;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddProductFragment extends Fragment {

    private EditText productName;
    private EditText productPrice;
    private Button addButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_add, container, false);
        productName = (EditText) view.findViewById(R.id.productName);
        productPrice = (EditText) view.findViewById(R.id.productPrice);
        addButton = (Button) view.findViewById(R.id.button);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try {
                    JSONObject body = new JSONObject();
                    body.put("name", productName.getText());
                    body.put("price", productPrice.getText());
                    final String requestBody = body.toString();

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.8.41:3000/products", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("response", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.i("getAllProducts()", "Error :" + error.toString());
                        }
                    }) {
                        @Override
                        public byte[] getBody() {
                            Log.i("body", requestBody);
                            return requestBody.getBytes();
                        }
                    };
                    AppController.getInstance().addToRequestQueue(jsonObjectRequest);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        SharedPreferences settings = this.getActivity().getSharedPreferences("PREFS", 0);
        return view;
    }
}
