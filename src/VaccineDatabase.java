import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccineDatabase extends Database {
    public static final String INSERT_VACCINES_SQL = "INSERT INTO vaccine " +
            "(batch_id) VALUES (?)";
    public static final String SELECT_A_BATCH_VACCINES_SQL = "SELECT * FROM vaccine WHERE batch_id = ?";
    public static final String UPDATE_INJECTED_VACCINES_SQL = "UPDATE vaccine SET injected = TRUE WHERE id = ?";
    public static final String SELECT_ALL_VACCINES_SQL = "SELECT * FROM vaccine";

    public List<String> insertVaccines(String batch_id, int amount) {
        List<String> vaccines = new ArrayList<>();
        try (
                Connection conn = this.getConnection();
                ) {
            PreparedStatement statement = conn.prepareStatement(INSERT_VACCINES_SQL);

            for (int i = 0; i < amount; i++) {
                statement.setString(1, batch_id);
                statement.addBatch();
            }
            statement.executeBatch();
            statement = conn.prepareStatement(SELECT_A_BATCH_VACCINES_SQL);
            statement.setString(1, batch_id);
            ResultSet resultSet = statement.executeQuery();
            String vaccineID;
            while (resultSet.next()) {
                vaccineID = resultSet.getString("id");
                vaccines.add(vaccineID);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return vaccines;
    }

    public void updateInjectedVaccines(String vaccineID) {
        try (
                Connection conn = this.getConnection();
                ) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_INJECTED_VACCINES_SQL);
            statement.setString(1, vaccineID);
            statement.addBatch();
            statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Vaccine> selectAllVaccines() {
        List<Vaccine> vaccines = new ArrayList<>();
        try (
                Connection conn = this.getConnection();
                ) {
            PreparedStatement statement = conn.prepareStatement(SELECT_ALL_VACCINES_SQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Vaccine vaccine = new Vaccine(
                        resultSet.getString("id"),
                        resultSet.getString("batch_id"),
                        resultSet.getBoolean("injected")
                );
                vaccines.add(vaccine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccines;
    }

    public static void main(String[] args) {
        VaccineDatabase vaccineDatabase = new VaccineDatabase();
        vaccineDatabase.updateInjectedVaccines("61bba5f5-10ad-4583-9c5c-dda90c804bba");
    }
}
