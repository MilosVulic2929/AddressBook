package se201.projekat.models;

import java.util.List;

public class Group {

    private int id;
    private String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(String name) {
        this.id = -1;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
