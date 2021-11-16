package methode.metier;

import java.util.Objects;

public class Revue {
    private int id_revue;
    private Periodicite periodicite;
    private String description;
    private float tarif_numeros;
    private String titre;
    private String visuel;

    public Revue(int id_revue,Periodicite periodicite, String description, float tarif_numeros, String titre, String visuel ) {
        this.id_revue = id_revue;
        this.description = description;
        this.tarif_numeros = tarif_numeros;
        this.titre = titre;
        this.visuel = visuel;
        this.periodicite = periodicite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revue)) return false;
        Revue revue = (Revue) o;
        return getId_revue() == revue.getId_revue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_revue());
    }

    public int getId_revue() {
        return id_revue;
    }

    public Periodicite getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(Periodicite periodicite) {
        this.periodicite = periodicite;
    }

    public void setId_revue(int id_revue) {
        this.id_revue = id_revue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTarif_numeros() {
        return tarif_numeros;
    }

    public void setTarif_numeros(float tarif_numeros) {
        this.tarif_numeros = tarif_numeros;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getVisuel() {
        return visuel;
    }

    public void setVisuel(String visuel) {
        this.visuel = visuel;
    }

    @Override
    public String toString() {
        return titre ;
    }
}
