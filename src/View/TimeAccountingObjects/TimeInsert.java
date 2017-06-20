package View.TimeAccountingObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Model.Setting;
import Model.Objects.ObjectSmallOrder;
import Model.Objects.ObjectTimeSave;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TimeInsert{


	// WINDOW / STAGE
	Stage window;
	Scene scene;

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	Pane pane;
	ComboBox<String> DropDownEmployee;
	TableView<ObjectTimeSave> timeTable;
	TextField TextFieldOrderNr;
	CheckBox CBoxOverTime;
	CheckBox CBoxOutside;
	DatePicker DatePickerDate;
	TextField TextFieldHours;
	ComboBox<Object> DropDownWorkTypes;
	TableView<ObjectSmallOrder> orderTable;
	ObservableList<ObjectTimeSave> dataList = FXCollections.observableArrayList();

	public TimeInsert() {
		pane = new Pane();
		// COMBOX FÖR ANSTÄLLD

		Label lInfo0 = new Label("Välj anställd");
		lInfo0.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		lInfo0.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.035);
		pane.getChildren().add(lInfo0);

		DropDownEmployee = new ComboBox<String>();
		DropDownEmployee.relocate(Setting.CurrentWidth * 0.1, Setting.CurrentHeight * 0.035);
		DropDownEmployee.setStyle("-fx-font-size: 20 pt");
		DropDownEmployee.setPrefWidth(Setting.CurrentWidth * 0.2);
		DropDownEmployee.setPromptText("-");
		DropDownEmployee.getItems().addAll("Marcus", "Thomas", "Silvester", "Jesper", "Muris", "Emil", "Peder",
				"Mikael", "Kjell-Arne", "Johan", "Freddie", "Anders");
		pane.getChildren().add(DropDownEmployee);

		DropDownEmployee.setOnAction(e -> {
			setTimeTablePeriod(DropDownEmployee.getValue().toString());
		});

		Button BtnDelete = new Button("Ta bort rad");
		BtnDelete.relocate(Setting.CurrentWidth * 0.36, Setting.CurrentHeight * 0.035);
		BtnDelete.setStyle("-fx-font-size: 16pt");
		BtnDelete.setPrefSize(Setting.CurrentWidth * 0.12, Setting.CurrentHeight * 0.03);
		pane.getChildren().addAll(BtnDelete);
		BtnDelete.setOnAction(e -> {
			deleteRow();
			if(DropDownEmployee.getSelectionModel().isEmpty()){
				return;
			}
			setTimeTablePeriod(DropDownEmployee.getValue().toString());
		});

		TextField SearchField = new TextField();
		SearchField.setStyle("-fx-font-size: 16pt");
		SearchField.relocate(Setting.CurrentWidth * 0.52, Setting.CurrentHeight * 0.035);
		SearchField.setPromptText("Sök");
		pane.getChildren().add(SearchField);

		SearchField.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {
				if (SearchField.textProperty().get().isEmpty()) {
					timeTable.setItems(dataList);
					return;
				}
				ObservableList<ObjectTimeSave> tableItems = FXCollections.observableArrayList();
				ObservableList<TableColumn<ObjectTimeSave, ?>> cols = timeTable.getColumns();

				for (int i = 0; i < dataList.size(); i++) {

					for (int j = 0; j < cols.size(); j++) {
						TableColumn col = cols.get(j);
						String cellValue = col.getCellData(dataList.get(i)).toString();
						cellValue = cellValue.toLowerCase();
						if (cellValue.contains(SearchField.textProperty().get().toLowerCase())) {
							tableItems.add(dataList.get(i));
							break;
						}
					}
				}
				timeTable.setItems(tableItems);
			}
		});

		// TABLE FOR CurrentHeightOWING TIME
		timeTable = new TableView<ObjectTimeSave>();
		timeTable.setPrefSize(Setting.CurrentWidth * 0.65, Setting.CurrentHeight * 0.6);
		timeTable.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.1);
		timeTable.setStyle("-fx-font-size: 14pt;");
		pane.getChildren().add(timeTable);

		TableColumn<ObjectTimeSave, String>[] colTimeSave = new TableColumn[9];
		// COLUMNS FOR CurrentHeightOWING TIME LOOP 9 COLUMNS
		for (int i = 0; i < 7; i++) {
			colTimeSave[i] = new TableColumn();
			timeTable.getColumns().add(colTimeSave[i]);
		}

		// COL 1
		colTimeSave[0].setCellValueFactory(new PropertyValueFactory<>("OrderId"));
		colTimeSave[0].setMinWidth(Setting.CurrentWidth * 0.1);
		colTimeSave[0].setText("Order");
		// COL 2
		colTimeSave[1].setCellValueFactory(new PropertyValueFactory<>("WorkTyp"));
		colTimeSave[1].setMinWidth(Setting.CurrentWidth * 0.29);
		colTimeSave[1].setText("Typ av arbete");
		// COL 3
		colTimeSave[2].setCellValueFactory(new PropertyValueFactory<>("Hours"));
		colTimeSave[2].setMinWidth(Setting.CurrentWidth * 0.06);
		colTimeSave[2].setText("Timmar");
		// COL 4
		colTimeSave[3].setCellValueFactory(new PropertyValueFactory<>("DateStamp"));
		colTimeSave[3].setMinWidth(Setting.CurrentWidth * 0.06);
		colTimeSave[3].setText("Datum");
		// COL 5
		colTimeSave[4].setCellValueFactory(new PropertyValueFactory<>("Week"));
		colTimeSave[4].setMinWidth(Setting.CurrentWidth * 0.04);
		colTimeSave[4].setText("Vecka");
		// COL 6
		colTimeSave[5].setCellValueFactory(new PropertyValueFactory<>("Outside"));
		colTimeSave[5].setMinWidth(Setting.CurrentWidth * 0.05);
		colTimeSave[5].setText("Utomhus");
		// COL 7
		colTimeSave[6].setCellValueFactory(new PropertyValueFactory<>("Overtime"));
		colTimeSave[6].setMinWidth(Setting.CurrentWidth * 0.05);
		colTimeSave[6].setText("Ö-tid");

		// INPUT

		TextFieldOrderNr = new TextField();
		TextFieldOrderNr.setStyle("-fx-font-size: 14pt");
		TextFieldOrderNr.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.72);
		TextFieldOrderNr.setPromptText("Order Nr");
		TextFieldOrderNr.setPrefWidth(Setting.CurrentWidth * 0.1);
		pane.getChildren().add(TextFieldOrderNr);

		DropDownWorkTypes = new ComboBox();
		DropDownWorkTypes.setStyle("-fx-font-size: 14pt");
		DropDownWorkTypes.relocate(Setting.CurrentWidth * 0.12, Setting.CurrentHeight * 0.72);
		DropDownWorkTypes.setPrefWidth(Setting.CurrentWidth * 0.27);
		DropDownWorkTypes.setPromptText("Välj typ av arbete");
		pane.getChildren().add(DropDownWorkTypes);
		DropDownWorkTypes.getItems().addAll("-10 Kapning av profiler och dylikt",
				"-11 Fräsning av lås, beslag, och dylikt", "-12 Borrning stansning etc", "-15 Plåtarbeten",
				"-16 Fyllningar", new Separator(), "-30 Svetsning vanligt stål", "-31 Svetsning rostfritt",
				"-32 Putsning rostfritt", new Separator(), "-50 Färdigställning al-partier",
				"-51 Färdigställning stålpartier", "-53 Glasning inne på verkstad", "-58 Tillverkningsfel",
				"-59 Ritningsfel", new Separator(), "-70 Montage", "-71 Glasning", "-72 Täcklister",
				"-73 Slutjustering i samband med slutbesiktning", "-74 Montage Beslag monteras separat",
				"-75 Reperationer", "-76 Rivning", "-77 Etablering/avetablering på montagearbetsplats",
				"-78 Tillverkningsfel - Montage", "-79 Ritningsfel - Montage", "-80 Mätning - Montage", new Separator(),
				"-90 Packning/Emballering", "-91 GlaSetting.CurrentHeightantering", "-93 Materialhanterning", new Separator(),
				"-100 Städning", "-110 Skrothanterning", "-150 Maskingfel", "-160 Maskinunderhåll",
				"-170 Verktygsunderhåll", "-200 Garantiarbeten", "-250 Körningar", "-350 Interna Arbeten",
				"-360 Bearbetningsmaskinen", "-370 Adm arbeten", "-800 Utbildning/möten/sammankomster mm",
				"-830 FöretagSetting.CurrentHeightälsovård", "-900 Arbete i lokalen", "-920 Underhållsarbeten",
				"-950 Mässor/utställningar");

		TextFieldHours = new TextField();
		TextFieldHours.setStyle("-fx-font-size: 14pt");
		TextFieldHours.relocate(Setting.CurrentWidth * 0.4, Setting.CurrentHeight * 0.72);
		TextFieldHours.setPromptText("Timmar");
		TextFieldHours.setPrefWidth(Setting.CurrentWidth * 0.06);
		pane.getChildren().add(TextFieldHours);

		DatePickerDate = new DatePicker();
		DatePickerDate.setStyle("-fx-font-size: 14pt");
		DatePickerDate.setValue(LocalDate.now());
		DatePickerDate.relocate(Setting.CurrentWidth * 0.47, Setting.CurrentHeight * 0.72);
		DatePickerDate.setPrefWidth(Setting.CurrentWidth * 0.1);
		pane.getChildren().add(DatePickerDate);

		Label LOutSide = new Label("Ute");
		LOutSide.setStyle("-fx-font-size: 12pt");
		LOutSide.relocate(Setting.CurrentWidth * 0.586, Setting.CurrentHeight * 0.705);
		pane.getChildren().add(LOutSide);

		CBoxOutside = new CheckBox();
		CBoxOutside.relocate(Setting.CurrentWidth * 0.59, Setting.CurrentHeight * 0.735);
		CBoxOutside.setScaleX(1.7);
		CBoxOutside.setScaleY(1.7);
		pane.getChildren().add(CBoxOutside);

		Label LOverTime = new Label("Ö-tid");
		LOverTime.setStyle("-fx-font-size: 12pt");
		LOverTime.relocate(Setting.CurrentWidth * 0.625, Setting.CurrentHeight * 0.705);
		pane.getChildren().add(LOverTime);

		CBoxOverTime = new CheckBox();
		CBoxOverTime.relocate(Setting.CurrentWidth * 0.63, Setting.CurrentHeight * 0.735);
		CBoxOverTime.setScaleX(1.7);
		CBoxOverTime.setScaleY(1.7);
		pane.getChildren().add(CBoxOverTime);

		Button BtnAdd = new Button("Lägg in");
		BtnAdd.relocate(Setting.CurrentWidth * 0.54, Setting.CurrentHeight * 0.78);
		BtnAdd.setStyle("-fx-font-size: 20pt");
		BtnAdd.setPrefSize(Setting.CurrentWidth * 0.12, Setting.CurrentHeight * 0.04);
		pane.getChildren().add(BtnAdd);
		BtnAdd.setOnAction(e -> {
			if (DropDownEmployee.getSelectionModel().isEmpty()) {
				messageBox("Lägg till", "Du måste välja anställd");
				return;
			}
			checkAndInsertAll();
			setTimeTablePeriod(DropDownEmployee.getValue().toString());
		});
		
		
		Label lInfo7 = new Label("Aktuella Orders");
		lInfo7.setStyle("-fx-font-size: 28pt");
		lInfo7.relocate(Setting.CurrentWidth * 0.68, Setting.CurrentHeight * 0.05);
		pane.getChildren().add(lInfo7);

		orderTable = new TableView<ObjectSmallOrder>();
		orderTable.setPrefSize(Setting.CurrentWidth * 0.22, Setting.CurrentHeight * 0.75);
		orderTable.relocate(Setting.CurrentWidth * 0.68, Setting.CurrentHeight * 0.1);
		orderTable.setStyle("-fx-font-size: 12pt;");
		pane.getChildren().add(orderTable);

		TableColumn<ObjectSmallOrder, String> ColOrderId = new TableColumn<>("Nummer");
		ColOrderId.setMinWidth(Setting.CurrentWidth * 0.11);
		ColOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));

		TableColumn<ObjectSmallOrder, String> ColOrderName = new TableColumn<>("Namn");
		ColOrderName.setMinWidth(Setting.CurrentWidth * 0.11);
		ColOrderName.setCellValueFactory(new PropertyValueFactory<>("Name"));

		orderTable.getColumns().addAll(ColOrderId, ColOrderName);
		
		setSmallOrderTable();
	}

	public void addToPane(Pane Mainpane) {
		Mainpane.getChildren().add(pane);
	}
	
	public Pane getPane(){
		return pane;
	}
	
	void setTimeTablePeriod(String Name) {

		timeTable.getItems().clear();
		
		if (DropDownEmployee.getSelectionModel().isEmpty()) {
			Alert alertBox = new Alert(AlertType.INFORMATION);
			alertBox.setTitle("CoreTime");
			alertBox.setHeaderText(null);
			alertBox.setContentText("Ange anställd!");
			alertBox.showAndWait();
			return;
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		dateFormat.format(date);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date todate1 = cal.getTime();
		String fromdate = dateFormat.format(todate1);

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM timesave WHERE DateStamp BETWEEN '" + fromdate + "' AND '" + dateFormat.format(date)
					+ "' AND Employee = '" + Name + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				String OutsideInput = "";
				if (rs.getString("Outside").equalsIgnoreCase("false")) {
					OutsideInput = "-";
				} else if (rs.getString("Outside").equalsIgnoreCase("true")) {
					OutsideInput = "Ja";
				}

				String OvertimeInput = "";
				if (rs.getString("OverTime").equalsIgnoreCase("false")) {
					OvertimeInput = "-";
				} else if (rs.getString("OverTime").equalsIgnoreCase("true")) {
					OvertimeInput = "Ja";
				}

				dataList.add(new ObjectTimeSave(rs.getString("TimeSaveId"), rs.getString("Employee"),
						rs.getString("OrderId"), rs.getString("WorkTyp"), rs.getString("Hours"),
						rs.getString("DateStamp"), rs.getString("Week"), OutsideInput, OvertimeInput));
			}

			timeTable.setItems(dataList);
		} catch (Exception e) {
			messageBox("Mysql", "Mysql Fel");
		}

	}

	void checkAndInsertAll() {
		
		if (TextFieldOrderNr.getText().isEmpty()) {
			messageBox("Lägg till", "Du måste ange order nr!");
			return;
		} 

		if (DropDownEmployee.getSelectionModel().isEmpty()) {
			messageBox("Lägg till", "Du måste välja anställd!");
			return;
		}

		// CHECK ORDER SO ONLY NUMBERS
		if (TextFieldOrderNr.getText().matches("^[0-9]*$")) {
			// OK
		} else {
			messageBox("Lägg till", "Order Nr kan bara bestå av siffror!");
			return;
		}

		// CHECK COMBOBOXES INPUT

		if (DropDownWorkTypes.getSelectionModel().isEmpty()) {
			messageBox("Lägg till", "Typ av arbete kan inte vara tom!");
			return;
		}
		if (DropDownWorkTypes.getValue().toString().substring(0, 9).equalsIgnoreCase("Separator")) {
			messageBox("Lägg till", "Typ av arbete kan inte ha ett streck!");
			return;
		}

		// Check Hour TextField

		if (TextFieldHours.getText().matches("^[0-9,;]+$")) {
			// OK
		} else {
			messageBox("Lägg till",
					"Timmar kan bara bestå av siffror och ( , ) och får inte vara tom!");
			return;
		}

		// CHECK DATE INPUT
		if (DatePickerDate.getValue() == null) {
			messageBox("Lägg till", "Datum ej vald!");
			return;
		}

		String datePicked = DatePickerDate.getValue().toString();

		System.out.println(datePicked);

		if (datePicked.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
			// ok
		} else {
			messageBox("Lägg till", "Datum måste bestå av YYYY-MM-DD.");
			return;
			// not
		}
		
		String d = DatePickerDate.getValue().toString();

		int Year = Integer.parseInt(d.substring(0, 4));
		int Month = Integer.parseInt(d.substring(5, 7));
		int Day = Integer.parseInt(d.substring(8, 10));

		boolean OutsideBox = CBoxOutside.isSelected();
		String OutsideBoxString = String.valueOf(OutsideBox);

		boolean OverTimeBox = CBoxOutside.isSelected();
		String OverTimeBoxString = String.valueOf(OverTimeBox);

		LocalDate localDate = LocalDate.of(Year, Month, Day);

		int weekNumber = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

		String WorkType = DropDownWorkTypes.getValue().toString();

		try{
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into TimeSave (Employee, OrderId, WorkTyp, Hours, DateStamp, Week, Outside, OverTime) values "
					+ "('" + DropDownEmployee.getValue() + "', '" + TextFieldOrderNr.getText() + "', '" + WorkType + "', '"
					+ TextFieldHours.getText() + "', '" + d + "'," + " '" + weekNumber + "', '" + OutsideBoxString + "', '"
					+ OverTimeBoxString + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		}catch(Exception e)
		{
			messageBox("Insert TimeSave", "Fel när du försökte göra insert till MYSQL TimeSave");
			return;
		}
		
		messageBox("Lägg till", "Tidsredovisning inlaggd!");
		clearAllFields();
	}

	void deleteRow(){
		
		if(timeTable.getSelectionModel().isEmpty()){
			return;
		}
		
		try {
			String Id = timeTable.getSelectionModel().getSelectedItem().getNumberId();
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM TimeSave where TimeSaveId = '" + Id + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			messageBox("Mysql Fel","Fel när du skulle ta bort rad! TimeSave");
		}
	}
	
	void messageBox(String a, String b) {
		Alert alertBox = new Alert(AlertType.INFORMATION);
		alertBox.setTitle(a);
		alertBox.setHeaderText(null);
		alertBox.setContentText(b);
		alertBox.showAndWait();
	}

	void clearAllFields() {
		TextFieldOrderNr.clear();
		DropDownWorkTypes.getSelectionModel().clearSelection();
		TextFieldHours.clear();
		DatePickerDate.setValue(LocalDate.now());
		CBoxOutside.setSelected(false);
		CBoxOutside.setSelected(false);
	}
	
	void setSmallOrderTable(){
		orderTable.getItems().clear();
		ObservableList<ObjectSmallOrder> SimpleOrdetList = FXCollections.observableArrayList();
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Orders where STATUS = 'Active'";
			rs = st.executeQuery(s);
			while (rs.next()) {
				SimpleOrdetList.add(new ObjectSmallOrder(rs.getString("OrderID"), rs.getString("Name")));
			}
			orderTable.setItems(SimpleOrdetList);
		} catch (Exception e) {
			messageBox("Mysql ", "Fel när aktiva orders skulle hämtas! Kontakta support!");
		}
	}
}


