package com.example.mystade;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

            private String TAG = MainActivity.class.getSimpleName();
            public EditText login ;
           public  EditText password ;
            Button loginB ;
            String key ,value ;
            boolean connection = false ;
            static String lgn ;

            ArrayList<HashMap<String, String>> usersList;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.login);

                usersList = new ArrayList<>();
                login =  findViewById(R.id.login);
                password =  findViewById(R.id.password);
                loginB =  findViewById(R.id.loginB);
                loginB.setOnClickListener(this);

                new GetUsers().execute();
            }

            private class GetUsers extends AsyncTask<Void, Void, Void> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

                }

                @Override
                protected Void doInBackground(Void... arg0) {
                    HttpHandler sh = new HttpHandler();
                    // Making a request to url and getting response
                    String url = "http://192.168.43.151:8080/android/connexion2.php";
                    String jsonStr = sh.makeServiceCall(url);

                   Log.e(TAG, "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                          //  JSONObject jsonObj = new JSONObject(jsonStr);

                            // Getting JSON Array node
                           // JSONArray users = jsonObj.getJSONArray("users");
                            JSONArray users = new JSONArray(jsonStr);

                            // looping through All Contacts
                            for (int i = 0; i < users.length(); i++) {

                                JSONObject user = users.getJSONObject(i);

                                String login = user.getString("login");
                                String mdp = user.getString("mdp");



                                // tmp hash map for single contact
                                HashMap<String, String> u = new HashMap<>();

                                // adding each child node to HashMap key => value
                                u.put(login, mdp);
                              //  u.put("mdp", mdp);


                                // adding contact to contact list
                               usersList.add(u);
                            }
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    } else {
                        Log.e(TAG, "Couldn't get json from server.");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Couldn't get json from server. Check LogCat for possible errors!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);


                }
            }

    @Override
    public void onClick(View view) {
                int i = view.getId() ;
        if (i == R.id.loginB) {

            String lg = login.getText().toString() ;
            String mdp = password.getText().toString();

            for (HashMap<String, String> map : usersList)
                for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                    key =mapEntry.getKey() ;
                    value = mapEntry.getValue();
                     if (key.equals(lg) && value.equals(mdp)) {
                         this.lgn = lg ;
                         startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                    else {
                        login.setText("");
                        password.setText("");
                         Toast.makeText(MainActivity.this,"Login ou mot de passe incorrecte!",Toast.LENGTH_LONG).show();

                     }
                }

        }
        if (i == R.id.signUp) {
            Intent intent = new Intent(MainActivity.this, inscription.class );
            startActivity(intent);
        }
    }
}