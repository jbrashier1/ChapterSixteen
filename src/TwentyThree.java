import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Jack Brashier
 * Date: 11/24/2017
 * This program is free to download, modify, and distribute with no restrictions.
 */

public class TwentyThree extends Application {
    public int count = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Textfields for prompting user input
        TextField speedField = new TextField();
        speedField.setPromptText("1000");
        TextField imgPrefix = new TextField();
        imgPrefix.setPromptText("L");
        TextField imgNum = new TextField();
        imgNum.setPromptText("52");
        TextField audAddress = new TextField();
        audAddress.setPromptText("http://www.cs.armstrong.edu/liang/common/audio/anthem/anthem2.mp3");

        // Default values for user input fields.
        int speed = 1000;
        char prefix = 'L';
        int num = 52;

        // Label for returning input
        final Label speedLabel = new Label();
        final Label prefLabel = new Label();
        final Label numLabel = new Label();
        final Label audLabel = new Label();

        // Submit button reads text input
        Button submit = new Button("Submit");
        submit.setPrefSize(150,20);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(speedField.getText() != null && !speedField.getText().isEmpty() && imgPrefix.getText() != null && !imgPrefix.getText().isEmpty() && imgNum.getText() != null && !imgNum.getText().isEmpty() && audAddress.getText() != null && !audAddress.getText().isEmpty()) {
                    speedLabel.setText(speedField.getText());
                    prefLabel.setText(imgPrefix.getText());
                    numLabel.setText(imgNum.getText());
                    audLabel.setText(audAddress.getText());
                }

            }
        });

        // Media player referencing a certain file
        Media media = new Media(audLabel.getText());
        MediaPlayer mediaplayer = new MediaPlayer(media);

        // Creates an array list and fills it with the specified number of frames
        ArrayList<Image> gifFrames = new ArrayList<Image>();
        for(int i = 0; i < Integer.parseInt(numLabel.getText()); i++) {
            gifFrames.add(i, new Image("File:image/" + prefLabel.getText() + Integer.toString(i) + ".gif"));
        }

        //Default Image
        ImageView imageview = new ImageView(new Image("File:/image/L1.gif"));

        // Labels
        Label speedL = new Label("Animation Speed:");
        Label prefL = new Label("Image File Prefix:");
        Label numL = new Label("Number of Images:");
        Label audL = new Label("Audio File URL:");

        // VBox for holding labels
        VBox vbLabels = new VBox();
        vbLabels.setSpacing(12);
        vbLabels.getChildren().addAll(speedL, prefL, numL, audL);

        // VBox for holding text fields
        VBox vbox = new VBox();
        vbox.getChildren().addAll(speedField, imgPrefix, imgNum, audAddress);

        // Gridpane for displaying the three buttons below
        GridPane pane = new GridPane();

        // Scene sets size of the window
        Scene scene = new Scene(pane, 450, 450);

        // Sets title to "16.22"
        primaryStage.setTitle("16.22");

        // Stop button halts the audio
        Button stop = new Button("Stop");
        stop.setPrefSize(150,20);
        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediaplayer.seek(Duration.ZERO);
                mediaplayer.pause();
            }
        });

        // Loop button sets the audio to be repeated when it is finished
        Button loop = new Button("Loop");
        loop.setPrefSize(160,20);
        loop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediaplayer.setOnEndOfMedia(new Runnable() {
                    public void run() {
                        mediaplayer.seek(Duration.ZERO);
                    }
                });
            }
        });

        // Play button plays the audio when pressed, will start over if audio was stopped with stop button
        Button play = new Button("Play");
        play.setPrefSize(150,20);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediaplayer.play();
            }
        });

        // Timer for refreshing the image, showing the "gif"
        long delay = Integer.parseInt(speedLabel.getText()) / Integer.parseInt(numLabel.getText());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                imageview.setImage(gifFrames.get(count++));
                if (count >= gifFrames.size()) {
                    count = 0;
                }
            }
        }, 0, delay);

        // Adding elements to the pane
        pane.add(play, 0, 0);
        pane.add(loop, 1, 0);
        pane.add(stop, 2, 0);
        pane.add(vbLabels, 0, 2);
        pane.add(vbox, 1, 2);
        pane.add(submit, 1, 3);
        pane.add(imageview, 1, 4);

        // Adding scene and showing stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
