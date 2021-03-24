package fr.itix.petstore;

public class Pet {
    public int id;
    public String name;
    public String tag;

    public Pet() {
    }

    public Pet(int id, String name, String tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
    }
}