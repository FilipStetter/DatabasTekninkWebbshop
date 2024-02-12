import java.util.Scanner;

public class DataHandler {



    public DataHandler() {
        Repository r = new Repository();



        Scanner sc = new Scanner(System.in);

        InputHandlerInterface.InputHandler

        inputHandler = () -> {
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

        System.out.println("Ange ditt beställningsId eller ange 0 om du saknar beställningsId: ");
        Integer beställningsId = sc.nextInt();
        if(beställningsId != null && beställningsId == 0){
            beställningsId = null;
        }

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
