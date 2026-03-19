package com.example.dvd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.*;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText edt_Email, edt_pswd;
    Button btn_ok;
    TextView txt_non_inscrit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_Email = findViewById(R.id.edt_email);
        edt_pswd = findViewById(R.id.edt_pswd);
        btn_ok = findViewById(R.id.btn_ok_login);
        txt_non_inscrit = findViewById(R.id.txt_non_inscrit);

        btn_ok.setOnClickListener(v -> {
            String email = edt_Email.getText().toString();
            String pswd = edt_pswd.getText().toString();

            if (email.isEmpty() || pswd.isEmpty()) {
                Toast.makeText(Login.this, "Veuillez saisir les deux valeurs !", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, pswd);
            }
        });


        txt_non_inscrit.setOnClickListener(v -> {

            Intent register = new Intent(Login.this, Register.class);
            startActivity(register);
        });
    }

    public void loginUser(String email, String pswd) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL.LOGIN_URL,
                response -> {
                    Log.d("ServerResponse", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("erreur")) {

                            // Enregistre l'email dans SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("Email", email); // Sauvegarde l'email
                            editor.apply();  // Applique les changements

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(Login.this, MainActivity.class);
                            home.putExtra("Email", email);
                            startActivity(home);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                erreur -> {
                    Log.e("Login", "Erreur : " + erreur.getMessage());
                    Toast.makeText(getApplicationContext(), "Erreur réseau", Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", email);
                params.put("PassWrd", pswd);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
