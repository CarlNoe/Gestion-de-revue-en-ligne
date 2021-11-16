package methode.dao;

import java.util.List;

public interface DAO<T> {
    public List<T> findAll();
    public T getById(int id);
    public boolean create(T objet);
    public boolean update(T objet);
    public boolean delete(T objet);

}
