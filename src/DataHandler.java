import java.util.Scanner;

public class DataHandler {

    public interface InputHandler {
        String getInput();
    }

    public DataHandler() {
        Repository r = new Repository();

        Scanner sc = new Scanner(System.in);

        InputHandler inputHandler = () -> {
            System.out.println("Ange ditt användarnamn: ");
            return sc.nextLine();
        };

        String användarnamn = inputHandler.getInput();

        inputHandler = () -> {
            System.out.println("Ange ditt lösenord: ");
            return sc.nextLine();
        };

        String lösenord = inputHandler.getInput();

        int kundId = r.authenticateUser(användarnamn, lösenord);
        if (kundId == -1) {
            System.out.println("Fel användarnamn eller lösenord. Försök igen.");
            return;
        }

        System.out.println("Ange ditt beställningsId: ");
        int beställningsId = sc.nextInt();

        System.out.println("Tillgängliga skor:");
        r.displayShoes();

        System.out.println("Ange numret för skon du vill beställa: ");
        int skoId = sc.nextInt();

        System.out.println(r.addToCart(kundId, beställningsId, skoId));
    }

    public static void main(String args[]) {
        DataHandler d = new DataHandler();
    }
}
