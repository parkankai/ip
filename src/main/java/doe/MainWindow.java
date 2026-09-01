package doe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Controls the main chat window and forwards each submitted line to Doe.
 */
public class MainWindow extends AnchorPane {
    private static final Rectangle2D FULL_BANNER_VIEWPORT =
            new Rectangle2D(0, 0, 2172, 724);
    private static final Rectangle2D FACE_BANNER_VIEWPORT =
            new Rectangle2D(840, 520, 490, 150);
    private static final double FULL_BANNER_HEIGHT = 200;
    private static final double FACE_BANNER_HEIGHT = 72;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private StackPane bannerPanel;
    @FXML
    private ImageView bannerImage;

    private doe chatbot;
    private boolean isBannerCollapsed;
    private final Image userImage = loadImage("/images/UserPixel.png");
    private final Image doeImage = loadImage("/images/DoePixel.png");

    /** Keeps the most recent exchange visible as messages are added. */
    @FXML
    public void initialize() {
        bannerImage.setImage(loadImage("/images/TerminalBannerOption1.png"));
        bannerImage.setViewport(FULL_BANNER_VIEWPORT);
        bannerImage.fitWidthProperty().bind(bannerPanel.widthProperty());

        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> updateBanner());
        scrollPane.viewportBoundsProperty().addListener(
                (observable, oldBounds, newBounds) -> updateBanner());
        dialogContainer.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            Platform.runLater(() -> {
                scrollPane.setVvalue(1.0);
                updateBanner();
            });
        });
    }

    /**
     * Supplies the application instance that owns Doe's task and command logic.
     *
     * @param doeApp Existing Doe application instance.
     */
    public void setDoe(doe doeApp) {
        chatbot = doeApp;
        dialogContainer.getChildren().add(DialogBox.getDoeDialog(chatbot.getWelcomeMessage(), doeImage));
    }

    /** Sends the text field contents to Doe and displays both sides of the exchange. */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (chatbot == null || input.isBlank()) {
            return;
        }

        String response = chatbot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDoeDialog(response, doeImage));
        userInput.clear();
    }

    /** Collapses the banner to its face while the scrollable conversation is away from the top. */
    private void updateBanner() {
        boolean conversationOverflows =
                dialogContainer.getHeight() > scrollPane.getViewportBounds().getHeight();
        boolean shouldCollapse = conversationOverflows && scrollPane.getVvalue() > 0.02;
        if (shouldCollapse == isBannerCollapsed) {
            return;
        }

        isBannerCollapsed = shouldCollapse;
        double height = shouldCollapse ? FACE_BANNER_HEIGHT : FULL_BANNER_HEIGHT;
        bannerImage.setViewport(shouldCollapse ? FACE_BANNER_VIEWPORT : FULL_BANNER_VIEWPORT);
        bannerImage.setFitHeight(height);
        bannerPanel.setMinHeight(height);
        bannerPanel.setPrefHeight(height);
        bannerPanel.setMaxHeight(height);
    }

    private static Image loadImage(String resourcePath) {
        return new Image(MainWindow.class.getResourceAsStream(resourcePath));
    }
}
