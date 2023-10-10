import java.sql.*;
import java.util.*;

public class Register {
    // JDBC driver name and database URL

    // Database credentials

    private static DAO_Factory daofactory = new DAO_Factory();

    public Patient registerPatient() throws Exception {
        Connection conn;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Get patient information from user
            while (true) {
                conn = daofactory.activateConnection();
                System.out.print("Enter patient name: ");
                String patientName = scanner.nextLine();
                System.out.print("Enter patient age: ");
                int age = scanner.nextInt();
                scanner.nextLine(); // consume newline character
                System.out.print("Enter patient gender: ");
                String gender = scanner.nextLine();
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                // Check if username is already taken
                String checkUsernameSql = "SELECT COUNT(*) FROM Patient WHERE username = ?";
                PreparedStatement checkUsernameStatement = conn.prepareStatement(checkUsernameSql);
                checkUsernameStatement.setString(1, username);
                ResultSet checkUsernameResult = checkUsernameStatement.executeQuery();
                checkUsernameResult.next();
                if (checkUsernameResult.getInt(1) > 0) {
                    System.out.println("Username already taken, try again");
                    daofactory.deactivateConnection(null);
                    continue;

                }

                // Generate patient ID
                String patientID = generatePatientID(conn);

                // Insert patient information into database
                String sql = "INSERT INTO Patient (patientID, patientName, age, gender, username, password) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, patientID);
                preparedStatement.setString(2, patientName);
                preparedStatement.setInt(3, age);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, username);
                preparedStatement.setString(6, password);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Patient registered successfully.");
                }
                conn.commit();
                Patient patient = new Patient(patientID, patientName, age, gender);

                daofactory.deactivateConnection(null);
                return patient;
            }
        } catch (SQLException e) {
            System.out.println("Error registering patient: " + e.getMessage());
        }
        return null;
    }

    private String generatePatientID(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Patient";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return "PAT" + String.format("%03d", count + 1);
    }
}