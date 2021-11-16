package methode.metier;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Abonnement  {
    private int id_abonnement;
    private LocalDate date_deb;
    private LocalDate date_fin;
    private Client client;
    private Revue revue;


    public Abonnement(int id_abonnement, LocalDate date_deb, LocalDate date_fin, Client client, Revue revue) {
        this.id_abonnement = id_abonnement;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.client = client;
        this.revue = revue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Abonnement)) return false;
        Abonnement that = (Abonnement) o;
        return getId_abonnement() == that.getId_abonnement();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_abonnement());
    }

    public int getId_abonnement() {
        return id_abonnement;
    }

    public void setId_abonnement(int id_abonnement) {
        this.id_abonnement = id_abonnement;
    }

    public LocalDate getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(LocalDate date_deb) {
        this.date_deb = date_deb;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Revue getRevue() {
        return revue;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id_abonnement=" + id_abonnement +
                ", date_deb=" + date_deb +
                ", date_fin=" + date_fin +
                ", id_client=" + client +
                ", id_revue=" + revue +
                '}';
    }
}
