import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputInjectRecord extends InputForm {
    private JComboBox citizenIdBox;
    private JComboBox vaccineIDBox;
    private JFormattedTextField dateField;
    private JButton submitButton;
    private JPanel inputInjectPanel;
    private JLabel citizenInfo;
    private JLabel vaccineInfo;
    private List<Citizen> citizens;
    private List<ExtendedVaccine> vaccines;

    public InputInjectRecord() {
        setSize(800, 600);
        setContentPane(inputInjectPanel);
        setVisible(true);
        setLocationRelativeTo(null);
        citizenIdBox.setEditable(true);
        Component component = citizenIdBox.getEditor().getEditorComponent();
        CitizenDatabase citizenDBObj = new CitizenDatabase();
        List<Citizen> citizenList = citizenDBObj.getAllCitizen();
        this.citizens = citizenList;
        citizenInfo.setText(citizenList.get(0).toString());
        for (Citizen citizen : citizenList) {
            citizenIdBox.addItem(citizen.getId());
        }

        ExtendedVaccineDatabase vaccineDBObj = new ExtendedVaccineDatabase();
        List<ExtendedVaccine> vaccinationRecordList = vaccineDBObj.getExtendedUnusedVaccines();
        vaccineInfo.setText(vaccinationRecordList.get(0).toString());
        for (ExtendedVaccine vaccinationRecord : vaccinationRecordList) {
            vaccineIDBox.addItem(vaccinationRecord.getId());
        }
        this.vaccines = vaccinationRecordList;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(sdf.format(new Date()));

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String citizenId = citizenIdBox.getSelectedItem().toString();
                String vaccineId = vaccineIDBox.getSelectedItem().toString();
                String date = dateField.getText();
                ArrayList<JTextField> fields = new ArrayList<>();
                fields.add(dateField);
//                  citizen and vaccine ID boxes always have a valid value, so we don't need to check them
                boolean ok = checkEmptyFields(fields);
                if (!ok) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    return;
                }

                java.util.Date dateObj;
                try {
                    sdf.setLenient(false);
                    dateObj = sdf.parse(date);
                } catch (ParseException ex) {
                    dateField.setBorder(new LineBorder(Color.RED, 2));
                    JOptionPane.showMessageDialog(null, "Please enter a valid date");
                    return;
                }
                VaccinationRecordDatabase vaccinationRecordDatabase = new VaccinationRecordDatabase();
                vaccinationRecordDatabase.insertVaccinationRecord(citizenId, vaccineId, dateObj);
                VaccineDatabase vaccineDatabase = new VaccineDatabase();
                vaccineDatabase.updateInjectedVaccines(vaccineId);

                citizenIdBox.setSelectedIndex(0);
                vaccineIDBox.setSelectedIndex(0);
                dateField.setText("");

                JOptionPane.showMessageDialog(null, "Insert record successfully");

            }
        });
        citizenIdBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String citizenId = citizenIdBox.getSelectedItem().toString();
                for (Citizen citizen : citizens) {
                    if (citizen.getId().equals(citizenId)) {
                        citizenInfo.setText(citizen.toString());
                        break;
                    }
                }
            }
        });
        vaccineIDBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vaccineId = vaccineIDBox.getSelectedItem().toString();
                for (Vaccine vaccine : vaccines) {
                    if (vaccine.getId().equals(vaccineId)) {
                        vaccineInfo.setText(vaccine.toString());
                        break;
                    }
                }
            }
        });
    }

}
