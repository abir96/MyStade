package com.example.mystade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;




import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class inscription extends AppCompatActivity  {

    EditText nomEdit, prenomEdit,loginEdit,mdpEdit, mdp2Edit;
    Button ok , annuler;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.43.151:8080/android/newUser.php";
    static String login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inscription");

        nomEdit = (EditText) findViewById(R.id.nomID);
        prenomEdit = (EditText) findViewById(R.id.prenomID);
        loginEdit = (EditText) findViewById(R.id.loginID);
        mdp2Edit =(EditText) findViewById(R.id.passe2ID);
        mdpEdit = (EditText) findViewById(R.id.passeID);
        ok = (Button) findViewById(R.id.okID);
        annuler = (Button) findViewById(R.id.cancelID);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomEdit.setText("");
                prenomEdit.setText("");
                loginEdit.setText("");
                mdpEdit.setText("");
                mdp2Edit.setText("");
                Intent intent = new Intent(inscription.this, MainActivity.class );
                startActivity(intent);
            }
        });



        ok.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if(!mdpEdit.getText().toString().equals(mdp2Edit.getText().toString())){
                    Toast.makeText(inscription.this, "les mots de passes ne sont pas identiques", Toast.LENGTH_LONG).show();
                }

                else{
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println(response.toString());
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("nom", nomEdit.getText().toString());
                            parameters.put("prenom", prenomEdit.getText().toString());
                            parameters.put("login", loginEdit.getText().toString());
                            parameters.put("mdp", mdpEdit.getText().toString());

                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(inscription.this, "Bienvenue", Toast.LENGTH_LONG).show();
                    login=loginEdit.getText().toString();
                    Intent intent1 = new Intent(inscription.this, Home.class );
                    startActivity(intent1);
                }

            }
        });

    }



}

