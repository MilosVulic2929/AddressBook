package se201.projekat.dao;

import java.util.HashMap;

/**
 * TODO INFO za vulica: ovde imamo Factory + Flyweight pattern, tako da imamo dosta patterna vise nam ne treba
 */
public class DaoFactory {

    private static final HashMap<Class<?>, IDao> instanceMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends IDao> T create(Class<T> clazz) {
        if (instanceMap.containsKey(clazz)) {
            return (T) instanceMap.get(clazz);
        } else if (clazz.equals(AddressDao.class)) {
            AddressDao addressDao = new AddressDao();
            instanceMap.put(clazz, addressDao);
            return (T) addressDao;
        } else if (clazz.equals(GroupDao.class)) {
            GroupDao groupDao = new GroupDao();
            instanceMap.put(clazz, groupDao);
            return (T) groupDao;
        } else if(clazz.equals(PersonDao.class)){
            PersonDao personDao = new PersonDao();
            instanceMap.put(clazz, personDao);
            return (T) personDao;
        } else if(clazz.equals(ContactDao.class)){
            ContactDao contactDao = new ContactDao();
            instanceMap.put(clazz, contactDao);
            return (T) contactDao;
        }
        throw new IllegalArgumentException("That argument is not supported");
    }
}
