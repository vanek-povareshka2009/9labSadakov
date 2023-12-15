package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Wolf[] wolfAray = {
                new Wolf("Volk11", 12.6, 200, Wolf.Habihat.FOREST),
                new Wolf("Volt", 10, 250, Wolf.Habihat.SEMIDESERT),
                new Wolf("Volfram", 9, 340, Wolf.Habihat.TUNDRA)
        };
        Fish fish = new Fish("123456789101", 6, 700 , Fish.Habihat.FOREST);

        Annotation.createTable(Fish.class);
        Annotation.insertIntoTable(fish);

        Annotation.createTable(Wolf.class);
        for (Wolf wolf : wolfAray) {
            Annotation.insertIntoTable(wolf);
        }
    }
}