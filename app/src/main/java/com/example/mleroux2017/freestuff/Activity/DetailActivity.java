package com.example.mleroux2017.freestuff.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import com.example.mleroux2017.freestuff.Fragment.DetailFragment;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intention = getIntent();
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.second);
        if(detailFragment.isInLayout()){
            //Annonce ann = intention.getParcelableExtra("information");
            Annonce annonceToShow = Parcels.unwrap(intention.getParcelableExtra("information"));
            detailFragment.setAnnonce(annonceToShow);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
