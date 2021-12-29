package com.fifa.restaurant.modele;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.R;


// fichier et méthode onCreat() généreer automatiquement par AndroidStudio

public class MainActivity extends AppCompatActivity {

   // SQLiteDataBaseHelper db;

    // déclaration des variables

   private TextView mWelcolmeText; // variables membres mWelcolmeText, mOrderButton...
   private Button mOrderButton;
   private Button mBookingButton;
   private Button mAccountButton;
   private Button mConnectButton;
   private Button mPartnerButton;
   private Button mPartnerSearchButton;
   private Button mWebSiteButton;
   private Button b_browser;
   private Button mDataButton;

   /*
   private RadioButton mBrochettesRadioButton;
   private RadioButton mGambasRadioButton;
   private RadioButton mAccrasRadioButton;
   private RadioButton mAvocatCrevettesRadioButton;

   private EditText mId;
   private EditText mFullName;
   private EditText mFirstName;
   private EditText mPhone;
   private EditText mMail;
   private Controle controle;

*/


  //  @SuppressLint("WrongViewCast")
    @Override         // à la création de l'activité: BDD SQLite se créer à l'aide de classe SQLiteDataBaseHelper
    protected void onCreate(Bundle savedInstanceState) { //méthode surchargée appelée dès que l'application est lancée
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // quand l'activité est créee , chargement du fichier activity_main.xml , Ctrl + clique

       // db = new SQLiteDataBaseHelper(this); // ensuite dans classe SQLiteDataBaseHelper.java, ajouter l'insertion dans base de données

        // identification des éléments graphiques et branchement des variables

        /*méthode findViewById() prend en paramètre l'identifiant
         de la vue qu'on cherche, et renvoie la vue correspondante
         */



                mWelcolmeText = (TextView) findViewById(R.id.welcolmeText);
       // la méthode retourne une View, il faut faire un cast
        // View est la super Classe de tt les éléments graphique Android (ex: Button, editText, TextView héritent de View)
       mOrderButton = (Button) findViewById(R.id.activity_main_order_button);
       mBookingButton = (Button) findViewById(R.id.activity_main_booking_button);
       mAccountButton = (Button) findViewById(R.id.activity_main_account_button);
       mConnectButton = (Button) findViewById(R.id.activity_main_connect_button);
       mPartnerButton = (Button) findViewById(R.id.activity_main_partner_button);
       mPartnerSearchButton = (Button) findViewById(R.id.activity_main_partnerSearch_button);
       mWebSiteButton = (Button) findViewById(R.id.activity_website_button);
       // b_browser = (Button)findViewById(R.id.button_browser);
       mDataButton = (Button) findViewById(R.id.rgpd);


       /*
        mAccrasRadioButton = (RadioButton) findViewById(R.id.activity_order_accras_radioButton);
        mAvocatCrevettesRadioButton = (RadioButton) findViewById(R.id.activity_order_avocatCrevettes_radioButton);
        mBrochettesRadioButton = (RadioButton) findViewById(R.id.activity_order_brochettes_radioButton);
        mGambasRadioButton = (RadioButton) findViewById(R.id.activity_order_gambas_radioButton);


        mFullName = (EditText) findViewById(R.id.activity_account_fullName_editText);
        mFirstName = (EditText) findViewById(R.id.activity_account_firstName_editText);
        mId = (EditText) findViewById(R.id.activity_account_id_editText);
        mPhone = (EditText) findViewById(R.id.activity_account_phone_editText);
        mMail = (EditText) findViewById(R.id.activity_account_mail_editText);
        this.controle = Controle.getInstance(this);
        */
        //recupUtilisateurs();

       // au clique des boutons, les pages correspondantes s'affichent
       mOrderButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View orderView) {

               //démarrer une nouvelle activité "orderActivity", lié au contexte "MainActivity", ouvre la nouvelle activité "OrderActivity"
               Intent orderActivityIntent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(orderActivityIntent); // la méthode récupère les informations de l'Intent et démarre l'activité
           }
       });

       mBookingButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View bookingView) {

               Intent bookingActivityIntent = new Intent(MainActivity.this, BookingActivity.class);
               startActivity(bookingActivityIntent);
           }
       });

       mAccountButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View accountView) {

               Intent accountActivityIntent = new Intent(MainActivity.this, AccountActivity.class);
               startActivity(accountActivityIntent);


           }
       });

       mPartnerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View partnerView) {

               Intent partnerActivityIntent = new Intent(MainActivity.this, PartnerActivity.class);
               startActivity(partnerActivityIntent);

           }
       });

        mPartnerSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View partnerSearchView) {

                Intent partnerSearchActivityIntent = new Intent(MainActivity.this, PartnerSearchActivity.class);
                startActivity(partnerSearchActivityIntent);

            }
        });

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View connectView) {

                Intent connectActivityIntent = new Intent(MainActivity.this, ConnectActivity.class);
                startActivity(connectActivityIntent);

            }
        });

        //

        mWebSiteButton.setOnClickListener(new View.OnClickListener() {
             @Override
                public void onClick(View v) {
                    // 1. Appeler une URL web
                    String url = "https://fifaparis.000webhostapp.com/apropos.html";
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                    startActivity(intent);
                }
        });

        mDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View dataView) {
                Intent dataActivityIntent = new Intent(MainActivity.this, DataActivity.class);
                startActivity(dataActivityIntent);

            }
        });
