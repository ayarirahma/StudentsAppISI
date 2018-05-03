package api;

import java.sql.Struct;

/**
 * Created by Geek Hamza on 03/05/2018.
 */
public class form {
    public  form()
    {
        this.nom=new variable_struct();
        this.prenom=new variable_struct();
        this.groupe=new variable_struct();
        this.commentaire=new variable_struct();
        this.matierre=new variable_struct();
        this.niveau=new variable_struct();
    }

    public variable_struct groupe,matierre,nom,prenom,commentaire,niveau;

    public variable_struct getGroupe() {
        return groupe;
    }

    public void setGroupe(variable_struct groupe) {
        this.groupe = groupe;
    }

    public variable_struct getMatierre() {
        return matierre;
    }

    public void setMatierre(variable_struct matierre) {
        this.matierre = matierre;
    }

    public variable_struct getNom() {
        return nom;
    }

    public void setNom(variable_struct nom) {
        this.nom = nom;
    }

    public variable_struct getPrenom() {
        return prenom;
    }

    public void setPrenom(variable_struct prenom) {
        this.prenom = prenom;
    }

    public variable_struct getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(variable_struct commentaire) {
        this.commentaire = commentaire;
    }

    public variable_struct getNiveau() {
        return niveau;
    }

    public void setNiveau(variable_struct niveau) {
        this.niveau = niveau;
    }
}
