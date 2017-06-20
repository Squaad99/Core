
package View.OrderKalkylObjects;

import java.time.LocalDate;

import Model.SM;
import Model.Setting;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ClientInsertKalkyl {
	
	Pane ContentPane;
	
	public ComboBox<String> chooseClientsBox;
	public ComboBox<String>[] CustomerBox;
	public ComboBox<String>[] ContactPersonBox;
	public DatePicker[] ClientDatePicker;
	
	@SuppressWarnings("unchecked")
	public ClientInsertKalkyl() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.4), (Setting.CurrentHeight * 0.4));
		ContentPane.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.4));
		
		Label Header = new Label("Beställare*");
		Header.relocate((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.01));
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header);
		
		Label Header1 = new Label("Välj antal:");
		Header1.relocate((Setting.CurrentWidth * 0.15), (Setting.CurrentHeight * 0.01));
		Header1.setStyle("-fx-font-size: 20pt");
		ContentPane.getChildren().add(Header1);
		
		chooseClientsBox = new ComboBox<String>();
		chooseClientsBox.relocate((Setting.CurrentWidth * 0.22), (Setting.CurrentHeight * 0.01));
		chooseClientsBox.setStyle("-fx-font-size: 12pt");
		chooseClientsBox.getItems().addAll("1", "2", "3", "4", "5");
		chooseClientsBox.setValue("1");
		ContentPane.getChildren().add(chooseClientsBox);
		chooseClientsBox.setOnAction(e -> {
			setVisibleCount();
		});
		
		//COMBOBOXES CUSTOMER CONTACT AND DATEPICKER
		
		Label lclient1 = new Label("Beställare");
		lclient1.relocate((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.06));
		lclient1.setStyle("-fx-font-size: 16pt; -fx-underline: true");
		ContentPane.getChildren().add(lclient1);
		
		Label lclient2 = new Label("Kontakt");
		lclient2.relocate((Setting.CurrentWidth * 0.2), (Setting.CurrentHeight * 0.06));
		lclient2.setStyle("-fx-font-size: 16pt; -fx-underline: true");
		ContentPane.getChildren().add(lclient2);
		
		Label lclient3 = new Label("Sista datum");
		lclient3.relocate((Setting.CurrentWidth * 0.31), (Setting.CurrentHeight * 0.06));
		lclient3.setStyle("-fx-font-size: 16pt; -fx-underline: true");
		ContentPane.getChildren().add(lclient3);
		
		
		CustomerBox = new ComboBox[5];
		
		ContactPersonBox = new ComboBox[5];
		
		ClientDatePicker = new DatePicker[5];
		
		for (int i = 0; i < 5; i++) {
			CustomerBox[i] = new ComboBox<String>();
			CustomerBox[i].relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * ((0.05 * i) + 0.1));
			SM.setCustomerList(CustomerBox[i]);
			CustomerBox[i].setStyle("-fx-font-size: 12pt");
			CustomerBox[i].setMinWidth(Setting.CurrentWidth * 0.14);
			CustomerBox[i].setVisible(false);
			ContentPane.getChildren().add(CustomerBox[i]);
			
			ContactPersonBox[i] = new ComboBox<String>();
			ContactPersonBox[i].relocate(Setting.CurrentWidth * 0.16, Setting.CurrentHeight * ((0.05 * i) + 0.1));
			ContactPersonBox[i].setStyle("-fx-font-size: 12pt");
			ContactPersonBox[i].setMinWidth(Setting.CurrentWidth * 0.12);
			ContactPersonBox[i].setVisible(false);
			ContentPane.getChildren().add(ContactPersonBox[i]);
			
			final int x = i;
			
			CustomerBox[i].setOnAction(e->{
				SM.setContactPersonBox(CustomerBox[x], ContactPersonBox[x]);
			});
		
			ClientDatePicker[i] = new DatePicker();
			ClientDatePicker[i].relocate(Setting.CurrentWidth * 0.29, Setting.CurrentHeight * ((0.05 * i) + 0.1));
			ClientDatePicker[i].setPrefSize(170, 30);
			ClientDatePicker[i].setValue(LocalDate.now());
			ClientDatePicker[i].setStyle("-fx-font-size: 12pt");
			ClientDatePicker[i].setVisible(false);
			ContentPane.getChildren().add(ClientDatePicker[i]);
		}
		
		CustomerBox[0].setVisible(true);
		ContactPersonBox[0].setVisible(true);
		ClientDatePicker[0].setVisible(true);
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	void setVisibleCount(){
		
		boolean viewValue = true;
		int number = Integer.parseInt(chooseClientsBox.getValue());
		
		for (int i = 0; i < 5; i++) {
			if(number == i){
				viewValue = false;
			}
			CustomerBox[i].setVisible(viewValue);
			ContactPersonBox[i].setVisible(viewValue);
			ClientDatePicker[i].setVisible(viewValue);
		}
	}
	
	public boolean checkInsertData(){
		int numberOfClientsInsert = Integer.parseInt(chooseClientsBox.getValue());
		
		for (int i = 0; i < numberOfClientsInsert; i++) {
			
			if(CustomerBox[i].getSelectionModel().isEmpty()){
				SM.messageBox("Ange beställare! Rad: "  + (i + 1));
				return false;
			}
			else if(ContactPersonBox[i].getSelectionModel().isEmpty()){
				SM.messageBox("Ange kontaktperson! Rad: "  + (i + 1));
				return false;
			}
			else if(ClientDatePicker[i].getValue() == null){
				SM.messageBox("Ange datum! Rad: " + (i + 1));
				return false;
			}
			
			String datePicked = ClientDatePicker[i].getValue().toString();
			if(datePicked.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")){
				//OK DATE
			}
			else{
				SM.messageBox("Datum måste vara i format: YYYY-MM-DD! Rad: " + (i + 1));
				return false;
			}	
		}
		return  true;
	}
	
	public void clearInsertFields(){
		for (int i = 0; i < 5; i++) {
			SM.setCustomerList(CustomerBox[i]);
			ContactPersonBox[i].getItems().clear();
			ClientDatePicker[i].setValue(LocalDate.now());
		}
		boolean viewValue = true;
		for (int i = 0; i < 5; i++) {
			if(1 == i){
				viewValue = false;
			}
			CustomerBox[i].setVisible(viewValue);
			ContactPersonBox[i].setVisible(viewValue);
			ClientDatePicker[i].setVisible(viewValue);
		}
		chooseClientsBox.setValue("1");
	}
	
	
}
