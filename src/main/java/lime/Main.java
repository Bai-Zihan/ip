package lime;

import javafx.application.Application;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

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

    /**
     * Builds and shows the primary stage with custom title bar and chat UI.
     *
     * @param stage primary application stage
     */
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

    /**
     * Creates the custom title bar with branding and window controls.
     *
     * @param stage primary stage for window actions
     * @return configured title bar
     */
    private HBox createCustomTitleBar(Stage stage) {
        HBox titleBar = createTitleBarContainer();
        HBox branding = createBranding();
        HBox windowButtons = createWindowButtons(stage);
        Region spacer = createSpacer();

        titleBar.getChildren().addAll(branding, spacer, windowButtons);
        configureDragHandlers(titleBar, stage);
        return titleBar;
    }

    /**
     * Creates the container for the title bar.
     *
     * @return styled title bar container
     */
    private HBox createTitleBarContainer() {
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #4F7942; -fx-padding: 10px; -fx-alignment: center-left;");
        return titleBar;
    }

    /**
     * Builds the icon and title text for the title bar.
     *
     * @return branding row
     */
    private HBox createBranding() {
        javafx.scene.image.ImageView appIcon = createAppIcon();
        javafx.scene.control.Label titleLabel = createTitleLabel();
        HBox branding = new HBox(appIcon, titleLabel);
        branding.setAlignment(Pos.CENTER_LEFT);
        return branding;
    }

    /**
     * Creates the application icon for the title bar.
     *
     * @return configured image view
     */
    private javafx.scene.image.ImageView createAppIcon() {
        javafx.scene.image.ImageView appIcon = new javafx.scene.image.ImageView(limeImage);
        double iconSize = 28.0;
        appIcon.setFitHeight(iconSize);
        appIcon.setFitWidth(iconSize);
        appIcon.setPreserveRatio(true);
        applyIconViewport(appIcon, limeImage);
        javafx.scene.shape.Circle clip = new javafx.scene.shape.Circle(iconSize / 2, iconSize / 2, iconSize / 2);
        appIcon.setClip(clip);
        return appIcon;
    }

    /**
     * Crops the app icon viewport to focus in the center.
     *
     * @param icon image view to update
     * @param image source image
     */
    private void applyIconViewport(javafx.scene.image.ImageView icon, Image image) {
        double width = image.getWidth();
        double height = image.getHeight();
        double side = Math.min(width, height) * 0.6;
        icon.setViewport(new javafx.geometry.Rectangle2D((width - side) / 2, (height - side) / 2, side, side));
    }

    /**
     * Creates the title label for the custom title bar.
     *
     * @return title label
     */
    private javafx.scene.control.Label createTitleLabel() {
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("Lime");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 0 0 0 10;");
        return titleLabel;
    }

    /**
     * Creates the minimize and close buttons for the custom title bar.
     *
     * @param stage primary stage for window actions
     * @return container with window buttons
     */
    private HBox createWindowButtons(Stage stage) {
        javafx.scene.control.Label minimizeButton = createTitleBarButton("  -  ", 22);
        minimizeButton.setOnMouseClicked(event -> stage.setIconified(true));

        javafx.scene.control.Label closeButton = createTitleBarButton("  x  ", 18);
        closeButton.setOnMouseClicked(event -> stage.close());

        return new HBox(minimizeButton, closeButton);
    }

    /**
     * Creates a label styled as a title bar button.
     *
     * @param text button text
     * @param fontSize font size in pixels
     * @return styled label
     */
    private javafx.scene.control.Label createTitleBarButton(String text, int fontSize) {
        javafx.scene.control.Label button = new javafx.scene.control.Label(text);
        button.setStyle("-fx-text-fill: white; -fx-font-size: " + fontSize + "px; -fx-cursor: hand;");
        return button;
    }

    /**
     * Creates a spacer that pushes window buttons to the right.
     *
     * @return stretchable spacer
     */
    private Region createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    /**
     * Enables dragging the window via the custom title bar.
     *
     * @param titleBar title bar node
     * @param stage primary stage to move
     */
    private void configureDragHandlers(HBox titleBar, Stage stage) {
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    /**
     * Creates the main content pane containing chat controls.
     *
     * @return content pane
     */
    private AnchorPane buildContentPane() {
        AnchorPane contentPane = new AnchorPane();
        contentPane.getChildren().addAll(scrollPane, userInput, sendButton);
        return contentPane;
    }

    /**
     * Configures the stage and applies optional styling.
     *
     * @param stage primary stage
     * @param rootLayout root layout node
     */
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
        configureRootLayout(rootLayout);
        configureScrollPane();
        configureDialogContainer();
        configureUserInput();
        configureSendButton();
        configureAnchors(contentPane);
        rootLayout.requestFocus();
    }

    /**
     * Applies top-level styling to the root layout.
     *
     * @param rootLayout root container
     */
    private void configureRootLayout(VBox rootLayout) {
        rootLayout.setStyle("-fx-background-color: white; -fx-border-color: #4F7942; "
                + "-fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");
    }

    /**
     * Configures the scroll pane appearance and behavior.
     */
    private void configureScrollPane() {
        scrollPane.setPrefSize(385, 500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: white; -fx-border-color: transparent;");
    }

    /**
     * Configures the container that holds dialog rows.
     */
    private void configureDialogContainer() {
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.setSpacing(15);
        dialogContainer.setPadding(new Insets(15));
        dialogContainer.setStyle("-fx-background-color: #ffffff;");
    }

    /**
     * Configures the user input field styling and prompt.
     */
    private void configureUserInput() {
        userInput.setPrefWidth(285.0);
        userInput.setPrefHeight(40.0);
        userInput.setPromptText("Ask Lime to manage your agenda...");
        userInput.setStyle("-fx-background-color: #f4f4f7; "
                + "-fx-background-radius: 20px; "
                + "-fx-border-color: #e0e0e0; "
                + "-fx-border-radius: 20px; "
                + "-fx-padding: 0 15 0 15; "
                + "-fx-font-size: 14px;");
    }

    /**
     * Configures the send button styling.
     */
    private void configureSendButton() {
        sendButton.setPrefWidth(70.0);
        sendButton.setPrefHeight(40.0);
        sendButton.setStyle("-fx-background-color: #4F7942; "
                + "-fx-text-fill: white; "
                + "-fx-font-weight: bold; "
                + "-fx-background-radius: 20px; "
                + "-fx-font-size: 14px; "
                + "-fx-cursor: hand;");
    }

    /**
     * Positions controls within the content pane.
     *
     * @param contentPane anchor container
     */
    private void configureAnchors(AnchorPane contentPane) {
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

    /**
     * Registers input and scroll handlers.
     */
    private void registerHandlers() {
        sendButton.setOnMouseClicked((event) -> handleUserInput());
        userInput.setOnAction((event) -> handleUserInput());
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Processes the current user input and renders the response.
     */
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

        if (input.trim().equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> javafx.application.Platform.exit());
            delay.play();
        }
    }
}
