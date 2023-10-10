public class Patient{
    private String patientID;
    private String patientName;
    private int age;
    private String gender;

    public Patient(String patientID, String patientName, int age, String gender) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
    }
    public String getPatientID(){ return patientID; }
    public String getPatientName(){ return patientName; }
    public int getAge(){ return age; }
    public String getGender(){ return gender; }

}