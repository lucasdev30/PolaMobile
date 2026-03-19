package com.example.dvd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reservation extends AppCompatActivity {
    private Button btnConfirmer;
    private Button btnAnnuler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        final Button btnRetour = (Button) findViewById(R.id.btnRetourReservation);

        btnRetour.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Lance l'activité qui affiche la fenêtre principale
                Intent intent = new Intent(Reservation.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnConfirmer = findViewById(R.id.btnConfirmerResa);
        btnConfirmer.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(Reservation.this, "Réservation confirmée", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnAnnuler = findViewById(R.id.btnAnnulerResa);
        btnAnnuler.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(Reservation.this, "Réservation anulée", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        String nomArtiste = getIntent().getStringExtra("nomArtiste");
        double tarif = getIntent().getDoubleExtra("tarif", 0);
        long dateMillis = getIntent().getLongExtra("dateConcert", 0);
        int imgResID = getIntent().getIntExtra("imgResID", 0);

        Date dateConcert = new Date(dateMillis);



    }
}