import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Patient_JDBC implements PatientDAO {

    private static DAO_Factory daoFactory = new DAO_Factory();
    private String patientID;
    private String patientName;
    private int age;
    private String gender;

    @Override
    public void RequestPrescription(String patientID) throws Exception {
        try {
            Connection conn = daoFactory.activateConnection();
            String sql = "SELECT * FROM ElectronicPrescription";
            if (patientID != "") {
                sql += " WHERE patientID=?";
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            if (patientID != "") {
                stmt.setString(1, patientID);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next() == true) {
                while (rs.next()) {
                    String PrescriptionID = rs.getString("prescriptionID");
                    String doctorID = rs.getString("doctorID");
                    String medicationName = rs.getString("medicationName");
                    String dosage = rs.getString("dosage");
                    String frequency = rs.getString("frequency");
                    String sql2 = "SELECT doctorName FROM Doctor WHERE doctorID = ?";
                    stmt = conn.prepareStatement(sql2);
                    stmt.setString(1, doctorID);
                    ResultSet rs2 = stmt.executeQuery();
                    rs2.next();
                    String doctorName = rs2.getString("doctorName");
                    System.out.println("PrescriptionID: " + PrescriptionID);
                    System.out.println("Doctor: " + doctorName);
                    System.out.println("Medication: " + medicationName);
                    System.out.println("dosage: " + dosage);
                    System.out.println("frequency: " + frequency);

                }
            } else {
                System.out.println("Please come back later, your prescription is not updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void RequestAppointment(String patientID, String doctorID) throws Exception {
        try {
            Connection conn = daoFactory.activateConnection();
            String appt_id;
            String newAppointmentID = "";
            String sql = "SELECT * FROM  Doctor WHERE doctorID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, doctorID);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {

                System.out.println("Please enter a valid DoctorID");
                return;

            }
            String sql2 = "SELECT Appointmentdate FROM Appointment WHERE doctorID=? ORDER BY Appointmentdate DESC LIMIT 1";
            stmt = conn.prepareStatement(sql2);
            stmt.setString(1, doctorID);
            ResultSet rs2 = stmt.executeQuery();
            if (rs2.next() == true) {
                Timestamp lastAppointment = rs2.getTimestamp("Appointmentdate");
                Timestamp appointmentDate = new Timestamp(lastAppointment.getTime() + (60 * 60 * 1000));
                String sql3 = "SELECT MAX(appointmentID) FROM Appointment";
                stmt = conn.prepareStatement(sql3);
                ResultSet rs3 = stmt.executeQuery();
                if (rs3.next() == true) {
                    appt_id = rs3.getString(1);
                    String prefix = "APPT";
                    String numericPart = appt_id.substring(prefix.length()); // Extract the numeric part of the
                                                                             // appointmentID
                    int numericValue = Integer.parseInt(numericPart);
                    int newNumericValue = numericValue + 1;
                    String newNumericPart = String.format("%03d", newNumericValue); // Format the new numeric value with
                                                                                    // leading zeros
                    newAppointmentID = prefix + newNumericPart;
                }
                System.out.println(
                        "Your appointment is successfully created and your appointment is at " + appointmentDate);
                String sql4 = "INSERT INTO Appointment VALUES (?,?,?,?)";
                stmt = conn.prepareStatement(sql4);
                stmt.setString(1, newAppointmentID);
                stmt.setTimestamp(2, appointmentDate);
                stmt.setString(3, doctorID);
                stmt.setString(4, patientID);
                stmt.executeUpdate();
                conn.commit();
            } else {
                System.out.print("Enter a timestamp (yyyy-MM-dd HH:mm:ss): ");
                Scanner scan = new Scanner(System.in);
                String inputTimestampStr = scan.nextLine();
                Timestamp inputTimestamp = Timestamp.valueOf(inputTimestampStr);
                String sql3 = "SELECT MAX(appointmentID) FROM Appointment";
                stmt = conn.prepareStatement(sql3);
                ResultSet rs3 = stmt.executeQuery();
                if (rs3.next() == true) {
                    appt_id = rs3.getString(1);
                    String prefix = "APPT";
                    String numericPart = appt_id.substring(prefix.length()); // Extract the numeric part of the
                                                                             // appointmentID
                    int numericValue = Integer.parseInt(numericPart);
                    int newNumericValue = numericValue + 1;
                    String newNumericPart = String.format("%03d", newNumericValue); // Format the new numeric value with
                                                                                    // leading zeros
                    newAppointmentID = prefix + newNumericPart;
                    System.out.println(newAppointmentID);
                }
                System.out.println(
                        "Your appointment is successfully created and your appointment is at " + inputTimestamp);
                String sql5 = "INSERT INTO Appointment VALUES (?,?,?,?)";
                stmt = conn.prepareStatement(sql5);
                stmt.setString(1, newAppointmentID);
                stmt.setTimestamp(2, inputTimestamp);
                stmt.setString(3, doctorID);
                stmt.setString(4, patientID);
                stmt.executeQuery();
                scan.close();
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}