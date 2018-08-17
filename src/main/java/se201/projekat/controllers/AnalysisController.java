package se201.projekat.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import se201.projekat.utils.PaneTransition;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class AnalysisController implements Initializable {

    //TODO
    // Dodaj funkcije na dugmice / vrednosti uzimaj iz baze / setuj name dijagrama u programu da bi mogo da ga update u runtime

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChartGender;

    @FXML
    private RadioButton radioCountryBtn;

    @FXML
    private RadioButton radioContactBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioCountryBtn.setSelected(true);
        loadDataBarChartCountries();
        loadDataPieChartGender();
    }

    private void loadDataBarChartCountries() {

        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Countries");
        series.getData().add(new XYChart.Data("Item 1", 1800.58));
        series.getData().add(new XYChart.Data("Item 2", 4300.40));
        series.getData().add(new XYChart.Data("Item 3", 2500.50));
        series.getData().add(new XYChart.Data("Item 5", 3450.89));
        barChart.getData().add(series);
    }

    private void loadDataBarChartContacts() {

        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Contact type");
        series.getData().add(new XYChart.Data("Item 1", 1800.58));
        series.getData().add(new XYChart.Data("Item 2", 4300.40));
        series.getData().add(new XYChart.Data("Item 3", 2500.50));
        barChart.getData().add(series);
    }

    private void loadDataPieChartGender() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Companies", 30),
                        new PieChart.Data("Male", 45),
                        new PieChart.Data("Female", 10));

        pieChartGender.setTitle("Gender statistics");
        pieChartGender.setData(pieChartData);
    }

    public void handleHome(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent,"../FirstView.fxml");
    }

    private void toggle(RadioButton first, RadioButton second) {
        first.setSelected(true);
        if (first.isSelected()) {
            second.setSelected(false);
        } else if (second.isSelected()) {
            first.setSelected(false);
        }
    }

    public void handleCountryRadio(ActionEvent actionEvent) {
        toggle(radioCountryBtn, radioContactBtn);
        barChart.getData().clear();
        loadDataBarChartCountries();
    }

    public void handleContactRadio(ActionEvent actionEvent) {
        toggle(radioContactBtn, radioCountryBtn);
        barChart.getData().clear();
        loadDataBarChartContacts();
    }

    public void handleMoreAnalysis(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent,"../AdditionalAnalysis.fxml");
    }
}
