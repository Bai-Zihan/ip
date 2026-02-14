package lime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Renders a dialog bubble with text and an avatar image.
 */
public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

    /**
     * Creates a dialog box with the given text and avatar.
     *
     * @param l dialog text
     * @param iv avatar image
     */
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

        setAlignment(Pos.TOP_RIGHT);
        setSpacing(10);
        setPadding(new Insets(10));

        getChildren().addAll(text, displayPicture);
    }

    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(tmp);
        getChildren().setAll(tmp);
    }

    /**
     * Creates a dialog box styled for user messages.
     *
     * @param l dialog text
     * @param iv avatar image
     * @return user dialog box
     */
    public static DialogBox getUserDialog(String l, Image iv) {
        var db = new DialogBox(l, iv);
        applyBubbleStyle(db, "#32CD32", "white");
        return db;
    }

    /**
     * Creates a dialog box styled for Lime's messages.
     *
     * @param l dialog text
     * @param iv avatar image
     * @return Lime dialog box
     */
    public static DialogBox getLimeDialog(String l, Image iv) {
        var db = new DialogBox(l, iv);
        db.flip();
        applyBubbleStyle(db, "#f0f0f0", "black");
        return db;
    }

    private static void applyBubbleStyle(DialogBox dialogBox, String background, String textColor) {
        dialogBox.text.setStyle("-fx-background-color: " + background + "; " +
                "-fx-text-fill: " + textColor + "; " +
                "-fx-background-radius: 15px; " +
                "-fx-padding: 10px; " +
                "-fx-font-size: 14px;");
    }
}
