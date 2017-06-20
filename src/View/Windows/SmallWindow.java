package View.Windows;

import Model.Setting;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SmallWindow {
	
	public Stage window;
	private Scene scene;
	public Pane MainPane;

	public SmallWindow(String Name) {
		window = new Stage();
		window.setTitle(Name);
		MainPane = new Pane();
		MainPane.setStyle("-fx-background-color: #a7bfe5, linear-gradient(to top left, #a7bfe5, #e6f3ff); -fx-opacity: 0.8;");
		scene = new Scene(MainPane, (Setting.CurrentWidth * 0.4), (Setting.CurrentHeight * 0.4));
		scene.getStylesheets().add("/View/Windows/MainStyle.css");
		window.setScene(scene);
		window.setOnCloseRequest(e -> window.hide());
		window.setResizable(false);
		window.hide();
	}
	
	public void use(){
		window.show();
		window.requestFocus();
	}

}
