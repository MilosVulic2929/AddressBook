package se201.projekat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import se201.projekat.dao.DaoFactory;
import se201.projekat.dao.GroupDao;
import se201.projekat.models.Group;
import se201.projekat.utils.FX;

import java.sql.SQLException;
import java.util.List;

public class NewGroupController {


    @FXML
    TextField textName;

    /**
     * Zatvara stage
     */
    public void onBack(ActionEvent event) {
        // Ako se stavi hide ostaje window u pozadini bolje je ovako jer se onda close
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void onSave(ActionEvent event) {
        if (textName.getText().length() > 0) {
            String name = textName.getText();
            GroupDao groupDao = DaoFactory.create(GroupDao.class);
            try {
                List<Group> allContacts = groupDao.getAll();
                for (Group g : allContacts) {
                    if (g.getName().equals(name)) {
                        FX.createAlert(Alert.AlertType.INFORMATION, "Duplicate Entry", "Duplicate entry",
                                "Group with same name already exists").showAndWait();
                        return;
                    }
                }
                groupDao.insert(new Group(name));
                Window window = ((Node) (event.getSource())).getScene().getWindow();
                window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
            } catch (SQLException e) {
                FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(), "Couldn't save new group!",
                        e.getMessage()).showAndWait();
            }
        } else {
            FX.createAlert(Alert.AlertType.INFORMATION, "Invalid input", "Group name can't be empty", "").showAndWait();
        }
    }
}
