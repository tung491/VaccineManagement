import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputCitizen extends InputForm {
    private JTextField NameField;
    private JTextField CitizenIDField;
    private JFormattedTextField dateOfBirthField;
    private JComboBox sexBox;
    private JPanel InputCitizen;
    private JButton Submit;

    public InputCitizen() {
//        Set date format for formattedTextField1
        setContentPane(InputCitizen);
        setTitle("Input Citizen");
//        Add Male/Female option for sexBox

        setSize(500, 500);
        setVisible(true);
        Submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String name = NameField.getText();
                String citizenID = CitizenIDField.getText();
                String dateOfBirth = dateOfBirthField.getText();
                String sex = (String) sexBox.getSelectedItem();
// Check date of birth is in format dd/mm/yyyy
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                ArrayList<JTextField> fields = new ArrayList<>();
                fields.add(NameField);
                fields.add(CitizenIDField);
                fields.add(dateOfBirthField);

                boolean ok = checkEmptyFields(fields);
                if (!ok) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    return;
                }
                Date date;
                try {
                    sdf.setLenient(false);
                    date = sdf.parse(dateOfBirth);
                } catch (ParseException ex) {
                    dateOfBirthField.setBorder(new LineBorder(Color.RED, 2));
                    JOptionPane.showMessageDialog(null, "Please enter date of birth in format dd/mm/yyyy");
                    return;
                }
                CitizenDatabase citizenDatabase = new CitizenDatabase();
                citizenDatabase.insertCitizen(citizenID, name, date, sex);
                JOptionPane.showMessageDialog(null, "Add citizen successfully");
                NameField.setText("");
                CitizenIDField.setText("");
                dateOfBirthField.setText("");
                sexBox.setSelectedIndex(0);
            }
        });
    }
}
