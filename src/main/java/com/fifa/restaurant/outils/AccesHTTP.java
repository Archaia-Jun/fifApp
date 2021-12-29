package com.fifa.restaurant.outils;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*class AsyncTask est de type Thread, s'éxécute en tâche de fond
Classe de connexion distante HTTP
 */

public class AccesHTTP extends AsyncTask<String, String, String> {

    // propriétés

    private String ret = ""; // information retournée par serveur
    public AsyncResponse delegate = null; // gestion du retour asynchrone, public car on aura besoin qu'il soit accessible depuis l'extérieur
    // par délégation on va éxécuter une méthode à l'extérieur du Thread en passant par une interface

    private  String parametres=""; // paramètres à envoyer en POST au serveur

    //Constructor
    public AccesHTTP() {
        super();
    }

    /**
     * Construction de la chaîne de paramètres à envoyer en POST au serveur
     * @param nom
     * @param valeur
     */

    //ajout des paramètres en post qui vont être envoyés avec l'URL et vont être récupérées par serveur
    public void addParam(String nom, String valeur) {
        try {
            if (parametres.equals("")) {
                // premier paramètre
                parametres = URLEncoder.encode(nom, "UTF-8") + "=" + URLEncoder.encode(valeur, "UTF-8");
            }else{
                // paramètres suivants (séparés par &)
                parametres += "&" + URLEncoder.encode(nom, "UTF-8") + "=" + URLEncoder.encode(valeur, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /*Connexion en tâche de fond dans un Thread séparé
    Méthode appelée par la méthode execute
	 * permet d'envoyer au serveur une liste de paramètres en GET
        @param urls contient l'adresse du serveur dans la case 0 de urls
        @return null
        */

/*
    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHttp = new DefaultHttpClient();
        HttpPost paramCnx = new HttpPost(strings[0]);

        try {
            paramCnx.setEntity(new UrlEncodedFormEntity(parametres));

            HttpResponse reponse = cnxHttp.execute(paramCnx);

            ret = EntityUtils.toString(reponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            Log.d("Ereur encodage", "*********" +e.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("Ereur protocole", "*********" +e.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("Ereur I/O", "*********" +e.toString());
                }
        return null;
    }

*/

   @Override
    protected String doInBackground(String... urls) {// méthode éxécutée par la méthode execute() qui vient de la classe mère AsyncTask
       // pour éliminer certaines erreurs
       System.setProperty("http.keepAlive", "false");
       // objets pour gérer la connexion, la lecture et l'écriture
       PrintWriter writer = null;
       BufferedReader reader = null;
       HttpURLConnection connexion = null;

       try {
           // création de l'url à partir de l'adresse reçu en paramètre, dans urls[0]
           URL url = new URL(urls[0]);
           // ouverture de la connexion
           connexion = (HttpURLConnection) url.openConnection();
           connexion.setDoOutput(true);
           // choix de la méthode POST pour l'envoi des paramètres
           connexion.setRequestMethod("POST");
           connexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
           connexion.setFixedLengthStreamingMode(parametres.getBytes().length);
           // création de la requete d'envoi sur la connexion, avec les paramètres
           writer = new PrintWriter(connexion.getOutputStream());
           writer.print(parametres);
           // Une fois l'envoi réalisé, vide le canal d'envoi
           writer.flush();
           // Récupération du retour du serveur
           reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
           ret = reader.readLine();

       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           // fermeture des canaux d'envoi et de réception
           try{
               writer.close();
           }catch(Exception e){}
           try{
               reader.close();
           }catch(Exception e){}
       }
       return null;
   }




/*
       } catch (UnsupportedEncodingException e) {
           Log.d("Erreur encodage", "****"+e.toString());
       // en cas d'erreur, message e.printStackTrace() étant très détaillé avec chemin de l'erreur,
         //   on personnalise le message d'erreur

        // Log.d permet d'afficher directement un message dans la console
       } catch (ClientProtocolException e) {
           Log.d("Erreur de protocole", "****"+e.toString());

       } catch (IOException e) {
           Log.d("Erreur I/O input/output", "****"+e.toString());

       }

       return null;


        // la méthode s'éxécute en tâche de fond
    }
*/

    @Override
    protected void onPostExecute(String result) { //méthode éxécutée au retour du serveur
        delegate.processFinish(this.ret.toString());
       // appel de la méthode processFinish() en lui envoyant le ret qui contient l'information récupérée

     /*Par la suite une des classes de l'application implémentera l'interface AsyncResponse qui redéfiniera la méthode processFinish(),
      ainsi il sera possible d'exécuter la méthode processFinish() par le delegate, pour éviter l'utilisation succéssive de méthodes doInBackground() pour faire des choses différentes,
      on passe par une interface pour exécuter une méthode en dehors du Thread.
       */
    }

}
