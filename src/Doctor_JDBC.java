
// So implement in jdbc that we can view every patients record passing their id 
// create table HealthRecords(
//     summary varchar(30),
//     -- Unique ID for each medication
//     isCured varchar(30),
//     -- Name of the medication
//     currentStatus varchar(30),
//     -- Dosage of the medication
//     ID varchar(30),
//     -- ID of the prescription associated with this medication
//     constraint pk_healthrecords PRIMARY KEY (ID) -- Set medicationID
// );
import java.sql.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Scanner;
//import java.util.UUID;

//import com.mysql.cj.sasl.ScramSha1SaslClient;

// import java.util.ArrayList;
// import java.util.List;

public class Doctor_JDBC implements DoctorDAO {
    // static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // static final String DB_URL = "jdbc:mysql://localhost/hs";
    private static DAO_Factory daoFactory = new DAO_Factory();
    // Database credentials
    // static final String USER = "root";
    // static final String PASS = "password";
    String doctorID;
    String doctorName;
    String dept;

    public void RequestSalary(String doctorID) throws Exception {

        // Establish JDBC connection
        System.out.println("Requesting Salary...");

        // Establish JDBC connection
        PreparedStatement stmt = null;
        double salary = 0.0;
        Connection conn = daoFactory.activateConnection();

        try {
            // Prepare SQL statement with parameterized query
            String sql = "SELECT Salary FROM HospitalStaff WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, doctorID);

            // Execute query and retrieve result set
            ResultSet rs = stmt.executeQuery();

            // Extract salary from result set
            if (rs.next()) {
                salary = rs.getDouble("Salary");
                System.out.println("Your salary is: $" + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        daoFactory.deactivateConnection(null);

    }

    public boolean updateHealthRecord(String recordID, String summary, String isCured, String currentStatus)
            throws Exception {
        Connection conn = daoFactory.activateConnection();
        PreparedStatement stmt = conn
                .prepareStatement("UPDATE HealthRecords SET summary=?, isCured=?, currentStatus=? WHERE ID=?");
        stmt.setString(1, summary);
        stmt.setString(2, isCured);
        stmt.setString(3, currentStatus);
        stmt.setString(4, recordID);
        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated);
        conn.commit();
        daoFactory.deactivateConnection(null);

        return (rowsUpdated == 1);
    }

    public boolean addHealthRecord(String patientID, String summary, String isCured, String currentStatus)
            throws Exception {
        // Generate a unique record ID
        // String recordID = UUID.randomUUID().toString();
        Connection conn = daoFactory.activateConnection();
        // Prepare the SQL statement
        PreparedStatement stmt = conn
                .prepareStatement("INSERT INTO HealthRecords(ID, summary, isCured, currentStatus) VALUES (?, ?, ?, ?)");
        stmt.setString(1, patientID);
        stmt.setString(2, summary);
        stmt.setString(3, isCured);
        stmt.setString(4, currentStatus);
        // stmt.setString(6, doctorID);

        // Execute the statement
        int rowsInserted = stmt.executeUpdate();
        conn.commit();
        daoFactory.deactivateConnection(null);
        // Return true if the record was added successfully
        return (rowsInserted == 1);
    }

    public void Doctor_getHealthRecord(String patientId) throws Exception {
        try {

            Connection conn = daoFactory.activateConnection();

            // Step 2: Retrieve the health records
            String sql = "SELECT * FROM HealthRecords";
            if (patientId != "") {
                sql += " WHERE ID=?";
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            if (patientId != "") {
                stmt.setString(1, patientId);
            }
            ResultSet rs = stmt.executeQuery();

            // Step 3: Display the health records
            System.out.println("+----------------------------------------------------------------------+");
            System.out.println("|                                Health Records                        |");
            System.out.println("+----------------------------------------------------------------------+");
            System.out.println("| ID        | Current Status           | Summary                        |");
            System.out.println("+-----------+--------------------------+--------------------------------+");
            while (rs.next()) {
                String summary = rs.getString("summary");
                String patient_id = rs.getString("ID");
                String currentStatus = rs.getString("currentStatus");
                System.out.printf("| %-10s| %-25s| %-30s|\n", patient_id, currentStatus, summary);
            }
            System.out.println("+--------------------------------------------------------------------+");

            // Step 4: Close the database connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
        daoFactory.deactivateConnection(null);

    }
    // return appointments;

    public boolean addPrescription(String prescriptionID, String patientID, String doctorID,
            String medicationName, String dosage, String frequency) throws Exception {
        // Generate a unique prescription ID
        // String prescriptionID = UUID.randomUUID().toString();
        Connection conn = daoFactory.activateConnection();

        // Prepare the SQL statement
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO ElectronicPrescription(prescriptionID, patientID, doctorID, medicationName, dosage, frequency) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, prescriptionID);
        stmt.setString(2, patientID);
        stmt.setString(3, doctorID);
        stmt.setString(4, medicationName);
        stmt.setString(5, dosage);
        stmt.setString(6, frequency);

        // Execute the statement
        int rowsInserted = stmt.executeUpdate();
        conn.commit();
        daoFactory.deactivateConnection(null);

        // Return true if the prescription was added successfully
        return (rowsInserted == 1);
    }

    public void viewAppointments(String doctorID) throws Exception {
        // List<Appointment> appointments = new ArrayList<>();
        Connection conn = daoFactory.activateConnection();

        PreparedStatement stmt = conn
                .prepareStatement("SELECT * FROM Appointment WHERE doctorID = ?");
        stmt.setString(1, doctorID);
        ResultSet rs = stmt.executeQuery();
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------");
        while (rs.next()) {
            String appointmentID = rs.getString("appointmentID");
            Timestamp appointmentDate = rs.getTimestamp("Appointmentdate");
            String patientID = rs.getString("patientID");
            System.out.println("The Appointment Id is " + appointmentID + " The date of the Appointment is "
                    + appointmentDate + " The ID for the patient is " + patientID + "\n");
            // Appointment appointment = new Appointment(appointmentID, appointmentDate,
            // doctorID, patientID);
            // appointments.add(appointment);
        }
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------");

        // return appointments;
        daoFactory.deactivateConnection(null);

    }

    public void Start(String DoctorID) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello Doctor !");
        System.out.println(DoctorID);
        System.out.println("So What would you want to do doc?");
        while (true) {
            System.out.println("|----------------------------------------------------|");
            System.out.println("| 1. View Health record of a Patient |");
            System.out.println("| 2. View your Appointment |");
            System.out.println("| 3. Make A prescription for a patient |");
            System.out.println("| 4. Request for your salary |");
            System.out.println("| 5. Add a health record |");
            System.out.println("| 6. Update a health record |");
            System.out.println("| 7. MakeBill |");
            System.out.println("| 8. Exit |");
            System.out.println("|----------------------------------------------------|");
            Integer choice = sc.nextInt();
            if (choice == 1) {
                sc.nextLine(); // consume any leftover characters

                System.out.print(
                        "So do you want some specific patient or do you want to view all patient records, enter 'all' if u want to see everyone else any other word:");
                String ID = sc.nextLine();
                if (ID.equals("all")) {
                    Doctor_getHealthRecord("");
                } else {
                    Doctor_getHealthRecord(ID);
                }

            }
            if (choice == 2) {
                viewAppointments(DoctorID);
            }
            if (choice == 3) {
                System.out.println("===========================================");
                System.out.println("|          ELECTRONIC PRESCRIPTION         |");
                System.out.println("===========================================");

                System.out.println("Okay, Doctor. Let's create a new prescription!");
                String prescriptionID = generatePrescriptionID();

                System.out.print("Please enter the patient's ID: ");
                String patientID = sc.next();
                sc.nextLine();

                System.out.print("Please enter the medication name: ");
                String medicationName = sc.nextLine().trim();

                System.out.print("Please enter the dosage: ");
                String dosage = sc.nextLine().trim();

                System.out.print("Please enter the frequency: ");
                String frequency = sc.nextLine().trim();

                daoFactory.deactivateConnection(null);

                if (addPrescription(prescriptionID, patientID, DoctorID, medicationName, dosage, frequency)) {
                    System.out.println("===========================================");
                    System.out.println("|         PRESCRIPTION SUCCESSFULLY        |");
                    System.out.println("|                ADDED!                    |");
                    System.out.println("===========================================");
                } else {
                    System.out.println("===========================================");
                    System.out.println("|        PRESCRIPTION NOT ADDED!           |");
                    System.out.println("===========================================");
                }

            }
            if (choice == 4) {
                RequestSalary(DoctorID);
                // System.out.printf("The salary is %d\n",sal);

            }
            if (choice == 5) {
                String recordID, patientID, doctorID, summary, isCured, currentStatus;

                System.out.print("Enter Patient ID: ");
                patientID = sc.nextLine();
                sc.nextLine();

                System.out.print("Enter Summary: ");
                summary = sc.nextLine();
                sc.nextLine();

                System.out.print("Enter isCured (true/false): ");
                isCured = sc.next();

                System.out.print("Enter Current Status: ");
                currentStatus = sc.nextLine();
                addHealthRecord(patientID, summary, isCured, currentStatus);
            }
            if (choice == 6) {
                String recordID, patientID, doctorID, summary, isCured, currentStatus;

                System.out.print("Enter Patient ID: ");
                patientID = sc.nextLine();
                sc.nextLine();
                System.out.print("Enter Summary: ");
                summary = sc.nextLine();

                // consume the newline character
                sc.nextLine();

                System.out.print("Enter isCured (true/false): ");
                isCured = sc.nextLine();
                sc.nextLine();

                System.out.print("Enter Current Status: ");
                currentStatus = sc.nextLine();

                updateHealthRecord(patientID, summary, isCured, currentStatus);
            }
            if (choice == 7) {
                System.out.print("Enter patient ID: ");
                String patientId = sc.next();

                System.out.print("Enter total amount: ");
                double totalAmount = sc.nextDouble();

                System.out.print("Enter due date (yyyy-mm-dd): ");
                String dueDateString = sc.next();
                Date dueDate = Date.valueOf(dueDateString);
                MakeBill(patientId, totalAmount, dueDate);
            }
            if (choice == 8) {
                break;
            }
            daoFactory.deactivateConnection(null);

        }
    }

    private static String generatePrescriptionID() throws Exception {
        Connection conn = daoFactory.activateConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(prescriptionID) FROM ElectronicPrescription");
        if (rs.next()) {
            String maxPrescriptionID = rs.getString(1);
            if (maxPrescriptionID != null) {
                int lastNumber = Integer.parseInt(maxPrescriptionID.substring(3));
                return "RX" + String.format("%03d", lastNumber + 1);
            }
        }
        daoFactory.deactivateConnection(null);
        return "RX001";
    }
    // @Override
    // public void MakeBill(int patientId, double totalAmount, java.sql.Date
    // dueDate) throws Exception {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'MakeBill'");
    // }

    public void MakeBill(String patientId, double totalAmount, java.sql.Date dueDate) throws Exception {
        // TODO Auto-generated method stub

        Connection conn = daoFactory.activateConnection();

        // Step 2: Insert the new billing record
        String sql = "INSERT INTO Billing (billID,patientID , totalAmount, amountPaid, dueDate, currStatus) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, generateBillID(conn));
        stmt.setString(2, patientId);
        stmt.setDouble(3, totalAmount);
        stmt.setDouble(4, 0.0);
        stmt.setDate(5, new java.sql.Date(dueDate.getTime()));
        stmt.setString(6, "unpaid");
        stmt.executeUpdate();
        conn.commit();
        // Step 3: Close the database connection
        daoFactory.deactivateConnection(null);

    }

    private String generateBillID(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Patient";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return "BILL" + String.format("%04d", count + 1);
    }

}
