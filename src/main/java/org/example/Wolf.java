package org.example;

//@Table(title = "wolf")
public class Wolf {
    public enum Habihat {
        SEMIDESERT, TUNDRA, FOREST
    }

    public Wolf(String name, double age, int maxRunDistance,Habihat habihat) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.habihat = habihat;
    }
    @Column
    private String name;

    @Column
    private double age;

    @Column
    private int maxRunDistance;

    @Column
    private Habihat habihat;

}
