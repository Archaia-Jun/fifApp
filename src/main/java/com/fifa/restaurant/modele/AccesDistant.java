package com.fifa.restaurant.modele;

import android.util.Log;

import com.fifa.restaurant.outils.AccesHTTP;
import com.fifa.restaurant.outils.AsyncResponse;

import org.json.JSONArray;

// classe qui se connecte au serveur, il lui faut l'adresse du serveur, on passe par une constante
public class AccesDistant implements AsyncResponse {

    /* constante de classe, adresse du serveur,
   faire : cmd, ipconfig, Adresse IPv4: 192.168.0.20, sur un vrai serveur distant sur internet il faudra enlever cette adresse
   adresse complète du fichier qui sera éxécuté sur le serveur distant
     */

    private static final String SERVERADDR = "http://192.168.0.20/fifa/serveurfifa.php";

    public AccesDistant() {
        super();
    }


    /**
     * Retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
    //dans cette méthode on va pouvoir utiliser les informations récupérées du serveur distant

        // avec Log.d() on affiche le contenu de ce que nous retourne le serveur, on peut voir les ereurs côté PHP
        Log.d("serveur","**********************************"+output);
        // découpage du message reçu avec "%"
        String[] message = output.split("%"); // découpage de la chaîne par rapport au charactère "%"
        //dans message[0] : soit "enreg", soit "dernier", soit "Erreur !"
        //dans message[1] : reste du message

        // s'il y a 2 cases
        if (message.length>1) {
            if (message[0].equals("enreg")){
                Log.d("enreg", "**********************************"+message[1]);

            } else {
                if (message[0].equals("dernier")) {
                    Log.d("dernier", "**********************************"+message[1]);

                } else {
                    if (message[0].equals("Erreur !")) {
                        Log.d("Erreur !", "**********************************"+message[1]);

                    }
                }
            }
        }

    }
    /*la méthode envoi() ne retourne rien mais envoi 2 operations vers le serveur (voir serveurfifa.php),

   on a besoin de savoir quelle opération et quelles données on envoi
   */
    public void envoi(String operation, JSONArray lesDonneesJSON){ //transfert d'information vers le serveur distant se font en format JSON
        AccesHTTP accesDonnees = new AccesHTTP();
   //      lien de délégation, voir description en fin de code
        accesDonnees.delegate = this;
      //   délégation AccesDistant / AccessHTTP, voir description après le code

    // ajout paramètres
    accesDonnees.addParam("operation", operation);
    /* paramètres:
    - "operation", va être récupéré dans le code PHP, (PHP attend le mot "operation" en texte)
    -   operation, envoi du contenu de operation (soit "enreg" ou "dernier")
    */

   accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());

    // appel au serveur, on lui envoi l'url
        //appel méthode execute() de la classe mère AsyncTask, qui fait une traitement , qui ensuite appel doInBackGround()
        accesDonnees.execute(SERVERADDR); //adresse du fichier auquel on va accéder depuis le serveur


    }
}

/*
        Principe d'une délégation (forme d'association autre que l'héritage):

        Délégation: Un Objet peut faire appel à un autre Objet
                       içi cela permet un lien entre en Thread et une classe qui n'est pas un Thread (AccesDistant n'est pas un Thread, AccesHTTP est un Thread).

        Héritage: Un Objet peut etre créé à partir du "moule" d'un autre Objet


        Avec l'objet accesDonnees qui est de type AccessHTTP :
         on accède à la propriété "delegate" et on lui envoi l'instance d'accès distant actuel (de la classe dans laquelle on se trouve actuellement),
         Dans AccesHTTP, delegate contiendra une instance d'AccesDistant,
         et dans méthode onPostExecute(), on prend l'objet actuel d'accesDistant et on appel méthode processFinish() d'AccesDistant,

         Ensuite dans AccesDistant: au retour du serveur, méthode processFinish() d'AccesDistant va être éxécuté

         Cela est possible grâce à Interface AsyncResponse:
          objet delegate a été déclaré de type AsyncResponse (dans AccesHTTP) et AccesDistant implémente l'interface AsyncResponse.

         */
