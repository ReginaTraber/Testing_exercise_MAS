package ch.hsr.testing.unittest.tdd.romannumbers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RomanNumberConverter {
    private static final RomanNumber[] ATOMIC_NUMBERS = new RomanNumber[]{
            new RomanNumber("I", 1),
            new RomanNumber("IV", 4),
            new RomanNumber("V", 5),
            new RomanNumber("IX", 9),
            new RomanNumber("X", 10),
            new RomanNumber("XL", 40),
            new RomanNumber("L", 50),
            new RomanNumber("XC", 90),
            new RomanNumber("C", 100),
            new RomanNumber("CD", 400),
            new RomanNumber("D", 500),
            new RomanNumber("CM", 900),
            new RomanNumber("M", 1000)
    };


    public static String convert(int i) {
        StringBuilder sb = new StringBuilder();
        int remainder = i;
        Optional<RomanNumber> biggestPossibleNumberOptional = getBiggestPossibleNumber(remainder);
        while (remainder > 0 && biggestPossibleNumberOptional.isPresent()){
            RomanNumber biggestPossibleNumber = biggestPossibleNumberOptional.get();
            sb.append(biggestPossibleNumber.getRoman());
            remainder -= biggestPossibleNumber.getArabic();
            biggestPossibleNumberOptional = getBiggestPossibleNumber(remainder);
        }
        return sb.toString();
    }

    private static Optional<RomanNumber> getBiggestPossibleNumber(int remainder) {
        List<RomanNumber> romanNumbers = Arrays.asList(ATOMIC_NUMBERS);
        romanNumbers
                .sort((o1, o2) -> Integer.compare(o2.getArabic(), o1.getArabic()));
        return romanNumbers.stream()
                .filter(rn -> rn.getArabic() <= remainder)
                .findFirst();

    }
}
