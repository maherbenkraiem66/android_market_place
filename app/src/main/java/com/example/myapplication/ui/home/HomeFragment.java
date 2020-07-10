package com.example.myapplication.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.Listing;
import com.example.myapplication.ui.login.LoginActivity;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout tech = root.findViewById(R.id.tech);
        TextView username = root.findViewById(R.id.username);
        SharedPreferences pref = getActivity().getSharedPreferences("pref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        username.setText(pref.getString("username", "Manel"));
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listing.class);
                intent.putExtra("type", "t");
                startActivity(intent);
            }
        });
        LinearLayout elect = root.findViewById(R.id.electro);
        elect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listing.class);
                intent.putExtra("type", "e");
                startActivity(intent);
            }
        });
        LinearLayout gro = root.findViewById(R.id.groceries);
        gro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listing.class);
                intent.putExtra("type", "g");
                startActivity(intent);
            }
        });
        LinearLayout clean = root.findViewById(R.id.clean);
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listing.class);
                intent.putExtra("type", "c");
                startActivity(intent);
            }
        });
        LinearLayout freshf = root.findViewById(R.id.freshf);
        freshf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listing.class);
                intent.putExtra("type", "ff");
                startActivity(intent);
            }
        });
        LinearLayout frozenf = root.findViewById(R.id.frozenf);
        frozenf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Listing.class);
                intent.putExtra("type", "fz");
                startActivity(intent);
            }
        });

        return root;
    }
}