package lime;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX application that wires the UI to the Lime chatbot logic.
 * Features a custom green title bar and a polished chat interface.
 */
public class Main extends Application {

    private Lime lime = new Lime("data/tasks.txt");
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    private final Image userImage = new Image(getClass().getResourceAsStream("/images/user.png"));
    private final Image limeImage = new Image(getClass().getResourceAsStream("/images/lime.png"));

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) {
        initializeComponents();

        HBox customTitleBar = createCustomTitleBar(stage);
        AnchorPane contentPane = buildContentPane();

        VBox rootLayout = new VBox(customTitleBar, contentPane);
        VBox.setVgrow(contentPane, Priority.ALWAYS);

        configureStage(stage, rootLayout);
        configureLayout(rootLayout, contentPane);
        registerHandlers();

        // Initial welcome message
        String welcomeMessage = "Refreshing to see you!\nI'm Lime. What's on the menu today?";
        dialogContainer.getChildren().add(
                DialogBox.getLimeDialog(welcomeMessage, limeImage)
        );
    }

    /**
     * Initializes UI components. (Fixed duplicate definition error)
     */
    private void initializeComponents() {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);
        userInput = new TextField();
        sendButton = new Button("Send");
    }

    private HBox createCustomTitleBar(Stage stage) {
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #4F7942; -fx-padding: 10px; -fx-alignment: center-left;");

        javafx.scene.image.ImageView appIcon = new javafx.scene.image.ImageView(limeImage);
        double iconSize = 28.0;
        appIcon.setFitHeight(iconSize);
        appIcon.setFitWidth(iconSize);
        appIcon.setPreserveRatio(true);

        double w = limeImage.getWidth();
        double h = limeImage.getHeight();
        double side = Math.min(w, h) * 0.6;
        appIcon.setViewport(new javafx.geometry.Rectangle2D((w - side) / 2, (h - side) / 2, side, side));

        javafx.scene.shape.Circle clip = new javafx.scene.shape.Circle(iconSize / 2, iconSize / 2, iconSize / 2);
        appIcon.setClip(clip);

        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("Lime");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 0 0 0 10;");

        HBox branding = new HBox(appIcon, titleLabel);
        branding.setAlignment(Pos.CENTER_LEFT);

        javafx.scene.control.Label minimizeButton = new javafx.scene.control.Label("  -  ");
        minimizeButton.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-cursor: hand;");
        minimizeButton.setOnMouseClicked(event -> stage.setIconified(true));

        javafx.scene.control.Label closeButton = new javafx.scene.control.Label("  x  ");
        closeButton.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand;");
        closeButton.setOnMouseClicked(event -> stage.close());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        titleBar.getChildren().addAll(branding, spacer, new HBox(minimizeButton, closeButton));

        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        return titleBar;
    }

    private AnchorPane buildContentPane() {
        AnchorPane contentPane = new AnchorPane();
        contentPane.getChildren().addAll(scrollPane, userInput, sendButton);
        return contentPane;
    }

    private void configureStage(Stage stage, VBox rootLayout) {
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(rootLayout, 400, 600);
        scene.setFill(Color.TRANSPARENT);

        java.net.URL cssResource = getClass().getResource("/style.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Polishes the GUI layout to match the LeadBot style.
     */
    private void configureLayout(VBox rootLayout, AnchorPane contentPane) {
        rootLayout.setStyle("-fx-background-color: white; -fx-border-color: #4F7942; "
                + "-fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");

        scrollPane.setPrefSize(385, 500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: white; -fx-border-color: transparent;");

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.setSpacing(15);
        dialogContainer.setPadding(new Insets(15));
        dialogContainer.setStyle("-fx-background-color: #ffffff;");

        userInput.setPrefWidth(285.0);
        userInput.setPrefHeight(40.0);
        userInput.setPromptText("Reply to Lime...");
        userInput.setStyle("-fx-background-color: #f4f4f7; "
                + "-fx-background-radius: 20px; "
                + "-fx-border-color: #e0e0e0; "
                + "-fx-border-radius: 20px; "
                + "-fx-padding: 0 15 0 15; "
                + "-fx-font-size: 14px;");

        sendButton.setPrefWidth(70.0);
        sendButton.setPrefHeight(40.0);
        sendButton.setStyle("-fx-background-color: #4F7942; "
                + "-fx-text-fill: white; "
                + "-fx-font-weight: bold; "
                + "-fx-background-radius: 20px; "
                + "-fx-font-size: 14px; "
                + "-fx-cursor: hand;");

        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 65.0);

        contentPane.setStyle("-fx-border-color: #eeeeee transparent transparent transparent; -fx-border-width: 1;");

        AnchorPane.setBottomAnchor(userInput, 12.0);
        AnchorPane.setLeftAnchor(userInput, 12.0);

        AnchorPane.setBottomAnchor(sendButton, 12.0);
        AnchorPane.setRightAnchor(sendButton, 12.0);
    }

    private void registerHandlers() {
        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }

        String response = lime.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getLimeDialog(response, limeImage)
        );
        userInput.clear();
    }
}