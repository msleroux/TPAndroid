package com.example.mleroux2017.freestuff.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mleroux2017.freestuff.Fragment.AnnonceFragment;
import com.example.mleroux2017.freestuff.Fragment.DetailFragment;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;

import org.parceler.Parcels;

public class ResultActivity extends AppCompatActivity implements
        AnnonceFragment.OnListFragmentInteractionListener,
        DetailFragment.OnFragmentInteractionListener
        {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    public void onListFragmentInteraction(Annonce item) {
        DetailFragment detailFragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.second);
        if(detailFragment!=null && detailFragment.isInLayout()){
            detailFragment.setAnnonce(item);
        }else{
            Intent intent = new Intent(ResultActivity.this, DetailActivity.class);
            intent.putExtra("information", Parcels.wrap(item));
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
