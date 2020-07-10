package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    public static ArrayList<Product> wishlist = new ArrayList<>();
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("mdp")
    @Expose
    private String mdp;
    @SerializedName("adresse")
    @Expose
    private String adresse;
    @SerializedName("id_user")
    @Expose
    private String idUser;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

}