import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputVaccineBatch extends JFrame {
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
                if (vaccineName.equals("") || batchId.equals("") || productionDateString.equals("") || expiryDateString.equals("") || intervalString.equals("")) {
                    if (vaccineName.equals("")) {
                        vaccineNameField.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        vaccineNameField.setBorder(new JTextField().getBorder());
                    }
                    if (batchId.equals("")) {
                        batchIdField.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        batchIdField.setBorder(new JTextField().getBorder());
                    }
                    if (productionDateString.equals("")) {
                        productionDateField.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        productionDateField.setBorder(new JTextField().getBorder());
                    }
                    if (expiryDateString.equals("")) {
                        expiryDateField.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        expiryDateField.setBorder(new JTextField().getBorder());
                    }
                    if (intervalString.equals("")) {
                        intervalField.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        intervalField.setBorder(new JTextField().getBorder());
                    }
                    if (amountString.equals("")) {
                        amountField.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        amountField.setBorder(new JTextField().getBorder());
                    }
                }

                Date productionDate;
                try {
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
