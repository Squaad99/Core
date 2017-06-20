package View.CustomerObject;

import Model.SM;
import Model.Setting;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ContactInsert {
		
	Pane ContentPane;
	
	public ComboBox<String> SelectCustomerBox;
	public TextField[] InputField;
	Button AddBtn;
	
	//INSERT VARIABLES
	public String customer = "";
	public String name = "";
	public String lastname = "";
	public String phone = "";
	public String mail = "";
	
	public ContactInsert() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.36), (Setting.CurrentHeight * 0.35));
		ContentPane.relocate((Setting.CurrentWidth * 0.5), (Setting.CurrentHeight * 0.45));
		ContentPane.setStyle("-fx-border-color: black;");
		
		Label Header = new Label("Skapa ny kontaktperson");
		Header.relocate((Setting.CurrentWidth * 0.1), (Setting.CurrentHeight * 0));
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header);
		
		Label Header1 = new Label("Välj kund:");
		Header1.relocate((Setting.CurrentWidth * 0.07), (Setting.CurrentHeight * 0.05));
		Header1.setStyle("-fx-font-size: 18pt");
		ContentPane.getChildren().add(Header1);
		
		SelectCustomerBox = new ComboBox();
		SelectCustomerBox.setStyle("-fx-font-size: 14pt");
		SelectCustomerBox.setPrefWidth(Setting.CurrentWidth * 0.14);
		SelectCustomerBox.relocate((Setting.CurrentWidth * 0.13), (Setting.CurrentHeight * 0.05));
		ContentPane.getChildren().add(SelectCustomerBox);
		SM.setCustomerList(SelectCustomerBox);
		
		Label[] DescLabel = new Label[4];
		InputField = new TextField[4];
		
		int x = 0;
		
		for (int i = 0; i < 4; i++) {
			DescLabel[i] = new Label();
			DescLabel[i].setStyle("-fx-font-size: 16pt");
			
			InputField[i] = new TextField();
			InputField[i].setStyle("-fx-font-size: 14pt");
			InputField[i].setPrefWidth(Setting.CurrentWidth * 0.115);
			
			if(i < 2){
				DescLabel[i].relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * (0.13 + ( 0.07 * i)));
				InputField[i].relocate(Setting.CurrentWidth * 0.07, Setting.CurrentHeight * (0.1275 + ( 0.07 * i)));
			}
			else{
				DescLabel[i].relocate(Setting.CurrentWidth * 0.19, Setting.CurrentHeight * (0.13 + ( 0.07 * x)));
				InputField[i].relocate(Setting.CurrentWidth * 0.24, Setting.CurrentHeight * (0.1275 + ( 0.07 * x)));	
				x++;
			}
			ContentPane.getChildren().add(DescLabel[i]);
			ContentPane.getChildren().add(InputField[i]);
		}
		
		DescLabel[0].setText("Namn:");
		DescLabel[1].setText("Efternamn:");
		DescLabel[2].setText("Telefon:");
		DescLabel[3].setText("Mail:");
		
		InputField[0].setPromptText("Namn");
		InputField[1].setPromptText("Efternamn");
		InputField[2].setPromptText("Telefon");
		InputField[3].setPromptText("Mail");
		
		AddBtn = new Button("Lägg till");
		AddBtn.setPrefSize(Setting.CurrentWidth * 0.1, Setting.CurrentHeight * 0.04);
		AddBtn.setStyle("-fx-font-size: 16pt");
		AddBtn.relocate(Setting.CurrentWidth * 0.255, Setting.CurrentHeight * 0.26);
		ContentPane.getChildren().add(AddBtn);
		
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	public boolean checkDataInsert(){
		if(SelectCustomerBox.getSelectionModel().isEmpty()){
			SM.messageBox("Ange kund!");
			return false;
		}
		else if(InputField[0].getText().isEmpty()){
			SM.messageBox("Ange namn!");
			return false;
		}
		else if(InputField[1].getText().isEmpty()){
			SM.messageBox("Ange efternamn!");
			return false;
		}
		else if(InputField[2].getText().isEmpty()){
			SM.messageBox("Ange telefon!");
			return false;
		}
		else if(InputField[3].getText().isEmpty()){
			SM.messageBox("Ange mail!");
			return false;
		}
		return true;
	}

	public void setInsertVariables(){
		customer = SelectCustomerBox.getValue().toString();
		name = InputField[0].getText();
		lastname = InputField[1].getText();
		phone = InputField[2].getText();
		mail = InputField[3].getText();
	}
	
	public void clearAllFields(){
		SelectCustomerBox.getSelectionModel().clearSelection();
		for(int i = 0; i < 4; i++){
			InputField[i].clear();
		}
	}
	
	public void addButton(EventHandler<ActionEvent> eventHandler){
		AddBtn.setOnAction(eventHandler);
	}
}
