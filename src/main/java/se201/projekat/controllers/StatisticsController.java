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
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.utils.FX;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class StatisticsController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChartGender;

    @FXML
    private RadioButton radioBtnCountry;

    @FXML
    private RadioButton radioBtnContact;


    private ContactDao contactDao = DaoFactory.create(ContactDao.class);
    private List<String> listGroupNames;
    private List<Integer> listGroupMemberCount;

    public StatisticsController() {
        try {
            listGroupNames = contactDao.countGroupNames();
            listGroupMemberCount = contactDao.countGroupMembers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioBtnCountry.setSelected(true);
        loadDataBarChartCountries();
        loadDataPieChartGender();
        barChart.setTitle("Statistics by countries");
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
            if (listGroupNames.size() == 4) {
                if (listGroupMemberCount.get(3) < contactDao.countNoGroupsMembers()) {
                    listGroupNames.set(3, "Bez grupe");
                    listGroupMemberCount.set(3, contactDao.countNoGroupsMembers());
                }
            } else {
                listGroupNames.add("Bez grupe");
                listGroupMemberCount.add(contactDao.countNoGroupsMembers());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataBarChartContacts() {
        modifyGroupList();
        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName("Group names");
        for (int i = 0; i < listGroupNames.size() && i < 4; i++) {
            series.getData().add(new XYChart.Data(listGroupNames.get(i), listGroupMemberCount.get(i)));
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

    public void onHome(ActionEvent actionEvent) {
        FX.transition(actionEvent, "../MainView.fxml");
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
        toggle(radioBtnCountry, radioBtnContact);
        barChart.getData().clear();
        loadDataBarChartCountries();
        barChart.setTitle("Statistics by countries");
    }

    public void handleContactRadio() {
        toggle(radioBtnContact, radioBtnCountry);
        barChart.getData().clear();
        loadDataBarChartContacts();
        barChart.setTitle("Statistics by groups");
    }

    public void onMoreStatistics(ActionEvent actionEvent) {
        FX.transition(actionEvent, "../AdditionalStatistics.fxml");
    }
}
