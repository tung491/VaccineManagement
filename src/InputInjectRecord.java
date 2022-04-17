import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InputInjectRecord extends JFrame {
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
        setSize(800,600);
        setContentPane(inputInjectPanel);
        setVisible(true);
        citizenIdBox.setEditable(true);
        Component component = citizenIdBox.getEditor().getEditorComponent();
        CitizenDatabase citizenDBObj = new CitizenDatabase();
        List<Citizen> citizenList = citizenDBObj.getAllCitizen();
        this.citizens = citizenList;
        for (Citizen citizen : citizenList) {
            citizenIdBox.addItem(citizen.getId());
            citizenInfo.setText(citizen.toString());
        }

        ExtendedVaccineDatabase vaccineDBObj = new ExtendedVaccineDatabase();
        List<ExtendedVaccine> vaccinationRecordList = vaccineDBObj.getExtendedUnusedVaccines();
        for (ExtendedVaccine vaccinationRecord : vaccinationRecordList) {
            vaccineIDBox.addItem(vaccinationRecord.getId());
            vaccineInfo.setText(vaccinationRecord.toString());
        }
        this.vaccines = vaccinationRecordList;

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String citizenId = citizenIdBox.getSelectedItem().toString();
                String vaccineId = vaccineIDBox.getSelectedItem().toString();
                String date = dateField.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (citizenId.equals("") || vaccineId.equals("") || date.equals("")) {
                    if (citizenId.equals("")) {
                        citizenIdBox.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        citizenIdBox.setBorder(new JTextField().getBorder());
                    }
                    if (vaccineId.equals("")) {
                        vaccineIDBox.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        vaccineIDBox.setBorder(new JTextField().getBorder());
                    }
                    if (date.equals("")) {
                        dateField.setBorder(new LineBorder(Color.RED, 2));
                    } else {
                        dateField.setBorder(new JTextField().getBorder());
                    }
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                    return;
                }
                java.util.Date dateObj = null;
                try {
                    dateObj = sdf.parse(date);
                } catch (ParseException ex) {
                    dateField.setBorder(new LineBorder(Color.RED, 2));
                    return;
                }
                VaccinationRecordDatabase vaccinationRecordDatabase = new VaccinationRecordDatabase();
                vaccinationRecordDatabase.insertVaccinationRecord(citizenId, vaccineId, dateObj);
                VaccineDatabase vaccineDatabase = new VaccineDatabase();
                vaccineDatabase.updateInjectedVaccines(vaccineId);
                JOptionPane.showMessageDialog(null, "Inserted successfully");

            }
        });
        citizenIdBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String citizenId = citizenIdBox.getSelectedItem().toString();
                for (Citizen citizen : citizens) {
                    if (citizen.getId().equals(citizenId)) {
                        System.out.println(citizen.toString());
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
                        System.out.println(vaccine.toString());
                        vaccineInfo.setText(vaccine.toString());
                        break;
                    }
                }
            }
        });
    }
}
