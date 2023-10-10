import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        DAO_Factory daofactory = new DAO_Factory();
        Receptionist_JDBC r1 = new Receptionist_JDBC();
        r1.setupDB();

        // Display welcome message and current date and time
        System.out.println("+--------------------------------------------+");
        System.out.println("|                                            |");
        System.out.println("|          Welcome to My Clinic System       |");
        System.out.println("|                                            |");
        System.out.println("+--------------------------------------------+");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        System.out.println("|              " + formattedDateTime + "           |");
        System.out.println("+--------------------------------------------+");
        System.out.println();

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("+--------------------------------------------+");
            System.out.println("|                Menu Selection              |");
            System.out.println("+--------------------------------------------+");
            System.out.println("| 1. Login (doc/patient)                     |");
            System.out.println("| 2. Register (patient)                       |");
            System.out.println("+--------------------------------------------+");
            System.out.print("Enter your choice: ");
            String st = sc.next();

            if (st.equals("1")) {
                Login L = new Login();
                L.Log();

            } else if (st.equals("2")) {
                // we will use the register class
                r1.registerPatient();

            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
