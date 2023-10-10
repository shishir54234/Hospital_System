
import java.lang.*;
import java.sql.*;

/*
	Methods to be called in the following order:

	1. activateConnection
	2. 	Any number getDAO calls with any number of database transactions
	3. deactivateConnection 
*/
public class DAO_Factory {

    public enum TXN_STATUS {
        COMMIT, ROLLBACK
    };

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/hs";
    static final String USER = "root";
    static final String PASS = "123";
    Connection dbconnection = null;

    // You can add additional DAOs here as needed
    // StudentDAO studentDAO = null;

    boolean activeConnection = false;

    public DAO_Factory() {
        dbconnection = null;
        activeConnection = false;
    }

    public Connection activateConnection() throws Exception {
        if (activeConnection == true)
            try {
                throw new Exception("Connection already active");
            } catch (Exception e) {
                e.printStackTrace();
            }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbconnection = DriverManager.getConnection(DB_URL, USER, PASS);
            dbconnection.setAutoCommit(false);
            activeConnection = true;
            return dbconnection;

        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        // return dbconnection;
        return dbconnection;

    }

    public void deactivateConnection(TXN_STATUS txn_status) {
        // Okay to keep deactivating an already deactivated connection
        activeConnection = false;
        if (dbconnection != null) {
            try {
                if (txn_status == TXN_STATUS.COMMIT)
                    dbconnection.commit();
                else
                    dbconnection.rollback();

                dbconnection.close();
                dbconnection = null;

                // Nullify all DAO objects
                // studentDAO = null;
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
    }
};
