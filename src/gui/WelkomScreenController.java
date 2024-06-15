package gui;

import java.io.IOException;

import domain.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class WelkomScreenController extends GridPane {
  // private DomeinController dc;

  public WelkomScreenController() {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("./WelkomScreen.fxml"));
    loader.setRoot(this);
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void LevelBtn_action(ActionEvent event) {
    System.out.println("lol");
  }
}
