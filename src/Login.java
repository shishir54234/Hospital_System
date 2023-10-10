import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Login {
    // JDBC driver name and database URL
    // static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // static final String DB_URL = "jdbc:mysql://localhost/hs";

    // // Database credentials
    // static final String USER = "root";
    // static final String PASS = "123";
    private static DAO_Factory daofactory = new DAO_Factory();
    // private static final String USER = "root";
    // private static final String PASS = "password";

    public static void Log() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccess = false;

        try {
            // Open a connection

            conn = daofactory.activateConnection();
            Doctor_JDBC d1 = new Doctor_JDBC();
            Receptionist_JDBC r1 = new Receptionist_JDBC();
            r1.setupDB();

            // Display welcome message and prompt user for login credentials

            System.out.print("If you are a patient, enter 1. If you are a doctor, enter any other number:");

            int check = scanner.nextInt();
            scanner.nextLine();

            if (check != 1) {
                // Prompt user for username and password
                loginSuccess = false;
                System.out.println("+------------------------------------+");
                System.out.println("| Welcome to the Doctor's Portal!    |");
                System.out.println("| " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        + "                |");
                System.out.println("+------------------------------------+\n");
                while (!loginSuccess) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    try {
                        // Check if username and password match in the Doctor table
                        String sql = "SELECT * FROM Doctor WHERE username = ? AND password = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);
                        ResultSet rs = preparedStatement.executeQuery();

                        // If there is a match, set loginSuccess to true and display welcome message
                        if (rs.next()) {
                            System.out.println("\n+-----------------------------------------------------------+");
                            System.out.println("| Welcome back, Dr. " + rs.getString("doctorName") + "!");
                            System.out.println("| "
                                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                                    + "                                                   |");
                            System.out.println("+-----------------------------------------------------------+\n");
                            loginSuccess = true;
                            String DocID = rs.getString("doctorID");
                            String dept = rs.getString("department");
                            r1.setupDB();
                            Doctor doc = r1.getDoc(DocID);
                            d1.Start(DocID);
                        } else {
                            System.out.println("\nInvalid username or password. Please try again.");
                        }

                        // Clean-up environment
                        rs.close();
                        preparedStatement.close();
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }

            else {
                System.out.println("+------------------------------------+");
                System.out.println("| Welcome to the Patient's Portal!    |");
                System.out.println("| " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        + "                |");
                System.out.println("+------------------------------------+\n");
                loginSuccess = false;
                while (!loginSuccess) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    try {
                        // Check if username and password match in the Patient table
                        while (true) {
                            String sql = "SELECT * FROM Patient WHERE username = ? AND password = ?";
                            PreparedStatement preparedStatement = conn.prepareStatement(sql);
                            preparedStatement.setString(1, username);
                            preparedStatement.setString(2, password);
                            ResultSet rs = preparedStatement.executeQuery();

                            // If there is a match, set loginSuccess to true and display welcome message
                            if (rs.next()) {
                                System.out.println("Welcome back, " + rs.getString("patientName") + "!");
                                loginSuccess = true;
                                sql = "SELECT * FROM Appointment WHERE patientID = ?";
                                preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setString(1, rs.getString("patientID"));
                                ResultSet appointments = preparedStatement.executeQuery();

                                if (appointments.next()) {
                                    // If the patient has appointments, ask if they want to request a prescription
                                    System.out.println("You already have an appointment scheduled.");
                                    System.out.println("Do you want to request a prescription? (Y/N)");
                                    String input = scanner.nextLine();
                                    if (input.equalsIgnoreCase("Y")) {
                                        sql = "SELECT medicationName, dosage, frequency FROM ElectronicPrescription WHERE patientID = ?";
                                        preparedStatement = conn.prepareStatement(sql);
                                        preparedStatement.setString(1, rs.getString("patientID"));
                                        ResultSet prescriptionRS = preparedStatement.executeQuery();
                                        System.out.println(
                                                "Medication information for " + rs.getString("patientID") + ":");
                                        System.out.println("-----------------------------------------");
                                        while (prescriptionRS.next()) {
                                            System.out.println(
                                                    "Medication Name: " + prescriptionRS.getString("medicationName"));
                                            System.out.println("Dosage: " + prescriptionRS.getString("dosage"));
                                            System.out.println("Frequency: " + prescriptionRS.getString("frequency"));
                                            System.out.println("-----------------------------------------");
                                        }

                                    } else {
                                        // End the program
                                        System.out.println("Thank you for using the system. Goodbye!");
                                    }
                                } else {
                                    // If the patient has no appointments, ask if they want to make one
                                    System.out.println("You currently have no appointments scheduled.");
                                    System.out.println("Do you want to make an appointment? (Y/N)");
                                    String input = scanner.nextLine();
                                    if (input.equalsIgnoreCase("Y")) {
                                        sql = "SELECT * FROM Patient WHERE username = ? AND password = ?";
                                        preparedStatement = conn.prepareStatement(sql);
                                        preparedStatement.setString(1, username);
                                        preparedStatement.setString(2, password);

                                        ResultSet rss = preparedStatement.executeQuery();
                                        if (rss.next()) {
                                            Patient patient = r1.getPat(rss.getString("patientID"));

                                            r1.getDocforAppointment(patient);
                                        }

                                    } else {
                                        // End the program
                                        System.out.println("Thank you for using the system. Goodbye!");
                                    }
                                    scanner.nextLine(); // consume newline character

                                }
                                System.out.println("Do you want to logout? (Y/N)");
                                String input = scanner.nextLine();
                                if (input.equalsIgnoreCase("Y")) {

                                    daofactory.deactivateConnection(null);
                                    Login.Logout();
                                    break;
                                }

                            } else {
                                System.out.println("Invalid username or password. Please try again.");
                            }

                        }

                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                }

                // Clean-up environment
            }
        } catch (

        SQLException se) {
            // Handle errors for JDBC
            // se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    }

    public static void Logout() {
        System.out.println("Logging out...");

    }
}