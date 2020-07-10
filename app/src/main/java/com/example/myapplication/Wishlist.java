package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.Detail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Wishlist extends AppCompatActivity {
    RecyclerView r;
    Context act;
    Button total;
    String sprix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Panier - " + User.wishlist.size() + " produit(s)");
        r = findViewById(R.id.recycler_prods);
        total = findViewById(R.id.total);
        act = getApplicationContext();
        FloatingActionButton fab = findViewById(R.id.fab);
        Button confirmerCommande = findViewById(R.id.confirmer);
        if (User.wishlist.size() == 0)
            confirmerCommande.setText("Panier vide");
        ConstraintLayout screen = findViewById(R.id.screen);

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        init_products(User.wishlist);
        float somme = 0;
        for (int i = 0; i < User.wishlist.size(); i++) {
            Float fprix = Float.parseFloat(User.wishlist.get(i).getPrix());
            Float reduction = Float.parseFloat(User.wishlist.get(i).getPromotion());
            if (!User.wishlist.get(i).getPromotion().equals("0"))
                fprix = fprix - (fprix * (reduction / 100));
            somme += fprix;
        }
        sprix = String.valueOf(somme);
        sprix += "000";
        int spos = sprix.indexOf(".");

        String after = sprix.substring(spos, sprix.length() - 1);

        if (after.length() > 3)
            after = after.substring(0, 3);

        sprix = sprix.substring(0, spos).concat(after);

        total.setText("TOTAL : " + String.valueOf(sprix) + "DT");

    }

    void init_products(ArrayList<Product> products) {
        RecyclerView recycler = r.findViewById(R.id.recycler_prods);
        RecyclerView1 adapter = new RecyclerView1(products, act);
        recycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                float somme = 0;
                for (int i = 0; i < User.wishlist.size(); i++) {
                    Float fprix = Float.parseFloat(User.wishlist.get(i).getPrix());
                    Float reduction = Float.parseFloat(User.wishlist.get(i).getPromotion());
                    if (!User.wishlist.get(i).getPromotion().equals("0"))
                        fprix = fprix - (fprix * (reduction / 100));
                    somme += fprix;


                }
                total.setText("TOTAL : " + String.valueOf(sprix) + "DT");
                getSupportActionBar().setTitle("Panier - " + User.wishlist.size() + " produit(s)");

                System.out.println("CALLED FROM RECYCLERVIEW");
            }
        });
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(act));
    }

    public class RecyclerView1 extends RecyclerView.Adapter<com.example.myapplication.Wishlist.RecyclerView1.ViewHolder> {
        Activity act;
        Activity mContext;
        ArrayList<Product> products;

        public RecyclerView1(ArrayList<Product> products, Context mContext) {
            this.products = products;

        }

        @NonNull
        @Override
        public com.example.myapplication.Wishlist.RecyclerView1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_wish, parent, false);
            com.example.myapplication.Wishlist.RecyclerView1.ViewHolder holder = new com.example.myapplication.Wishlist.RecyclerView1.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.myapplication.Wishlist.RecyclerView1.ViewHolder holder, int position) {
            Product p = products.get(position);
            System.out.println("http://192.168.1.17:80/manel/images/" + p.getImage());
            Glide.with(holder.img.getContext())
                    .asBitmap()
                    .load("http://192.168.1.17:80/manel/images/" + p.getImage())
                    .into(holder.img);
            holder.label.setText(p.getDescription());
            holder.nom.setText(p.getLibelle());
            holder.prix.setText(p.getPrix() + "dt");
            if (Integer.parseInt(p.getQt()) > 0) {
                //holder.stock.setTextColor(Color.parseColor("#47c271"));
                //holder.stock.setText("En stock");
            } else {
                //  holder.stock.setTextColor(Color.parseColor("#EE1D23"));
                // holder.stock.setText("Hors stock");
            }
            String promo = p.getPromotion();
            float prix = Float.parseFloat(p.getPrix());


            if (!p.getPromotion().equals("0")) {
                Float reduction = Float.parseFloat(promo);
                float nouv_prix = prix - (prix * (reduction / 100));
                String sprix = String.valueOf(nouv_prix);
                sprix += "000";
                int spos = sprix.indexOf(".");

                String after = sprix.substring(spos, sprix.length() - 1);

                if (after.length() > 3)
                    after = after.substring(0, 3);
                after += "DT";
                sprix = sprix.substring(0, spos).concat(after);

                holder.nouv_prix.setText(sprix);

                holder.amount.setText("ÉCONOMISER -" + p.getPromotion() + "%");
                holder.nouv_prix.setVisibility(View.VISIBLE);
                holder.amount.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            Button prix;
            TextView nom;
            TextView label;
            ImageView img;
            Button removeFromCart;
            Button nouv_prix;
            TextView amount;
            LinearLayout cardView;

            public ViewHolder(@NonNull final View itemView) {
                super(itemView);

                prix = itemView.findViewById(R.id.prix);
                nom = itemView.findViewById(R.id.primary_text);
                label = itemView.findViewById(R.id.sub_text);
                img = itemView.findViewById(R.id.media_image);
                removeFromCart = itemView.findViewById(R.id.stock);
                amount = itemView.findViewById(R.id.amount);
                nouv_prix = itemView.findViewById(R.id.red);
                Button action_button_1 = itemView.findViewById(R.id.prix);
                final Context c = action_button_1.getContext();
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), Detail.class);
                        int pos = getAdapterPosition();
                        intent.putExtra("id", products.get(pos).getIdProduit());
                        intent.putExtra("name", products.get(pos).getLibelle());
                        intent.putExtra("desc", products.get(pos).getDescription());
                        intent.putExtra("img", products.get(pos).getImage());
                        intent.putExtra("prix", products.get(pos).getPrix());
                        intent.putExtra("reduction", products.get(pos).getPromotion());
                        intent.putExtra("qt", products.get(pos).getQt());
                        intent.putExtra("type", products.get(pos).getType());
                        c.startActivity(intent);


                    }
                });

                removeFromCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        User.wishlist.remove(pos);
                        float somme = 0;
                        for (int i = 0; i < User.wishlist.size(); i++) {
                            Float fprix = Float.parseFloat(User.wishlist.get(i).getPrix());
                            Float reduction = Float.parseFloat(User.wishlist.get(i).getPromotion());
                            if (!User.wishlist.get(i).getPromotion().equals("0"))
                                fprix = fprix - (fprix * (reduction / 100));
                            somme += fprix;
                        }
                        sprix = String.valueOf(somme);
                        total.setText("Total : " + sprix);
                        notifyItemChanged(pos);
                        notifyItemRangeRemoved(pos, 1);
                        notifyDataSetChanged();
                    }
                });
            }

        }
    }
}