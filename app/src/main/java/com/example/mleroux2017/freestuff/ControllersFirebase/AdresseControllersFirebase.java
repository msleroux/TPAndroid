package com.example.mleroux2017.freestuff.ControllersFirebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mleroux2017.freestuff.Objects.Adresse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdresseControllersFirebase {
    private FirebaseFirestore db;

    public AdresseControllersFirebase() {
        this.db = FirebaseFirestore.getInstance();

    }


    public void insert(Adresse addr, final OnCreateListener listener){
        Map<String, Object> adresse = new HashMap<>();
        adresse.put("rue",addr.getRue());
        adresse.put("codePostal",addr.getCodePostal());
        adresse.put("ville",addr.getVille());
        db.collection("adresses")
                .add(adresse)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onCreateListener(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onCreateListener("failure");
                    }
                });
    }

    public void update(Adresse addr, final OnUpdateListener listener){
        if(addr.getId()!=null){
            DocumentReference reference = db.collection("adresses").document(addr.getId());
            reference.update("rue",addr.getRue());
            reference.update("codePostal",addr.getCodePostal());
            reference.update("ville",addr.getVille())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listener.onUpdateListener(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onUpdateListener(false);
                        }
                    });
        }else{
            listener.onUpdateListener(false);
        }
    }

    public void getAll(final OnTabListener listener){

        db.collection("adresses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Adresse> adresses = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("DataBase", document.getId() + " => " + document.getData());
                                String ville = document.getString("ville");
                                String codePostal = document.getString("codePostal");
                                String rue = document.getString("rue");
                                String id = document.getId();
                                adresses.add(new Adresse(id,rue,codePostal,ville));
                            }
                            listener.onGetTabListener(adresses);
                        } else {
                            Log.w("DataBase", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void getById(String idAdresse, final OnSingleListener listener){
        db.collection("adresses").document(idAdresse)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Adresse adresse = null;
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                String ville = document.getString("ville");
                                String codePostal = document.getString("codePostal");
                                String rue = document.getString("rue");
                                String id = document.getId();
                                adresse = new Adresse(id,rue,codePostal,ville);
                            }
                            listener.onSingleListener(adresse);
                        }
                    }
                });
    }

    public void deleteById(String idAdresse, final OnDeleteListener listener){
        db.collection("adresses").document(idAdresse)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onDeleteListener(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onDeleteListener(false);
                    }
                });
    }

    public interface OnCreateListener{
        void onCreateListener(String idAdresse);
    }

    public interface OnUpdateListener{
        void onUpdateListener(boolean result);
    }

    public interface OnDeleteListener{
        void onDeleteListener(boolean result);
    }

    public interface OnSingleListener{
        void onSingleListener(Adresse adr);
    }

    public interface OnTabListener{
        void onGetTabListener(ArrayList<Adresse> tab);
    }
}
