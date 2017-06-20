package View.OrderKalkylObjects;

import Model.Setting;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CustomerDisplay {
	
	Pane ContentPane;
	
	Label[] CustomerLabel;
	Label[] ContactPersonLabel;

	public CustomerDisplay() {
	
		ContentPane = new Pane();
		ContentPane.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.55));
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.5), (Setting.CurrentHeight * 0.15));
		
		Label Header = new Label("Kund");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0));
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header);
		
		CustomerLabel = new Label[4];
		
		for(int i = 0; i < 4; i++){
			CustomerLabel[i] = new Label("TEST LABEL FOR DD");
			CustomerLabel[i].setStyle("-fx-font-size: 16pt");
			CustomerLabel[i].relocate(Setting.CurrentWidth * ((0.12 * i) + 0.01), Setting.CurrentHeight * 0.04);
			ContentPane.getChildren().add(CustomerLabel[i]);
		}
		
		Label Header1 = new Label("Kontaktperson");
		Header1.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.07));
		Header1.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header1);
		
		ContactPersonLabel = new Label[3];
		
		for(int i = 0; i < 3; i++){
			ContactPersonLabel[i] = new Label("TEST LABEL FOR DD");
			ContactPersonLabel[i].setStyle("-fx-font-size: 16pt");
			ContactPersonLabel[i].relocate(Setting.CurrentWidth * ((0.14 * i) + 0.01), Setting.CurrentHeight * 0.11);
			ContentPane.getChildren().add(ContactPersonLabel[i]);
		}
		
	}
	
	public void setCustomer(String Customer, String CustomerPhone, String CustomerEmail, String CustomerWeb){
		CustomerLabel[0].setText(Customer);
		CustomerLabel[1].setText(CustomerPhone);
		CustomerLabel[2].setText(CustomerEmail);
		CustomerLabel[3].setText(CustomerWeb);
	}
	
	public void setContactPerson(String Name, String Phone, String Email){
		ContactPersonLabel[0].setText(Name);
		ContactPersonLabel[1].setText(Phone);
		ContactPersonLabel[2].setText(Email);
		
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	
}
