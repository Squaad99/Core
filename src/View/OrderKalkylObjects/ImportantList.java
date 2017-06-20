package View.OrderKalkylObjects;

import Model.Setting;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ImportantList {
	
	Pane ContentPane;
	
	public CheckBox[] ImpBox;
	public Label[] LabelImpBox;
	public Button BtnUpdate;
	
	//INSERT VARIABLES
	public String[] impValue = new String[5];
	
	public ImportantList() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.15), (Setting.CurrentHeight * 0.4));
		ContentPane.relocate((Setting.CurrentWidth * 0.41), (Setting.CurrentHeight * 0.05));
		ContentPane.setStyle("-fx-border-color: black;");
		
		Label Header = new Label("Viktigt");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.04), (Setting.CurrentHeight * 0.01));
		ContentPane.getChildren().add(Header);
		
		BtnUpdate = new Button("Spara");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.03));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.095), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");	
		ContentPane.getChildren().add(BtnUpdate);
		
		
		LabelImpBox = new Label[5];
		ImpBox = new CheckBox[5];
		for (int i = 0; i < 5; i++) {
			ImpBox[i] = new CheckBox();
			ImpBox[i].relocate(Setting.CurrentWidth * 0.02, Setting.CurrentHeight * ((0.06 * i) + 0.075));
			ImpBox[i].setScaleX(1.4);
			ImpBox[i].setScaleY(1.4);
			ContentPane.getChildren().add(ImpBox[i]);
			
			LabelImpBox[i] = new Label(Setting.impVar[i] +": Nej");
			LabelImpBox[i].relocate(Setting.CurrentWidth * 0.04, Setting.CurrentHeight * ((0.06 * i) + 0.065));
			LabelImpBox[i].setStyle("-fx-font-size: 18pt;");
			LabelImpBox[i].setTextFill(Color.RED);
			ContentPane.getChildren().add(LabelImpBox[i]);
			
			final int value = i;
			
			ImpBox[i].setOnAction(e -> {
				if (ImpBox[value].isSelected()) {
					LabelImpBox[value].setText(Setting.impVar[value] + ": Ja");
					LabelImpBox[value].setTextFill(Color.GREEN);
				} else {
					LabelImpBox[value].setText(Setting.impVar[value] + ": Nej");
					LabelImpBox[value].setTextFill(Color.RED);
				}
			});		
		}	
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public void openView(){
		ContentPane.relocate((Setting.CurrentWidth * 0.22), (Setting.CurrentHeight * 0.1));
		BtnUpdate.setVisible(true);
	}
	
	public void createView(){
		BtnUpdate.setVisible(false);
	}
	
	public void setInsertVariables(){
		for(int i = 0; i < 5; i++){
			if(ImpBox[i].isSelected()){
				impValue[i] = "true";
			}
			else{
				impValue[i] = "false";
			}
		}
	}
	
	public void clearInsertFields(){
		for (int i = 0; i < 5; i++) {
			ImpBox[i].setSelected(false);
			LabelImpBox[i].setText(Setting.impVar[i] + ": Nej");
			LabelImpBox[i].setTextFill(Color.RED);
		}
	}

	public void setViewData(String[] Imp){
		for(int i = 0; i < 5; i++){
			if(Imp[i].equalsIgnoreCase("true")){
				ImpBox[i].setSelected(true);
				LabelImpBox[i].setText(Setting.impVar[i] + ": Ja");
				LabelImpBox[i].setTextFill(Color.GREEN);
			}
			else{
				ImpBox[i].setSelected(false);
				LabelImpBox[i].setText(Setting.impVar[i] + ": Nej");
				LabelImpBox[i].setTextFill(Color.RED);
			}
		}
	}
	

}
