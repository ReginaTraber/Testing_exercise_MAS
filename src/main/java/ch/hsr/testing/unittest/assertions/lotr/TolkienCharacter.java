package ch.hsr.testing.unittest.assertions.lotr;

public class TolkienCharacter {
    String name;
    Race race;
    int age;

    public TolkienCharacter(String name, Race hobbit, int age) {
        this.name = name;
        race = hobbit;
        this.age = age;
    }

    public Race getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
