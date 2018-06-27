package com.example.mleroux2017.freestuff.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Objects.Adresse;
import com.example.mleroux2017.freestuff.R;

import org.parceler.Parcels;

public class CreateAdresseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_adresse);
        Button validation = findViewById(R.id.btn_validerAdresse_create);
        validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText_rue_create);
                String rue = editText.getText().toString().trim();
                editText = findViewById(R.id.editText_codepostal_create);
                String codePostal = editText.getText().toString().trim();
                editText = findViewById(R.id.editText_ville_create);
                String ville = editText.getText().toString().trim();
                if(rue.length()<15){
                    Toast.makeText(CreateAdresseActivity.this,"Rue non renseignée",Toast.LENGTH_LONG).show();
                }else if(codePostal.length()<1 && codePostal.length()>5){
                    Toast.makeText(CreateAdresseActivity.this,"Le code postal doit comprendre 5 chiffres",Toast.LENGTH_LONG).show();
                }else if(ville.length()<5){
                    Toast.makeText(CreateAdresseActivity.this,"Ville non renseignée",Toast.LENGTH_LONG).show();
                }else{
                    Adresse addr = new Adresse(rue,codePostal,ville);
                    Intent intent = new Intent();
                    intent.putExtra("adresseRetour", Parcels.wrap(addr));
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            }
        });
        Button annulation = findViewById(R.id.btn_annulerAdresse_create);
        annulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });
    }


}
