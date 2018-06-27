package com.example.mleroux2017.freestuff.Objects;


import com.example.mleroux2017.freestuff.Objects.Categorie;
import com.example.mleroux2017.freestuff.Objects.Adresse;
import com.google.firebase.firestore.ServerTimestamp;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Annonce {
    private String id;
    private String titre;
    private String description;
    private String etatArticle;

    @ServerTimestamp
    private Date heureRDV;

    private Adresse adresseRDV;
    private Categorie categorieArticle;
	private String id_auteur;
    private boolean etatAnnonce;
	
	
    public Annonce() {
    }

    public Annonce(String id, String titre, String description, String etatArticle, Categorie categorieArticle) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.categorieArticle = categorieArticle;
    }

    public Annonce(String id, String titre, String description, String etatArticle, Date heureRDV, Adresse adresseRDV, Categorie categorieArticle) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.heureRDV = heureRDV;
        this.adresseRDV = adresseRDV;
        this.categorieArticle = categorieArticle;
    }

    public Annonce(String titre, String description, String etatArticle, Categorie categorieArticle) {
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.categorieArticle = categorieArticle;
    }

    public Annonce(String titre, String description, String etatArticle, Categorie categorieArticle, Date date) {
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.categorieArticle = categorieArticle;
        this.heureRDV = date;
    }

    public Annonce(String titre, String description, String etatArticle, Date heureRDV, Adresse adresseRDV, Categorie categorieArticle) {
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.heureRDV = heureRDV;
        this.adresseRDV = adresseRDV;
        this.categorieArticle = categorieArticle;
    }




	
	/* public Annonce(String id, String titre, String description, String etatArticle, String id_CategorieArticle) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.id_CategorieArticle = id_CategorieArticle;
    }

    public Annonce(String id, String titre, String description, String etatArticle, Date heureRDV, String id_adresseRDV, String id_CategorieArticle) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.heureRDV = heureRDV;
        this.id_AdresseRDV = id_adresseRDV;
        this.id_CategorieArticle = id_CategorieArticle;
    }

    public Annonce(String titre, String description, String etatArticle, String categorieArticle) {
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.id_CategorieArticle = categorieArticle;
    }

    public Annonce(String titre, String description, String etatArticle, Date heureRDV, String id_adresseRDV, String id_CategorieArticle) {
        this.titre = titre;
        this.description = description;
        this.etatArticle = etatArticle;
        this.heureRDV = heureRDV;
        this.id_AdresseRDV = id_adresseRDV;
        this.id_CategorieArticle = id_CategorieArticle;
    }*/
	
	
	
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtatArticle() {
        return etatArticle;
    }

    public void setEtatArticle(String etatArticle) {
        this.etatArticle = etatArticle;
    }

    public Date getHeureRDV() {
        return heureRDV;
    }

    public void setHeureRDV(Date heureRDV) {
        this.heureRDV = heureRDV;
    }

    public Adresse getAdresseRDV() {
        return adresseRDV;
    }

    public void setAdresseRDV(Adresse adresseRDV) {
        this.adresseRDV = adresseRDV;
    }

    public Categorie getCategorieArticle() {
        return categorieArticle;
    }

    public void setCategorieArticle(Categorie categorieArticle) {
        this.categorieArticle = categorieArticle;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "id='" + id + '\'' +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", etatArticle='" + etatArticle + '\'' +
                ", heureRDV=" + heureRDV +
                ", adresseRDV=" + adresseRDV +
                ", categorieArticle=" + categorieArticle.toString() +
                '}';
    }
}
