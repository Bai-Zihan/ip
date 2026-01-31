package lime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.shape.Circle;

//Sets up the dialog box
public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    public DialogBox(String l, Image iv) {
        text = new Label(l);
        displayPicture = new ImageView(iv);

        text.setWrapText(true);
        text.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        double imageSize = 45.0;
        displayPicture.setFitWidth(imageSize);
        displayPicture.setFitHeight(imageSize);

        Circle clip = new Circle(imageSize / 2, imageSize / 2, imageSize / 2);
        displayPicture.setClip(clip);

        this.setAlignment(Pos.TOP_RIGHT);
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        this.getChildren().addAll(text, displayPicture);
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    //Acquires the message of the user
    public static DialogBox getUserDialog(String l, Image iv) {
        var db = new DialogBox(l, iv);
        db.text.setStyle("-fx-background-color: #32CD32; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 15px; " +
                "-fx-padding: 10px; " +
                "-fx-font-size: 14px;");
        return db;
    }

    //Acquires the message of Lime
    public static DialogBox getLimeDialog(String l, Image iv) {
        var db = new DialogBox(l, iv);
        db.flip();
        db.text.setStyle("-fx-background-color: #f0f0f0; " +
                "-fx-text-fill: black; " +
                "-fx-background-radius: 15px; " +
                "-fx-padding: 10px; " +
                "-fx-font-size: 14px;");
        return db;
    }
}