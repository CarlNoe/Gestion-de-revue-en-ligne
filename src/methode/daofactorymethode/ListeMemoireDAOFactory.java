package methode.daofactorymethode;

import methode.daofactory.DAOFactory;
import methode.idao.AbonnementDAO;
import methode.idao.ClientDAO;
import methode.idao.PeriodiciteDAO;
import methode.idao.RevueDAO;
import methode.listememoiredao.ListeMemoireAbonnementDAO;
import methode.listememoiredao.ListeMemoireClientDAO;
import methode.listememoiredao.ListeMemoirePeriodiciteDAO;
import methode.listememoiredao.ListeMemoireRevueDAO;


public class ListeMemoireDAOFactory extends DAOFactory {
    @Override
    public AbonnementDAO getAbonnementDAO(){
        return ListeMemoireAbonnementDAO.getInstance();
    }

    @Override
    public ClientDAO getClientDAO() {
        return ListeMemoireClientDAO.getInstance();
    }

    @Override
    public PeriodiciteDAO getPeriodicteDAO() {
        return ListeMemoirePeriodiciteDAO.getInstance();
    }

    @Override
    public RevueDAO getRevueDAO() {
        return ListeMemoireRevueDAO.getInstance();
    }
}
