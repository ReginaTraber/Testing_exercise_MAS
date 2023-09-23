package ch.hsr.testing.unittest.assertions.lotr;


import org.hamcrest.*;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ch.hsr.testing.unittest.assertions.lotr.LotrActorsTest.ContainsOnlySpecifiedRacesMatcher.containsOnlySpecifiedRaces;
import static ch.hsr.testing.unittest.assertions.lotr.Race.*;

class LotrActorsTest {

    private LotrActors lotrActors = new LotrActors();
    private TolkienCharacter frodo;
    private TolkienCharacter sauron;

    @BeforeEach
    void setup() {
        Optional<TolkienCharacter> frodoOpt = lotrActors.getCharactersByName("Frodo");
        Optional<TolkienCharacter> sauronOpt = lotrActors.getCharactersByName("Sauron");

        Assumptions.assumeTrue(frodoOpt.isPresent());
        Assumptions.assumeTrue(sauronOpt.isPresent());

        frodo = frodoOpt.get();
        sauron = sauronOpt.get();


    }

    // Explanation: https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
    @Test
    void useOldFashionedAssertions() {
        org.junit.jupiter.api.Assertions.assertEquals("Frodo", frodo.getName());
        org.junit.jupiter.api.Assertions.assertEquals(33, frodo.getAge());
    }


    // Tutorial: http://www.vogella.com/tutorials/Hamcrest/article.html
    @Test
    void useHamcrestMatchers() {
        org.hamcrest.MatcherAssert.assertThat(frodo.getName(), org.hamcrest.Matchers.equalTo("Frodo"));
        org.hamcrest.MatcherAssert.assertThat(frodo.getAge(), org.hamcrest.Matchers.equalTo(33));
    }

    // Overview: http://joel-costigliola.github.io/assertj/
    @Test
    void useAssertjMatchers() {

        org.assertj.core.api.Assertions
                .assertThat(lotrActors.getFellowshipOfTheRing())
                .hasSize(9)
                .contains(frodo)
                .doesNotContain(sauron);

        org.assertj.core.api.Assertions.assertThat(frodo.getName())
                .isNotEmpty()
                .startsWith("F")
                .isEqualTo("Frodo");
    }

    // Tutorial: http://www.baeldung.com/hamcrest-custom-matchers
    @Test
    void useCustomMatcher() {

        MatcherAssert.assertThat(lotrActors.getFellowshipOfTheRing(),
                containsOnlySpecifiedRaces(Arrays.asList(HOBBIT, DWARF, MAN, ELF, WIZARD)));

    }

    public static class ContainsOnlySpecifiedRacesMatcher
            extends TypeSafeDiagnosingMatcher<List<TolkienCharacter>> {

        List<Race> races;

        ContainsOnlySpecifiedRacesMatcher(List<Race> races) {
            this.races = races;
        }

        @Override
        protected boolean matchesSafely(List<TolkienCharacter> characters, Description mismatchDescription) {
            Set<Race> undesiredRaces = getUndesiredRaces(characters);
            mismatchDescription.appendText(" contains characters of additional race(s)").appendValue(undesiredRaces);
            return characters.stream()
                    .allMatch(character -> races.contains(character.getRace()));
        }

        private Set<Race> getUndesiredRaces(List<TolkienCharacter> characters) {
            return characters.stream()
                            .filter(character -> !races.contains(character.getRace()))
                            .map(TolkienCharacter::getRace)
                            .collect(Collectors.toSet());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(" contains only characters of races " + races);
        }

        @Factory
        static Matcher<List<TolkienCharacter>> containsOnlySpecifiedRaces(List<Race> races) {
            return new ContainsOnlySpecifiedRacesMatcher(races);
        }

    }
}