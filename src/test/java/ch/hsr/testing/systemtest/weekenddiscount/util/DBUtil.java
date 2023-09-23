/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.util;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DBUtil. Utility class for resetting the test data.
 * <p>
 * The user and password should be read from the properties...
 */
public class DBUtil implements Constants {

    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final String DB_URL = "jdbc:hsqldb:hsql://" + HOST + ":9001/broadleaf";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    // Database credentials
    private static final String USER = "SA";
    private static final String PASS = "";

    private static final Log LOG = LogFactory.getLog(DBUtil.class);

    public static void setTestTime(Date date) {

        Connection conn = null;
        try {
            conn = connectToDB();
            Statement stmt = conn.createStatement();
            String sql = "UPDATE BLC_TESTING_TIME SET TESTING_TIME = '"
                    + DATE_FORMAT.format(date) + "'";
            stmt.executeUpdate(sql);
            conn.commit();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

    private static Connection connectToDB() throws SQLException,
            ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;

    }

    private static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException se) {
        }// do nothing
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }

    }

}
