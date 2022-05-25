import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CitizenStatistic extends JFrame {
    public CitizenStatistic() {
        CitizenDatabase db = new CitizenDatabase();
        List<Citizen> citizens = db.getAllCitizen();
        JFreeChart chart = ChartFactory.createPieChart("Citizen Statistic", createDataset(citizens),
                true, true, false);
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} doses: ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
        setLocationRelativeTo(null);
        setSize(800, 600);
    }

    private PieDataset createDataset(List<Citizen> citizens) {
        Map<Integer, Integer> injectedVaccine = new HashMap<>();
        for (Citizen citizen : citizens) {
            int injectedDoses = citizen.getDoses().size();
            if (injectedVaccine.containsKey(citizen.getDoses().size())) {
                injectedVaccine.put(injectedDoses, injectedVaccine.get(injectedDoses) + 1);
            } else {
                injectedVaccine.put(injectedDoses, 1);
            }
        }
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<Integer, Integer> entry : injectedVaccine.entrySet()) {
            dataset.setValue(entry.getKey().toString(), entry.getValue());
        }
        return dataset;
    }
}
