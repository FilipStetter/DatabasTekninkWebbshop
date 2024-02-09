import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.function.Consumer;


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
            return "Kunde inte skapa beställning ";
        }
        return "Din beställning är skapad " ;

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


    public void displayShoes() {
        String query = "SELECT s.id, s.modell, s.storlek, s.pris, m.namn AS markenamn, f.namn AS färgnamn FROM Skor s " +
                "JOIN Märke m ON s.märkeid = m.id " +
                "JOIN Färg f ON s.färgid = f.id";

        try (Connection conn = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            processResultSet(rs, result -> {
                try {
                    int skoId = result.getInt("id");
                    String modell = result.getString("modell");
                    int storlek = result.getInt("storlek");
                    double pris = result.getDouble("pris");
                    String märkeNamn = result.getString("markenamn");
                    String färgNamn = result.getString("färgnamn");

                    System.out.println("Sko: " + skoId + ", Modell: " + modell + ", Storlek: " + storlek +
                            ", Märke: " + märkeNamn + ", Färg: " + färgNamn + ", Pris: " + pris);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void processResultSet(ResultSet rs, Consumer<ResultSet> consumer) throws SQLException {
        while (rs.next()) {
            consumer.accept(rs);
        }
    }
}
