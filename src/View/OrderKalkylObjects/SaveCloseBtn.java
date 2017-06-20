package View.OrderKalkylObjects;

import Model.Setting;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class SaveCloseBtn{
	
	public Button Button;
	
	public SaveCloseBtn() {
		
		Button = new Button("Stäng/Spara");
		Button.setPrefSize((Setting.CurrentWidth * 0.1), (Setting.CurrentHeight * 0.06));
		Button.relocate((Setting.CurrentWidth * 0.79), (Setting.CurrentHeight * 0.82));
		Button.setStyle("-fx-font-size: 16pt");
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(Button);
	}
	
	public void saveButton(EventHandler<ActionEvent> eventHandler){
		Button.setOnAction(eventHandler);
	}
	
	public void saveMode() {
		Button.setText("Spara");
	}
	
	public void closeMode() {
		Button.setText("Stäng");
	}

}
