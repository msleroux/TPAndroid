package com.example.mleroux2017.freestuff.ControllersFirebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mleroux2017.freestuff.Objects.Adresse;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.Objects.Categorie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnnonceControllerFirebase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final static String TAG="aCtr";
   // final static String COLLECTION="annnonces";
    private FirebaseAuth dbAuth = FirebaseAuth.getInstance();

    public void insert(Annonce a){
        Map<String, Object> annonce = new HashMap<>();
        annonce.put("titre",a.getTitre());
        annonce.put("description",a.getDescription());
        annonce.put("etatArticle",a.getEtatArticle());
        annonce.put("heureRDV",a.getHeureRDV());

        if(dbAuth.getCurrentUser()!=null) {
            String userId = dbAuth.getCurrentUser().getUid();
            annonce.put("idAuteur", userId);
            DocumentReference categorieRef = db.collection("categories").document(a.getCategorieArticle().getId());
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
                            Log.i(TAG, "onSuccess ");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i(TAG, "onFailure: ");
                        }
                    });

        }else {
            //pas de current user
            Log.i("annonce","utilisateur non authentifié ");
        }

    }

    public void update(Annonce ann){
        //on crée le nouvel objet à insérer
        Map<String,Object> annToInsert = new HashMap<>();
        annToInsert.put("titre",ann.getTitre());
        annToInsert.put("description",ann.getDescription());
        annToInsert.put("etatArticle",ann.getEtatArticle());
        annToInsert.put("heureRDV",ann.getHeureRDV());
        if(ann.getAdresseRDV().getId()!= null){
            DocumentReference adresseRef = db.collection("adresses").document(ann.getAdresseRDV().getId());
            annToInsert.put("Addresse",adresseRef);

        }
        //TODO verifier id user identique ? afficher le bouton modifier que dans "mes annonces" ?

        //on update en donnant l'id de la catégorie à udpater et le nouvel objet à insérer
        db.collection("annonces")
                .document(ann.getId())
                .update(annToInsert);
    }

    public void getAll(final AnnonceControllerFirebase.OnTabListener listener) {
        db.collection("annonces")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Annonce> liste = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                //TODO create constructeur sans cat sans adresse
                                liste.add(new Annonce(

                                        doc.getId(),
                                        doc.getString("titre"),
                                        doc.getString("description"),
                                        doc.getString("etatArticle"),
                                        doc.getDate("heureRDV")

                                        ));
                                //TODO faire query séparée pour récupérer cat et adr à partir de la ref
                                task.addOnSuccessListener()
                                (Categorie)document.get("categorieArticle")
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        City city = documentSnapshot.toObject(City.class);
                                    }
                                });
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
        void onGetTabListener(ArrayList<Annonce> tab);
    }



    public void getListByTitre(final AnnonceControllerFirebase.OnTabListener listener, String libelle) {
        db.collection("annonces")
                .orderBy("titre")
                .startAt(libelle)
                .endAt(libelle+'\uf8ff')
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                             ArrayList<Annonce> listeResults = new ArrayList<Annonce> ();
                             List<DocumentSnapshot> documents = task.getResult().getDocuments();
                             if(documents.size()>0){
                                 for (int i = 0; i < documents.size(); i++)
                                 {  listeResults.add(new Annonce(
                                         documents.get(i).getId(),
                                         documents.get(i).getString("titre"),
                                         documents.get(i).getString("description"),
                                         documents.get(i).getString("etatArticle"),
                                         documents.get(i).getDate("heureRDV"),
                                         (Adresse)documents.get(i).get("Adresse"),
                                         (Categorie)documents.get(i).get("categorieArticle")));
                                 }

                            }
                            Log.d("doc", "listener", task.getException());
                            listener.onGetTabListener(listeResults);

                        } else {
                            Log.d("doc", "Error getting document.", task.getException());
                        }
                    }
                });

    }

    public interface OnValueListener{
        void onGetValueListener(List<Annonce> liste);
    }

    public void delete(Annonce a){
        if(a.getId()!=null){
            db.collection("categories").document(a.getId()).delete();

        }else {
            Log.i("doc","id null impossible de suprimer cette catégorie");
        }
    }



}
