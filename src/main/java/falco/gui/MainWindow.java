package falco.gui;

import falco.interact.Falco;
import falco.interact.Ui;
import falco.interact.UiResponse;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Falco falco;
    private UiResponse ui = new UiResponse();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image falcoImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        greetings();
    }

    /** Injects the Falco instance */
    public void setFalco(Falco f) {
        falco = f;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Falco's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = falco.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getFalcoDialog(response, falcoImage)
        );
        userInput.clear();

        // Close GUI if response is the trigger
        if (response.equals(ui.sayGoodbye())) {
            // Delay slightly so user sees the last message
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    @FXML
    public void greetings() {
        dialogContainer.getChildren().addAll(
                DialogBox.getFalcoDialog(ui.sayGreetings(), falcoImage)
        );
    }
}
