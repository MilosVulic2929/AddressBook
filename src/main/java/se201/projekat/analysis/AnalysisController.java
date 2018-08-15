package se201.projekat.analysis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class AnalysisController implements Initializable {

    //TODO
    // Dodaj funkcije na dugmice / disable multyselect / vrednosti uzimaj iz baze / setuj name dijagrama u programu da bi mogo da ga update u runtime

    @FXML
    private BarChart<String, Number> barChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    public void loadData() {


        XYChart.Series<String, Number> series = new XYChart.Series();

        series.setName("2018");

        series.getData().add(new XYChart.Data("Item 1", 1800.58));
        series.getData().add(new XYChart.Data("Item 2", 4300.40));
        series.getData().add(new XYChart.Data("Item 3", 2500.50));
        series.getData().add(new XYChart.Data("Item 5", 3450.89));

        barChart.getData().add(series);
    }
}
