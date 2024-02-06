import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Repository {

        Properties p = new Properties();


    public Repository() {
        try {
            p.load(new FileInputStream("C:\\Users\\fs308\\IdeaProjects\\DatabasTekninkWebbshop\\src\\Settings"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public String addToCart (int kundId, int beställningsId, int skoId) {

        ResultSet rs = null;
        String query = "call AddToCart(?,?,?)";

        String errormessage = "";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)){

            stmt.setInt(1, kundId);
            stmt.setInt(2, beställningsId);
            stmt.setInt(3, skoId);

            rs = stmt.executeQuery();



        }

        catch (Exception e){

            e.printStackTrace();
            System.out.println("e.mess "+e.getMessage());
            return "Could not add order ";
        }
        return "Your order was added." ;
    }

    public int authenticateUser(String username, String password) {
        String query = "SELECT id FROM Kund WHERE användarnamn = ? AND lösenord = ?";
        int kundId = -1;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kundId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kundId;
    }
}
