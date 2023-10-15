package ch.hsr.testing.unittest.assertions.lotr;

import org.hamcrest.*;
import org.junit.jupiter.api.Test;

public class EvenTest {

    @Test
    public void evenNumber() {
        MatcherAssert.assertThat(2, EvenMatcher.isEven());
    }

    @Test
    public void oddNumber() {
        MatcherAssert.assertThat(3, Matchers.not(EvenMatcher.isEven()));
    }
}

class EvenMatcher extends TypeSafeDiagnosingMatcher<Integer> {

    @Override
    public void describeTo(Description description) {
        description.appendText("An even number");
    }

    @Override
    protected boolean matchesSafely(Integer item, Description mismatchDescription) {
        mismatchDescription.appendText(" was ").appendValue(item).appendText(" which is an odd number!");
        return item % 2 == 0;
    }

    @Factory
    public static EvenMatcher isEven() {
        return new EvenMatcher();
    }
}
