package ch.hsr.testing.systemtest.weekenddiscount.util;

import org.junit.jupiter.api.Test;

class DBUtilTest {

    @Test
    void testSetTime() {
        DBUtil.setTestTime(DateFactory.createDate(2018, 5, 28, 7, 1, 1));
    }

}