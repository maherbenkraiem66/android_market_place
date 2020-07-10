package com.example.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ActivityNavigator;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class Detail extends AppCompatActivity {
    Product p = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String name = i.getExtras().getString("name");
        String prix = i.getExtras().getString("prix");
        String img = i.getExtras().getString("img");
        String desc = i.getExtras().getString("desc");
        String id = i.getExtras().getString("id");
        String qt = i.getExtras().getString("qt");
        String promo = i.getExtras().getString("reduction");
        String type = i.getExtras().getString("type");
        TextView tname = findViewById(R.id.primary_text);
        TextView tdesc = findViewById(R.id.sub_text);
        ImageView vimg = findViewById(R.id.media_image);
        Button tprix = findViewById(R.id.prix);
        Button stock;
        Button nouv_prix;
        TextView amount;
        stock = findViewById(R.id.stock);
        amount = findViewById(R.id.amount);
        nouv_prix = findViewById(R.id.red);
        p.setDescription(desc);
        p.setIdProduit(id);
        p.setImage(img);
        p.setPrix(prix);
        p.setPromotion(promo);
        p.setType(type);
        p.setQt(qt);
        p.setLibelle(name);
        tname.setText(name);
        tdesc.setText(desc);
        tprix.setText(prix + "DT");
        Glide.with(getApplicationContext())
                .asBitmap()
                .load("http://192.168.1.17:80/manel/images/" + img)
                .into(vimg);

        if (Integer.parseInt(qt) > 0) {
            stock.setTextColor(Color.parseColor("#47c271"));
            stock.setText("En stock");
        } else {
            stock.setTextColor(Color.parseColor("#EE1D23"));
            stock.setText("Hors stock");
        }
        float fprix = Float.parseFloat(prix);

        float nprix = Float.parseFloat(prix);
        if (!promo.equals("0")) {
            Float reduction = Float.parseFloat(promo);
            //nprix=fprix-(fprix*(reduction/100));
            p.setPrix(String.valueOf(nprix));
            System.out.println("P GET PRIX " + p.getPrix());

            String sprix = String.valueOf(nprix);
            sprix += "000";
            int spos = sprix.indexOf(".");

            String after = sprix.substring(spos, sprix.length() - 1);

            if (after.length() > 3)
                after = after.substring(0, 3);
            after += "DT";
            sprix = sprix.substring(0, spos).concat(after);
            System.out.println("SPRIX : " + sprix + " promo " + promo + " ");
            nouv_prix.setText(sprix);
            tprix.setPaintFlags(tprix.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            amount.setText("-" + promo + "%");
            nouv_prix.setVisibility(View.VISIBLE);
            amount.setVisibility(View.VISIBLE);
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Produit ajouté au panier", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                User.wishlist.add(p);
            }
        });
    }
}