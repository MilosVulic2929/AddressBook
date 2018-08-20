package se201.projekat.models;

public enum EmailExtension {

    YAHOO("@yahoo.com"),
    GMAIL("@gmail.com"),
    OUTLOOK("@hotmail.com"),
    ICLOUD("@icloud.com"),
    OTHER("");

    private String name;

    EmailExtension(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
