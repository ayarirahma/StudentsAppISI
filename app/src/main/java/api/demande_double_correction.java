package api;

import java.util.ArrayList;

/**
 * Created by Geek Hamza on 27/04/2018.
 */

public class demande_double_correction {


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getMatierre() {
        return matierre;
    }

    public void setMatierre(String matierre) {
        this.matierre = matierre;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    String nom,prenom,commentaire,matierre,niveau,groupe;
}
