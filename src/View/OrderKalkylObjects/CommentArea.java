package View.OrderKalkylObjects;

import Model.Setting;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class CommentArea {
	
	Pane ContentPane;
	
	public TextArea CommentTa;
	
	public Button BtnUpdate;
	
	public CommentArea() {
	
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.33), (Setting.CurrentHeight * 0.3));
		ContentPane.relocate((Setting.CurrentWidth * 0.57), (Setting.CurrentHeight * 0.08));
		
		Label Header = new Label("Kommentar");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.01));

		BtnUpdate = new Button("Spara");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.03));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.09), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");
		
		CommentTa = new TextArea();
		CommentTa.setPrefSize((Setting.CurrentWidth * 0.315), (Setting.CurrentHeight * 0.22));
		CommentTa.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.05));
		CommentTa.setStyle("-fx-font-size: 14pt");

		ContentPane.getChildren().addAll(Header, CommentTa, BtnUpdate);
		
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public void createMode(){
		BtnUpdate.setVisible(false);
	}
	
	public void viewMode(){
		BtnUpdate.setVisible(true);
	}
	
	public String Comment = "";
	
	public void setInsertVariables(){
		Comment = CommentTa.getText();
	}
	
	public void clearInsertFields(){
		CommentTa.clear();
	}

	public void setViewData(String CommentValue){
		CommentTa.setText(CommentValue);
	}
	
	
}
