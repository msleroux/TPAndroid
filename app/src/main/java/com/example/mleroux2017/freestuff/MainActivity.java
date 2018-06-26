package com.example.mleroux2017.freestuff;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Controllers.AnnonceController;
import com.example.mleroux2017.freestuff.Controllers.CategorieControllerFirebase;
import com.example.mleroux2017.myapplication.R;

import org.parceler.Parcels;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CategorieControllerFirebase.insert(new Categorie("Beaute"));

        CategorieControllerFirebase.getAll(new CategorieControllerFirebase.OnTabListener() {
          @Override
          public void onGetTabListener(ArrayList<Categorie> tab) {
              //écrire ici ce qu'on fait de la liste
              for (Categorie c :tab)
              {
                  Toast.makeText(MainActivity.this,"titre"+c.getTitre(), Toast.LENGTH_LONG).show();
              }
          }
        });



    }


   public void onClickAddAnnonce(View view){
        Intent intent = new Intent(this, AddAnnonce.class);
        startActivityForResult(intent,123);
    }

    public void onClickVoirAnnonce(View view){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 123 && resultCode == Activity.RESULT_OK)
        {
            Annonce annonce = Parcels.unwrap(data.getParcelableExtra("annonce"));
            Log.i("annonce", "onActivityResult: "+annonce.toString());
            Toast.makeText(MainActivity.this, "ResultOK "+annonce.getTitre(), Toast.LENGTH_SHORT).show();
            AnnonceController aCtrl = new AnnonceController();
            aCtrl.insert(annonce);
        }

    }
}
