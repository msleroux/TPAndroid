package com.example.mleroux2017.freestuff.Controllers;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Categorie;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CategoryController {

   static FirebaseFirestore db;
   static {
       db = FirebaseFirestore.getInstance();
   }

  public static void insert(Categorie cat) {
        //creation de la map correspondant à l'objet categorie afin de pouvoir l'insérer dans firebase
       Map<String, Object> categorie = new HashMap<>();
       categorie.put("titre", cat.getTitre());
       //ajout de l'objet à la base
       db.collection("categories")
                   .add(categorie)
                   .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                       @Override
                       public void onSuccess(DocumentReference documentReference) {

                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) { }
                   });
   }
}
