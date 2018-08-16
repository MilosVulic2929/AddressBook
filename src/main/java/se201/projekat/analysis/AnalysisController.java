package se201.projekat.analysis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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

    public void loadDataBarChartCountries() {

        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("2018");
        series.getData().add(new XYChart.Data("Item 1", 1800.58));
        series.getData().add(new XYChart.Data("Item 2", 4300.40));
        series.getData().add(new XYChart.Data("Item 3", 2500.50));
        series.getData().add(new XYChart.Data("Item 5", 3450.89));
        barChart.getData().add(series);
    }

    public void loadDataBarChartContacts() {

        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("2018");
        series.getData().add(new XYChart.Data("Item 1", 1800.58));
        series.getData().add(new XYChart.Data("Item 2", 4300.40));
        series.getData().add(new XYChart.Data("Item 3", 2500.50));
        barChart.getData().add(series);
    }

    public void loadDataPieChartGender() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Companies", 30),
                        new PieChart.Data("Male", 45),
                        new PieChart.Data("Female", 10));

        pieChartGender.setTitle("Gender statistics");
        pieChartGender.setData(pieChartData);
    }

    public void handleHome(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FirstView.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggle(RadioButton first, RadioButton second) {
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
}
