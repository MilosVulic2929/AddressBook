package se201.projekat.dao;

import org.junit.Before;
import org.junit.Test;
import se201.projekat.models.Address;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AddressDaoTest {

    private AddressDao addressDao = DaoFactory.create(AddressDao.class);

    private Address address = new Address("Srbija", "Nis", "Neka ulica", "34");

    @Before
    public void setup(){
        addressDao.deleteAll();
    }

    @Test
    public void testAddressInsert() throws SQLException {
        addressDao.insert(address);
        Address address2 = addressDao.getById(1);
        assertEquals(address2, address);
    }

    @Test
    public void testAddressDelete() throws SQLException{
        addressDao.insert(address);
        addressDao.delete(address);
        assertEquals(-1, address.getId());
    }

    @Test(expected = SQLException.class)
    public void testAddressGetByIdThrowsExceptionWhenIdDoesntExist() throws SQLException{
        Address address2 = addressDao.getById(5);
        fail("Didnt throw exception");
    }

}