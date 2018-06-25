package com.example.mleroux2017.freestuff.Controllers;

import android.support.annotation.NonNull;

import com.example.mleroux2017.freestuff.Adresse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdresseControllers {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void insert(Adresse addr){
        Map<String, Object> adresse = new HashMap<>();
        adresse.put("rue",addr.getRue());
        adresse.put("codePostal",addr.getCodePostal());
        adresse.put("ville",addr.getVille());
        db.collection("adresses")
                .add(adresse)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public List<Adresse> getAll(){

    }
}
