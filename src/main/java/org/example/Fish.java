package org.example;

@Table(title = "Fish")
public class Fish {
    public enum Habihat {
        SEMIDESERT, TUNDRA, FOREST
    }

    public Fish(String name, double age, int maxRunDistance, Habihat habihat) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.habihat = habihat;
    }
    // Inside the Fish class
    @Column(maxLength = 10)
    private String name;


    @Column
    private double age;

    @Column
    private int maxRunDistance;

    @Column
    private Habihat habihat;

}
