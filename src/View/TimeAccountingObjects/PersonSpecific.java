package View.TimeAccountingObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.SM;
import Model.Setting;
import Model.Objects.ObjectPersonSpecific;
import Model.Objects.ObjectTimeSave;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class PersonSpecific {

	Pane ContentPane;

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;
	TableView Table;

	public PersonSpecific() {

		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.65), (Setting.CurrentHeight * 0.33));
		ContentPane.relocate((Setting.CurrentWidth * 0), (Setting.CurrentHeight * 0.56));

		Label l = new Label("Anställd:");
		l.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.01);
		l.setStyle("-fx-font-size: 16pt");
		ContentPane.getChildren().add(l);

		ComboBox<String> DropDownEmployee = new ComboBox<String>();
		DropDownEmployee.relocate(Setting.CurrentWidth * 0.06, Setting.CurrentHeight * 0.0075);
		DropDownEmployee.setStyle("-fx-font-size: 12pt");
		DropDownEmployee.setPrefWidth(Setting.CurrentWidth * 0.08);
		DropDownEmployee.setPromptText("-");
		ContentPane.getChildren().add(DropDownEmployee);
		DropDownEmployee.getItems().addAll("Marcus", "Thomas", "Silvester", "Jesper", "Muris", "Emil", "Peder",
				"Mikael", "Kjell-Arne", "Johan");

		Label l1 = new Label("Period:");
		l1.relocate(Setting.CurrentWidth * 0.16, Setting.CurrentHeight * 0.01);
		l1.setStyle("-fx-font-size: 16pt");
		ContentPane.getChildren().add(l1);

		// DATE PICKER FOR PERIOD
		DatePicker DatePickerPeriod1 = new DatePicker();
		DatePickerPeriod1.relocate(Setting.CurrentWidth * 0.21, Setting.CurrentHeight * 0.01);
		DatePickerPeriod1.setStyle("-fx-font-size: 12pt");
		DatePickerPeriod1.setPrefWidth(Setting.CurrentWidth * 0.1);
		ContentPane.getChildren().add(DatePickerPeriod1);

		Label l2 = new Label("-");
		l2.relocate(Setting.CurrentWidth * 0.32, Setting.CurrentHeight * 0);
		l2.setStyle("-fx-font-size: 30pt");
		ContentPane.getChildren().add(l2);

		DatePicker DatePickerPeriod2 = new DatePicker();
		DatePickerPeriod2.relocate(Setting.CurrentWidth * 0.34, Setting.CurrentHeight * 0.01);
		DatePickerPeriod2.setStyle("-fx-font-size: 12pt");
		DatePickerPeriod2.setPrefWidth(Setting.CurrentWidth * 0.1);
		ContentPane.getChildren().add(DatePickerPeriod2);

		Button BtnSearchOrder = new Button("Sök");
		BtnSearchOrder.relocate(Setting.CurrentWidth * 0.45, Setting.CurrentHeight * 0.01);
		BtnSearchOrder.setPrefSize(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * 0.0275);
		BtnSearchOrder.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().addAll(BtnSearchOrder);

		BtnSearchOrder.setOnAction(e -> {
			setPersonSpecificData(DatePickerPeriod1.getValue().toString(), DatePickerPeriod2.getValue().toString(),
					DropDownEmployee.getValue().toString());

		});

		// TABLE FOR CurrentHeightOWING TIME
		Table = new TableView();
		Table.setPrefSize(Setting.CurrentWidth * 0.59, Setting.CurrentHeight * 0.24);
		Table.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.06);
		Table.setStyle("-fx-font-size: 10pt;");
		ContentPane.getChildren().add(Table);

		TableColumn[] colTimeSave = new TableColumn[2];
		// COLUMNS FOR CurrentHeightOWING TIME LOOP 9 COLUMNS
		for (int i = 0; i < 2; i++) {
			colTimeSave[i] = new TableColumn();
			Table.getColumns().add(colTimeSave[i]);
		}

		// COL 1
		colTimeSave[0].setCellValueFactory(new PropertyValueFactory<>("OrderId"));
		colTimeSave[0].setMinWidth(Setting.CurrentWidth * 0.3);
		colTimeSave[0].setText("Order Nummer");
		// COL 2
		colTimeSave[1].setCellValueFactory(new PropertyValueFactory<>("Hours"));
		colTimeSave[1].setMinWidth(Setting.CurrentWidth * 0.29);
		colTimeSave[1].setText("Totalt Timmar");

	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	void setPersonSpecificData(String FirstDate, String LastDate, String Employee) {

		Table.getItems().clear();

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM timesave WHERE DateStamp BETWEEN '" + FirstDate + "' AND '" + LastDate
					+ "' AND Employee = '" + Employee + "'";
			rs = st.executeQuery(s);

			ArrayList<String> list = new ArrayList<String>();

			while (rs.next()) {
				if (list.contains(rs.getString("OrderId"))) {
					// Nothing
				} else {
					list.add(rs.getString("OrderId"));
				}
			}

			float[] perOrder = new float[list.size()];
			for (int i = 0; i < list.size(); i++) {
				perOrder[i] = 0;
			}

			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM timesave WHERE DateStamp BETWEEN '" + FirstDate + "' AND '" + LastDate
					+ "' AND Employee = '" + Employee + "'";
			rs = st.executeQuery(s);

			String beforeValue = "";
			String afterValue = "";

			while (rs.next()) {
				beforeValue = rs.getString("Hours");
				afterValue = beforeValue.replace(",", ".");
				perOrder[list.indexOf(rs.getString("OrderId"))] += Float.parseFloat(afterValue);
			}

			ObservableList<ObjectPersonSpecific> dataList = FXCollections.observableArrayList();

			for (int i = 0; i < list.size(); i++) {
				dataList.add(new ObjectPersonSpecific(list.get(i), perOrder[i]));
			}

			Table.setItems(dataList);

		} catch (Exception e) {
			SM.messageBox("Fel med MySQL");
			System.out.println(e);
		}

	}
}
