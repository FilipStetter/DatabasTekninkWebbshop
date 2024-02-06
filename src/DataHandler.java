import java.sql.*;
import java.util.Scanner;

public class DataHandler {

    public DataHandler() {
        Repository r = new Repository();
        int kundId;
        int beställningsId;
        int skoId;

        Scanner sc = new Scanner(System.in);

        while(true){

            System.out.println("Ange ditt användarnamn: ");
            String användarnamn = sc.nextLine();
            System.out.println("Ange ditt lösenord: ");
            String lösenord = sc.nextLine();


            kundId = r.authenticateUser(användarnamn, lösenord);
            if (kundId == -1) {
                System.out.println("Fel användarnamn eller lösenord. Försök igen.");
                continue;
            }
            System.out.println("Skriv ditt Beställningsid?  ");
            beställningsId = sc.nextInt();

            System.out.println("Vilken sko vill du beställa?  ");
            skoId = sc.nextInt();


            System.out.println(r.addToCart( kundId,  beställningsId,  skoId));
        }
    }

    public static void main(String args[])  {
        DataHandler d = new DataHandler();
    }


}

