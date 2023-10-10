import java.sql.Connection;
import java.util.*;
import java.sql.*;

public class Receptionist extends HospitalStaff {
    private String receptionistID;
    private String receptionistName;
    private int salary;

    public Receptionist(String receptionistID, String receptionistName, int salary) throws Exception {
        super(receptionistID, receptionistName, receptionistName, salary, salary);
    }

    public String getReceptionistID() {
        return receptionistID;
    }

    public String getReceptionistName() {
        return receptionistName;
    }

    public int getSalary() {
        return salary;
    }

    public void getDocforAppointment(Patient patient) {
    }

}