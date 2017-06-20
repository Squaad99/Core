package View.OrderKalkylObjects;

import java.time.LocalDate;

import Model.SM;
import Model.Setting;
import Model.Objects.ObjectClient;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ClientDisplay {

	Pane ContentPane;
	public Button BtnUpdate;
	public TableView<ObjectClient> tableClient;
	Label[] lclient;
	public ComboBox<String> ClientBox;
	public ComboBox<String> CustomerBox;
	DatePicker ClientDatePicker;
	
	public ClientDisplay() {
	
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.5), (Setting.CurrentHeight * 0.25));
		ContentPane.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.63));
		
		Label Header = new Label("Beställare");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.01));
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header);
		
		//VIEW MODE
		
		BtnUpdate = new Button("Lägg till beställare");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.08), (Setting.CurrentHeight * 0.03));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.08), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");	
		ContentPane.getChildren().add(BtnUpdate);
		
		tableClient = new TableView<ObjectClient>();
		tableClient.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.05));
		tableClient.setPrefSize((Setting.CurrentWidth * 0.48), (Setting.CurrentHeight * 0.18));
		ContentPane.getChildren().add(tableClient);
		
		TableColumn<ObjectClient, String>[] ClientColumn = new TableColumn[7];
	
		for(int i = 0; i < 7; i++){
			ClientColumn[i] = new TableColumn<>();
			ClientColumn[i].setMinWidth(Setting.CurrentWidth * 0.07);
			tableClient.getColumns().add(ClientColumn[i]);
		}
		
		ClientColumn[0].setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
		ClientColumn[1].setCellValueFactory(new PropertyValueFactory<>("CustomerMail"));
		ClientColumn[2].setCellValueFactory(new PropertyValueFactory<>("CustomerPhone"));
		ClientColumn[3].setCellValueFactory(new PropertyValueFactory<>("ContactName"));
		ClientColumn[4].setCellValueFactory(new PropertyValueFactory<>("ContactMail"));
		ClientColumn[5].setCellValueFactory(new PropertyValueFactory<>("ContactPhone"));
		ClientColumn[6].setCellValueFactory(new PropertyValueFactory<>("ContactPhone"));
		ClientColumn[6].setMinWidth(Setting.CurrentWidth * 0.06);
		
		//Update mode
		lclient = new Label[3];
		
		for (int i = 0; i < 3; i++) {
			lclient[i] = new Label();
			lclient[i].setStyle("-fx-font-size: 16pt; -fx-underline: true");
			ContentPane.getChildren().add(lclient[i]);
		}
		
		lclient[0].relocate((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.06));
		lclient[0].setText("Beställare");
		lclient[1].relocate((Setting.CurrentWidth * 0.2), (Setting.CurrentHeight * 0.06));
		lclient[1].setText("Kontakt");
		lclient[2].relocate((Setting.CurrentWidth * 0.31), (Setting.CurrentHeight * 0.06));
		lclient[2].setText("Slutdatum");
		
		ClientBox = new ComboBox<String>();
		ClientBox.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.1);
		ClientBox.getItems().add("CustoemrTEst1");
		ClientBox.setStyle("-fx-font-size: 12pt");
		ClientBox.setMinWidth(Setting.CurrentWidth * 0.14);
		ClientBox.setVisible(true);
		ContentPane.getChildren().add(ClientBox);
		SM.setCustomerList(ClientBox);
		
		CustomerBox = new ComboBox<String>();
		CustomerBox.relocate(Setting.CurrentWidth * 0.16, Setting.CurrentHeight * 0.1);
		CustomerBox.setStyle("-fx-font-size: 12pt");
		CustomerBox.setMinWidth(Setting.CurrentWidth * 0.12);
		CustomerBox.setVisible(true);
		ContentPane.getChildren().add(CustomerBox);
		
		ClientDatePicker = new DatePicker();
		ClientDatePicker.relocate(Setting.CurrentWidth * 0.3, Setting.CurrentHeight * 0.1);
		ClientDatePicker.setPrefSize(170, 30);
		ClientDatePicker.setValue(LocalDate.now());
		ClientDatePicker.setStyle("-fx-font-size: 12pt");
		ClientDatePicker.setVisible(true);
		ContentPane.getChildren().add(ClientDatePicker);
		
		ClientBox.setOnAction(e->{
			SM.setContactPersonBox(ClientBox, CustomerBox);
		});
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public void viewMode(){
		BtnUpdate.setText("Lägg till beställare");
		tableClient.setVisible(true);
		for(int i = 0; i < 3; i++){
			lclient[i].setVisible(false);
		}
		ClientBox.setVisible(false);
		CustomerBox.setVisible(false);
		ClientDatePicker.setVisible(false);
	}
	
	public void updateMode(){
		BtnUpdate.setText("Spara beställare");
		tableClient.setVisible(false);
		for(int i = 0; i < 3; i++){
			lclient[i].setVisible(true);
		}
		ClientBox.setVisible(true);
		CustomerBox.setVisible(true);
		ClientDatePicker.setVisible(true);
	}

	public void setViewData(ObservableList<ObjectClient> List){
		viewMode();
		tableClient.setItems(List);
	}
	
	public int CustomerId;
	public int ContactId;
	public String Date;
	
	public boolean setUpdateVariables(){
		if(ClientBox.getSelectionModel().isEmpty()){
			SM.messageBox("Ange beställare!");
			return false;
		}
		else if(CustomerBox.getSelectionModel().isEmpty()){
			SM.messageBox("Ange kontaktperson!");
			return false;
		}
		else if(ClientDatePicker.getValue() == null){
			SM.messageBox("Ange datum!");
			return false;
		}
		
		String datePicked = ClientDatePicker.getValue().toString();
		if(datePicked.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")){
			//OK DATE
		}
		else{
			SM.messageBox("Datum måste vara i format: YYYY-MM-DD!");
			return false;
		}
		
		CustomerId = SM.getSelectedCustomerId(ClientBox);
		ContactId = SM.getSelectedPersonId(CustomerBox);
		Date = datePicked;
		return true;
	}
	
	
	
	
	
	
	
	
	
}
