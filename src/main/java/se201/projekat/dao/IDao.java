package se201.projekat.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfejs za Dao klase
 * Sve metode throwaju SQLException da bi posle kod controlera mogli da prikazujemo panel sa porukom greske
 * @param <T>
 */
public interface IDao<T> {

    int insert(T value) throws SQLException;
    T getById(int id) throws SQLException;
    List<T> getAll()  throws SQLException;
    void update(T value) throws SQLException;
    void delete(int id) throws SQLException;
    void delete(T value) throws SQLException;
    void deleteAll();
}
