/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller.Component;


import com.doan.client.Controller.PublicController;
import com.doan.client.Controller.UserScreen.MainScreenController;
import com.doan.client.Model.Playlist;
import com.doan.client.Model.Song;
import com.doan.client.Model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;


/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LoginFormController implements Initializable {
    public Button validCodeBtn;
    public TextField privateCode;
    public AnchorPane privateCodeForm;
    public Label privateCodeText;
    public Label changePasswordText;
    public PasswordField firstPassword;
    public PasswordField secondPassword;
    public AnchorPane changePasswordForm;
    public Button changePasswordBtn;
    public MainScreenController mainController;

    @FXML
    public AnchorPane loginPanel;
    public AnchorPane asynchronousLogin;
    public Label forgotPasswordBtn;
    public TextField userNameReset;
    public TextField emailReset;
    public Button getPasswordBtn;
    public Label backToLoginBtn;
    public AnchorPane resetPasswordForm;
    public AnchorPane loadingResetPassword;
    public AnchorPane loadingChangePassword;
    public Label imageText;
    public Button uploadImageBtn;
    public Button createAccountBtn;
    public Button createNewAccountBtn;
    public TextField newPassword;
    public TextField newUsername;
    public TextField newEmail;
    public TextField lastName;
    public TextField firstName;
    public AnchorPane asynchronousAddUser;
    public AnchorPane createNewAccountForm;

    @FXML
    private AnchorPane overlay;
    @FXML
    private AnchorPane loginForm;
    @FXML
    private TextField userName;
    @FXML
    private Label errorLogin;
    @FXML
    private PasswordField passWord;

    private String privateCodeMail = "";
    User userGlobal = new User();
    File file;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void guestLogin(MouseEvent event) {
        setVisibleLogin(false);

        mainController.bannerPrice.setVisible(true);
    }

    public void setVisibleLogin(boolean x) {

        mainController.setLoginPaneVisible();
    }
    @FXML
    private void userLogin(MouseEvent event) throws IOException, UnirestException {
        if ("".equals(userName.getText()) && "".equals(passWord.getText())) {
            errorLogin.setStyle("-fx-text-fill: red");
            errorLogin.setText("Username and Password can't be empty");
        } else if ("".equals(userName.getText())) {
            errorLogin.setStyle("-fx-text-fill: red");
            errorLogin.setText("Username can't be empty");
        } else if ("".equals(passWord.getText())) {
            errorLogin.setStyle("-fx-text-fill: red");
            errorLogin.setText("Password can't be empty");

        } else {
            loginPanel.setVisible(false);
            asynchronousLogin.setVisible(true);
            new Thread(() -> {
                HttpResponse<JsonNode> apiResponse = null;
                try {
                    apiResponse = Unirest.get("http://localhost:8080/user/findUserByNameAndPassword/" + userName.getText() + "/" + passWord.getText()).asJson();
                    User user = new Gson().fromJson(apiResponse.getBody().toString(), User.class);
                    if (user.getName() != null) {
                        if (user.getRole().equals("user")) {
                            HttpResponse<JsonNode> jsonNodeHttpResponse= Unirest.get("http://localhost:8080/user/getLastListenSong/" + user.getId()).asJson();

                            List<Song> songList= new ObjectMapper().readValue(jsonNodeHttpResponse.getBody().getObject().get("lastListenSongs").toString(), new TypeReference<List<Song>>() {
                                @Override
                                public Type getType() {
                                    return super.getType();
                                }
                            });
                            if (songList.size()==0){
                                songList.add(mainController.homeScreenController.listSongBanner.get(0));
                            }
                            HttpResponse<JsonNode> playlistHttpResponse= Unirest.get("http://localhost:8080/user/findPlaylistFromUser?idUser=" + user.getId()).asJson();

                            ObjectMapper mapper = new ObjectMapper();
                            List<Playlist> playlistList = mapper.readValue(playlistHttpResponse.getBody().toString(), new TypeReference<>() {
                            });

                            for (int i = 0 ; i< playlistList.size(); i++){

                                if (playlistList.get(i).getName().equals("Like")){
                                    MainScreenController.likedList = playlistList.get(i).getIdSongs();
                                    MainScreenController.idPlaylistLike= playlistList.get(i).getId();
                                }else{

                                    MainScreenController.playlistOfUser.add(playlistList.get(i));

                                    ToggleButton button = new ToggleButton();
                                    button.setText(playlistList.get(i).getName());
                                    FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
                                    fontAwesomeIconView.setGlyphName("CLOSE");
                                    button.setGraphic(fontAwesomeIconView);
                                    button.getStyleClass().add("playlistBtn");
                                    VBox.setMargin(button, new Insets(10, 0, 0, 0));
                                    button.setToggleGroup(mainController.Group1);
                                    button.setId(playlistList.get(i).getId());
                                    button.setOnAction(mainController::toggleBtn);
                                    button.setPrefWidth(165);
                                    button.setAlignment(Pos.BASELINE_LEFT);
                                    fontAwesomeIconView.setOnMouseClicked(mouseEvent -> mainController.removePlaylist(button.getId(), button));
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            mainController.mainVbox.getChildren().add(button);
                                        }
                                    });

                                }
                            }

                            MainScreenController.idUser= user.getId();
                            MainScreenController.albumFollowed= user.getIdAlbums();
                            MainScreenController.artistFollowed= user.getIdArtists();


                            if (songList.size()==0){
                                songList.add(mainController.homeScreenController.listSongBanner.get(0));
                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    mainController.homeScreenController.initAlbum();
                                    mainController.homeScreenController.initArtist();
                                    mainController.homeScreenController.addChildrenToLastListening(songList);
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setHeaderText("Login Success");
                                    alert.setContentText("Hello " + user.getDisplayName());
                                    mainController.discoverController.initOffer();
                                    mainController.discoverController.recommend.setVisible(false);

                                    loginPanel.setVisible(true);
                                    asynchronousLogin.setVisible(false);
                                    alert.show();
                                    setVisibleLogin(false);


                                }
                            });
                            mainController.setUser(user);
                        }else{

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    FXMLLoader adminFxmlLoader= new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/AdminScreen.fxml"));
                                    Parent root = null;
                                    try {
                                        root = adminFxmlLoader.load();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    loginPanel.setVisible(true);
                                    asynchronousLogin.setVisible(false);
                                    setVisibleLogin(false);
                                    Scene scene = new Scene(root);
                                    scene.getStylesheets().add(getClass().getResource("/com/doan/client/application.css").toExternalForm());
                                    PublicController.primaryStage.setScene(scene);
                                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                                    alert.setHeaderText("Welcome Admin");
                                    alert.show();
                                }
                            });

                        }
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                asynchronousLogin.setVisible(false);
                                loginPanel.setVisible(true);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("User or password is wrong");
                                alert.show();
                            }
                        });
                    }
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();


        }
    }

    @FXML
    private void createAccount(MouseEvent event) {
        createNewAccountForm.setVisible(true);
        loginForm.setVisible(false);

    }

    @FXML
    private void googleLogin(MouseEvent event) {

    }

    public void getPassword(ActionEvent actionEvent) {
        loadingResetPassword.setVisible(true);
        getPasswordBtn.setVisible(false);
        new Thread(() -> {
            try {
                HttpResponse<JsonNode> apiResetPassword = Unirest.get("http://localhost:8080/user/findUserByNameAndEmail/" + userNameReset.getText() + "/" + emailReset.getText()).asJson();
                userGlobal = new Gson().fromJson(apiResetPassword.getBody().toString(), User.class);
                if (userGlobal.getName() == null) {
                    loadingResetPassword.setVisible(false);
                    getPasswordBtn.setVisible(true);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("User not exist");
                            alert.show();
                        }
                    });
                } else {
                    String to = emailReset.getText();

                    // Sender's email ID needs to be mentioned
                    String from = "TesterSendingMessage@gmail.com";

                    // Assuming you are sending email from through gmails smtp
                    String host = "smtp.gmail.com";
                    Properties properties = System.getProperties();

                    // Setup mail server
                    properties.put("mail.smtp.host", host);
                    properties.put("mail.smtp.port", "465");
                    properties.put("mail.smtp.ssl.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    // Get the Session object.// and pass username and password
                    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("19521790@gm.uit.edu.vn", "long7890");
                        }
                    });
                    // Used to debug SMTP issues
                    session.setDebug(true);
                    try {
                        // Create a default MimeMessage object.
                        MimeMessage message = new MimeMessage(session);

                        // Set From: header field of the header.
                        message.setFrom(new InternetAddress(from));

                        // Set To: header field of the header.
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                        // Set Subject: header field
                        message.setSubject("Password reset DoAnJava!");
                        double privateCode = Math.random() * 100000;

                        privateCodeMail = String.format("%.0f", privateCode);

                        message.setText("This is your code: " + privateCodeMail);

                        Transport.send(message);
                        getPasswordBtn.setVisible(true);
                        loadingResetPassword.setVisible(false);
                        privateCodeForm.setVisible(true);
                    } catch (MessagingException mex) {
                        mex.printStackTrace();
                    }
                }
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void backToLoginFunction() {
        loginForm.setVisible(true);
        resetPasswordForm.setVisible(false);
        privateCodeForm.setVisible(false);
        getPasswordBtn.setVisible(true);
        loadingResetPassword.setVisible(false);
        changePasswordForm.setVisible(false);
    }

    public void backToLogin(MouseEvent mouseEvent) {
        backToLoginFunction();
    }

    public void forgotPassWord(MouseEvent mouseEvent) {
        loginForm.setVisible(false);
        resetPasswordForm.setVisible(true);
    }

    public void validCode(ActionEvent actionEvent) {
        if (privateCode.getText().equals(privateCodeMail)) {
            changePasswordForm.setVisible(true);

        } else {
            privateCodeText.setText("Your private code is wrong");
        }
    }

    public void changePassword(ActionEvent actionEvent) throws UnirestException {
        if (firstPassword.getText().length() < 4) {
            changePasswordText.setText("Your password must have at least 4 character");
        } else if (firstPassword.getText().equals(secondPassword.getText())) {
            loadingChangePassword.setVisible(true);
            changePasswordBtn.setVisible(false);
            new Thread(() -> {
                userGlobal.setPassword(firstPassword.getText());
                HttpResponse<JsonNode> jsonResponse
                        = null;

                try {
                    jsonResponse = Unirest.post("http://localhost:8080/user/updatePassword").header("Content-Type", "application/json")
                            .body(userGlobal)
                            .asJson();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Change password success");
                            alert.show();
                        }
                    });
                    backToLoginFunction();
                    loadingChangePassword.setVisible(false);
                    changePasswordBtn.setVisible(true);
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        } else {
            changePasswordText.setText("Your Password's not same as Confirm password");
        }
    }

    public void uploadImage(ActionEvent actionEvent) throws UnirestException {

        FileChooser fc = new FileChooser();
        fc.setTitle("Select your Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        file = fc.showOpenDialog(null);
        if (file != null) {
            imageText.setText(file.getName());
        }

    }

    public void createNewAccount(ActionEvent actionEvent) throws UnirestException {
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || newEmail.getText().isEmpty() || newUsername.getText().isEmpty() || newPassword.getText().isEmpty() || imageText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all information");
            alert.show();
        } else if (newPassword.getText().length() < 4) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Your pass word must have more than 3 characters");
            alert.show();
        } else {
            asynchronousAddUser.setVisible(true);
            createNewAccountBtn.setVisible(false);

            new Thread(() -> {

                try {
                    String displayName = lastName.getText() + " " + firstName.getText();
                    String json = "{\"name\":\"" + newUsername.getText() + "\",\"displayName\":\"" + displayName + "\", \"email\":\"" + newEmail.getText() + "\", \"password\":\"" + newPassword.getText() + "\"}";


                    HttpResponse<String> a = Unirest.post("http://localhost:8080/user/addUser").field("image", file).field("user", json).asString();
                    if (a.getStatus()==200) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Your account has been created!");
                                alert.show();
                                loginForm.setVisible(true);
                                createNewAccountForm.setVisible(false);
                            }

                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Your Username or Email has existed");
                                alert.show();
                            }
                        });
                    }
                    asynchronousAddUser.setVisible(false);
                    createNewAccountBtn.setVisible(true);

                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }

            }).start();
        }
    }

    public void backToLoginForm(MouseEvent mouseEvent) {
        loginForm.setVisible(true);
        createNewAccountForm.setVisible(false);
    }
}
