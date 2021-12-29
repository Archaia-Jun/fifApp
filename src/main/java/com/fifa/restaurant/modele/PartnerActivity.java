package com.fifa.restaurant.modele;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.BuildConfig;
import com.fifa.restaurant.controleur.Partner;
import com.fifa.restaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PartnerActivity extends AppCompatActivity {

    // Pour les partenaires

    private static final String TAG = PartnerActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputCompanyName, inputHeadName, inputMail, inputZipCode, inputCity, inputType;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String partnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);

        // affiche icone barre d'outils
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.txt_partner);
        inputCompanyName = (EditText) findViewById(R.id.company);
        inputHeadName = (EditText) findViewById(R.id.head);
        inputMail = (EditText) findViewById(R.id.mail);
        inputZipCode = (EditText) findViewById(R.id.zipCode);
        inputCity = (EditText) findViewById(R.id.city);
        inputType = (EditText) findViewById(R.id.type);
        btnSave = (Button) findViewById(R.id.btn_save);
        //btnNotification = (Button) findViewById(R.id.notification);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // obtiens reference au noeud 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("partners");

        // stock titre de l'app sur noeud 'app_title'
        mFirebaseInstance.getReference("app_title").setValue("Intégrez le réseau");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(BuildConfig.DEBUG) {
                    Log.e(TAG, "App title updated");
                }

                String appTitle = dataSnapshot.getValue(String.class);

                // MAJ titre barre d'outils
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // échec lecture valeur
                if(BuildConfig.DEBUG) {
                    Log.e(TAG, "Failed to read app title value.", error.toException());
                }
            }
        });

        // Save / MAJ user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputCompanyName.getText().toString();
                String head = inputHeadName.getText().toString();
                String mail = inputMail.getText().toString();
                String zipCode = inputZipCode.getText().toString();
                String city = inputCity.getText().toString();
                String type = inputType.getText().toString();

                // vérifie si partnerId existe
                if (TextUtils.isEmpty(partnerId)) {
                    createPartner(name, head, mail, zipCode, city, type);
                } else {
                    updatePartner(name, head, mail, zipCode, city, type);
                }
            }
        });

        toggleButton();
    }

    // change bouton text
    private void toggleButton() {
        if (TextUtils.isEmpty(partnerId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Creating new partner node under 'partners'
     */
    private void createPartner(String name, String head, String mail, String zipCode, String city, String type) {
        // TODO
        // In real apps this partnerId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(partnerId)) {
            partnerId = mFirebaseDatabase.push().getKey();
        }

        // creation objet user
        Partner partner = new Partner(name, head, mail, zipCode, city, type);

        //met user dans la collection 'users' en utilisant partnerId
        mFirebaseDatabase.child(partnerId).setValue(partner);

        addRegisterChangeListener();
    }

    /**
     * Partner data change listener
     */
    private void addRegisterChangeListener() {
        // Partner data change listener
        mFirebaseDatabase.child(partnerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Partner partner = dataSnapshot.getValue(Partner.class);

                // Check for null
                if (partner == null) {
                    if(BuildConfig.DEBUG) {
                        Log.e(TAG, "Partner data is null!");
                    }
                    return;
                }

                if(BuildConfig.DEBUG) {
                    Log.e(TAG, "Partner data is changed!" + partner.name + ", " + partner.head + ", " + partner.mail + ", " + ", " + partner.zipCode + partner.city + ", " + partner.type);

                }
                // affiche nouvelle infos MAJ
                txtDetails.setText(partner.name + ", " + partner.head + ", " + partner.mail + ", " + ", " + partner.zipCode + partner.city + ", " + partner.type);

                // efface edit text
                inputCompanyName.setText("");
                inputHeadName.setText("");
                inputMail.setText("");
                inputZipCode.setText("");
                inputCity.setText("");
                inputType.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if(BuildConfig.DEBUG) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read partner", error.toException());
                }
            }
        });
    }

    private void updatePartner(String name, String head, String mail, String zipCode, String city, String type) {
        // MAJ partner via noeuds enfants
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(partnerId).child("name").setValue(name);

        if (!TextUtils.isEmpty(head))
            mFirebaseDatabase.child(partnerId).child("head").setValue(head);

        if (!TextUtils.isEmpty(mail))
            mFirebaseDatabase.child(partnerId).child("mail").setValue(mail);

        if (!TextUtils.isEmpty(zipCode))
            mFirebaseDatabase.child(partnerId).child("zip code").setValue(zipCode);

        if (!TextUtils.isEmpty(city))
            mFirebaseDatabase.child(partnerId).child("city").setValue(city);

        if (!TextUtils.isEmpty(type))
            mFirebaseDatabase.child(partnerId).child("type").setValue(type);
    }
}
