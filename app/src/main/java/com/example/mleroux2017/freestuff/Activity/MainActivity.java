package com.example.mleroux2017.freestuff.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.ControllersFirebase.AdresseControllersFirebase;
import com.example.mleroux2017.freestuff.ControllersFirebase.AnnonceControllerFirebase;
import com.example.mleroux2017.freestuff.Objects.Adresse;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


import org.parceler.Parcels;


public class MainActivity extends AppCompatActivity {

	private Adresse adresseForAnnonce = null;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // *** SETTINGS de la BDD ***
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

		ImageButton btnGoToListAnnonces = findViewById(R.id.liste_annonces);
        btnGoToListAnnonces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                startActivity(intent);
            }
        });
		
        ImageButton btnGoToMyAccount = findViewById(R.id.my_account);
        btnGoToMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyAccountActivity.class);
                startActivity(intent);
            }
        });
        Button btnGoToCameraControl = findViewById(R.id.gestion_camera);
        btnGoToCameraControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestCameraActivity.class);
                startActivity(intent);
            }
        });
        ImageButton btnGoToFavourite = findViewById(R.id.btn_goto_favourite);
        btnGoToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyFavoriteActivity.class);
                startActivity(intent);
            }
        });

    }


   public void onClickAddAnnonce(View view){
        Intent intent = new Intent(this,AddAnnonce.class);
        startActivityForResult(intent,987);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);

		 //ajout adresse
		 if(requestCode==123 && resultCode == Activity.RESULT_OK){
					adresseForAnnonce = Parcels.unwrap(data.getParcelableExtra("adresseRetour"));
		 }else if(requestCode==123 && resultCode == Activity.RESULT_CANCELED){
					adresseForAnnonce = null;
		}

		// ajout annonce
        if (requestCode == 987 && resultCode == Activity.RESULT_OK)
        {Annonce annonce = Parcels.unwrap(data.getParcelableExtra("annonce"));
            Log.i("annonce", "onActivityResult: "+annonce.toString());
            AnnonceControllerFirebase aCtrl = new AnnonceControllerFirebase();
            aCtrl.insert(annonce);
            Toast.makeText(MainActivity.this, "ResultOK "+annonce.getTitre(), Toast.LENGTH_SHORT).show();
        }

    }
}
