package com.fifa.restaurant.controleur;

import com.fifa.restaurant.outils.SQLiteDataBaseHelper;

public class AccesLocal { //pour accéder à la base de données locale
// classe va enregistrer les différents utilisateurs

    //propriétés

    private String nomBase = "fifaBDD.sqlite"; // nom BDDD
    private Integer versionBase = 1;
    private SQLiteDataBaseHelper accesBD; //accès à la classe
   // private SQLiteDatabase bd; // pour créer des canaux pour écrire ou lire dans BDD

    /**
     * Constructeur
     * @param contexte
     */
   // public AccesLocal(Context contexte) { // instancie la classe qui va remplir accesBD
     //   accesBD = new SQLiteDataBaseHelper(contexte, nomBase,null, versionBase);
    //}

    /**
     * Ajout d'un Utilisateur dans BDD
     * @param utilisateurs
     */
   /* public void ajout(Utilisateurs utilisateurs) { // ne retourne rien et reçoit un profil en paramètre
        bd = accesBD.getWritableDatabase(); //pour écrire dans BDD
        //reqête à partir de l'objet utilisateurs qui fait un insert , en précisant les champs à insérer'
        String req = "insert into utilisateurs(id, nom, prenom, telephone, mail) values ";
        req += "(\""+utilisateurs.getID()+"\",\""+utilisateurs.getNOM()+"\",\""+utilisateurs.getPRENOM()+"\","+utilisateurs.getTELEPHONE()+",\""+utilisateurs.getMAIL()+"\")";


        bd.execSQL(req); //éxécution de la requête SQL sur BDD
    }
    */
    /**
     *  Récupération du dernier profil utilisateur dans la BDD
     */
  /* public Utilisateurs recupDernier() {
        bd = accesBD.getReadableDatabase(); // pour lire dernier profil utilisateur enregistré dans la BDD
        Utilisateurs utilisateurs = null; // contienra le dernier utilisateur récupéré
        String req = "select * from Utilisateurs"; // récupère tous les profils

        //utiliser Cursor quand on veut travailler avec le résultat d'une reqête "select"
        Cursor curseur = bd.rawQuery(req, null); //lis résultats ligne par ligne

        curseur.moveToLast(); // metre curseur sur la dernière ligne (possibilité de mettre curseur sur différentes lignes)
        if (!curseur.isAfterLast()) { // s'il y a un utilisateur dans la table : on le récupère, 5 infos à récupérer
            String Id = curseur.getString(0);
            String Nom = curseur.getString(1);
            String Prenom= curseur.getString(2);
            Integer Telephone = curseur.getInt(3);
            String Mail = curseur.getString(4) ;
            utilisateurs = new Utilisateurs(Id, Nom, Prenom, Telephone, Mail );
        }
        curseur.close(); //on ferme curseur (canal d'accès d'informations à la BDD)
        return utilisateurs; // récupérer l'utilisateur lorsqu'on on va appeller le recupDernier() dans la classe Controle
    }*/

}
