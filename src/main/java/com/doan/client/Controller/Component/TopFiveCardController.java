package com.doan.client.Controller.Component;

import com.doan.client.Controller.PublicController;
import com.doan.client.Controller.UserScreen.MainScreenController;
import com.doan.client.Model.Song;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TopFiveCardController implements Initializable {

    public Button playSongBtn;
    public Label songDuration;
    public Label songName;
    public ImageView songImage;
    public Label songIndex;
    public MainScreenController mainScreenController;

    public Button moreBtn;
    public int currentIndex;
    Song curSong;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setSongToComponent(Song song, int i) {

        songDuration.setText(PublicController.setTimePlay(song.getDuration()));
        songName.setText(song.getName());
        songImage.setImage(new Image(song.getAlbum().getImage()));
        songIndex.setText(Integer.toString(i));
        curSong= song;
        currentIndex= i;
    }

    public void playSong(ActionEvent actionEvent) {
        mainScreenController.setMediaPlaying(curSong);
    }
    public void showMoreCard(ActionEvent actionEvent) {
       if (mainScreenController.login){
           AnchorPane parentAnchorPane= mainScreenController.homeScreenController.outsideParent;
           moreBtn.getStyleClass().add("add_opacity");
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/MoreOptionCard.fxml"));
           try {
               AnchorPane card = fxmlLoader.load();

               card.setLayoutY(710 + (currentIndex-1)*44);
               card.setLayoutX(710);

               AnchorPane layer = new AnchorPane();

               layer.setPrefWidth(parentAnchorPane.getPrefWidth());
               layer.setPrefHeight(parentAnchorPane.getPrefHeight());
               layer.getChildren().add(card);

               parentAnchorPane.getChildren().add(layer);
               layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                   @Override
                   public void handle(MouseEvent mouseEvent) {
                       parentAnchorPane.getChildren().remove(layer);
                       moreBtn.getStyleClass().remove("add_opacity");
                   }
               });
               MoreOptionCardController moreOptionCardController = PublicController.createMoreOptionCard(fxmlLoader, layer, curSong);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }else{
           mainScreenController.loginPaneFromHome.setVisible(true);
       }


    }
}
