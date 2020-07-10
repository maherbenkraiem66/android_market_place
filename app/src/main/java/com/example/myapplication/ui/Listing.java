package com.example.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;

import com.example.myapplication.http.http;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.gallery.Recycler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.myapplication.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Listing extends AppCompatActivity {
    ArrayList<Product> products;
    View r;
    Activity act;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        act = this;
        rv = findViewById(R.id.recycler_prods);
        String type = getIntent().getExtras().getString("type");
        String text = "Products";
        switch (type) {
            case "c":
                text = "Cleaning";
                break;
            case "fz":
                text = "Frozen food";
                break;
            case "ff":
                text = "Fresh food";
                break;
            case "e":
                text = "Electronics";
                break;
            case "s":
                text = "Smartphone & technology";
                break;
            case "g":
                text = "Groceries";
                break;
        }
        ;
        getSupportActionBar().setTitle(text);

        rv = findViewById(R.id.recycler_prods);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.17:80/manel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        http Http = new http();
        http.LoadProducts loadProducts = retrofit.create(http.LoadProducts.class);

        Call<ArrayList<Product>> call = loadProducts.getProds(type);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                System.out.println(response.code());
                if (response.isSuccessful()) {

                    products = response.body();

                    init_products(products);

                } else {
                    System.out.println("LOADING CHALLENGE FAILED " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                System.out.println("Fail");
                System.out.println(t);
            }
        });

    }

    void init_products(ArrayList<Product> products) {
        RecyclerView recycler = rv.findViewById(R.id.recycler_prods);
        Recycler adapter = new Recycler(products, act);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(act));
    }
}