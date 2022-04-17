import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

public class ListVaccinationRecord extends JFrame {
    private JPanel panel1;
    private JButton exportToFile;
    private JTextField searchFilterField;
    private JButton filterButton;
    private JTable table1;
    private JScrollPane scrollPanel;

    public ListVaccinationRecord() {
        DefaultTableModel modelTable = new DefaultTableModel();
        ExtendedVaccinationRecordDatabase database = new ExtendedVaccinationRecordDatabase();
        List<ExtendedVaccinationRecord> records = database.getExtendedVaccinationRecords();

        modelTable.addColumn("ID");
        modelTable.addColumn("Citizen ID");
        modelTable.addColumn("Vaccination ID");
        modelTable.addColumn("Vaccination Date");
        modelTable.addColumn("Citizen Name");
        modelTable.addColumn("Vaccination Name");
        modelTable.addColumn("Production Date");
        modelTable.addColumn("Expiration Date");
        table1.setModel(modelTable);
        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        for (ExtendedVaccinationRecord record : records) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(record.getId()));
            row.add(String.valueOf(record.getCitizenId()));
            row.add(String.valueOf(record.getVaccineId()));
            row.add(spd.format(record.getDate()));
            row.add(record.getCitizenName());
            row.add(record.getVaccineName());
            row.add(spd.format(record.getProductionDate()));
            row.add(spd.format(record.getExpiryDate()));
            modelTable.addRow(row);
        }
        table1.setAutoCreateRowSorter(true);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelTable);
        table1.setRowSorter(sorter);
        scrollPanel.setBounds(0, 0, 800, 600);
        scrollPanel.setVisible(true);
        setContentPane(panel1);
        setSize(800, 600);
        filterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String searchText = searchFilterField.getText();
                if (searchText.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(searchText));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        searchFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = searchFilterField.getText();
                    if (searchText.isEmpty()) {
                        sorter.setRowFilter(null);
                    } else {
                        try {
                            sorter.setRowFilter(RowFilter.regexFilter(searchText));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        exportToFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fchoose = new JFileChooser();
                int option = fchoose.showSaveDialog(ListVaccinationRecord.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String name = fchoose.getSelectedFile().getName();
                    String path = fchoose.getSelectedFile().getParentFile().getPath();
                    File exportFile = new File(path, name + ".tsv");
                    exportToTSVFile(table1, exportFile);
                }
            }
        });
    }

    public void exportToTSVFile(JTable table, File file) {
        try {
            TableModel m = table.getModel();
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < m.getColumnCount(); i++) {
                fw.write(m.getColumnName(i) + "\t");
            }
            fw.write("\n");
            for (int i = 0; i < m.getRowCount(); i++) {
                for (int j = 0; j < m.getColumnCount(); j++) {
                    fw.write(m.getValueAt(i, j).toString() + "\t");
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
