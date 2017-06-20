package View.OrderKalkylObjects;

import Model.SM;
import Model.Setting;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class AreaDisplay {
	
	Pane ContentPane;
	
	public Button BtnUpdate;

	public ListView<String> list1;
	
	CheckBox[] AreaBox;
	
	public AreaDisplay() {
		
		//VIEW MODE
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.2), (Setting.CurrentHeight * 0.22));
		ContentPane.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.33));
		
		Label Header = new Label("Område");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.01));
		ContentPane.getChildren().add(Header);
		
		list1 = new ListView<>();
		list1.setPrefSize(Setting.CurrentWidth * 0.18, Setting.CurrentHeight * 0.15);
		list1.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.05);
		list1.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(list1);
		
		BtnUpdate = new Button("Ändra");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.03));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.07), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");	
		ContentPane.getChildren().add(BtnUpdate);
		
		//UPDATE MODE
		int extraCounter = 0;
		AreaBox = new CheckBox[6];
		
		for(int i = 0; i < 6; i++){
			AreaBox[i] = new CheckBox(Setting.areaType[i]);
			AreaBox[i].setScaleX(1.4);
			AreaBox[i].setScaleY(1.4);
			AreaBox[i].setVisible(true);
			if(i < 3){
				AreaBox[i].relocate(Setting.CurrentWidth * ((0.06 * i) + 0.025), Setting.CurrentHeight * 0.08);
			}
			else{
				AreaBox[i].relocate(Setting.CurrentWidth * ((0.06 * extraCounter) + 0.025), Setting.CurrentHeight * 0.16);
				extraCounter++;
			}
			ContentPane.getChildren().add(AreaBox[i]);	
		}
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public void viewMode() {
		list1.setVisible(true);
		BtnUpdate.setText("Ändra");
		for(int i = 0; i < 6; i++){
			AreaBox[i].setVisible(false);
		}
	}
	
	public void updateMode() {
		list1.setVisible(false);
		BtnUpdate.setText("Spara");
		for(int i = 0; i < 6; i++){
			AreaBox[i].setVisible(true);
		}
	}
	
	public void setViewData(String[] Area, ListView<String>	List){
		viewMode();
		List.getItems().clear();
		for(int i = 0; i < 6; i++){
			if (Area[i].equals("true")) {
				List.getItems().add(Setting.areaType[i]);
			}
		}
	}
	
	public void setUpdateBoxes(String[] Area){
		for(int i = 0; i < 6; i++){
			if(Area[i].equalsIgnoreCase("true")){
				AreaBox[i].setSelected(true);
			}
			else{
				AreaBox[i].setSelected(false);
			}
		}
	}
	
	public String[] updateArea = new String[6];
	
	public boolean setUpdateVariables(){
		if(AreaBox[0].isSelected() || AreaBox[1].isSelected() || AreaBox[2].isSelected() || AreaBox[3].isSelected() || AreaBox[4].isSelected() || AreaBox[5].isSelected()){
			//OK
		}
		else{
			SM.messageBox("Du måste minst välja ett område!");
			return false;
		}
		
		for(int i = 0; i < 6; i++){
			if(AreaBox[i].isSelected()){
				updateArea[i] = "true";
			}
			else{
				updateArea[i] = "false";
			}
		}
		return true;
	}

}
