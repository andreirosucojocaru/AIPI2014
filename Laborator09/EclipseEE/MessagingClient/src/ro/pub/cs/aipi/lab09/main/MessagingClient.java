package ro.pub.cs.aipi.lab09.main;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.pub.cs.aipi.lab09.graphicuserinterface.Authentication;

public class MessagingClient extends Application {

	@Override
    public void start(Stage mainStage) throws Exception {
        new Authentication().start();
    }

    public static void main(String[] args) {
        launch(args);
    }
 
}
