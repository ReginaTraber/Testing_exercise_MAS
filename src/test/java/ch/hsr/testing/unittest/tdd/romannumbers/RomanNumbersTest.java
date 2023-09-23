package ch.hsr.testing.unittest.tdd.romannumbers;


import ch.hsr.testing.unittest.tdd.romannumbers.RomanNumberConverter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class RomanNumbersTest {

    @ParameterizedTest(name = "{index} => arabic number:{0} => roman number:{1}")
    @MethodSource("createTestInput")
    void testRomanNumberConverter(int arabicNumber, String romanNumber) {
        String actual = RomanNumberConverter.convert(arabicNumber);
        MatcherAssert.assertThat(actual, Matchers.is(romanNumber));
    }

    private static Stream<Arguments> createTestInput() {
        return Stream.of(
                Arguments.of(1, "I"),
                Arguments.of(2, "II"),
                Arguments.of(3, "III"),
                Arguments.of(4, "IV"),
                Arguments.of(5, "V"),
                Arguments.of(6, "VI"),
                Arguments.of(7, "VII"),
                Arguments.of(9, "IX"),
                Arguments.of(10, "X"),
                Arguments.of(11, "XI"),
                Arguments.of(14, "XIV"),
                Arguments.of(15, "XV"),
                Arguments.of(19, "XIX"));
    }
}



//
//
//
//    @Test
//    void convertToRomanNumber() {
//        Assertions.assertThat(RomanNumberConverter.convert(1)).isEqualTo("I");
//        Assertions.assertThat(RomanNumberConverter.convert(2)).isEqualTo("II");
//        Assertions.assertThat(RomanNumberConverter.convert(3)).isEqualTo("III");
//        Assertions.assertThat(RomanNumberConverter.convert(4)).isEqualTo("IV");
//        Assertions.assertThat(RomanNumberConverter.convert(5)).isEqualTo("V");
//        Assertions.assertThat(RomanNumberConverter.convert(6)).isEqualTo("VI");
//        Assertions.assertThat(RomanNumberConverter.convert(7)).isEqualTo("VII");
//        Assertions.assertThat(RomanNumberConverter.convert(8)).isEqualTo("VIII");
//        Assertions.assertThat(RomanNumberConverter.convert(9)).isEqualTo("IX");
//        Assertions.assertThat(RomanNumberConverter.convert(10)).isEqualTo("X");
//    }
//
//


