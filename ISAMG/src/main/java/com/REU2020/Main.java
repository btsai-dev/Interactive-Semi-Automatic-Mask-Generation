package com.REU2020;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            program(primaryStage);
        } catch(Exception e){
            displayError(e);
        }
    }

    private static void program(Stage primaryStage) throws Exception{
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(primaryStage);
        File saveDir = new File(directory.getAbsolutePath() + "/segmentation");
        saveDir.mkdirs();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Display.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(
                loader.load()
        );

        stage.setTitle("Select");
        stage.setResizable(true);
        // primaryStage.setScene(defalt);
        Controller controller = loader.<Controller>getController();

        File[] dirList = directory.listFiles();

        if (dirList == null) {
            Platform.exit();
            System.exit(0);
        }
        for (File file : dirList){
            System.out.printf("Attempting to read %s\n", file.getName());
            BufferedImage img;
            try {
                img = ImageIO.read(file);
            } catch(Exception e){
                System.out.println("Could not open file. Could be a folder.");
                continue;
            }
            if (img == null) {
                System.out.println("File is not an image.");
                continue;
            }

            Image image = SwingFXUtils.toFXImage(img, null);
            controller.saveDir = saveDir;
            controller.initData(image, file);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    public static void main(String[] args){
        try {
            launch(args);
        } catch(Exception e){
            System.out.println("Uncaught Exception that broken even the standard alert box!.");
        }
    }

    private static void displayError(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Uncaught exception!");
        alert.setHeaderText(null);
        alert.setContentText("Something unexpected and really bad happened.");

// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
