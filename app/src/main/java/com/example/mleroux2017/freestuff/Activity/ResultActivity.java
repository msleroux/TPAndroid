package com.example.mleroux2017.freestuff.Activity;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.mleroux2017.freestuff.ControllersFirebase.AnnonceControllerFirebase;
import com.example.mleroux2017.freestuff.Fragment.AnnonceFragment;
import com.example.mleroux2017.freestuff.Fragment.DetailFragment;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;


import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements
        AnnonceFragment.OnListFragmentInteractionListener,
        DetailFragment.OnFragmentInteractionListener
        {

     private List<Annonce> listeResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        AnnonceControllerFirebase acf = new AnnonceControllerFirebase();
        acf.getAll(new AnnonceControllerFirebase.OnTabListener() {
            @Override
            public void onGetTabListener(ArrayList<Annonce> tab) {
                if(tab.size()>0){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    for (int i = 0; i <tab.size(); i++) {
                        listeResult.add(tab.get(i));
                        Log.i("ResultActivity", "onGetTabListener: "+tab.get(i).toString());
                    }
                    AnnonceFragment annonce = AnnonceFragment.newInstance(listeResult);
                    fragmentTransaction.replace(R.id.first,annonce);
                    fragmentTransaction.commit();
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
            Intent intent = new Intent(ResultActivity.this, DetailActivity.class);
            intent.putExtra("information", Parcels.wrap(item));
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
