package com.example.mleroux2017.freestuff.ControllersFirebase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Activity.AddAnnonce;
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

import javax.security.auth.login.LoginException;


public class AnnonceController {

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
        if(ann.getAdresseRDV()!= null){
            DocumentReference adresseRef = db.collection("adresses").document(a.getAdresseRDV().getId());
            annToInsert.put("Addresse",adresseRef);
        }
        //TODO verifier id user identique ? afficher le bouton modifier que dans "mes annonces"

        //on update en donnant l'id de la catégorie à udpater et le nouvel objet à insérer
        db.collection("annonces")
                .document(ann.getId())
                .update(annToInsert);
    }

    public void getAll(final AnnonceController.OnTabListener listener) {
        db.collection("annonces")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Categorie> liste = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                liste.add(new Annonce(
                                        document.getId(),
                                        document.getString("titre"),
                                        document.getString("description"),
                                        document.getString("etatArticle"),
                                        document.getDate("heureRDV"),
                                        ));
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



    public void getByTitre(final CategorieControllerFirebase.OnValueListener listener, String libelle) {
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


}
