public interface ReceptionistDAO {
    public void registerPatient(Patient patient) throws Exception;

    public void setupDB() throws Exception;

    public Patient getPat(String PatID);

    public void getDocforAppointment(Patient patient) throws Exception;

    public void registerPatient() throws Exception;
}
