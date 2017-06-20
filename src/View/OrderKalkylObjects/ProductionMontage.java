package View.OrderKalkylObjects;

import Model.SM;
import Model.Setting;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class ProductionMontage {

	Pane ContentPane;
	ProgressBar[] ProgBarTime;
	Label[] LabelTime;
	Label[] DescLabel;
	Line line1;
	Line line2;
	Label[] LabelHour;
	public TextField[] TextFieldHour;
	CheckBox[] CheckBox;
	public ComboBox<String>[] YearBox;
	public ComboBox<String>[] WeekBox;
	Label[] LabelNo;
	public Button BtnUpdate;

	public ProductionMontage() {

		ContentPane = new Pane();
		ContentPane.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.7));
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.5), (Setting.CurrentHeight * 0.18));

		// VIEW MODE
		Label Header1 = new Label("Produktion");
		Header1.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header1.relocate(Setting.CurrentWidth * 0.085, Setting.CurrentHeight * 0);
		ContentPane.getChildren().add(Header1);

		Label Header2 = new Label("Montage");
		Header2.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header2.relocate(Setting.CurrentWidth * 0.32, Setting.CurrentHeight * 0);
		ContentPane.getChildren().add(Header2);

		BtnUpdate = new Button("Ändra");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.01));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.445), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(BtnUpdate);

		int extraCounter = 0;
		LabelTime = new Label[4];

		for (int i = 0; i < 4; i++) {
			LabelTime[i] = new Label();
			LabelTime[i].setStyle("-fx-font-size: 16pt;");
			if (i < 2) {
				LabelTime[i].relocate(Setting.CurrentWidth * 0.095, Setting.CurrentHeight * ((0.08 * i) + 0.05));
			} else {
				LabelTime[i].relocate(Setting.CurrentWidth * 0.325,
						Setting.CurrentHeight * ((0.08 * extraCounter) + 0.05));
				extraCounter++;
			}
			ContentPane.getChildren().add(LabelTime[i]);
		}
		
		LabelTime[0].relocate(Setting.CurrentWidth * 0.07, Setting.CurrentHeight * 0.05);
		LabelTime[2].relocate(Setting.CurrentWidth * 0.3, Setting.CurrentHeight * 0.05);
		
		ProgBarTime = new ProgressBar[2];

		for (int i = 0; i < 2; i++) {
			ProgBarTime[i] = new ProgressBar(0);
			ProgBarTime[i].relocate(Setting.CurrentWidth * (0.07 + (0.2275 * i)), Setting.CurrentHeight * 0.095);
			ProgBarTime[i].setPrefSize(Setting.CurrentWidth * 0.1, Setting.CurrentHeight * 0.03);
			ContentPane.getChildren().add(ProgBarTime[i]);
		}

		// UPDATE / INSERT MODE

		DescLabel = new Label[4];
		for (int i = 0; i < 4; i++) {
			DescLabel[i] = new Label("Välj år:");
			DescLabel[i].setStyle("-fx-font-size: 16pt");
			if (i < 2) {
				DescLabel[i].relocate(Setting.CurrentWidth * (0.025 + (0.225 * i)), Setting.CurrentHeight * 0.05);
			} else if (i < 3) {
				DescLabel[i].relocate(Setting.CurrentWidth * (-0.415 + (0.21 * i)), Setting.CurrentHeight * 0.085);
				DescLabel[i].setText("Välj Vecka:");
			} else if (i < 4) {
				DescLabel[i].relocate(Setting.CurrentWidth * (-0.4 + (0.21 * i)), Setting.CurrentHeight * 0.085);
				DescLabel[i].setText("Välj Vecka:");
			}
			ContentPane.getChildren().add(DescLabel[i]);
		}

		WeekBox = new ComboBox[4];
		for (int i = 0; i < 4; i++) {
			WeekBox[i] = new ComboBox<String>();
			WeekBox[i].setPrefSize(Setting.CurrentWidth * 0.05, Setting.CurrentHeight * 0.02);
			WeekBox[i].setStyle("-fx-font-size: 10pt");
			SM.setWeekCombo(WeekBox[i]);
			if (i < 2) {
				WeekBox[i].relocate(Setting.CurrentWidth * (0.06 + (0.08 * i)), Setting.CurrentHeight * 0.09);
			} else if (i < 4) {
				WeekBox[i].relocate(Setting.CurrentWidth * (0.125 + (0.08 * i)), Setting.CurrentHeight * 0.09);
			}
			ContentPane.getChildren().add(WeekBox[i]);
		}

		YearBox = new ComboBox[4];
		for (int i = 0; i < 4; i++) {
			YearBox[i] = new ComboBox<String>();
			YearBox[i].setPrefSize(Setting.CurrentWidth * 0.05, Setting.CurrentHeight * 0.02);
			YearBox[i].setStyle("-fx-font-size: 10pt");
			SM.setYearComboBox(YearBox[i]);
			final int x = i;
			YearBox[i].setOnAction(e ->{
				if(YearBox[x].getSelectionModel().isEmpty()){
					//nothing
				}
				else{
					SM.setYearForSelectedValue(YearBox[x], WeekBox[x]);
				}
			});

			if (i < 2) {
				YearBox[i].relocate(Setting.CurrentWidth * (0.06 + (0.08 * i)), Setting.CurrentHeight * 0.05);
			} else if (i < 4) {
				YearBox[i].relocate(Setting.CurrentWidth * (0.125 + (0.08 * i)), Setting.CurrentHeight * 0.05);
			}
			ContentPane.getChildren().add(YearBox[i]);
		}

		CheckBox = new CheckBox[4];
		for (int i = 0; i < 4; i++) {
			CheckBox[i] = new CheckBox();
			CheckBox[i].setScaleX(1.4);
			CheckBox[i].setScaleY(1.4);
			if (i < 2) {
				CheckBox[i].relocate(Setting.CurrentWidth * (0.015 + (0.155 * i)), Setting.CurrentHeight * 0.015);
			} else {
				CheckBox[i].relocate(Setting.CurrentWidth * (0.0 + (0.13 * i)), Setting.CurrentHeight * 0.015);
			}
			ContentPane.getChildren().add(CheckBox[i]);
		}

		CheckBox[0].setText("[Ej Produktion]");
		CheckBox[1].setText("[Preiod Ok]");
		CheckBox[2].setText("[Ej Montage]");
		CheckBox[3].setText("[Preiod Ok]");

		CheckBox[0].setOnAction(e -> {
			if (CheckBox[0].isSelected()) {
				noProduction();
			} else {
				yesProduction();
			}
		});

		CheckBox[2].setOnAction(e -> {
			if (CheckBox[2].isSelected()) {
				noMontage();
			} else {
				yesMontage();
			}
		});

		line1 = new Line();
		line1.setStartX(Setting.CurrentWidth * 0.12);
		line1.setStartY(Setting.CurrentHeight * 0.08);
		line1.setEndX(Setting.CurrentWidth * 0.13);
		line1.setEndY(Setting.CurrentHeight * 0.08);
		line1.setStrokeWidth(2);
		ContentPane.getChildren().add(line1);

		line2 = new Line();
		line2.setStartX(Setting.CurrentWidth * 0.345);
		line2.setStartY(Setting.CurrentHeight * 0.08);
		line2.setEndX(Setting.CurrentWidth * 0.355);
		line2.setEndY(Setting.CurrentHeight * 0.08);
		line2.setStrokeWidth(2);
		ContentPane.getChildren().add(line2);

		LabelHour = new Label[2];
		for (int i = 0; i < 2; i++) {
			LabelHour[i] = new Label("Antal timmar:");
			LabelHour[i].relocate(Setting.CurrentWidth * (0.03 + (0.23 * i)), Setting.CurrentHeight * 0.135);
			LabelHour[i].setStyle("-fx-font-size: 16pt");
			ContentPane.getChildren().add(LabelHour[i]);
		}

		TextFieldHour = new TextField[2];
		for (int i = 0; i < 2; i++) {
			TextFieldHour[i] = new TextField();
			TextFieldHour[i].setStyle("-fx-font-size: 14pt");
			TextFieldHour[i].setPromptText("Timmar");
			TextFieldHour[i].setPrefWidth(Setting.CurrentWidth * 0.08);
			TextFieldHour[i].relocate(Setting.CurrentWidth * (0.1 + (0.23 * i)), Setting.CurrentHeight * 0.13);
			ContentPane.getChildren().add(TextFieldHour[i]);
		}

		LabelNo = new Label[2];
		for (int i = 0; i < 2; i++) {
			LabelNo[i] = new Label();
			LabelNo[i].setStyle("-fx-font-size: 28pt; -fx-text-fill: red;");
			LabelNo[i].relocate(Setting.CurrentWidth * (0.06 + (0.24 * i)), Setting.CurrentHeight * 0.08);
			LabelNo[i].setVisible(false);
			ContentPane.getChildren().add(LabelNo[i]);
		}
		LabelNo[0].setText("Ej Produktion");
		LabelNo[1].setText("Ej Montage");
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	//---------------------------------------------
	
	public void viewProductionYes() {
		ProgBarTime[0].setVisible(true);
		for (int i = 0; i < 2; i++) {
			LabelTime[i].setVisible(true);
		}
		LabelNo[0].setVisible(false);
		CheckBox[0].setVisible(false);
		CheckBox[1].setVisible(false);
		DescLabel[0].setVisible(false);
		DescLabel[2].setVisible(false);
		line1.setVisible(false);
		LabelHour[0].setVisible(false);
		TextFieldHour[0].setVisible(false);
		for (int i = 0; i < 2; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
		}
		CheckBox[0].setSelected(false);
		CheckBox[1].setSelected(false);
	}

	public void viewProductionNo(){
		ProgBarTime[0].setVisible(false);
		for (int i = 0; i < 2; i++) {
			LabelTime[i].setVisible(false);	
		}
		LabelNo[0].setVisible(true);
		CheckBox[0].setVisible(false);
		CheckBox[1].setVisible(false);
		DescLabel[0].setVisible(false);
		DescLabel[2].setVisible(false);
		line1.setVisible(false);
		LabelHour[0].setVisible(false);
		TextFieldHour[0].setVisible(false);
		for (int i = 0; i < 2; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
		}
		CheckBox[0].setSelected(false);
		CheckBox[1].setSelected(false);
	}

	public void viewMontageYes() {
		ProgBarTime[1].setVisible(true);
		for (int i = 2; i < 4; i++) {
			LabelTime[i].setVisible(true);
		}
		LabelNo[1].setVisible(false);
		CheckBox[2].setVisible(false);
		DescLabel[1].setVisible(false);
		DescLabel[3].setVisible(false);
		line2.setVisible(false);
		LabelHour[1].setVisible(false);
		TextFieldHour[1].setVisible(false);
		CheckBox[3].setVisible(false);
		for (int i = 2; i < 4; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
		}
		CheckBox[2].setSelected(false);
		CheckBox[3].setSelected(false);
	}
	
	public void viewMontageNo() {
		ProgBarTime[1].setVisible(false);
		for (int i = 2; i < 4; i++) {
			LabelTime[i].setVisible(false);
		}
		LabelNo[1].setVisible(true);
		CheckBox[2].setVisible(false);
		DescLabel[1].setVisible(false);
		DescLabel[3].setVisible(false);
		line2.setVisible(false);
		LabelHour[1].setVisible(false);
		TextFieldHour[1].setVisible(false);
		CheckBox[3].setVisible(false);
		for (int i = 2; i < 4; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
		}
		CheckBox[2].setSelected(false);
		CheckBox[3].setSelected(false);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	// ----------------------------------------------

	public void insertProductionShow() {
		DescLabel[0].setVisible(true);
		DescLabel[2].setVisible(true);
		line1.setVisible(true);
		LabelHour[0].setVisible(true);
		TextFieldHour[0].setVisible(true);

		for (int i = 0; i < 2; i++) {
			YearBox[i].setVisible(true);
			WeekBox[i].setVisible(true);
			CheckBox[i].setVisible(true);
		}
		LabelNo[0].setVisible(false);;
	}

	public void insertProductionHide() {
		DescLabel[0].setVisible(false);
		DescLabel[2].setVisible(false);
		line1.setVisible(false);
		LabelHour[0].setVisible(false);
		TextFieldHour[0].setVisible(false);

		for (int i = 0; i < 2; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
			CheckBox[i].setVisible(false);
		}
	}

	public void insertMontageShow() {
		DescLabel[1].setVisible(true);
		DescLabel[3].setVisible(true);
		line2.setVisible(true);
		LabelHour[1].setVisible(true);
		TextFieldHour[1].setVisible(true);

		for (int i = 2; i < 4; i++) {
			YearBox[i].setVisible(true);
			WeekBox[i].setVisible(true);
			CheckBox[i].setVisible(true);
		}
		LabelNo[1].setVisible(false);
	}

	public void insertMontageHide() {
		DescLabel[1].setVisible(false);
		DescLabel[3].setVisible(false);
		line2.setVisible(false);
		LabelHour[1].setVisible(false);
		TextFieldHour[1].setVisible(false);

		for (int i = 2; i < 4; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
			CheckBox[i].setVisible(false);
		}
	}

	// ---------------------------------------------

	public void yesProduction() {
		CheckBox[0].setVisible(true);
		CheckBox[1].setVisible(true);
		DescLabel[0].setVisible(true);
		DescLabel[2].setVisible(true);
		line1.setVisible(true);
		LabelHour[0].setVisible(true);
		TextFieldHour[0].setVisible(true);

		for (int i = 0; i < 2; i++) {
			YearBox[i].setVisible(true);
			WeekBox[i].setVisible(true);
		}
		LabelNo[0].setVisible(false);
	}
	
	public void noProduction() {
		CheckBox[0].setVisible(true);
		CheckBox[1].setVisible(false);
		DescLabel[0].setVisible(false);
		DescLabel[2].setVisible(false);
		line1.setVisible(false);
		LabelHour[0].setVisible(false);
		TextFieldHour[0].setVisible(false);

		for (int i = 0; i < 2; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
		}
		LabelNo[0].setVisible(true);
	}

	public void yesMontage() {
		CheckBox[2].setVisible(true);
		CheckBox[3].setVisible(true);
		DescLabel[1].setVisible(true);
		DescLabel[3].setVisible(true);
		line2.setVisible(true);
		LabelHour[1].setVisible(true);
		TextFieldHour[1].setVisible(true);

		for (int i = 2; i < 4; i++) {
			YearBox[i].setVisible(true);
			WeekBox[i].setVisible(true);
		}
		LabelNo[1].setVisible(false);
	}

	public void noMontage() {
		CheckBox[2].setVisible(true);
		CheckBox[3].setVisible(false);
		DescLabel[1].setVisible(false);
		DescLabel[3].setVisible(false);
		line2.setVisible(false);
		LabelHour[1].setVisible(false);
		TextFieldHour[1].setVisible(false);

		for (int i = 2; i < 4; i++) {
			YearBox[i].setVisible(false);
			WeekBox[i].setVisible(false);
		}
		LabelNo[1].setVisible(true);
	}

	// --------------------------------------------------

	public void insertMode() {
		viewProductionNo();
		viewMontageNo();
		insertProductionShow();
		insertMontageShow();
		BtnUpdate.setVisible(false);
	}

	public void openMode(){
		viewProductionNo();
		viewMontageNo();
		insertProductionHide();
		insertMontageHide();
		BtnUpdate.setVisible(true);
	}
	
	public void setYearBoxes() {

	}

	public boolean checkInsertData() {
		// CHECK PRODUCTION
		if (CheckBox[0].isSelected()) {
			// NO PRODUCTUION
		} else {
			int yearStart = Integer.parseInt(YearBox[0].getValue().toString());
			int yearStop = Integer.parseInt(YearBox[1].getValue().toString());

			int weekStart = Integer.parseInt(WeekBox[0].getValue().toString());
			int weekStop = Integer.parseInt(WeekBox[1].getValue().toString());

			if (yearStart == yearStop) {
				if (weekStart <= weekStop) {
					// ok
				} else {
					SM.messageBox("Vecka då produktion avslutas kan inte vara före den påbörjas!");
					return false;
				}
			} else {
				if (yearStart > yearStop) {
					SM.messageBox("Året då produktion avslutas kan inte vara före det påbörjas!");
					return false;
				}
			}

			if (TextFieldHour[0].getText().isEmpty()) {
				SM.messageBox("Ange timmar produktion!");
				return false;
			}
			else{
				if(TextFieldHour[0].getText().matches("^[0-9]*$")){
					//ok
				}
				else{
					SM.messageBox("Produktions timmar kan bara vara siffror!");
					return false;
				}
			}	
			if (CheckBox[1].isSelected()) {
				// KLAR
			} else {
				SM.messageBox("Klar markera period för produktion!");
				return false;
			}
		}
		// CHECK MONTAGE
		if (CheckBox[2].isSelected()) {
			// NO MONTAGE
		} else {
			int yearStart = Integer.parseInt(YearBox[2].getValue().toString());
			int yearStop = Integer.parseInt(YearBox[3].getValue().toString());

			int weekStart = Integer.parseInt(WeekBox[2].getValue().toString());
			int weekStop = Integer.parseInt(WeekBox[3].getValue().toString());

			if (yearStart == yearStop) {
				if (weekStart <= weekStop) {
					// ok
				} else {
					SM.messageBox("Vecka då montage avslutas kan inte vara före den påbörjas!");
					return false;
				}
			} else {
				if (yearStart > yearStop) {
					SM.messageBox("Året då montage avslutas kan inte vara före det påbörjas!");
					return false;
				}
			}
			if (TextFieldHour[1].getText().isEmpty()) {
				SM.messageBox("Ange timmar montage!");
				return false;
			}
			else{
				if(TextFieldHour[1].getText().matches("^[0-9]*$")){
					//ok
				}
				else{
					SM.messageBox("Montage timmar kan bara vara siffror!");
					return false;
				}
			}

			if (CheckBox[3].isSelected()) {
				// KLAR
			} else {
				SM.messageBox("Klar markera period för montage!");
				return false;
			}
		}
		return true;
	}

	//---------------------------------------------------
	
	public void updateProductionState(String Start, String Hours){
		
		ProgBarTime[0].setVisible(false);
		for (int i = 0; i < 2; i++) {
			LabelTime[i].setVisible(false);	
		}
		CheckBox[0].setVisible(true);
		if(Start.equalsIgnoreCase("false")){
			CheckBox[1].setVisible(false);
			DescLabel[0].setVisible(false);
			DescLabel[2].setVisible(false);
			line1.setVisible(false);
			LabelHour[0].setVisible(false);
			TextFieldHour[0].setVisible(false);
			for (int i = 0; i < 2; i++) {
				YearBox[i].setVisible(false);
				WeekBox[i].setVisible(false);
			}
			LabelNo[0].setVisible(true);
			CheckBox[0].setSelected(true);
			CheckBox[1].setSelected(false);
		}
		else{
			CheckBox[1].setVisible(true);
			DescLabel[0].setVisible(true);
			DescLabel[2].setVisible(true);
			line1.setVisible(true);
			LabelHour[0].setVisible(true);
			TextFieldHour[0].setVisible(true);
			TextFieldHour[0].setText(Hours);
			for (int i = 0; i < 2; i++) {
				YearBox[i].setVisible(true);
				WeekBox[i].setVisible(true);
			}
			LabelNo[0].setVisible(false);
			CheckBox[0].setSelected(false);
			CheckBox[1].setSelected(false);
		}
	}
	
	public void updateMontageState(String Start, String Hours){
		ProgBarTime[1].setVisible(false);
		for (int i = 2; i < 4; i++) {
			LabelTime[i].setVisible(false);
		}
		CheckBox[2].setVisible(true);
		if(Start.equalsIgnoreCase("false")){
			DescLabel[1].setVisible(false);
			DescLabel[3].setVisible(false);
			line2.setVisible(false);
			LabelHour[1].setVisible(false);
			TextFieldHour[1].setVisible(false);
			CheckBox[3].setVisible(false);
			
			for (int i = 2; i < 4; i++) {
				YearBox[i].setVisible(false);
				WeekBox[i].setVisible(false);
			}
			LabelNo[1].setVisible(true);
			CheckBox[2].setSelected(true);
			CheckBox[3].setSelected(false);
		}
		else{
			DescLabel[1].setVisible(true);
			DescLabel[3].setVisible(true);
			line2.setVisible(true);
			LabelHour[1].setVisible(true);
			TextFieldHour[1].setVisible(true);
			TextFieldHour[1].setText(Hours);
			CheckBox[3].setVisible(true);
			for (int i = 2; i < 4; i++) {
				YearBox[i].setVisible(true);
				WeekBox[i].setVisible(true);
			}
			LabelNo[1].setVisible(false);
			CheckBox[2].setSelected(false);
			CheckBox[3].setSelected(false);
		}
	}
	
	//----------------------------------------------------
	
	// PRODUCTION
	public String StartP = "";
	public String StopP = "";
	public String HoursP = "";
	// MONTAGE
	public String StartM = "";
	public String StopM = "";
	public String HoursM = "";

	public void setInsertVariables() {
		// PRODUCTION
		if (CheckBox[0].isSelected()) {
			// NO PRODUCTUION
			StartP = "false";
			StopP = "false";
			HoursP = "false";
		} else {
			StartP = YearBox[0].getValue() + "-" + WeekBox[0].getValue();
			StopP = YearBox[1].getValue() + "-" + WeekBox[1].getValue();
			HoursP = TextFieldHour[0].getText();
		}
		// MONTAGE
		if (CheckBox[2].isSelected()) {
			// NO PRODUCTUION
			StartM = "false";
			StopM = "false";
			HoursM = "false";
		} else {
			StartM = YearBox[2].getValue() + "-" + WeekBox[2].getValue();
			StopM = YearBox[3].getValue() + "-" + WeekBox[3].getValue();
			HoursM = TextFieldHour[1].getText();
		}
	}

	public void clearAllFields() {
		for (int i = 0; i < 4; i++) {
			CheckBox[i].setSelected(false);
			SM.setYearComboBox(YearBox[i]);
			SM.setWeekCombo(WeekBox[i]);
		}
		TextFieldHour[0].clear();
		TextFieldHour[1].clear();
		yesProduction();
		yesMontage();
	}
	
	public void setProductionView(String Start, String Stop, float HoursDone, String HoursCalc){
		BtnUpdate.setText("Ändra");
		if(Start.equalsIgnoreCase("false")){
			viewProductionNo();
			return;
		}
		viewProductionYes();
		LabelTime[0].setText(Start + " till " + Stop);
		LabelTime[1].setText(HoursDone + " / " + HoursCalc);
		
		float progress = HoursDone / Float.parseFloat(HoursCalc);
		ProgBarTime[0].setProgress(progress);
	}
	
	public void setMontageView(String Start, String Stop, float HoursDone, String HoursCalc){
		if(Start.equalsIgnoreCase("false")){
			viewMontageNo();
			return;
		}
		viewMontageYes();
		LabelTime[2].setText(Start + " till " + Stop);
		LabelTime[3].setText(HoursDone + " / " + HoursCalc);
		
		float progress = HoursDone / Float.parseFloat(HoursCalc);
		ProgBarTime[1].setProgress(progress);
	}
}
