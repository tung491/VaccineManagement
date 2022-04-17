import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CitizenMenu extends JFrame {
    private JButton listCitizensButton;
    private JButton inputCitizenButton;
    private JPanel CitizenMenu;
    private JButton inputInjectRecordButton;
    private JButton listInjectRecordButton;
    private JPanel citizenMenuPanel;

    public CitizenMenu() {
        setContentPane(CitizenMenu);
        setTitle("Citizen Menu");
        setSize(400, 400);
        setVisible(true);
        inputCitizenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InputCitizen inputCitizen = new InputCitizen();
                inputCitizen.setVisible(true);

            }
        });
        listCitizensButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ListCitizen listCitizens = new ListCitizen();
                listCitizens.setVisible(true);
            }
        });
        inputInjectRecordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                InputInjectRecord inputInjectRecord = new InputInjectRecord();
                inputInjectRecord.setVisible(true);
            }
        });
        listInjectRecordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ListVaccinationRecord listInjectRecord = new ListVaccinationRecord();
                listInjectRecord.setVisible(true);
            }
        });
    }
}
