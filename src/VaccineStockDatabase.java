import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccineStockDatabase extends Database {
    private static final String GET_AMOUNT_OF_VACCINE_TYPES = """
            SELECT vb.name as vaccine_name, count(vb.id) as amount
            FROM vaccine v
            JOIN vaccine_batch vb ON v.batch_id = vb.id
            WHERE v.injected = false and vb.expiry_date > now()
            GROUP BY vb.name;""";

    private static final String SELECT_VACCINE_RECORDS_GROUP_BY_NAME = """
            SELECT vb.name as vaccine_name, count(vb.id) as amount
                                             FROM vaccination_record vr
                                             JOIN citizen c ON vr.citizen_id = c.id
                                             JOIN vaccine v on vr.vaccine_id = v.id
                                             JOIN vaccine_batch vb on v.batch_id = vb.id
                                 GROUP BY vaccine_name;
                                             """;


    public List<VaccineStock> getVaccineStocks() {
        List<VaccineStock> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_AMOUNT_OF_VACCINE_TYPES)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new VaccineStock(resultSet.getString("vaccine_name"), resultSet.getInt("amount")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<VaccineStock> getVaccineRecordsGroupByName() {
        List<VaccineStock> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_VACCINE_RECORDS_GROUP_BY_NAME)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new VaccineStock(resultSet.getString("vaccine_name"), resultSet.getInt("amount")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}



