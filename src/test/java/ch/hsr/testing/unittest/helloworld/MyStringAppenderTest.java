package ch.hsr.testing.unittest.helloworld;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MyStringAppenderTest {
    private MyStringAppender myStringAppender;

    public MyStringAppenderTest() {
        System.out.println("Constructor");
    }

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach");
        myStringAppender = new MyStringAppender();
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll");
    }

    @Test
    void appendNormalString() {
        System.out.println("Test1");
        String aNormalString = "aNormalString";
        String appended = myStringAppender.append(aNormalString);
        Assertions.assertThat(appended).isEqualTo(aNormalString);
    }

    @Test
    void appendEmptyString() {
        System.out.println("Test2");
        String appended = myStringAppender.append("");
        Assertions.assertThat(appended).isEqualTo("");
    }

    @Test
    @Disabled
    void appendMultipleStrings() {
        System.out.println("Test3");
        myStringAppender.append("part1");
        String appended = myStringAppender.append("part2");
        Assertions.assertThat(appended).isEqualTo("part1part2");
    }

}
