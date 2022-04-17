import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class VaccineBatchDatabase extends Database {
    private static final String INSERT_VACCINE_BATCH = "INSERT INTO vaccine_batch (id, name, production_date, expiry_date, interval)" +
            " VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_VACCINE_BATCHES = "SELECT * FROM vaccine_batch";

    public void insertVaccineBatch(String id, String name, Date production_date, Date expiry_date, int interval) {

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VACCINE_BATCH);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setDate(3, new java.sql.Date(production_date.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(expiry_date.getTime()));
            preparedStatement.setInt(5, interval);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<VaccineBatch> getVaccineBatches() {
        List<VaccineBatch> vaccineBatches = new java.util.ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VACCINE_BATCHES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vaccineBatches.add(new VaccineBatch(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("production_date"),
                        resultSet.getDate("expiry_date"),
                        resultSet.getInt("interval")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaccineBatches;
    }

    public static void main(String[] args) {
        VaccineBatchDatabase vaccineBatchDatabase = new VaccineBatchDatabase();
        Date production_date = new Date();
        Date expiry_date = new Date(production_date.getTime() + (1000L * 60 * 60 * 24 * 365));
        vaccineBatchDatabase.insertVaccineBatch("93a7d182-71a3-45bd-add3-3d7a39822e95","Vaccine Batch 2", production_date, expiry_date, 14);

        List<VaccineBatch> vaccineBatches = vaccineBatchDatabase.getVaccineBatches();
        for (VaccineBatch vaccineBatch : vaccineBatches)
            System.out.println(vaccineBatch.name);

    }
}
