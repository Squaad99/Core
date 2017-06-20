package View.OrderKalkylObjects;

import Model.SM;
import Model.Setting;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class InsertCustomer {
	
	Pane ContentPane;

	@SuppressWarnings("unchecked")
	public ComboBox<String>[] CustomerInsert;
	
	public InsertCustomer() {
		
		ContentPane = new Pane();
		ContentPane.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.45));
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.4), (Setting.CurrentHeight * 0.18));
		
		Label Header1 = new Label("Kund*");
		Header1.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header1.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.03);
		ContentPane.getChildren().add(Header1);
		
		Label Header2 = new Label("Kontakt*");
		Header2.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header2.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.08);
		ContentPane.getChildren().add(Header2);
		
		CustomerInsert = new ComboBox[3];
		for (int i = 0; i < 2; i++) {
			CustomerInsert[i] = new ComboBox<String>();
			CustomerInsert[i].setMinWidth(Setting.CurrentWidth * 0.2);
			CustomerInsert[i].setStyle("-fx-font-size: 14pt;");
			CustomerInsert[i].relocate(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * (0.03 + (0.05 * i)));
			ContentPane.getChildren().add(CustomerInsert[i]);
		}
		
		
		SM.setCustomerList(CustomerInsert[0]);
		
		CustomerInsert[0].setOnAction(e->{
			SM.setContactPersonBox(CustomerInsert[0], CustomerInsert[1]);
		});
	}
	
	public boolean checkInsertData(){
		if(CustomerInsert[0].getSelectionModel().isEmpty()){
			SM.messageBox("Ange kund!");
			return false;
		}
		if(CustomerInsert[1].getSelectionModel().isEmpty()){
			SM.messageBox("Ange kontaktperson!");
			return false;
		}
		
		return true;
	}
	
	public int CustomerId;
	public int ContactPersonId;
	
	public void setInsertVariables(){
		CustomerId = SM.getSelectedCustomerId(CustomerInsert[0]);
		ContactPersonId = SM.getSelectedPersonId(CustomerInsert[1]);
	}
	
	public void clearAllFields(){
		SM.setCustomerList(CustomerInsert[0]);
		CustomerInsert[1].getItems().clear();
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	
	
}
