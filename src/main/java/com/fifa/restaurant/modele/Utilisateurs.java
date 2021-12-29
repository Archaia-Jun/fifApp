package com.fifa.restaurant.modele;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Utilisateurs implements Serializable {

    // propriétés
    private String ID;
    private String NOM;
    private String PRENOM;
    private double TELEPHONE;
    private String MAIL;

    public Utilisateurs() {
        super();
    }


    public Utilisateurs(String ID, String NOM, String PRENOM, double TELEPHONE, String MAIL) {
        this.ID = ID;
        this.NOM = NOM;
        this.PRENOM = PRENOM;
        this.TELEPHONE = TELEPHONE;
        this.MAIL = MAIL;
    }

    public String getID() {return ID;}

    public String getNOM() {
        return NOM;
    }

    public String getPRENOM() {
        return PRENOM;
    }

    public double getTELEPHONE() {
        return TELEPHONE;
    }

    public String getMAIL() {
        return MAIL;
    }

    private void validerCompte() {

    if ((NOM != null) || (PRENOM != null) ) {
        System.out.println(NOM + PRENOM);
    }
    }

    /**
     * Convertion du utilisateur au format JSONArray
     * @return
     */
    public JSONArray convertToJSONArray() {
        List laListe = new ArrayList();
        laListe.add(ID);
        laListe.add(NOM);
        laListe.add(PRENOM);
        laListe.add(TELEPHONE);
        laListe.add(MAIL);

        return new JSONArray(laListe);

    }

}
