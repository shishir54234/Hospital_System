import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Receptionist_JDBC implements ReceptionistDAO {
    private DAO_Factory daofactory = new DAO_Factory();
    private ArrayList<Patient> patients = new ArrayList();
    private ArrayList<Doctor> doctors = new ArrayList();
    private Patient_JDBC p1 = new Patient_JDBC();

    public void registerPatient(Patient patient) throws Exception {
        Register register = new Register();
        register.registerPatient();
        // setupDB();
    }

    public void setupDB() throws Exception {
        Connection conn = daofactory.activateConnection();
        String sql = "SELECT * FROM Doctor d INNER JOIN HospitalStaff hs ON d.doctorID = hs.id";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Step 3: Display the health records
        while (rs.next()) {
            String docID = rs.getString("doctorID");
            String docName = rs.getString("doctorName");
            String department = rs.getString("department");
            int salary = rs.getInt("salary");
            int performance = rs.getInt("performance");

            Doctor doctor = new Doctor(docID, docName, department, salary, performance);
            doctors.add(doctor);
        }
        sql = "SELECT * FROM Patient";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        while (rs.next()) {
            String patientID = rs.getString("patientID");
            String patientName = rs.getString("patientName");
            int age = rs.getInt("age");
            String gender = rs.getString("gender");

            Patient patient = new Patient(patientID, patientName, age, gender);
            patients.add(patient);

        }

        // Step 4: Close the database connection
        daofactory.deactivateConnection(null);
    }

    public Doctor getDoc(String DoctorID) {
        for (Doctor e : doctors) {
            if (e.getDoctorID().equals(DoctorID)) {
                return e;
            }

        }
        return null;
    }

    public Patient getPat(String PatID) {

        for (Patient e : patients) {
            if (e.getPatientID().equals(PatID)) {
                return e;
            }

        }
        return null;
    }

    public void getDocforAppointment(Patient patient) throws Exception {
        // setupDB();
        int i = 1;
        for (Doctor e : doctors) {
            System.out.println(i + ") Doctor Name: " + e.getDoctorName() + "Doctor Speciality: " + e.getDept()
                    + " Performance: " + e.getPerformance());
            i++;
        }
        System.out.println(patients.size());
        System.out.print("Enter number of the doctor you want to get assigned to:");
        Scanner sc = new Scanner(System.in);

        int size = doctors.size();
        int num;
        while (true) {
            num = sc.nextInt();
            if (num > 0 && num < size + 1) {
                break;
            } else {
                System.out.println("Please enter a valid number");
            }
        }
        p1.RequestAppointment(patient.getPatientID(), doctors.get(num - 1).getDoctorID());

    }

    public void addMoneyToBilling(int billId, double amount) throws Exception {

        // Step 1: Establish a connection to the database
        try {
            Connection conn = daofactory.activateConnection();

            // Step 2: Retrieve the existing billing record
            String sql = "SELECT * FROM billing WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Step 3: Update the billing record with the new amount paid
                double currentAmountPaid = rs.getDouble("amount_paid");
                double newAmountPaid = currentAmountPaid + amount;
                double totalAmount = rs.getDouble("total_amount");
                String currentStatus = rs.getString("current_status");
                if (newAmountPaid >= totalAmount) {
                    currentStatus = "paid";
                }
                sql = "UPDATE billing SET amount_paid=?, current_status=? WHERE id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, newAmountPaid);
                stmt.setString(2, currentStatus);
                stmt.setInt(3, billId);
                stmt.executeUpdate();
            }

            // Step 4: Close the database connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerPatient() throws Exception {
        Register register = new Register();
        Patient pat = register.registerPatient();
        patients.add(pat);

    }

}
