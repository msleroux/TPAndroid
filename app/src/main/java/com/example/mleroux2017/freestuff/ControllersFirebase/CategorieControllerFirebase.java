package com.example.mleroux2017.freestuff.ControllersFirebase;

import android.support.annotation.NonNull;
import android.util.Log;


import com.example.mleroux2017.freestuff.Objects.Adresse;
import com.example.mleroux2017.freestuff.Objects.Categorie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

public class CategorieControllerFirebase {

   static FirebaseFirestore db;
   private static List<Categorie> allCategories;
   static {
       db = FirebaseFirestore.getInstance();
       allCategories = new ArrayList<>();
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

   public static void update(Categorie cat){
       //on crée le nouvel objet à insérer
       Map<String,Object> catToInsert = new HashMap<>();
       catToInsert.put("titre",cat.getTitre());
       //on update en donnant l'id de la catégorie à udpater et le nouvel objet à insérer
       db.collection("categories")
               .document(cat.getId())
               .update(catToInsert);
              
   }


    public static void getById(String idCategorie, final OnValueListener listener){
        db.collection("categories").document(idCategorie)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Categorie cat = null;
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                String titre = document.getString("titre");
                                String id = document.getId();
                                cat= new Categorie(id,titre);
                            }
                            listener.onGetValueListener(cat);
                        }
                    }
                });
    }

    public static void getAll(final OnTabListener listener) {
        db.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Categorie> liste = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                liste.add(new Categorie(document.getId(),document.getString("titre")));
                                Log.d("doc", document.getId() + " => " + document.getData());
                            }
                            listener.onGetTabListener(liste);
                        } else {
                            Log.w("doc", "Error getting documents.", task.getException());
                        }
                    }
                });
   }

   public interface OnTabListener{
       void onGetTabListener(ArrayList<Categorie> tab);
   }




   public static void getByTitre(final OnValueListener listener,String libelle) {
       db.collection("categories").whereEqualTo("titre",libelle)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           Categorie cat = new Categorie();
                           List<DocumentSnapshot> document = task.getResult().getDocuments();
                           //TODO vérifier un seul résultat - gérer si plusieurs results
                            if(document.size()>0 && document.size()<2){
                                cat.setTitre(document.get(0).getString("titre"));
                                cat.setId(document.get(0).getId());
                                Log.d("doc","value",task.getException());
                                Log.d("doc", "exists", task.getException());
                            }
                           Log.d("doc", "listener", task.getException());
                         listener.onGetValueListener(cat);

                       } else {
                           Log.d("doc", "Error getting document.", task.getException());
                       }
                   }
               });

   }

    public interface OnValueListener{
        void onGetValueListener(Categorie cat);
    }



   public static void delete(Categorie cat){
       if(cat.getId()!=null){
           db.collection("categories").document(cat.getId()).delete();

       }else {
           Log.i("doc","id null impossible de suprimer cette catégorie");
       }
   }

      /*  CategorieControllerFirebase.getByTitre(new CategorieControllerFirebase.OnValueListener() {
                                                  @Override
                                                   public void onGetValueListener(Categorie cat) {
                                                       Log.i("doc","main"+cat.getTitre());
                                                       cat.setTitre("Electro");
                                                       CategorieControllerFirebase.update(cat);

                                                   }
                                               },"Beaute");*/



}
