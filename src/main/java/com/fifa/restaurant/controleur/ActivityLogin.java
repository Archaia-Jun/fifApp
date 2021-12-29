package com.fifa.restaurant.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.R;
import com.fifa.restaurant.modele.ConnectActivity;
import com.fifa.restaurant.modele.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// pour les partenaires

    public class ActivityLogin extends AppCompatActivity {
        public EditText loginEmailId, logInpasswd;
        Button btnLogIn;
        TextView signup;
        FirebaseAuth firebaseAuth;
        private FirebaseAuth.AuthStateListener authStateListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            firebaseAuth = FirebaseAuth.getInstance();
            loginEmailId = findViewById(R.id.loginEmail);
            logInpasswd = findViewById(R.id.loginpaswd);
            btnLogIn = findViewById(R.id.btnLogIn);
            signup = findViewById(R.id.TVSignIn);
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        Toast.makeText(ActivityLogin.this, "Utilisateur connecté", Toast.LENGTH_SHORT).show();
                        Intent I = new Intent(ActivityLogin.this, UserActivity.class);
                        startActivity(I);
                    } else {
                        Toast.makeText(ActivityLogin.this, "Connectez-vous pour poursuivre", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent I = new Intent(ActivityLogin.this, ConnectActivity.class);
                    startActivity(I);
                }
            });
            btnLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userEmail = loginEmailId.getText().toString();
                    String userPaswd = logInpasswd.getText().toString();
                    if (userEmail.isEmpty()) {
                        loginEmailId.setError("Inscrivez votre email");
                        loginEmailId.requestFocus();
                    } else if (userPaswd.isEmpty()) {
                        logInpasswd.setError("Entrez votre mot de passe!");
                        logInpasswd.requestFocus();
                    } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                        Toast.makeText(ActivityLogin.this, "Champs vides!", Toast.LENGTH_SHORT).show();
                    } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                        firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(ActivityLogin.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(ActivityLogin.this, "Echec", Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(ActivityLogin.this, UserActivity.class));
                                }
                            }
                        });
                    } else {
                        Toast.makeText(ActivityLogin.this, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }