package com.example.mleroux2017.freestuff.Controllers;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Adresse;
import com.example.mleroux2017.freestuff.Annonce;
import com.example.mleroux2017.freestuff.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class AnnonceController {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final static String TAG="aCtr";
    
    public void insert(Annonce a){
        Map<String, Object> annonce = new HashMap<>();
        annonce.put("titre",a.getTitre());
        annonce.put("description",a.getDescription());
        annonce.put("etatArticle",a.getEtatArticle());
        annonce.put("heureRDV",a.getHeureRDV());

        DocumentReference categorieRef = db.collection("categories").document(a.getCategorieArticle().getId());
        //TODO vérifier que la categorie chope un id lors de l'adapter ?
        annonce.put("categorieArticle",categorieRef);
        if(a.getAdresseRDV()!= null){
           DocumentReference adresseRef = db.collection("adresses").document(a.getAdresseRDV().getId());
           annonce.put("Addresse",adresseRef);
       }


        db.collection("annonces")
                .add(annonce)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "onSuccess: "+documentReference.get().getResult().getString("titre"));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: ");
                    }
                });

    }

}
