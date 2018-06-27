package com.example.mleroux2017.freestuff.Activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;



import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.mleroux2017.freestuff.ControllersFirebase.AdresseControllersFirebase;
import com.example.mleroux2017.freestuff.ControllersFirebase.CategorieControllerFirebase;
import com.example.mleroux2017.freestuff.Fragment.DatePickerFragment;
import com.example.mleroux2017.freestuff.Fragment.TimePickerFragment;
import com.example.mleroux2017.freestuff.Objects.Adresse;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.Objects.Categorie;
import com.example.mleroux2017.freestuff.R;


import org.parceler.Parcels;


import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class AddAnnonce extends FragmentActivity implements
        DatePickerFragment.EditDateDialogListener,
       TimePickerFragment.EditTimeDialogListener {

    //attributs privés : sont modifié lorsqu'on utilise les datepicker
    //sont vérifiables 0 ou valeur existe
    //mis en attribut pour faire le lien entre les actions des event des pickers
    // et l'action bouton valider
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private Adresse adresseForAnnonce;

    private Spinner spinnerCat;
    private List<Categorie> spinnerCatList;
    private ArrayAdapter<Categorie> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annonce);

        //**** HEURE PAR DEFAUT DU RDV;
        this.hour=9;

        //**** CREATION SPINNER POUR CATEGORIES *****
        // initializing a list
        spinnerCatList = new ArrayList<>();

        // (1) get a reference to the spinner
        spinnerCat= findViewById(R.id.spinner);

        // (2) create adapter with your list (empty no worry)
        adapter = new ArrayAdapter<Categorie>(AddAnnonce.this,
                        simple_spinner_item,
                        spinnerCatList
                );

        // (3) Get data and add item(object BO) in your ADAPTER
        CategorieControllerFirebase.getAll(new CategorieControllerFirebase.OnTabListener() {
            @Override
            public void onGetTabListener(ArrayList<Categorie> tab) {
                for (Categorie c :tab
                     ) {
                    adapter.add(c);
                }
            }
        });

        // (4) set adapter as the resource for spinner in your layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter);

        ImageButton btnAjoutAdresse = findViewById(R.id.btnAdresse);
        btnAjoutAdresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAnnonce.this,CreateAdresseActivity.class);
                startActivityForResult(intent,765);
            }
        });

    }

    public void onValiderAnnonceCliked(View view) {

        String titre = ((EditText) findViewById(R.id.editTitre)).getText().toString();
        String description = ((EditText) findViewById(R.id.editDescription)).getText().toString();
        RadioGroup rg = ((RadioGroup) findViewById(R.id.radio_grp));
        Categorie categorie = (Categorie) spinnerCat.getSelectedItem();

            // *** VERIFS infos
            if (titre.length() < 3) {
                Toast.makeText(AddAnnonce.this, "Le titre doit être renseigné et doit faire au moins 3 caractères", Toast.LENGTH_LONG).show();
            } else if (description.length() < 8) {
                Toast.makeText(AddAnnonce.this, "La description doit être renseignée et doit faire au moins 8 caractères", Toast.LENGTH_LONG).show();
            }else if ((rg.getCheckedRadioButtonId() == -1)) {
                // si aucun radio button n'est selectionne, l'id checked est = -1
                Toast.makeText(AddAnnonce.this, "L'état de l'article doit être renseigné", Toast.LENGTH_LONG).show();
            } else if (this.year < Calendar.getInstance().get(Calendar.YEAR) && month < 1 && day < 1) {
                //l'année ne peut pas être passée, les int non renseignés seront à 0 par défaut
                Toast.makeText(AddAnnonce.this, "La date doit être renseignée", Toast.LENGTH_LONG).show();
            } else if (categorie == null) {
                Toast.makeText(AddAnnonce.this, "La catégorie doit être renseignée", Toast.LENGTH_LONG).show();
            } else {
                String etatUsage = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                Date date = getDateFromPicker();

                Annonce annonce = new Annonce(titre, description, etatUsage, categorie, date);
                if(adresseForAnnonce!=null){
                    annonce.setAdresseRDV(adresseForAnnonce);
                }
                Log.i("annonce", "onActivityResult: ");
                Intent intent = new Intent();
                intent.putExtra("annonce", Parcels.wrap(annonce));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

    }


    //ouverture boite de dialog au clic bouton
    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    //ouverture boite de dialog au clic bouton
    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

    }

    //construction de la date JavaUtil à partir des entiers récupérés
    public Date getDateFromPicker() {
        //on récupère la date à partir des attributs settés avec les boite de dialog
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year,this.month,this.day,this.hour,this.minute);
        //on renvoie la date sous format java util
        return calendar.getTime();
    }



    @Override
    public void onFinishEditDateDialog(int year, int month, int day) {
        this.day =day;
        this.month = month;
        this.year =year;
        Log.i("onFinishEditDateDialog", "onFinishEditDateDialog: "+year+month+day);
    }

    @Override
    public void onFinishEditTimeDialog(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        Log.i("onFinishEditDateDialog", "onFinishEditDateDialog: "+hour+minute);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==765 && resultCode == Activity.RESULT_OK){
            adresseForAnnonce = Parcels.unwrap(data.getParcelableExtra("adresseRetour"));
            AdresseControllersFirebase adrC = new AdresseControllersFirebase();
            adrC.insert(adresseForAnnonce, new AdresseControllersFirebase.OnCreateListener() {
                @Override
                public void onCreateListener(String idAdresse) {
                    if(!idAdresse.equalsIgnoreCase("failure")){
                       adresseForAnnonce.setId(idAdresse);
                    }
                }
            });
        }else if(requestCode==765 && resultCode == Activity.RESULT_CANCELED){
            adresseForAnnonce = null;
        }
    }
}
