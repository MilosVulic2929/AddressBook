package se201.projekat.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.utils.PaneTransition;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class AnalysisController implements Initializable {

    //TODO
    // Data za prvi dijagram enabluj iz baze da se gettuje

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChartGender;

    @FXML
    private RadioButton radioCountryBtn;

    @FXML
    private RadioButton radioContactBtn;


    ContactDao contactDao = DaoFactory.create(ContactDao.class);
    private List<String> listaImenaGrupa;
    private List<Integer> listaBrojaClanova;

    {
        try {
            listaImenaGrupa = contactDao.countGroupNames();
            listaBrojaClanova = contactDao.countGroupMembers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioCountryBtn.setSelected(true);
        loadDataBarChartCountries();
        loadDataPieChartGender();
    }

    private void loadDataBarChartCountries() {

        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Countries");
        try {
            for (int i = 0; i < contactDao.countCountryNames().size() && i < 4; i++) {
                series.getData().add(new XYChart.Data(contactDao.countCountryNames().get(i), contactDao.countCountryAddresses().get(i)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        barChart.getData().add(series);
    }

    private void modifyGroupList() {
        try {
            if (listaImenaGrupa.size() == 4) {
                if (listaBrojaClanova.get(3) < contactDao.countNoGroupsMembers()) {
                    listaImenaGrupa.set(3, "Bez grupe");
                    listaBrojaClanova.set(3, contactDao.countNoGroupsMembers());
                }
            } else {
                listaImenaGrupa.add("Bez grupe");
                listaBrojaClanova.add(contactDao.countNoGroupsMembers());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataBarChartContacts() {
        modifyGroupList();
        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Group names");
        for (int i = 0; i < listaImenaGrupa.size() && i < 4; i++) {
            series.getData().add(new XYChart.Data(listaImenaGrupa.get(i), listaBrojaClanova.get(i)));
        }
        barChart.getData().add(series);
    }

    private void loadDataPieChartGender() {
        ObservableList<PieChart.Data> pieChartData = null;
        try {
            pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Other", contactDao.countContacts("other")),
                    new PieChart.Data("Female", contactDao.countContacts("female")),
                    new PieChart.Data("Male", contactDao.countContacts("male")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pieChartGender.setTitle("Gender statistics");
        pieChartGender.setData(pieChartData);
    }

    public void handleHome(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../MainView.fxml");
    }

    private void toggle(RadioButton first, RadioButton second) {
        first.setSelected(true);
        if (first.isSelected()) {
            second.setSelected(false);
        } else if (second.isSelected()) {
            first.setSelected(false);
        }
    }

    public void handleCountryRadio() {
        toggle(radioCountryBtn, radioContactBtn);
        barChart.getData().clear();
        loadDataBarChartCountries();
    }

    public void handleContactRadio() {
        toggle(radioContactBtn, radioCountryBtn);
        barChart.getData().clear();
        loadDataBarChartContacts();
    }

    public void handleMoreAnalysis(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../AdditionalAnalysis.fxml");
    }
}
