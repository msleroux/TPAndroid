package com.example.mleroux2017.freestuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Controllers.CategorieControllerFirebase;
import com.example.mleroux2017.myapplication.R;

import java.util.ArrayList;


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


        CategorieControllerFirebase.getByTitre(new CategorieControllerFirebase.OnValueListener() {
                                                   @Override
                                                   public void onGetValueListener(Categorie cat) {
                                                       Log.i("doc","main"+cat.getTitre());
                                                       cat.setTitre("Electro");
                                                       CategorieControllerFirebase.update(cat);

                                                   }
                                               },"Beaute");

        //CategorieControllerFirebase.delete(cat);

    }
}
