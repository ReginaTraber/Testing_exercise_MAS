package ch.hsr.testing.unittest.assertions.lotr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LotrActors {

    List<TolkienCharacter> fellowshipOfTheRing = new ArrayList<>();
    List<TolkienCharacter> allCharacters = new ArrayList<>();



    public LotrActors() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", Race.HOBBIT, 33);
        TolkienCharacter sam = new TolkienCharacter("Sam", Race.HOBBIT, 39);
        TolkienCharacter aragorn = new TolkienCharacter("Aragorn", Race.MAN, 143);
        TolkienCharacter boromir = new TolkienCharacter("Boromir", Race.MAN, 37);
        TolkienCharacter legolas = new TolkienCharacter("Legolas", Race.ELF, 921);
        TolkienCharacter merry = new TolkienCharacter("Merry", Race.HOBBIT, 29);
        TolkienCharacter pippin = new TolkienCharacter("Pippin", Race.HOBBIT, 27);
        TolkienCharacter gimli = new TolkienCharacter("Gimli", Race.DWARF, 41);
        TolkienCharacter gandalf = new TolkienCharacter("Gandalf", Race.WIZARD, 830);
        TolkienCharacter sauron = new TolkienCharacter("Sauron", Race.EVIL, 999);
        fellowshipOfTheRing.add(frodo);
        fellowshipOfTheRing.add(sam);
        fellowshipOfTheRing.add(merry);
        fellowshipOfTheRing.add(pippin);
        fellowshipOfTheRing.add(aragorn);
        fellowshipOfTheRing.add(boromir);
        fellowshipOfTheRing.add(legolas);
        fellowshipOfTheRing.add(gimli);
        fellowshipOfTheRing.add(gandalf);

        allCharacters.addAll(fellowshipOfTheRing);
        allCharacters.add(sauron);

    }


    public List<TolkienCharacter> getAllCharacters() {
        return allCharacters;
    }

    public List<TolkienCharacter> getCharactersByRace(Race race) {
        return allCharacters.stream()
                .filter(character -> character.getRace().equals(race))
                .collect(Collectors.toList());
    }


    public Optional<TolkienCharacter> getCharactersByName(String name) {
        return allCharacters.stream()
                .filter(character -> character.getName().equals(name))
                .findFirst();
    }

    public List<TolkienCharacter> getFellowshipOfTheRing() {
        return fellowshipOfTheRing;
    }
}
