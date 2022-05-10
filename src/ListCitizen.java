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

public class ListCitizen extends ListTable {
    public ListCitizen() {
        DefaultTableModel modelTable = new DefaultTableModel();
        CitizenDatabase citizenDatabase = new CitizenDatabase();
        List<Citizen> citizens = citizenDatabase.getAllCitizen();
        modelTable.addColumn("ID");
        modelTable.addColumn("Name");
        modelTable.addColumn("Date of Birth");
        modelTable.addColumn("Sex");
        modelTable.addColumn("Vaccine IDs");
        modelTable.addColumn("Vaccine Dose Count");
        contentTable.setModel(modelTable);
        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < citizens.size(); i++) {
            Vector<String> row = new Vector<String>();
            String dateString = spd.format(citizens.get(i).getDateOfBirth());
            row.add(citizens.get(i).getId());
            row.add(citizens.get(i).getName());
            row.add(dateString);
            row.add(citizens.get(i).getSex());
            row.add(citizens.get(i).getDoses().toString());
            row.add(String.valueOf(citizens.get(i).getDoses().size()));
            modelTable.addRow(row);
        }
        contentTable.setAutoCreateRowSorter(true);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelTable);
        contentTable.setRowSorter(sorter);
        scrollPane.setBounds(0, 0, 800, 600);
        scrollPane.setVisible(true);
        setSize(800, 600);
        setVisible(true);
        setContentPane(mainPane);
        filterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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
                int option = fchoose.showSaveDialog(ListCitizen.this);
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



