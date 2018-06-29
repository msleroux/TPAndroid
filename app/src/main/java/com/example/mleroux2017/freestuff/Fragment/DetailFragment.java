package com.example.mleroux2017.freestuff.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private String adresse;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onClickMap(View v){


    }

    public void setAnnonce(final Annonce annonce){
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //annonce.searchCategorieById();
       // annonce.searchAdresseById();
        TextView view = getView().findViewById(R.id.detail_annonce_description);
        view.setText(annonce.getDescription());
        view = getView().findViewById(R.id.detail_annonce_etat);
        view.setText(annonce.getEtatArticle());
        view = getView().findViewById(R.id.detail_annonce_titre);
        view.setText(annonce.getTitre());

        ImageButton btnMap = getView().findViewById(R.id.btn_gotoMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode("7 rue des salicornes 44200 Nantes"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });



        final ToggleButton btnFavorite = getView().findViewById(R.id.btn_tgl_favorite);
        String userID = auth.getCurrentUser().getUid();

        /* Vérifie si l'utilisateur a l'annonce dans sa liste de favoris */
        db.collection("JoinTable_User_AnnonceFavorite")
                .whereEqualTo("idUser",userID)
                .whereEqualTo("idAnnonce",annonce.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                            /* Si il y a des résultats*/
                            if(querySnapshot.size()>0){
                                /* Passer le bouton en mode checked */
                                btnFavorite.setChecked(true);
                            }else {
                                /* sinon passer le bouton en mode unchecked */
                                btnFavorite.setChecked(false);
                            }
                        }else{
                            btnFavorite.setChecked(false);
                        }
                    }
                });

        /* Initialise l'action sur le bouton pour les favoris */
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String userID = auth.getCurrentUser().getUid();
                /* Si l'état du boutton est en mode checked */
                if(btnFavorite.isChecked()){
                    /* insérer en BDD un élément TableJoin ID_USER & ID_ANNONCE */
                    Map<String, Object> adresse = new HashMap<>();
                    adresse.put("idUser",userID);
                    adresse.put("idAnnonce",annonce.getId());
                    db.collection("JoinTable_User_AnnonceFavorite")
                            .add(adresse)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getContext(),"Success Ajout favoris",Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"Erreur Ajout favoris",Toast.LENGTH_LONG).show();
                                }
                            });
                }else{
                    /* Récupération de l'ID du lien du JoinTable contenant en attribut l'ID_USER et ID_ANNONCE */
                    db.collection("JoinTable_User_AnnonceFavorite")
                            .whereEqualTo("idUser",userID)
                            .whereEqualTo("idAnnonce",annonce.getId())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    /* si l'ID_JOIN_TABLE trouvé */
                                    if(task.isSuccessful()){
                                        QuerySnapshot document = task.getResult();
                                        if(document.size()>0){
                                            List<DocumentChange> documentSnapshot = document.getDocumentChanges();

                                            /* pour chaque résultats retournés */
                                            for(int i=0;i<document.size();i++){
                                                String idFound = documentSnapshot.get(i).getDocument().getId();

                                                /* Supprimer le JoinTable avec l'ID de l'élément en BDD */
                                                FirebaseFirestore dbFirebase = FirebaseFirestore.getInstance();
                                                dbFirebase.collection("JoinTable_User_AnnonceFavorite").document(idFound)
                                                        .delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(getContext(),"Success Suppression Favoris",Toast.LENGTH_LONG).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(),"Erreur Suppression Favoris",Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }

                                        }
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                                      @Override
                                                      public void onFailure(@NonNull Exception e) {
                                                          Toast.makeText(getContext(),"Erreur recherche du favori pour suppression",Toast.LENGTH_LONG).show();
                                                      }
                                                  }
                            );
                }
            }
        });

        /* Récupération de l'image en BDD pour l'afficher */
        final long TWO_MEGABYTE = 2048 * 2048;
        StorageReference imageRef = mStorageRef.child("test/objetTrouve.jpg");
        imageRef.getBytes(TWO_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ImageView imageView = getView().findViewById(R.id.detail_annonce_image);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
