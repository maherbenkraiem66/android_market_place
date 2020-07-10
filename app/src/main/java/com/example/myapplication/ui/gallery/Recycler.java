package com.example.myapplication.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.Detail;
import com.example.myapplication.ui.login.LoginActivity;

import java.util.ArrayList;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {
    Activity act;
    Activity mContext;
    ArrayList<Product> products;

    public Recycler(ArrayList<Product> products, Context mContext) {
        this.products = products;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
            holder.stock.setTextColor(Color.parseColor("#47c271"));
            holder.stock.setText("En stock");
        } else {
            holder.stock.setTextColor(Color.parseColor("#EE1D23"));
            holder.stock.setText("Hors stock");
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
        Button stock;
        Button nouv_prix;
        TextView amount;
        LinearLayout cardView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            prix = itemView.findViewById(R.id.prix);
            nom = itemView.findViewById(R.id.primary_text);
            label = itemView.findViewById(R.id.sub_text);
            img = itemView.findViewById(R.id.media_image);
            stock = itemView.findViewById(R.id.stock);
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
        }

    }
}