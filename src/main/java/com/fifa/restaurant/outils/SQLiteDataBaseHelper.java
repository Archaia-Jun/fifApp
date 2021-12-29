package com.fifa.restaurant.outils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper { // faire hériter classe de SQLiteOpenHelper, surcharger méthodes onCreate() et onUpgrade()


// popriétés qui permettent de faire la requête de création de BDD

    /*
Création BDD avec une table;
Déclarer constantes de la table qu'on va créer,

            */

    public static final String DATABASE_NAME = "Fifa.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOM";
    public static final String COL_3 = "PRENOM";
    public static final String COL_4 = "TELEPHONE";
    public static final String COL_5 = "MAIL";


    /*
     mettre constructeur avec paramètres;
     */

    // méthode insertData() prend instance de BDD,
    // ContentValues : regroupe toutes les données en paramètres et fait l'insertion dans BDD
    // retourne true / false en fonction du succès de la query
    public boolean insertData(String nom, String prenom, Editable telephone, Editable mail) {
        SQLiteDatabase db = this.getWritableDatabase(); // pour ajouter l'inertion dans BDD
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,nom);
        contentValues.put(COL_3,prenom);
        contentValues.put(COL_4, String.valueOf(telephone));
        contentValues.put(COL_5, String.valueOf(mail));
        long result=db.insert(TABLE_NAME, null, contentValues);
        if(result== -1)
            return false;
        else
            return true;
    } //ensuite, dans MainActivity: implémenter code bouton d'insertion

    public Cursor getAllData() { // récupère toutes les données de BDD
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+TABLE_NAME, null);
        return  result;

        /*
        on retourne les données dans objet Cursor car résulta​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​t d'une query SELECT exécutée.
        Ensuite, dans "PartnerActivity.java" on commence d'abord par instancier notre bouton "Afficher les données" :
         */
    }

    public SQLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /*
    Implémenter le code dans les méthodes surchargées pour que tout fonctionne à l'instanciation de SQLiteDataBaseHelper

     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ,NOM TEXT, PRENOM TEXT, TELEPHONE INTEGER, MAIL TEXT) ");
        // commande SQL pour créer table avec + nom de la table + colonnes
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        // si table existe: la remplacer par nouvelle version + nom de table ,
        // puis appeler onCreate() avec l'ensemble des colonnes adéquates

        // suite: dans MainActivity.java (activité principale), à la création de l'activité: BDD SQLite se créer à l'aide de classe SQLiteDataBaseHelper
    }

}


/*

SQLite : 4 type : TEXT, INTEGER, REAL, NUMERIQUE (pour les dates, il faut les enregistrées au format texte)
 1 - Créaton de la classe
 2 - créatio ndes variables
  */
