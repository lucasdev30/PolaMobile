package com.example.dvd;

import android.content.Intent;
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

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail, edtPassword, edtFirstName, edtLastName, edtAge;
    Button btnValider;
    TextView txt_deja_parmi_nous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edt_email_register);
        edtPassword = findViewById(R.id.edt_pswd_register);
        edtFirstName = findViewById(R.id.edt_first_name_register);
        edtLastName = findViewById(R.id.edt_last_name_register);
        edtAge = findViewById(R.id.edt_age_register);
        txt_deja_parmi_nous = findViewById(R.id.txt_deja_parmi_nous);

        btnValider = findViewById(R.id.btn_ok_register);

        btnValider.setOnClickListener(this);

        txt_deja_parmi_nous.setOnClickListener(v -> {

            Intent login = new Intent(Register.this, Login.class);
            startActivity(login);
        });
    }

    public void registerUser() {
        final String email = edtEmail.getText().toString().trim();
        final String pswd = edtPassword.getText().toString().trim();
        final String firstName = edtFirstName.getText().toString().trim();
        final String lastName = edtLastName.getText().toString().trim();
        final String age = edtAge.getText().toString().trim();

        // Validation des champs
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || pswd.isEmpty() || age.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL.REGISTER_URL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.optString("message", "Message non disponible");
                        boolean erreur = jsonObject.optBoolean("erreur", false);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        if (!erreur) {
                            Intent login = new Intent(Register.this, Login.class);
                            startActivity(login);
                        }
                    } catch (JSONException e) {
                        Log.e("Register", "JSON error: " + e.getMessage());
                    }
                },
                erreur -> {
                    Toast.makeText(getApplicationContext(), "Erreur réseau", Toast.LENGTH_LONG).show();
                    Log.e("Register", "Volley error: " + erreur.toString());
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Nom", lastName);
                params.put("Prenom", firstName);
                params.put("Age", age);
                params.put("Email", email);
                params.put("PassWrd", pswd);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btnValider) {
            registerUser();

        }
    }
}
