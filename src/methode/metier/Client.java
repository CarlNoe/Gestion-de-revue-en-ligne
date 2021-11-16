package methode.metier;

import java.util.Objects;

public class Client {
    private String nom;
    private String prenom;
    private String noRue;
    private String ville;
    private String pays;
    private String voie;
    private String code_postal;
    private String adresseComplete;
    private int id_client;

    public Client(String nom, String prenom, String noRue, String ville, String pays, String voie, String code_postal, int id_client) {
        this.nom = nom;
        this.prenom = prenom;
        this.noRue = noRue;
        this.ville = ville;
        this.pays = pays;
        this.voie = voie;
        this.code_postal = code_postal;
        this.id_client = id_client;
    }

    public int getId_client() {
        return id_client;
    }

    @Override
    public String toString() {
        return id_client + " " + prenom + " " + nom;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNoRue() {
        return noRue;
    }

    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }

    public String getVoie() {
        return voie;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNoRue(String noRue) {
        this.noRue = noRue;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id_client == client.id_client && Objects.equals(nom, client.nom) && Objects.equals(prenom, client.prenom) && Objects.equals(noRue, client.noRue) && Objects.equals(ville, client.ville) && Objects.equals(pays, client.pays) && Objects.equals(voie, client.voie) && Objects.equals(code_postal, client.code_postal) && Objects.equals(adresseComplete, client.adresseComplete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, prenom, noRue, ville, pays, voie, code_postal, adresseComplete, id_client);
    }

    public String getAdresseComplete(){
        return adresseComplete = noRue+' ' + ' ' +voie +' '+ ' ' +ville+' '+code_postal +' '+pays;
    }

}
