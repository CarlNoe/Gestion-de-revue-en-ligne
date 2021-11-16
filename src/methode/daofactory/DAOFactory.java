package methode.daofactory;

import methode.daofactorymethode.ListeMemoireDAOFactory;
import methode.daofactorymethode.MySQLDAOFactory;
import methode.idao.AbonnementDAO;
import methode.idao.ClientDAO;
import methode.idao.PeriodiciteDAO;
import methode.idao.RevueDAO;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(Persistance cible){

        DAOFactory daoF = null;

        switch (cible) {
            case MYSQL:
                daoF = new MySQLDAOFactory();
                break;
            case ListeMemoire:
                daoF = new ListeMemoireDAOFactory();
                break;
        }
        return daoF;
    }
    public abstract AbonnementDAO getAbonnementDAO();
    public abstract ClientDAO getClientDAO();
    public abstract PeriodiciteDAO getPeriodicteDAO();
    public abstract RevueDAO getRevueDAO();

}

