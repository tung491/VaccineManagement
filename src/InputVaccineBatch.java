import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputVaccineBatch extends InputForm {
    private JTextField vaccineNameField;
    private JTextField batchIdField;
    private JTextField productionDateField;
    private JTextField expiryDateField;
    private JTextField intervalField;
    private JButton submitButton;
    private JPanel inputVaccinePanel;
    private JTextField amountField;

    public InputVaccineBatch() {
        setContentPane(inputVaccinePanel);
        setSize(500, 500);
        setTitle("Input Vaccine Batch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        submitButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Get input from user
                String vaccineName = vaccineNameField.getText();
                String batchId = batchIdField.getText();
                String productionDateString = productionDateField.getText();
                String expiryDateString = expiryDateField.getText();
                String intervalString = intervalField.getText();
                String amountString = amountField.getText();
                // Input validation
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                ArrayList<JTextField> fields = new ArrayList<>();
                fields.add(vaccineNameField);
                fields.add(batchIdField);
                fields.add(productionDateField);
                fields.add(expiryDateField);
                fields.add(intervalField);
                fields.add(amountField);
                boolean ok = checkEmptyFields(fields);
                if (!ok) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    return;
                }

                Date productionDate;
                try {
                    sdf.setLenient(false);
                    productionDate = sdf.parse(productionDateString);
                } catch (ParseException ex) {
                    productionDateField.setBorder(new LineBorder(Color.RED, 2));
                    return;
                }

                Date expiryDate;
                try {
                    expiryDate = sdf.parse(expiryDateString);
                } catch (ParseException ex) {
                    expiryDateField.setBorder(new LineBorder(Color.RED, 2));
                    return;
                }
                if (expiryDate.before(productionDate)) {
                    expiryDateField.setBorder(new LineBorder(Color.RED, 2));
                    JOptionPane.showMessageDialog(null, "Expiry date cannot be before production date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    expiryDateField.setBorder(new JTextField().getBorder());
                }

                int interval;
                try {
                    interval = Integer.parseInt(intervalString);
                    intervalField.setBorder(new JTextField().getBorder());
                } catch (NumberFormatException ex) {
                    intervalField.setBorder(new LineBorder(Color.RED, 2));
                    JOptionPane.showMessageDialog(null, "Interval must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int amount;
                try {
                    amount = Integer.parseInt(amountString);
                    amountField.setBorder(new JTextField().getBorder());
                } catch (NumberFormatException ex) {
                    amountField.setBorder(new LineBorder(Color.RED, 2));
                    JOptionPane.showMessageDialog(null, "Amount must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                VaccineBatchDatabase vaccineBatchDatabase = new VaccineBatchDatabase();
                VaccineDatabase vaccineDatabase = new VaccineDatabase();
                vaccineBatchDatabase.insertVaccineBatch(batchId, vaccineName, productionDate, expiryDate, interval);
                vaccineDatabase.insertVaccines(batchId, amount);
                JOptionPane.showMessageDialog(null, "Add vaccine successfully");
                vaccineNameField.setText("");
                batchIdField.setText("");
                productionDateField.setText("");
                expiryDateField.setText("");
                intervalField.setText("");
                amountField.setText("");
            }
        });
    }
}
