package ru.dfhub.dfe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;

public class Main extends Application {

    public enum Action {
        ENCRYPT, DECRYPT
    }

    private static Action action;
    private static File file;

    @Override
    public void start(Stage stage) throws Exception {
        VBox mainPane = new VBox();
        Text title = new Text("Welcome to DFE!");
        HBox fileChooseBox = new HBox();
        TextField fileName = new TextField("");
        TextField password = new TextField("Password");
        Button fileChooseButton = new Button("Choose file");
        Button actionButton = new Button("File not specified!");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*"),
                new FileChooser.ExtensionFilter("DFE encoded file", "*.dfe")
        );
        fileChooser.setTitle("Choose file to Encrypt/Decrypt");

        title.getStyleClass().addAll("title", "custom-font");
        HBox.setMargin(title, new Insets(0, 0, 5, 0));

        fileName.setPrefSize(230, 20);
        fileName.setEditable(false);
        HBox.setMargin(fileName, new Insets(0, 5, 0, 0));

        fileChooseBox.getChildren().addAll(fileName, fileChooseButton);
        VBox.setMargin(fileChooseBox, new Insets(0, 0, 5, 0));

        fileChooseButton.setOnAction(e -> {
            file = fileChooser.showOpenDialog(stage);
            if (file == null) return;
            fileName.setText(file.getName());

            if (file.getName().endsWith(".dfe")) {
                actionButton.setText("Decrypt");
                action = Action.DECRYPT;
            } else {
                actionButton.setText("Encrypt");
                action = Action.ENCRYPT;
            }
        });

        password.setMaxSize(320, 20);
        VBox.setMargin(password, new Insets(0, 0, 10, 0));

        actionButton.setOnAction(e -> {
            if (action == Action.ENCRYPT) {
                try {
                    EncryptDecrypt.encrypt(
                            file.getAbsolutePath(),
                            EncryptDecrypt.getEncryptionKey(password.getText())
                    );
                    actionButton.setText("Successfully encrypted!");
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileName.setText("");
                    password.setText("");
                }
            } else if (action == Action.DECRYPT) {
                try {
                    EncryptDecrypt.decrypt(
                            file.getAbsolutePath(),
                            EncryptDecrypt.getEncryptionKey(password.getText())
                    );
                    actionButton.setText("Successfully decrypted!");
                }
                catch (IllegalBlockSizeException ex) {
                    actionButton.setText("File was damaged!");
                }
                catch (BadPaddingException ex) {
                    actionButton.setText("Wrong password!");
                }
                finally {
                    fileName.setText("");
                    password.setText("");
                }
            } else {
                actionButton.setText("File not specified!");
            }
        });

        mainPane.setPadding(new Insets(10));

        mainPane.getChildren().addAll(title, fileChooseBox, password, actionButton);

        Scene scene = new Scene(mainPane, 400, 180);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("DF File Encryptor");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
