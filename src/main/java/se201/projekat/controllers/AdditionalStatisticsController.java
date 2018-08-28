package se201.projekat.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.utils.FX;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ResourceBundle;

@SuppressWarnings("All")
public class AdditionalStatisticsController implements Initializable {

    @FXML
    private DatePicker dataPicker;

    @FXML
    private BarChart<String, Number> barChart;

    private LocalDate date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        barChart.setAnimated(false);
        createArrayOfMonths();
        setUpDefaultDiagram();
    }

    public void handleDataPicker() {
        barChart.getData().clear();
        date = dataPicker.getValue();
        setUpFinalDiagram();
    }

    private void setUpDefaultDiagram() {
        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Added contacts");
        for (int i = 0; i < createArrayOfMonths().length; i++) {
            series.getData().add(new XYChart.Data(createArrayOfMonths()[i], 0));
        }
        barChart.getData().addAll(series);
    }

    private void setUpFinalDiagram() {
        ContactDao contactDao = DaoFactory.create(ContactDao.class);
        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Added contacts");
        for (int i = 0; i < createArrayOfMonths().length; i++) {
            try {
                series.getData().add(new XYChart.Data(createArrayOfMonths()[i], contactDao.countContactsPerMounth(String.valueOf(date.getYear()), String.valueOf(i))));
            } catch (SQLException e) {
                e.printStackTrace();
                FX.createAlert(Alert.AlertType.ERROR, "Database Access Error " + e.getErrorCode(), "Couldn't load data",
                        "System will exit now!").showAndWait();
                System.exit(1);
            }
        }
        barChart.getData().addAll(series);
    }

    private String[] createArrayOfMonths() {
        String[] months = new DateFormatSymbols().getMonths();
        String[] finalMonths = new String[12];
        for (int i = 0; i < months.length - 1; i++) {
            finalMonths[i] = months[i];
        }
        return finalMonths;
    }


    public void onBack(ActionEvent actionEvent) {
        FX.transition(actionEvent, "../Statistics.fxml");
    }

    public void onHome(ActionEvent actionEvent) {
        FX.transition(actionEvent, "../MainView.fxml");
    }

}
