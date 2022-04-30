import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

public class ListVaccinationRecord extends ListTable {
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
        contentTable.setModel(modelTable);
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
        contentTable.setAutoCreateRowSorter(true);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelTable);
        contentTable.setRowSorter(sorter);
        scrollPane.setBounds(0, 0, 800, 600);
        scrollPane.setVisible(true);
        setContentPane(mainPane);
        setSize(800, 600);
        filterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String searchText = searchTextField.getText();
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
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = searchTextField.getText();
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
        exportToFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fchoose = new JFileChooser();
                int option = fchoose.showSaveDialog(ListVaccinationRecord.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String name = fchoose.getSelectedFile().getName();
                    String path = fchoose.getSelectedFile().getParentFile().getPath();
                    File exportFile = new File(path, name + ".tsv");
                    exportToTSVFile(contentTable, exportFile);
                }
            }
        });
    }
}
