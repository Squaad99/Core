package View.CustomerObject;

import Model.SM;
import Model.Setting;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CustomerInsert {
	
	Pane ContentPane;
	TextField[] InputField;
	Button AddBtn;

	public CustomerInsert() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.36), (Setting.CurrentHeight * 0.35));
		ContentPane.relocate((Setting.CurrentWidth * 0.5), (Setting.CurrentHeight * 0.05));
		ContentPane.setStyle("-fx-border-color: black;");
		
		Label Header = new Label("Skapa ny kund");
		Header.relocate((Setting.CurrentWidth * 0.13), (Setting.CurrentHeight * 0));
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header);
		
		Label[] DescLabel = new Label[6];
		InputField = new TextField[6];
		
		int x = 0;
		
		for (int i = 0; i < 6; i++) {
			DescLabel[i] = new Label();
			DescLabel[i].setStyle("-fx-font-size: 16pt");
			
			InputField[i] = new TextField();
			InputField[i].setStyle("-fx-font-size: 14pt");
			InputField[i].setPrefWidth(Setting.CurrentWidth * 0.125);
			
			if(i < 3){
				DescLabel[i].relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * (0.08 + ( 0.06 * i)));
				InputField[i].relocate(Setting.CurrentWidth * 0.06, Setting.CurrentHeight * (0.075 + ( 0.06 * i)));
			}
			else{
				DescLabel[i].relocate(Setting.CurrentWidth * 0.19, Setting.CurrentHeight * (0.08 + ( 0.06 * x)));
				InputField[i].relocate(Setting.CurrentWidth * 0.23, Setting.CurrentHeight * (0.075 + ( 0.06 * x)));	
				x++;
			}
			ContentPane.getChildren().add(DescLabel[i]);
			ContentPane.getChildren().add(InputField[i]);
		}
		
		DescLabel[0].setText("Namn:");
		DescLabel[1].setText("Adress:");
		DescLabel[2].setText("Hemsida:");
		DescLabel[3].setText("Telefon:");
		DescLabel[4].setText("Mail:");
		DescLabel[5].setText("Org-nr:");
		InputField[0].setPromptText("Namn");
		InputField[1].setPromptText("Adress");
		InputField[2].setPromptText("Hemsida");
		InputField[3].setPromptText("Telefon");
		InputField[4].setPromptText("Mail");
		InputField[5].setPromptText("Org-nr");
		
		AddBtn = new Button("Lägg till");
		AddBtn.setPrefSize(Setting.CurrentWidth * 0.1, Setting.CurrentHeight * 0.04);
		AddBtn.setStyle("-fx-font-size: 16pt");
		AddBtn.relocate(Setting.CurrentWidth * 0.255, Setting.CurrentHeight * 0.26);
		ContentPane.getChildren().add(AddBtn);
	}
	
	public String name = "";
	public String address = "";
	public String web = "";
	public String phone = "";
	public String email = "";
	public String orgNr = "";
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public boolean checkInsertData(){
		if(InputField[0].getText().isEmpty()){
			SM.messageBox("Ange namn");
			return false;
		}
		else if(InputField[1].getText().isEmpty()){
			SM.messageBox("Ange adress");
			return false;
		}
		else if(InputField[2].getText().isEmpty()){
			SM.messageBox("Ange Hemsida");
			return false;
		}
		else if(InputField[3].getText().isEmpty()){
			SM.messageBox("Ange Telefon");
			return false;
		}
		else if(InputField[4].getText().isEmpty()){
			SM.messageBox("Ange Mail");
			return false;
		}
		else if(InputField[5].getText().isEmpty()){
			SM.messageBox("Ange Org-nr");
			return false;
		}
		return true;
	}
	
	public void setInsertVariables(){
		name = InputField[0].getText();
		address = InputField[1].getText();
		web = InputField[2].getText();
		phone = InputField[3].getText();
		email = InputField[4].getText();
		orgNr = InputField[5].getText();
	}
	
	public void clearAllFields(){
		for (int i = 0; i < 6; i++) {
			InputField[i].clear();
		}
	}
	
	public void addButton(EventHandler<ActionEvent> eventHandler){
		AddBtn.setOnAction(eventHandler);
	}
}
