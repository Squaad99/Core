package View.CustomerObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import Model.Objects.ObjectContactPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class CustomerDisplay {

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	Pane ContentPane;

	Label[] HoldLabel;
	TextField[] UpdateField;
	Button BtnUpdate;
	public ComboBox<String> SelectCustomerBox;
	TableView<ObjectContactPerson> listEmployee;

	public CustomerDisplay() {

		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.4), (Setting.CurrentHeight * 0.75));
		ContentPane.relocate((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.05));
		ContentPane.setStyle("-fx-border-color: black;");

		Label Header = new Label("Välj kund:");
		Header.relocate((Setting.CurrentWidth * 0.065), (Setting.CurrentHeight * 0));
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header);

		SelectCustomerBox = new ComboBox<String>();
		SelectCustomerBox.setStyle("-fx-font-size: 14pt");
		SelectCustomerBox.setPrefWidth(Setting.CurrentWidth * 0.2);
		SelectCustomerBox.relocate((Setting.CurrentWidth * 0.13), (Setting.CurrentHeight * 0.005));
		ContentPane.getChildren().add(SelectCustomerBox);
		SM.setCustomerList(SelectCustomerBox);

		SelectCustomerBox.setOnAction(e -> {
			if (SelectCustomerBox.getSelectionModel().isEmpty()) {
				//Nothing
			} else {
				getSelectedCustomer();
				getContactPerson();
			}
		});

		BtnUpdate = new Button("Ändra");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.07), (Setting.CurrentHeight * 0.035));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.325), (Setting.CurrentHeight * 0.055));
		BtnUpdate.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(BtnUpdate);

		BtnUpdate.setOnAction(e -> {
			if (BtnUpdate.getText().equalsIgnoreCase("Ändra")) {
				updateMode();
			} else {
				viewMode();
				updateCustomerData();
			}
		});

		Label[] DescLabel = new Label[6];
		HoldLabel = new Label[6];
		UpdateField = new TextField[6];
		int x = 0;
		for (int i = 0; i < 6; i++) {
			DescLabel[i] = new Label();
			DescLabel[i].setStyle("-fx-font-size: 16pt");

			HoldLabel[i] = new Label();
			HoldLabel[i].setStyle("-fx-font-size: 14pt");

			UpdateField[i] = new TextField();
			UpdateField[i].setStyle("-fx-font-size: 14pt");
			UpdateField[i].setPrefWidth(Setting.CurrentWidth * 0.135);
			if (i < 3) {
				DescLabel[i].relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * (0.1 + (0.07 * x))));
				UpdateField[i].relocate((Setting.CurrentWidth * 0.06), (Setting.CurrentHeight * (0.1 + (0.07 * x))));
				HoldLabel[i].relocate((Setting.CurrentWidth * 0.06), (Setting.CurrentHeight * (0.1 + (0.07 * x))));
			} else {
				DescLabel[i].relocate((Setting.CurrentWidth * 0.21), (Setting.CurrentHeight * (0.1 + (0.07 * x))));
				UpdateField[i].relocate((Setting.CurrentWidth * 0.26), (Setting.CurrentHeight * (0.1 + (0.07 * x))));
				HoldLabel[i].relocate((Setting.CurrentWidth * 0.26), (Setting.CurrentHeight * (0.1 + (0.07 * x))));
			}

			ContentPane.getChildren().add(DescLabel[i]);
			ContentPane.getChildren().add(UpdateField[i]);
			ContentPane.getChildren().add(HoldLabel[i]);
			UpdateField[i].setVisible(false);
			if (x == 2) {
				x = -1;
			}
			x++;
		}
		DescLabel[0].setText("Namn:");
		DescLabel[1].setText("Adress:");
		DescLabel[2].setText("Hemsida:");
		DescLabel[3].setText("Telefon:");
		DescLabel[4].setText("Mail:");
		DescLabel[5].setText("Org-nr:");

		Label Header1 = new Label("Kontaktpersoner");
		Header1.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.365));
		Header1.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		ContentPane.getChildren().add(Header1);

		Button BtnDelete = new Button("Ta bort markerad");
		BtnDelete.setPrefSize((Setting.CurrentWidth * 0.09), (Setting.CurrentHeight * 0.036));
		BtnDelete.relocate((Setting.CurrentWidth * 0.3), (Setting.CurrentHeight * 0.36));
		BtnDelete.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(BtnDelete);
		
		BtnDelete.setOnAction(e->{
			if(SM.confirmationBox("Vill du ta bort kontaktperson?") == true){
				deleteSelectedContact();
				getContactPerson();
			}
		});

		listEmployee = new TableView<ObjectContactPerson>();
		listEmployee.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.41);
		listEmployee.setPrefSize(Setting.CurrentWidth * 0.38, Setting.CurrentHeight * 0.25);
		listEmployee.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(listEmployee);

		// Columns for listEmployee
		TableColumn<ObjectContactPerson, String> nameCol = new TableColumn<>("Namn");
		nameCol.setMinWidth(Setting.CurrentWidth * 0.095);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

		TableColumn<ObjectContactPerson, String> lastnameCol = new TableColumn<>("Efternamn");
		lastnameCol.setMinWidth(Setting.CurrentWidth * 0.095);
		lastnameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));

		TableColumn<ObjectContactPerson, String> telenrCol = new TableColumn<>("TeleNr");
		telenrCol.setMinWidth(Setting.CurrentWidth * 0.095);
		telenrCol.setCellValueFactory(new PropertyValueFactory<>("TeleNr"));

		TableColumn<ObjectContactPerson, String> mailCol = new TableColumn<>("Mail");
		mailCol.setMinWidth(Setting.CurrentWidth * 0.095);
		mailCol.setCellValueFactory(new PropertyValueFactory<>("Mail"));

		listEmployee.getColumns().addAll(nameCol, lastnameCol, telenrCol, mailCol);
		
		//Delete customer whole
		Button BtnDelete1 = new Button("Ta bort kund");
		BtnDelete1.setPrefSize((Setting.CurrentWidth * 0.09), (Setting.CurrentHeight * 0.035));
		BtnDelete1.relocate((Setting.CurrentWidth * 0.3), (Setting.CurrentHeight * 0.68));
		BtnDelete1.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(BtnDelete1);
		
		BtnDelete1.setOnAction(e->{
			if(SM.confirmationBox("Vill du ta bort denna kund?") == true){
				deleteCustomer();
			}	
		});

	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	void viewMode() {
		BtnUpdate.setText("Ändra");
		for (int i = 0; i < 6; i++) {
			HoldLabel[i].setVisible(true);
			UpdateField[i].setVisible(false);
		}
	}

	void updateMode() {
		BtnUpdate.setText("Spara");
		for (int i = 0; i < 6; i++) {
			HoldLabel[i].setVisible(false);
			UpdateField[i].setVisible(true);
			UpdateField[i].setText(HoldLabel[i].getText());
		}
	}

	public void getSelectedCustomer() {

		viewMode();

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Customer where CustomerId = '" + SM.getSelectedCustomerId(SelectCustomerBox) + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				HoldLabel[0].setText(rs.getString("CompanyName"));
				HoldLabel[1].setText(rs.getString("CompanyAdress"));
				HoldLabel[2].setText(rs.getString("CompanyWeb"));
				HoldLabel[3].setText(rs.getString("CompanyTeleNr"));
				HoldLabel[4].setText(rs.getString("CompanyEmail"));
				HoldLabel[5].setText(rs.getString("CompanyOrgNr"));
			}
		} catch (Exception e) {
			SM.messageBox("Fel när val av kund sker, kontakta support!");
			System.out.println(e);
		}
	}

	void updateCustomerData() {

		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser,
					Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update Customer Set CompanyName = '" + UpdateField[0].getText() + "', CompanyAdress = '"
					+ UpdateField[1].getText() + "' , CompanyWeb = '" + UpdateField[2].getText()
					+ "' , CompanyTeleNr = '" + UpdateField[3].getText() + "' , CompanyEmail = '"
					+ UpdateField[4].getText() + "' , CompanyOrgNr = '" + UpdateField[5].getText()
					+ "' where CustomerId = '" + SM.getSelectedCustomerId(SelectCustomerBox) + "'";

			myStmt.executeUpdate(sql);
			myStmt.close();

			SM.setCustomerList(SelectCustomerBox);

			SM.messageBox(UpdateField[0].getText() + ": Uppdaterad!");

			SelectCustomerBox.setValue(UpdateField[0].getText());

			for (int i = 0; i < 6; i++) {
				HoldLabel[i].setText(UpdateField[i].getText());
				UpdateField[i].clear();
			}

		} catch (Exception exc) {
			SM.messageBox("Fel med Mysql Kontakta support!");
		}

	}

	public void getContactPerson() {

		listEmployee.getItems().clear();

		ObservableList<ObjectContactPerson> data = FXCollections.observableArrayList();

		try {

			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM CustomerContact where CustomerId = '" + SM.getSelectedCustomerId(SelectCustomerBox) + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				data.add(new ObjectContactPerson(rs.getString("PersonId"), rs.getString("Name"), rs.getString("LastName"), rs.getString("TeleNr"),
						rs.getString("Mail")));
			}
			listEmployee.setItems(data);
		} catch (Exception e) {
			System.out.println("Fail");
		}
	}

	void deleteSelectedContact(){
		
		if(SelectCustomerBox.getSelectionModel().isEmpty()){
			SM.messageBox("Kund ej vald");
			return;
		}
		
		if(listEmployee.getSelectionModel().isEmpty()){
			SM.messageBox("Kontaktperson ej vald");
			return;
		}
		
		int deletePersonId = Integer.parseInt(listEmployee.getSelectionModel().getSelectedItem().getId());
		
		try{
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "DELETE FROM CustomerContact where PersonId = '"+deletePersonId+"'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			getContactPerson();
		}
		catch(Exception e){
			SM.messageBox("Kunde inte hitta databasen, kontakta support!");
		}
		
	}
	
	void deleteCustomer(){
		if(SelectCustomerBox.getSelectionModel().isEmpty()){
			SM.messageBox("Kund ej vald");
			return;
		}
		
		try{
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "DELETE FROM CustomerContact where CustomerId = '"+SM.getSelectedCustomerId(SelectCustomerBox)+"'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		}
		catch(Exception e){
			SM.messageBox("Kunde inte hitta databsen, kontakta support! KontaktPerson");	
		}
		
		try{
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "DELETE FROM Customer where CustomerId = '"+SM.getSelectedCustomerId(SelectCustomerBox)+"'";
			myStmt.executeUpdate(sql);
			myStmt.close();

			SM.setCustomerList(SelectCustomerBox);
		
			for(int i = 0; i < 6; i++){
				HoldLabel[i].setText("");
			}
			listEmployee.getItems().clear();
	
			SM.messageBox("Kund borttagen!");	
		}
		catch(Exception e){
			SM.messageBox("Kunde inte hitta databsen, kontakta support! Kunder");
		}
	}
	
}
