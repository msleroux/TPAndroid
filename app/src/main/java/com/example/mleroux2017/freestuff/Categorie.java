package com.example.mleroux2017.freestuff;

public class Categorie {
    private String id;
    private String titre;

    public Categorie() {
    }

    public Categorie(String titre) {
        this.titre = titre;
    }

    public Categorie(String id, String titre) {
        this.id = id;
        this.titre = titre;
    }

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
}
