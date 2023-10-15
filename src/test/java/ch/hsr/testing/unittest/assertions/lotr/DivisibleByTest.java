package ch.hsr.testing.unittest.assertions.lotr;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.jupiter.api.Test;

public class DivisibleByTest {

    @Test
    public void numberIsDivisible() {
        MatcherAssert.assertThat(16, DivisibleByMatcher.isDivisibleBy(4));
    }

    @Test
    public void numberIsNotDivisible() {
        MatcherAssert.assertThat(17, Matchers.not(DivisibleByMatcher.isDivisibleBy(4)));
    }
}

class DivisibleByMatcher extends TypeSafeDiagnosingMatcher<Integer> {

    private int by;

    public DivisibleByMatcher(int by) {
        this.by = by;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("An number divisible by ").appendValue(by);
    }

    @Override
    protected boolean matchesSafely(Integer item, Description mismatchDescription) {
        mismatchDescription
                .appendText(" was ")
                .appendValue(item)
                .appendText(" which is not divisible by ")
                .appendValue(by)
                .appendText("!");
        return item % by == 0;
    }

    @Factory
    public static DivisibleByMatcher isDivisibleBy(int by) {
        return new DivisibleByMatcher(by);
    }
}
