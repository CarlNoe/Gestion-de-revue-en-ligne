package methode.metier;

import java.util.Objects;

public class Periodicite {

    private int id;
    private String libelle;

    public Periodicite(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Periodicite)) return false;
        Periodicite that = (Periodicite) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }



    @Override
    public String toString() {
        return id +" " + libelle ;
    }
}
