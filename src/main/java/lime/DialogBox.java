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

        initializeText();
        initializeImage(iv);
        configureLayout();
    }

    private void initializeText() {
        text.setWrapText(true);
        text.setMinHeight(35);
    }

    /**
     * Configures the avatar image view and applies a circular clip.
     *
     * @param image avatar image to render
     */
    private void initializeImage(Image image) {
        double imageSize = 45.0;
        displayPicture.setFitWidth(imageSize);
        displayPicture.setFitHeight(imageSize);
        displayPicture.setPreserveRatio(true);
        displayPicture.setSmooth(true);
        applyImageViewport(image);
        Circle clip = new Circle(imageSize / 2, imageSize / 2, imageSize / 2);
        displayPicture.setClip(clip);
    }

    /**
     * Centers and crops the image to keep the avatar framing consistent.
     *
     * @param image source image for the viewport
     */
    private void applyImageViewport(Image image) {
        double width = image.getWidth();
        double height = image.getHeight();
        double minSide = Math.min(width, height);
        double zoomFactor = 0.6;
        displayPicture.setViewport(new javafx.geometry.Rectangle2D(
                (width - minSide * zoomFactor) / 2,
                (height - minSide * zoomFactor) / 2,
                minSide * zoomFactor,
                minSide * zoomFactor
        ));
    }

    /**
     * Sets bubble alignment and spacing for the dialog row.
     */
    private void configureLayout() {
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(12);
        setPadding(new Insets(10, 15, 10, 15));
        getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog direction to align the avatar on the left.
     */
    private void flip() {
        setAlignment(Pos.CENTER_LEFT);
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
        applyBubbleStyle(db, "#4F7942", "white", "18px 5px 18px 18px");
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
        applyBubbleStyle(db, "#f0f0f0", "black", "5px 18px 18px 18px");
        return db;
    }


    /**
     * Applies refined bubble styling with horizontal breathing room.
     */
    private static void applyBubbleStyle(DialogBox db, String bg, String color, String corners) {
        db.text.setStyle("-fx-background-color: " + bg + "; " +
                "-fx-text-fill: " + color + "; " +
                "-fx-background-radius: " + corners + "; " +
                "-fx-padding: 8px 15px 8px 15px; " +
                "-fx-font-size: 14px;");
    }
}
