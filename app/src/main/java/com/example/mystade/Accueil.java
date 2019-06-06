package com.example.mystade;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Accueil extends AppCompatActivity {


    static String zone="";
    Button suivant ;
    private RadioGroup radioZoneGroup;
    private RadioButton radioZoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Réservation");

        addListenerOnButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {

        case R.id.accueil:
            Intent intent = new Intent(this, Home.class );
            startActivity(intent);
            return(true);
        case R.id.mesReservations:
            Intent intent2 = new Intent(this, reservation.class );
            startActivity(intent2);
            return(true);
        case R.id.deconnexion:
            Intent intent1 = new Intent(this, MainActivity.class );
            startActivity(intent1);
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }


    public void addListenerOnButton() {

        radioZoneGroup = (RadioGroup) findViewById(R.id.zoneRadio);
        suivant = (Button) findViewById(R.id.suivant1ID);

        suivant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioZoneGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioZoneButton = (RadioButton) findViewById(selectedId);

                zone = radioZoneButton.getText().toString();


                Intent intent = new Intent(Accueil.this, tranche.class );
                intent.putExtra("zone" , zone) ;
                startActivity(intent);

            }

        });

    }




    /*public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.lateralID:
                if (checked)
                    zone = "Lateral";
                    break;
            case R.id.tribunaID:
                if (checked)
                    zone = "Tribuna";
                    break;

            case R.id.golnordID:
                if (checked)
                    zone = "Gol nord";
                    break;

            case R.id.golsudnikeID:
                if (checked)
                    zone = "Gol sud nike";
                    break;
        }
    } */


}
