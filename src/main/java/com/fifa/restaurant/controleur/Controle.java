package com.fifa.restaurant.controleur;

//pattern Singleton, pour connexion unique

import android.content.Context;

//import com.fifa.restaurant.modele.AccesDistant;
import com.fifa.restaurant.modele.AccesDistant;
import com.fifa.restaurant.modele.Utilisateurs;

import org.json.JSONArray;

public final class Controle { //déclarer final: une autre classe ne pourra pas hérité de Controle

private static Controle instance = null; //propriété qui va servir à la mémorisation de l'instance
    // static: la propriété va être accessible directement par la classe et non par une instance de la classe

private static Utilisateurs utilisateurs; // création d'Utilisateurs

//constructeur déclaré private, on ne pourra pas instancier d'autres objets

    private static String nomFic = "saveutilisateurs";

   // private  static  AccesLocal accesLocal;

    private static AccesDistant accesDistant;

    /**
     * Constructeur private
     */
    private Controle() {
        super();
    }

    // méthode public (accessible de l'extérieur, pour remplir l'instance en une seule fois), static (accessible par la classe)
        // final (on ne peut la redéfinir), Controle (retourne un Objet de type Controle)

    /**
     * Création de l'instance
     * @return instance
     */
    public static final Controle getInstance(Context contexte) {
        //quand on veut générer un controleur, au lieu de faire "new", on appel méthode getInstance()
        //qui va soit générer un nouveau contrôleur s'il n'en existe pas, soit créer un nouveau contrôleur

        if (Controle.instance == null) { // si instance n'existe pas encore, elle est créee
            Controle.instance = new Controle();

            //récupération de BDD
          // accesLocal = new AccesLocal(contexte); // dans objet accesLocal qu'on vient de déclarer: on le créer en lui affectant une instancie de la classe AccesLocal avec un contexte en parametre
           //utilisateurs = accesLocal.recupDernier(); // ensuite dans l'objet utilisateur, on lui affecte l'appel de la méthode recupDernier() de la classe AccesLocal

            accesDistant = new AccesDistant();

            accesDistant.envoi("dernier", new JSONArray()); // on evoi une demande de récupération
        } //sinon rien ne se produit et on retourne l'instance créée précédement

        // depuis l'extérieur on appel getInstance(), soit on retourne l'instance qui vient d'être créée,
        // soit on retourne l'instance crééee précédement
        return Controle.instance;
    }

    //méthode réceptionne toutes les informations qui viennent de la vue

    /**
     * Création de l'utilisateur
     * @param ID
     * @param NOM
     * @param PRENOM
     * @param TELEPHONE
     * @param MAIL
     */
    public void creerUtilisateurs(String ID, String NOM, String PRENOM, double TELEPHONE, String MAIL) {
        //création de l'utilisateur
        utilisateurs= new Utilisateurs(ID, NOM, PRENOM, TELEPHONE, MAIL);

      //  accesLocal.ajout(utilisateurs); //enregistrer utilisateur


        // on réétulise l'envoi "accesDistant.envoir()" pour communiquer avec serveur
        // envoi de l'ordre "enreg" au serveur, de l'utilisateur converti au format JSONArray (va être lu par fichier PHP)
        accesDistant.envoi("enreg", utilisateurs.convertToJSONArray());
   }

    // MainActivity va appeller "creerUtilisateur" dès qu'on clique sur le bouton "valider", un utilisateur sera créé
    //On va ensuite récupérer les informations générées

    // méthode public qui va retourner l'utilisateur

    /**
     * Récupération compte de utilisateur
     * @return compte
     */
    public String getCompte() {
        return utilisateurs.toString();
    }

   /*
    public String getMessage() {
        return utilisateurs.getMessage();
    }
    */

}
