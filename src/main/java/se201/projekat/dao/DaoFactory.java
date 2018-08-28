package se201.projekat.dao;

import java.util.HashMap;

/**
 * Factory pattern
 * Kreira odredjenu instancu IDao interfejsa na osnovu prosledjenog parametra
 */
public class DaoFactory {

    @SuppressWarnings("unchecked")
    public static <T extends IDao> T create(Class<T> clazz) {
        if (clazz.equals(AddressDao.class)) {
            return (T)new AddressDao();
        } else if (clazz.equals(GroupDao.class)) {
            return (T) new GroupDao();
        } else if(clazz.equals(PersonDao.class)){
            return (T) new PersonDao();
        } else if(clazz.equals(ContactDao.class)){
            return (T) new ContactDao();
        }
        throw new IllegalArgumentException("That argument is not supported");
    }
}
