import java.sql.Connection;
import java.sql.Date;

public interface PatientDAO {
    public void RequestPrescription(String prescriptionID) throws Exception;

    public void RequestAppointment(String patientID, String doctorID) throws Exception;

}