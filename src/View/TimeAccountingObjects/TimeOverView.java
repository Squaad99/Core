package View.TimeAccountingObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import Model.Objects.ObjectTimeOrder;
import Model.Objects.ObjectTimeSave;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class TimeOverView {

	Pane ContentPane;

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	TableView<ObjectTimeSave> timeTable;
	TextField SearchField;
	ObservableList<ObjectTimeSave> dataList = FXCollections.observableArrayList();

	public TimeOverView() {

		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.65), (Setting.CurrentHeight * 0.55));
		ContentPane.relocate((Setting.CurrentWidth * 0), (Setting.CurrentHeight * 0));

		Label l1 = new Label("Period:");
		l1.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.01);
		l1.setStyle("-fx-font-size: 20pt");
		ContentPane.getChildren().add(l1);

		// DATE PICKER FOR PERIOD
		DatePicker DatePickerPeriod1 = new DatePicker();
		DatePickerPeriod1.relocate(Setting.CurrentWidth * 0.06, Setting.CurrentHeight * 0.01);
		DatePickerPeriod1.setStyle("-fx-font-size: 16pt");
		DatePickerPeriod1.setPrefWidth(Setting.CurrentWidth * 0.1);
		ContentPane.getChildren().add(DatePickerPeriod1);

		Label l2 = new Label("-");
		l2.relocate(Setting.CurrentWidth * 0.17, Setting.CurrentHeight * 0);
		l2.setStyle("-fx-font-size: 30pt");
		ContentPane.getChildren().add(l2);

		DatePicker DatePickerPeriod2 = new DatePicker();
		DatePickerPeriod2.relocate(Setting.CurrentWidth * 0.19, Setting.CurrentHeight * 0.01);
		DatePickerPeriod2.setStyle("-fx-font-size: 16pt");
		DatePickerPeriod2.setPrefWidth(Setting.CurrentWidth * 0.1);
		ContentPane.getChildren().add(DatePickerPeriod2);

		Button BtnPeriod = new Button("Filtrera");
		BtnPeriod.relocate(Setting.CurrentWidth * 0.3, Setting.CurrentHeight * 0.01);
		BtnPeriod.setPrefSize(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * 0.0275);
		BtnPeriod.setStyle("-fx-font-size: 16pt");
		ContentPane.getChildren().addAll(BtnPeriod);

		BtnPeriod.setOnAction(e -> {
			setTimeTablePeriod(DatePickerPeriod2.getValue().toString(), DatePickerPeriod1.getValue().toString());

		});

		SearchField = new TextField();
		SearchField.relocate(Setting.CurrentWidth * 0.46, Setting.CurrentHeight * 0.01);
		SearchField.setStyle("-fx-font-size: 16pt");
		SearchField.setPromptText("Sök");
		ContentPane.getChildren().add(SearchField);

		// TABLE FOR CurrentHeightOWING TIME
		timeTable = new TableView<ObjectTimeSave>();
		timeTable.setPrefSize(Setting.CurrentWidth * 0.59, Setting.CurrentHeight * 0.48);
		timeTable.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.07);
		timeTable.setStyle("-fx-font-size: 10pt;");
		ContentPane.getChildren().add(timeTable);

		TableColumn<ObjectTimeSave, String>[] colTimeSave = new TableColumn[9];
		// COLUMNS FOR CurrentHeightOWING TIME LOOP 9 COLUMNS
		for (int i = 0; i < 9; i++) {
			colTimeSave[i] = new TableColumn();
			timeTable.getColumns().add(colTimeSave[i]);
		}

		// COL 1
		colTimeSave[0].setCellValueFactory(new PropertyValueFactory<>("NumberId"));
		colTimeSave[0].setMinWidth(Setting.CurrentWidth * 0.05);
		colTimeSave[0].setText("Nummer");
		// COL 2
		colTimeSave[1].setCellValueFactory(new PropertyValueFactory<>("Employee"));
		colTimeSave[1].setMinWidth(Setting.CurrentWidth * 0.08);
		colTimeSave[1].setText("Anställd");
		// COL 3
		colTimeSave[2].setCellValueFactory(new PropertyValueFactory<>("OrderId"));
		colTimeSave[2].setMinWidth(Setting.CurrentWidth * 0.05);
		colTimeSave[2].setText("Order");
		// COL 4
		colTimeSave[3].setCellValueFactory(new PropertyValueFactory<>("WorkTyp"));
		colTimeSave[3].setMinWidth(Setting.CurrentWidth * 0.185);
		colTimeSave[3].setText("Typ av uppdrag");
		// COL 5
		colTimeSave[4].setCellValueFactory(new PropertyValueFactory<>("Hours"));
		colTimeSave[4].setMinWidth(Setting.CurrentWidth * 0.05);
		colTimeSave[4].setText("Timmar");
		// COL 6
		colTimeSave[5].setCellValueFactory(new PropertyValueFactory<>("DateStamp"));
		colTimeSave[5].setMinWidth(Setting.CurrentWidth * 0.05);
		colTimeSave[5].setText("Datum");
		// COL 7
		colTimeSave[6].setCellValueFactory(new PropertyValueFactory<>("Week"));
		colTimeSave[6].setMinWidth(Setting.CurrentWidth * 0.03);
		colTimeSave[6].setText("Vecka");
		// COL 8
		colTimeSave[7].setCellValueFactory(new PropertyValueFactory<>("Outside"));
		colTimeSave[7].setMinWidth(Setting.CurrentWidth * 0.04);
		colTimeSave[7].setText("Utomhus");
		// COL 9
		colTimeSave[8].setCellValueFactory(new PropertyValueFactory<>("Overtime"));
		colTimeSave[8].setMinWidth(Setting.CurrentWidth * 0.04);
		colTimeSave[8].setText("Ö-tid");

		String CurrentDate = SM.getDate();
		int year = Integer.parseInt(CurrentDate.substring(0, 4));
		int lastYear = year - 1;
		String lastDate = lastYear + CurrentDate.substring(4, 10);

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
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	void setTimeTablePeriod(String FirstDate, String LastDate) {

		timeTable.getItems().clear();

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM timesave WHERE DateStamp BETWEEN '" + LastDate + "' AND '" + FirstDate + "'";
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
			System.out.println("Fail att hämta kalkyler");

		}

	}

}
