import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Vector;

public class ListVaccineStocks extends ListTable {
    public ListVaccineStocks() {
        DefaultTableModel modelTable = new DefaultTableModel();
        VaccineStockDatabase vaccineStockDatabase = new VaccineStockDatabase();
        List<VaccineStock> vaccineStocks = vaccineStockDatabase.getVaccineStocks();
        modelTable.addColumn("Vaccine Name");
        modelTable.addColumn("Amount");
        contentTable.setModel(modelTable);
        for (VaccineStock vaccineStock : vaccineStocks) {
            Vector<String> row = new Vector<>();
            row.add(vaccineStock.getName());
            row.add(String.valueOf(vaccineStock.getAmount()));
            modelTable.addRow(row);
        }
        contentTable.setAutoCreateRowSorter(true);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelTable);
        contentTable.setRowSorter(sorter);
        scrollPane.setBounds(0, 0, 800, 600);
        scrollPane.setVisible(true);
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setContentPane(mainPane);

        filterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = searchTextField.getText();
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
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String text = searchTextField.getText();
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
        exportToFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFileChooser fchoose = new JFileChooser();
                int option = fchoose.showSaveDialog(ListVaccineStocks.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String name = fchoose.getSelectedFile().getName();
                    String path = fchoose.getSelectedFile().getParentFile().getPath();
                    File exportFile = new File(path, name + ".csv");
                    exportToTSVFile(contentTable, exportFile);
                }

            }
        });

    }
}
