package se201.projekat.models;

import se201.projekat.dao.Entity;

import java.time.LocalDate;

public class Contact  implements Entity{

    private int id = -1;
    private Person person;
    private String email;
    private String phone;
    private Address address;
    private final LocalDate creationDate;
    // Ako je groupId negativan ne pripada ni jednoj grupi
    private int groupId = -1;

    public Contact(int id, Person person, Address address, String email, String phone, LocalDate creationDate) {
        this.id = id;
        this.person = person;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.creationDate = creationDate;
    }

    public Contact(int id, Person person, Address address, String email, String phone, int groupId, LocalDate creationDate) {
        this.id = id;
        this.person = person;
        this.email = email;
        this.phone = phone;
        this.groupId = groupId;
        this.address = address;
        this.creationDate = creationDate;
    }

    public Contact(Person person, Address address, String email, String phone) {
        this.person = person;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.creationDate = LocalDate.now();
    }

    public Contact(Contact toCopy) {
        this.person = new Person(toCopy.person);
        this.address = new Address(toCopy.address);
        this.email = toCopy.email;
        this.phone = toCopy.phone;
        this.creationDate = toCopy.creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean belongToGroup() {
        return groupId != -1;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return id == contact.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return person.getFirstName() + " " + person.getLastName()
                + ", " + email;
    }
}
