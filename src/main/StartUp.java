package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.WelkomScreenController;

public class StartUp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    WelkomScreenController controller = new WelkomScreenController();
    Scene scene = new Scene(controller);

    primaryStage.setTitle("MineSweeper");
    primaryStage.setScene(scene);
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

}
