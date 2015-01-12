package ro.pub.cs.aipi.lab09.graphicuserinterface;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.jms.JMSException;

import ro.pub.cs.aipi.lab09.communicator.PublishSubscribe;
import ro.pub.cs.aipi.lab09.general.Constants;

public class MessageExchange {

    private Stage applicationStage;
    private Scene applicationScene;

    private MessageExchange messageExchange;

    private PublishSubscribe communicator;
    private ContactsList contactsList;

    private String currentUserName, interlocutorUserName;

    @FXML
    private TextField interlocutorUserNameTextField;
    @FXML
    private TextArea conversationHistoryTextArea, messageContentTextArea;
    @FXML
    private Button sendMessageButton;
    @FXML
    private Label errorLabel;

    public MessageExchange(String currentUserName, PublishSubscribe communicator, ContactsList contactsList) {
        messageExchange = this;
        this.currentUserName = currentUserName;
        this.communicator = communicator;
        this.contactsList = contactsList;
    }

    public MessageExchange(String currentUserName, String interlocutorUserName, PublishSubscribe communicator, ContactsList contactsList) {
        messageExchange = this;
        this.currentUserName = currentUserName;
        this.interlocutorUserName = interlocutorUserName;
        this.communicator = communicator;
        this.contactsList = contactsList;
    }

    public void start() {
        applicationStage = new Stage();
        try {
            applicationScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(Constants.MESSAGE_EXCHANGE_FXML_FILE)));
        } catch (Exception exception) {
            System.out.println("An exception has occurred: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
        applicationStage.setTitle(Constants.APPLICATION_TITLE);
        applicationStage.getIcons().add(new Image(getClass().getResource(Constants.ICON_FILE_NAME).toExternalForm()));
        interlocutorUserNameTextField = (TextField) applicationScene.lookup("#interlocutorUserNameTextField");
        if (interlocutorUserName != null && !interlocutorUserName.isEmpty()) {
            interlocutorUserNameTextField.setText(interlocutorUserName);
            interlocutorUserNameTextField.setEditable(false);
        }
        sendMessageButton = (Button) applicationScene.lookup("#sendMessageButton");
        sendMessageButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                interlocutorUserName = interlocutorUserNameTextField.getText();
                messageContentTextArea = (TextArea) applicationScene.lookup("#messageContentTextArea");
                String messageContent = messageContentTextArea.getText();
                if (interlocutorUserName.isEmpty() || messageContent.isEmpty()) {
                    errorLabel = (Label) applicationScene.lookup("#errorLabel");
                    errorLabel.setText(Constants.NO_INTERLOCUTOR_MESSAGE_ERROR);
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(Constants.MESSAGE_ERROR_TIMEOUT), errorLabel);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setAutoReverse(false);
                    fadeTransition.play();
                } else {
                    try {
                        communicator.publish(interlocutorUserName, messageContent);
                    } catch (JMSException | IOException exception) {
                        System.out.println("An exception has occurred: " + exception.getMessage());
                        if (Constants.DEBUG) {
                            exception.printStackTrace();
                        }
                    }
                    interlocutorUserNameTextField.setEditable(false);
                    conversationHistoryTextArea = (TextArea) applicationScene.lookup("#conversationHistoryTextArea");
                    String conversationHistory = conversationHistoryTextArea.getText();
                    GregorianCalendar messageDateTime = new GregorianCalendar();
                    if (conversationHistory.isEmpty()) {
                        conversationHistoryTextArea.setText(currentUserName + "(" + messageDateTime.get(Calendar.DAY_OF_MONTH) + "/" + messageDateTime.get(Calendar.MONTH) + "/" + messageDateTime.get(Calendar.YEAR) + " " + messageDateTime.get(Calendar.HOUR_OF_DAY) + ":" + messageDateTime.get(Calendar.MINUTE) + ":" + messageDateTime.get(Calendar.SECOND) + ")> " + messageContent);
                        contactsList.handleCurrentMessagingWindows(interlocutorUserName, messageExchange);
                    } else {
                        conversationHistoryTextArea.appendText("\n" + currentUserName + "(" + messageDateTime.get(Calendar.DAY_OF_MONTH) + "/" + messageDateTime.get(Calendar.MONTH) + "/" + messageDateTime.get(Calendar.YEAR) + " " + messageDateTime.get(Calendar.HOUR_OF_DAY) + ":" + messageDateTime.get(Calendar.MINUTE) + ":" + messageDateTime.get(Calendar.SECOND) + ")> " + messageContent);
                    }
                    messageContentTextArea.setText(Constants.EMPTY_STRING);
                }
            }
        });
        applicationStage.setScene(applicationScene);
        applicationStage.show();
    }

    public void show() {
        applicationStage.show();
    }

    public void hide() {
        applicationStage.hide();
    }

    public void handleConversationHistory(String messageContent, GregorianCalendar messageDateTime) {
        conversationHistoryTextArea = (TextArea) applicationScene.lookup("#conversationHistoryTextArea");
        String conversationHistory = conversationHistoryTextArea.getText();
        if (conversationHistory.isEmpty()) {
            conversationHistoryTextArea.setText(interlocutorUserName + "(" + messageDateTime.get(Calendar.DAY_OF_MONTH) + "/" + messageDateTime.get(Calendar.MONTH) + "/" + messageDateTime.get(Calendar.YEAR) + " " + messageDateTime.get(Calendar.HOUR_OF_DAY) + ":" + messageDateTime.get(Calendar.MINUTE) + ":" + messageDateTime.get(Calendar.SECOND) + ")> " + messageContent);
            contactsList.handleCurrentMessagingWindows(interlocutorUserName, messageExchange);
        } else {
            conversationHistoryTextArea.appendText("\n" + interlocutorUserName + "(" + messageDateTime.get(Calendar.DAY_OF_MONTH) + "/" + messageDateTime.get(Calendar.MONTH) + "/" + messageDateTime.get(Calendar.YEAR) + " " + messageDateTime.get(Calendar.HOUR_OF_DAY) + ":" + messageDateTime.get(Calendar.MINUTE) + ":" + messageDateTime.get(Calendar.SECOND) + ")> " + messageContent);
        }
    }
}
