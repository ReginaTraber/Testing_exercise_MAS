package ch.hsr.testing.unittest.helloworld;

public class MyStringAppender {

    private final StringBuilder builder = new StringBuilder();

    public String append(String toBeAppended) {
        builder.append(toBeAppended);
        return builder.toString();
    }

}
