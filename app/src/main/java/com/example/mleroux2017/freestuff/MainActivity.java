package com.example.mleroux2017.freestuff;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Controllers.CategorieControllerFirebase;
import com.example.mleroux2017.myapplication.R;

import org.parceler.Parcels;

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


      /*  CategorieControllerFirebase.getByTitre(new CategorieControllerFirebase.OnValueListener() {
                                                  @Override
                                                   public void onGetValueListener(Categorie cat) {
                                                       Log.i("doc","main"+cat.getTitre());
                                                       cat.setTitre("Electro");
                                                       CategorieControllerFirebase.update(cat);

                                                   }
                                               },"Beaute");*/

        //CategorieControllerFirebase.delete(cat);

        Intent intent = new Intent(this, AddAnnonce.class);
        startActivityForResult(intent,123);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 123 && resultCode == Activity.RESULT_OK)
        {
            Annonce annonce = Parcels.unwrap(intent.getParcelableExtra("annonce"));
            Toast.makeText(MainActivity.this, "ResultOK "+annonce.getTitre(), Toast.LENGTH_SHORT).show();
            //TODO insert(annonce) -> annonce controller
        }

    }
}
