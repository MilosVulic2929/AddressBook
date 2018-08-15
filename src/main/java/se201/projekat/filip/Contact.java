package se201.projekat.filip;

import se201.projekat.filip.Address;

public class Contact {

    private String email;
    private String phone;
    private Address address;

    public Contact(String email, String phone, Address address) {
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
