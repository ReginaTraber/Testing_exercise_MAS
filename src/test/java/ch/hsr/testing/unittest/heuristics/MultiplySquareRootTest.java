package ch.hsr.testing.unittest.heuristics;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class MultiplySquareRootTest {

    @ParameterizedTest(name = "{index} => sqrt({0} * {1}) == {2}")
    @MethodSource("createTestInput")
    void testRomanNumberConverter(String mult1, String mult2, int expectedResult) {
        int actualResult = MultiplySquareRoot.calculate(mult1, mult2);
        MatcherAssert.assertThat(actualResult, Matchers.is(expectedResult));
    }

    private static Stream<Arguments> createTestInput() {
        return Stream.of(


                // INPUT

                // normal
                Arguments.of("1","2",1),

                // falsches Zahlenformat
                Arguments.of("3.1","3",3),
                Arguments.of("3","3.1",3),

                // Ganzzahl in FLoat Notation
                Arguments.of("3.0","3",3),

                // overflow
                Arguments.of(""+ Long.MAX_VALUE,"1",1),


                // CALCULATION

                // overflow
                Arguments.of(""+ Integer.MAX_VALUE,""+ Integer.MAX_VALUE, Integer.MAX_VALUE),

                // Wurzel aus 0
                Arguments.of("3","0",0),


                // Wurzel aus negativ
                Arguments.of("3","-3",42),


                // Mult negativ * negativ
                Arguments.of("-3","-3",3),


                // OUTPUT

                // quadratzahl => nix runden
                Arguments.of("3","3",3),

                // Resultat == 0
                Arguments.of("0","0",0),


                // Keine Quadratzahl => runden => aufrunden
                Arguments.of("3","5",4),

                // Keine Quadratzahl => runden => abrunden
                Arguments.of("5","4",4),



                Arguments.of("4","3",3));
    }

}