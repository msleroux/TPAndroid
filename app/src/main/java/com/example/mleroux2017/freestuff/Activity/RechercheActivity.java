package com.example.mleroux2017.freestuff.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mleroux2017.freestuff.ControllersFirebase.AnnonceControllerFirebase;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;

import java.util.ArrayList;


public class RechercheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);




    }

    public void lancerRecherche(View view) {
        //Faire  la recherche en base de données
        String libelle =((EditText)findViewById(R.id.recherche)).getText().toString();
        AnnonceControllerFirebase acf = new AnnonceControllerFirebase();
        acf.getListByTitre(libelle, new AnnonceControllerFirebase.OnTabListener() {
            @Override
            public void onGetTabListener(ArrayList<Annonce> tab) {

            }
        });

        //ramener la liste
        //l'afficher comme dans ResultActivity
    }
}
