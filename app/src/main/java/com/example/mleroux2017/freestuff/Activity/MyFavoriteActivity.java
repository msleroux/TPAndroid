package com.example.mleroux2017.freestuff.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mleroux2017.freestuff.ControllersFirebase.AnnonceControllerFirebase;
import com.example.mleroux2017.freestuff.ControllersFirebase.CategorieControllerFirebase;
import com.example.mleroux2017.freestuff.Fragment.AnnonceFragment;
import com.example.mleroux2017.freestuff.Fragment.DetailFragment;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.Objects.Categorie;
import com.example.mleroux2017.freestuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MyFavoriteActivity extends AppCompatActivity implements
        AnnonceFragment.OnListFragmentInteractionListener,
        DetailFragment.OnFragmentInteractionListener{
private List<String> ids = new ArrayList<String>();
private List<Annonce> annonces = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userID = auth.getCurrentUser().getUid();

        /* Vérifie si l'utilisateur a des annonces dans sa liste de favoris */
        db.collection("JoinTable_User_AnnonceFavorite")
                .whereEqualTo("idUser",userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                            List<DocumentChange> documentSnapshot = querySnapshot.getDocumentChanges();

                            /* Si il y a des résultats*/
                            if(querySnapshot.size()>0){
                               for(int i=0;i<documentSnapshot.size();i++) {
                                   Log.i("document", "onGetValueListener: ");

                                   String idAnnonce = documentSnapshot.get(i).getDocument().get("idAnnonce").toString();
                                   ids.add(idAnnonce);
                               }
                                   AnnonceControllerFirebase acf = new AnnonceControllerFirebase();

                                   for(int j=0;j<ids.size();j++){

                                       acf.getById(ids.get(j), new AnnonceControllerFirebase.OnSingleListener() {
                                           @Override
                                           public void onGetValueListener(Annonce a) {
                                               annonces.add(a);
                                               Log.i("annonces", "onGetValueListener: ");
                                               if(annonces.size() == ids.size()){
                                                   setContentView(R.layout.activity_result);
                                                   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                                   AnnonceFragment annonce = AnnonceFragment.newInstance(annonces);
                                                   fragmentTransaction.replace(R.id.first,annonce);
                                                   fragmentTransaction.commit();
                                               }
                                           }
                                       });
                                   }




                            }else {


                            }
                        }else{

                        }
                    }
                });




    }


    @Override
    public void onListFragmentInteraction(Annonce item) {
        DetailFragment detailFragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.second);
        if(detailFragment!=null && detailFragment.isInLayout()){
            detailFragment.setAnnonce(item);
        }else{
            Intent intent = new Intent(MyFavoriteActivity.this, DetailActivity.class);
            intent.putExtra("information", Parcels.wrap(item));
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
