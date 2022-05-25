import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatisticMenu extends JFrame {
    private JButton vaccineStockStatisticButton;
    private JButton injectedVaccineStatisticButton;
    private JButton exitButton;
    private JPanel statisticMenu;

    public StatisticMenu() {
        setContentPane(statisticMenu);
        setSize(500, 500);
        setTitle("Statistic Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        vaccineStockStatisticButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RecordStatistic recordStatistic = new RecordStatistic();
                recordStatistic.setVisible(true);
            }
        });
        injectedVaccineStatisticButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CitizenStatistic citizenStatistic = new CitizenStatistic();
                citizenStatistic.setVisible(true);
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
