package com.example.martijncoomans.mbd2android;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiController {
    private String server_url = "http://localhost:3000";
    private Context context;
    Cache cache;
    Network network;
    RequestQueue requestQueue;
    private Fragment fragment;

    public ApiController(Fragment fragment) {
        this.fragment = fragment;
        context = fragment.getContext();
        this.context = context;
        cache = new DiskBasedCache(context.getCacheDir(),1024*1024);
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
    }

    public void getAllProducts() {
        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url + "/products", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonProducts = response.getJSONArray("products");
                    List<Product> products = new ArrayList<>();

                    for(int i=0;i<jsonProducts.length();i++){
                        JSONObject jsonProduct = jsonProducts.getJSONObject(i);
                        Product product = new Product();

                        product.setName(jsonProduct.getString("name"));
                        product.setPrice(jsonProduct.getString("price"));
                        //product.setBarcode(jsonProduct.getString("barcode"));
                        //product.setCategory(jsonProduct.getString("category"));
                        //product.setExpireDate(stringToDate(jsonProduct.getString("expireDate"), ""));
                       //product.setImagePath(jsonProduct.getString("imagepath"));

                        products.add(product);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("getAllProducts()","Error :" + error.toString());
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("contenttype", "application/json");
                params.put("content-type", "application/json");
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);*/
    }
}
