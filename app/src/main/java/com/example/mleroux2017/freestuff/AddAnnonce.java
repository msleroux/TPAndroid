package com.example.mleroux2017.freestuff;

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


import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mleroux2017.freestuff.Controllers.CategorieControllerFirebase;
import com.example.mleroux2017.myapplication.R;

import org.parceler.Parcels;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class AddAnnonce extends FragmentActivity implements
        DatePickerFragment.EditDateDialogListener,
        TimePickerFragment.EditTimeDialogListener {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private Spinner spinnerCat;
    private List<Categorie> spinnerCatArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annonce);
        // initializing list
        spinnerCatArray = new ArrayList<>();

        //**** HEURE PAR DEFAUT DU RDV;
        this.hour=9;


        //**** CREATION SPINNER POUR CATEGORIES *****
        // set the spinner data programmatically, from a string array or list
        // (1) get a reference to the spinner
        spinnerCat= findViewById(R.id.spinner);

        // (2) Get data for your array or list = aller chercher les catégories via le controller
        CategorieControllerFirebase.getAll(new CategorieControllerFirebase.OnTabListener() {
            @Override
            public void onGetTabListener(ArrayList<Categorie> tab) {
                spinnerCatArray.addAll(tab);
                }
        });

        // (3) create an adapter from the list
        //@param : context;simple_spinner_item; your list
        if(spinnerCatArray.size()>0) {
            ArrayAdapter<Categorie> adapter = new ArrayAdapter<Categorie>(
                    AddAnnonce.this,
                    simple_spinner_item,
                    spinnerCatArray
                     );
            // (4) set the adapter on the spinner
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCat.setAdapter(adapter);
            Log.i("spin","spinnerCatArray>0");
        }else {
                Log.i("spin","spinnerCatArray<0");
        }

    }

    public void onValiderAnnonceCliked(View view){

       String titre = ((EditText)findViewById(R.id.editTitre)).getText().toString();
       String description = ((EditText)findViewById(R.id.editDescription)).getText().toString();
       RadioGroup rg = ((RadioGroup)findViewById(R.id.radio_grp));
       String etatUsage = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
       Categorie categorie = (Categorie) spinnerCat.getSelectedItem();
       // *** VERIFS infos
        if (titre.length()<3){
            Toast.makeText(AddAnnonce.this,"Le titre doit être renseigné et doit faire au moins 3 caractères",Toast.LENGTH_LONG).show();
        } else if (description.length()<8){
            Toast.makeText(AddAnnonce.this,"La description doit être renseignée et doit faire au moins 8 caractères",Toast.LENGTH_LONG).show();
        } else if (etatUsage.length()<1){
            Toast.makeText(AddAnnonce.this,"L'état de l'article doit être renseigné",Toast.LENGTH_LONG).show();
        } else if (etatUsage.length()<1){
        Toast.makeText(AddAnnonce.this,"L'état de l'article doit être renseigné",Toast.LENGTH_LONG).show();
        } else if(this.year < Calendar.getInstance().get(Calendar.YEAR) && month<1 && day<1){
            //l'année ne peut pas être passée, les int non renseignés seront à 0 par défaut
            Toast.makeText(AddAnnonce.this,"La date doit être renseignée",Toast.LENGTH_LONG).show();
        } else if( categorie == null) {
            Toast.makeText(AddAnnonce.this,"La catégorie doit être renseignée",Toast.LENGTH_LONG).show();
        } else {
            Date date = getDateFromPicker();
            Annonce annonce= new Annonce(titre,description,etatUsage,categorie,date);
            Intent intent = new Intent();
            intent.putExtra("annonce",Parcels.wrap(annonce));
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

    }



    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

    }

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
}
