package View.TimeAccountingObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import Model.Objects.ObjectTimeOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class SpecificOrder {

	Pane ContentPane;

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;
	
	String[] WorkType;
	
	Label[] OrderLabel;
	float[] workHours;
	
	TableView<ObjectTimeOrder> OrderSpecificTable;
	
	public SpecificOrder() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.21), (Setting.CurrentHeight * 0.87));
		ContentPane.relocate((Setting.CurrentWidth * 0.67), (Setting.CurrentHeight * 0));
		
		WorkType = new String[42];
		
		for(int i = 0; i < 42; i++){
			WorkType[i] = new String();
		}
		
		WorkType[0] = "-10 Kapning av profiler och dylikt";
		WorkType[1] = "-11 Fräsning av lås, beslag, och dylikt";
		WorkType[2] = "-12 Borrning stansning etc";
		WorkType[3] = "-15 Plåtarbeten";
		WorkType[4] = "-16 Fyllningar";
		WorkType[5] = "-30 Svetsning vanligt stål";
		WorkType[6] = "-31 Svetsning rostfritt";
		WorkType[7] = "-32 Putsning rostfritt";
		WorkType[8] = "-50 Färdigställning al-partier";
		WorkType[9] = "-51 Färdigställning stålpartier";
		WorkType[10] = "-53 Glasning inne på verkstad";
		WorkType[11] = "-58 Tillverkningsfel";
		WorkType[12] = "-59 Ritningsfel";
		WorkType[13] = "-70 Montage";
		WorkType[14] = "-71 Glasning";
		WorkType[15] = "-72 Täcklister";
		WorkType[16] = "-73 Slutjustering i samband med slutbesiktning";
		WorkType[17] = "-74 Montage Beslag monteras separat";
		WorkType[18] = "-75 Reperationer";
		WorkType[19] = "-76 Rivning";
		WorkType[20] = "-77 Etablering/avetablering på montagearbetsplats";
		WorkType[21] = "-78 Tillverkningsfel - Montage";
		WorkType[22] = "-79 Ritningsfel - Montage";
		WorkType[23] = "-80 Mätning";
		WorkType[24] = "-90 Packning/Emballering";
		WorkType[25] = "-91 GlaCurrentHeightantering";
		WorkType[26] = "-93 Materialhanterning";
		WorkType[27] = "-100 Städning";
		WorkType[28] = "-110 Skrothanterning";
		WorkType[29] = "-150 Maskingfel";
		WorkType[30] = "-160 Maskinunderhåll";
		WorkType[31] = "-170 Verktygsunderhåll";
		WorkType[32] = "-200 Garantiarbeten";
		WorkType[33] = "-250 Körningar";
		WorkType[34] = "-350 Interna Arbeten";
		WorkType[35] = "-360 Bearbetningsmaskinen";
		WorkType[36] = "-370 Adm arbeten";
		WorkType[37] = "-800 Utbildning/möten/sammankomster mm";
		WorkType[38] = "-830 FöretagCurrentHeightälsovård";
		WorkType[39] = "-900 Arbete i lokalen";
		WorkType[40] = "-920 Underhållsarbeten";
		WorkType[41] = "-950 Mässor/utställningar";
		
		OrderSpecificTable = new TableView<ObjectTimeOrder>();
		OrderSpecificTable.setPrefSize(Setting.CurrentWidth * 0.2, Setting.CurrentHeight * 0.6);
		OrderSpecificTable.relocate(Setting.CurrentWidth * 0.005, Setting.CurrentHeight * 0.07);
		OrderSpecificTable.setStyle("-fx-font-size: 12pt;");
		ContentPane.getChildren().add(OrderSpecificTable);
		
		TableColumn<ObjectTimeOrder, String> WorkTypeCol = new TableColumn();
		WorkTypeCol.setCellValueFactory(new PropertyValueFactory<>("WorkType"));
		WorkTypeCol.setMinWidth(Setting.CurrentWidth * 0.15);
		WorkTypeCol.setText("Typ av arbete");
		
		TableColumn<ObjectTimeOrder, String> HoursCol = new TableColumn();
		HoursCol.setCellValueFactory(new PropertyValueFactory<>("Hours"));
		HoursCol.setMinWidth(Setting.CurrentWidth * 0.05);
		HoursCol.setText("Timmar");
		
		OrderSpecificTable.getColumns().addAll(WorkTypeCol, HoursCol);
		
		workHours = new float[42];
		
		for(int i = 0; i < 42; ++i) {
			workHours[i] = (float)workHours[i];
		}
		
		TextField SearchOrder = new TextField();
		SearchOrder.relocate(Setting.CurrentWidth * 0.005, Setting.CurrentHeight * 0.01);
		SearchOrder.setPrefWidth(Setting.CurrentWidth * 0.1);
		SearchOrder.setStyle("-fx-font-size: 16pt");
		SearchOrder.setPromptText("Sök");
		ContentPane.getChildren().add(SearchOrder);
		
		Button BtnSearchOrder = new Button("Sök");
		BtnSearchOrder.relocate(Setting.CurrentWidth * 0.12, Setting.CurrentHeight * 0.01);
		BtnSearchOrder.setPrefSize(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * 0.0275);
		BtnSearchOrder.setStyle("-fx-font-size: 16pt");
		ContentPane.getChildren().addAll(BtnSearchOrder);
		
		BtnSearchOrder.setOnAction(e -> {
			setOrderTableSpecific(SearchOrder);
		});
		
		OrderLabel = new Label[6];
		
		for(int i = 0; i < 6; ++i) {
			OrderLabel[i] = new Label();
			OrderLabel[i].setStyle("-fx-font-size: 24pt");	
			ContentPane.getChildren().add(OrderLabel[i]);
		}
		
		OrderLabel[0].setText("Produktion");
		OrderLabel[0].relocate(Setting.CurrentWidth * 0.005, Setting.CurrentHeight * 0.69);
		OrderLabel[1].setText("Montage");
		OrderLabel[1].relocate(Setting.CurrentWidth * 0.005, Setting.CurrentHeight * 0.74);
		OrderLabel[2].setText("Total");
		OrderLabel[2].relocate(Setting.CurrentWidth * 0.005, Setting.CurrentHeight * 0.79);
		OrderLabel[3].relocate(Setting.CurrentWidth * 0.105, Setting.CurrentHeight * 0.69);
		OrderLabel[4].relocate(Setting.CurrentWidth * 0.105, Setting.CurrentHeight * 0.74);
		OrderLabel[5].relocate(Setting.CurrentWidth * 0.105, Setting.CurrentHeight * 0.79);
	
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	void setOrderTableSpecific(TextField tf1){
		ObservableList<ObjectTimeOrder> orderSpecificList = FXCollections.observableArrayList();
		
		if (tf1.getText().matches("^[0-9]*$")) {
			// OKEJ
		} else {
			SM.messageBox("Order nr kan bara bestå av siffror (0-9)");
			return;
		}
		
		OrderSpecificTable.getItems().clear();
		
		//RESET FLOAT VARIABLES
		for(int i = 0; i < 42; ++i) {
			workHours[i] = 0;
		}
	
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "Select * from timesave where OrderId = '" + tf1.getText()  + "'";
			rs = st.executeQuery(s);
			
			String beforeValue = "";
			String afterValue = "";
			float holdfloatValue = 0;

			while (rs.next()) {
				
				if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-10")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					 holdfloatValue =+ Float.parseFloat(afterValue);
					workHours[0] +=  holdfloatValue;
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-11")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[1] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-12")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[2] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-15")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[3] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-16")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[4] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-30")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[5] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-31")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[6] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-32")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[7] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-50")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[8] =+ Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-51")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[9] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-53")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[10] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-58")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[11] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-59")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[12] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-70")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[13] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-71")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[14] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-72")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[15] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-73")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[16] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-74")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[17] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-75")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[18] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-76")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[19] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-77")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[20] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-78")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[21] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-79")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[22] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-80")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[23] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-90")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[24] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-91")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[25] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-93")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[26] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-93")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[26] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-100")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[27] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-110")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[28] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-150")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[29] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-160")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[30] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-170")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[31] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-200")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[32] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-250")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[33] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-350")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[34] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-360")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[35] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-370")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[36] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-800")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[37] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-830")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[38] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-900")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[39] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-920")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[40] += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 4).equalsIgnoreCase("-950")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					workHours[41] += Float.parseFloat(afterValue);
				}
			}
		} 
		catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! TimeSave");
		}
		
		float productionTime = 0;
		float montageTime = 0;
		float totalTime = 0;
		
		for(int i = 0; i < 13; ++i) {
			productionTime += workHours[i];
		}
		
		for(int i = 13; i < 24; ++i) {
			montageTime += workHours[i];
		}
		
		for(int i = 24; i < 42; ++i) {
			productionTime += workHours[i];
		}
		
		OrderLabel[3].setText(productionTime + ": Timmar");
	
		OrderLabel[4].setText(montageTime + ": Timmar");
	
		OrderLabel[5].setText((productionTime + montageTime) + ": Timmar");
		

		for(int i = 0; i < 42; i++){
			orderSpecificList.add(new ObjectTimeOrder(WorkType[i], Float.toString(workHours[i])));		
		}
		
		OrderSpecificTable.setItems(orderSpecificList);
		
	
	}

}
