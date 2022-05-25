import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.text.DecimalFormat;
import java.util.List;

public class RecordStatistic extends JFrame {
    private JPanel recordStatisticPane;

    public RecordStatistic() {
        VaccineStockDatabase vaccineStockDatabase = new VaccineStockDatabase();
        List<VaccineStock> vaccineStockList = vaccineStockDatabase.getVaccineRecordsGroupByName();
        JFreeChart chart = ChartFactory.createPieChart("Injected Vaccine Record Statistic",
                createDataset(vaccineStockList), true, true, false);
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
        setLocationRelativeTo(null);
        setSize(800, 600);
    }


    private PieDataset createDataset(List<VaccineStock> vaccineStockList) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (VaccineStock vaccineStock : vaccineStockList) {
            dataset.setValue(vaccineStock.getName(), vaccineStock.getAmount());
        }
        return dataset;
    }
}
