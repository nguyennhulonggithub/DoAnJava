package com.doan.client.Controller.Screen;

import com.doan.client.Controller.Component.EditPlaylistFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayListScreenController implements Initializable {
    public static String image;
    public static String name;
    public ImageView avatarUser;
    public Label username;
    public static boolean isSetUser;
    public Label playlistName;
    public AnchorPane playlistCover;
    public EditPlaylistFormController editPlaylistFormController;
    public AnchorPane editPlaylistPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isSetUser) {
            avatarUser.setImage(new Image(image));
            username.setText(name);
        } else {
            username.setText("Anonymous");
        }



    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.setText(playlistName);
    }

    public void setEditPaneVisible(ActionEvent actionEvent) {
        editPlaylistPane.setVisible(true);
    }
}
