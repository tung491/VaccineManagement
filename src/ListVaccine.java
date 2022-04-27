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

public class ListVaccine extends JFrame {
    private JPanel listVaccinePane;
    private JButton exportToFieldButton;
    private JButton filterButton;
    private JTextField searchField;
    private JTable vaccineTable;
    private JScrollPane scrollPane;
    private JPanel filterPane;

    public ListVaccine() {
        DefaultTableModel modelTable = new DefaultTableModel();
        ExtendedVaccineDatabase vaccineDatabase = new ExtendedVaccineDatabase();
        List<ExtendedVaccine> vaccines = vaccineDatabase.getExtendedVaccines();
        modelTable.addColumn("ID");
        modelTable.addColumn("Vaccine Name");
        modelTable.addColumn("Batch ID");
        modelTable.addColumn("Production Date");
        modelTable.addColumn("Expiration Date");
        modelTable.addColumn("Interval");
        modelTable.addColumn("Injected");
        vaccineTable.setModel(modelTable);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (ExtendedVaccine vaccine : vaccines) {
            Vector<String> row = new Vector<>();
            row.add(vaccine.getId());
            row.add(vaccine.getVaccineName());
            row.add(vaccine.getBatchId());
            row.add(dateFormat.format(vaccine.getProductionDate()));
            row.add(dateFormat.format(vaccine.getExpirationDate()));
            row.add(String.valueOf(vaccine.getInterval()));
            row.add(String.valueOf(vaccine.isInjected()));
            modelTable.addRow(row);
        }
        vaccineTable.setAutoCreateRowSorter(true);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelTable);
        vaccineTable.setRowSorter(sorter);
        scrollPane.setBounds(0, 0, 800, 600);
        scrollPane.setVisible(true);
        setSize(800, 600);
        setVisible(true);
        setContentPane(listVaccinePane);
        filterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = searchField.getText();
                if (text.equals("")) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String text = searchField.getText();
                    if (text.equals("")) {
                        sorter.setRowFilter(null);
                    } else {
                        try {
                            sorter.setRowFilter(RowFilter.regexFilter(text));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        exportToFieldButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fchoose = new JFileChooser();
                int option = fchoose.showSaveDialog(ListVaccine.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String name = fchoose.getSelectedFile().getName();
                    String path = fchoose.getSelectedFile().getParentFile().getPath();
                    File exportFile = new File(path, name + ".tsv");
                    exportToTSVFile(vaccineTable, exportFile);
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
