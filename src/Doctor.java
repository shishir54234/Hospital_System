public class Doctor extends HospitalStaff {
    private String doctorID;
    private String doctorName;
    private String dept;

    public Doctor(String doctorID, String doctorName, String dept, int salary,int performance) {
        super(doctorID, doctorName, dept, salary, performance);
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.dept = dept;
    }
    public String getDoctorID() {
        return doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDept() {
        return dept;
    }   
}
