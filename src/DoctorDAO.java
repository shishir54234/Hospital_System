import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public interface DoctorDAO {

		// JDBC driver name and database URL
 		//  Database credentials
    public void Doctor_getHealthRecord(String patientId) throws Exception;
    public void viewAppointments(String doctorID) throws Exception;
    public boolean addPrescription(String prescriptionID, String patientID, String doctorID,
            String medicationName, String dosage, String frequency) throws Exception;
        
        public void RequestSalary(String DoctorID) throws Exception;
    public void Start(String DoctorID) throws Exception;
    public boolean addHealthRecord(String patientID, String summary, String isCured, String currentStatus) throws Exception;
    public void MakeBill(String patientId, double totalAmount, Date dueDate) throws Exception;
}
