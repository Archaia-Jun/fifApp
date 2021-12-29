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
import com.fifa.restaurant.R;
import com.fifa.restaurant.controleur.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    // pour les clients

    private static final String TAG = AccountActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail, inputPhone;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // affiche icône barre d'outils
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPhone = (EditText) findViewById(R.id.phone);
        btnSave = (Button) findViewById(R.id.btn_save);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // obtenir référence au noeud 'users'
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        // stocker titre de l'application sur le nœud 'app_title'
        mFirebaseInstance.getReference("app_title").setValue("Connectez-vous");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(BuildConfig.DEBUG) {

                    Log.e(TAG, "App title updated");
                }

                String appTitle = dataSnapshot.getValue(String.class);

                // mettre à jour titre de la barre d'outils
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if(BuildConfig.DEBUG) {

                    // échec lecture valeur
                    Log.e(TAG, "Failed to read app title value.", error.toException());
                }
            }
        });

        // sauvegarde / MAJ user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String phone = inputPhone.getText().toString();

                // Vérifie si userId existe déja
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, email, phone);
                } else {
                    updateUser(name, email, phone);
                }
            }
        });

        toggleButton();
    }

    // change texte bouton
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Créer un nouveau noeud sous 'users'
     */
    private void createUser(String name, String email, String phone) {
        // TODO
        // recherche de userId par firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        // creation objet user
        User user = new User(name, email, phone);

        //met user dans le noeud 'users' en utilisant userId
        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // Écouteur de modification de données utilisateur
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // vérification user
                if (user == null) {
                    if(BuildConfig.DEBUG) {

                        Log.e(TAG, "User data is null!");
                    }
                    return;
                }

                if(BuildConfig.DEBUG) {

                    Log.e(TAG, "User data is changed!" + user.name + ", " + user.email + ", " + user.phone);
                }

                // Affiche données mises à jour (name, email, téléphone)
                txtDetails.setText(user.name + ", " + user.email + ", " + user.phone);

                // efface les champs
                inputEmail.setText("");
                inputName.setText("");
                inputPhone.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if(BuildConfig.DEBUG) {

                    // Echec de lecture
                    Log.e(TAG, "Failed to read user", error.toException());
                }
            }
        });
    }

    private void updateUser(String name, String email, String phone) {
        // MAJ user via noeuds enfants
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);

        if (!TextUtils.isEmpty(phone))
            mFirebaseDatabase.child(userId).child("phone").setValue(phone);
    }
}
