package com.example.dvd;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRap = findViewById(R.id.btnRap);
        Button btnElectro = findViewById(R.id.btnElectro);
        Button btnHumour = findViewById(R.id.btnHumour);

        // Configuration des boutons
        btnRap.setOnClickListener(view -> {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Rap");
            alertDialog.setMessage("Voulez-vous choisir un artiste de rap ?");
            alertDialog.setPositiveButton("Oui", (arg0, arg1) -> {
                Intent intent = new Intent(MainActivity.this, Rap.class);
                startActivity(intent);
            });
            alertDialog.setNegativeButton("Non", (arg0, arg1) -> {
                // Ne rien faire quand "Non" est cliqué
            });
            alertDialog.show();
        });

        btnElectro.setOnClickListener(view -> {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Electro");
            alertDialog.setMessage("Voulez-vous choisir un artiste electro ?");
            alertDialog.setPositiveButton("Oui", (arg0, arg1) -> {
                Intent intent = new Intent(MainActivity.this, Electro.class);
                startActivity(intent);
            });
            alertDialog.setNegativeButton("Non", (arg0, arg1) -> {
                // Ne rien faire quand "Non" est cliqué
            });
            alertDialog.show();
        });

        btnHumour.setOnClickListener(view -> {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Humour");
            alertDialog.setMessage("Voulez-vous choisir un humoriste ?");
            alertDialog.setPositiveButton("Oui", (arg0, arg1) -> {
                Intent intent = new Intent(MainActivity.this, Humour.class);
                startActivity(intent);
            });
            alertDialog.setNegativeButton("Non", (arg0, arg1) -> {
                // Ne rien faire quand "Non" est cliqué
            });
            alertDialog.show();
        });
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menugeneral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRechercher:
                Intent intentRecherche = new Intent(MainActivity.this, Recherche.class);
                startActivity(intentRecherche);
                return true;

            case R.id.menuEquipe:

                return true;

            case R.id.menuContact:

                return true;

            case R.id.menuDeconnexion:

                afficherBoiteDeDeconnexion();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void afficherBoiteDeDeconnexion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Déconnexion");
        builder.setMessage("Voulez-vous vous déconnecter ?");
        builder.setPositiveButton("Oui", (dialog, which) -> {

            SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("Email");
            editor.apply();


            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Effacer l'historique
            startActivity(intent);


            finish();
        });
        builder.setNegativeButton("Non", null);
        builder.show();
    }



}
