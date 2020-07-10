package com.example.myapplication.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.http.http;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.login.LoginActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    ArrayList<Product> products;
    View r;
    Activity act;
    RecyclerView rv;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            act = (Activity) context;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        act = getActivity();

        r = root;
        rv = root.findViewById(R.id.recycler_prods);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.17:80/manel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        http Http = new http();
        http.LoadProducts loadUser = retrofit.create(http.LoadProducts.class);

        Call<ArrayList<Product>> call = loadUser.getProds("");
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


        return root;
    }

    void init_products(ArrayList<Product> products) {
        RecyclerView recycler = r.findViewById(R.id.recycler_prods);
        Recycler adapter = new Recycler(products, act);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(act));
    }
}