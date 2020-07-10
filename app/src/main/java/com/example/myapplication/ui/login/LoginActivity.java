package com.example.myapplication.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.http.http;
import com.example.myapplication.http.http.LoadUser;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.login.LoginViewModel;
import com.example.myapplication.ui.login.LoginViewModelFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                System.out.println("test123" + name + pass);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.17:80/manel/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                http Http = new http();
                http.LoadUser loadUser = retrofit.create(http.LoadUser.class);
                Call<User> call = loadUser.performLogin(name, pass);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        System.out.println(response.code());
                        if (response.isSuccessful()) {
                            System.out.println("User name " + response.body().getNom());
                            User user = response.body();
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("name", user.getNom());
                            editor.putString("adress", user.getAdresse());
                            editor.putString("budget", user.getBudget());
                            editor.putString("pwd", user.getMdp());
                            editor.putString("id", user.getIdUser());

                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            System.out.println("LOADING CHALLENGE FAILED " + response.raw());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("Fail");
                        System.out.println(t);
                    }
                });

            }
        });


    }


}