/*
       mValidateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View accountView) {

               Intent accountActivityIntent = new Intent(MainActivity.this, PartnerActivity.class);
               startActivity(accountActivityIntent);

           }
       });
*/



  /*
           private void ecouteValider() {
               ((Button) findViewById(R.id.activity_account_validate_button)).setOnClickListener(new  Button.OnClickListener() {
                   public void onClick(View v){

                       Toast.makeText(MainActivity.this, "Compte créer", Toast.LENGTH_SHORT).show(); //affiche text temporaire
                    // context : MainActivity, le "this" de la classe MainActivity
                   }

                   // setOnclickListener() : permet de redéfinir la méthode onClick(View v) qui est à l'intérieur d'elle même,
                   //   tout ce qui sera dans la méthode onClick(View v) s'éxécutera au clique du bouton Valider



       });


    }
    */

            // Récupération de l'utilisateur s'il a été sérialisé
            
 /*   private void recupUtilisateurs() {
               if (controle.getCompte() != null) {
                   activit


               }
    }
    */
};
    }



/*
1-Création d'une vue, disposition des éléments (TextView, EditText, Button...etc) dans activity_main.xml
    Ajouter référence (@+id/) aux variables
    Référencer variables dans "MainActivity.java" en précisant types et en déclarant "private"
    Branchement des variables
    Pour variables membres : appliquer méthode "setOnClickListener()"
        => Déclarer "Intent" (Pour permettre ouverture nouvelle activité au clique bouton)



    Partie connexion à Base de données :

    1-création 2 fichiers php : fonctions.php et serveurfifa.php

    2- ajout de la ligne suivante dans AndroidManifest.xml pour authoriser Android d'aller sur internet:
     <uses-permission android:name="android.permission.INTERNET"/>

    3-création de la classe AccesHTTP.java pour l'accès à distance à la BDD

    4-création de l'interface AsyncResponse.java pour redéfinir méthode processFinish() qui doit s'exécuter
     à chaque retour du serveur, avec en paramètre l'information qui est revenue du serveur

étapes connexion android à BDD:
 - utilisation de la librairie volley pour faire des requêtes au serveur et récupérer les infos par script php
 - utilisation librairie design com.support
- mettre dans AndroidManifest permission Internet pour Android : <uses-permission android:name="android.permission.INTERNET"/>
_____

 -  MainActivity va appeller "creerUtilisateur" dès qu'on clique sur le bouton "valider", un utilisateur sera créé
    On va ensuite récupérer les informations générées
 */