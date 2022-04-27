import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WarehouseMenu extends JFrame {
    private JButton inputVaccineInformationButton;
    private JButton vaccineRecordListButton;
    private JButton vaccineStockListButton;
    private JButton exitButton;
    private JPanel warehouseMenu;

    public WarehouseMenu() {
        setContentPane(warehouseMenu);
        setSize(500, 500);
        setTitle("Warehouse Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        inputVaccineInformationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InputVaccineBatch inputVaccineBatch = new InputVaccineBatch();
                inputVaccineBatch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                inputVaccineBatch.setVisible(true);
            }
        });
        vaccineRecordListButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ListVaccine listVaccine = new ListVaccine();
                listVaccine.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listVaccine.setVisible(true);
            }
        });
        vaccineStockListButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ListVaccineStocks listVaccineStock = new ListVaccineStocks();
                listVaccineStock.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listVaccineStock.setVisible(true);
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }
}
