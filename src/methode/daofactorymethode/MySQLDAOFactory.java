package methode.daofactorymethode;

import methode.daofactory.DAOFactory;
import methode.idao.AbonnementDAO;
import methode.idao.ClientDAO;
import methode.idao.PeriodiciteDAO;
import methode.idao.RevueDAO;
import methode.mysqldao.MySQLAbonnementDAO;
import methode.mysqldao.MySQLClientDAO;
import methode.mysqldao.MySQLPeriodiciteDAO;
import methode.mysqldao.MySQLRevueDAO;


public class MySQLDAOFactory extends DAOFactory {

    @Override
    public AbonnementDAO getAbonnementDAO() {
        return MySQLAbonnementDAO.getInstance();
    }

    @Override
    public ClientDAO getClientDAO() {
        return MySQLClientDAO.getInstance();
    }

    @Override
    public PeriodiciteDAO getPeriodicteDAO() {
        return MySQLPeriodiciteDAO.getInstance();
    }

    @Override
    public RevueDAO getRevueDAO() {
        return MySQLRevueDAO.getInstance();
    }
}
