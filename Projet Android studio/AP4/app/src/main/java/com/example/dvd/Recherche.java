package com.example.dvd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Recherche extends AppCompatActivity {

    // Définition de 2 attributs privés
    private Button btnCherche;
    private EditText edtCherche;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        // Association des attributs privés à la vue
        edtCherche = findViewById(R.id.edtRecherche);
        btnCherche = findViewById(R.id.btnRecherche);




        //-------------------------------------------------------------------------------
        // Gestion du bouton Retour associé à la ressource btnRetourRecherche
        //-------------------------------------------------------------------------------
        final Button btnRetour = (Button) findViewById(R.id.btnRetourRecherche);
        //Utilisation d'un Listener [interface de gestion d'évènements] pour récupérer l'interaction avec le bouton
        btnRetour.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Lance l'activité qui affiche la fenêtre principale
                Intent intent = new Intent(Recherche.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}