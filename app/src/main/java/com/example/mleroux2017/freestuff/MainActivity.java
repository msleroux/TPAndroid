package com.example.mleroux2017.freestuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mleroux2017.freestuff.Controllers.CategoryController;
import com.example.mleroux2017.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoryController.insert(new Categorie("Meubles"));
    }
}
