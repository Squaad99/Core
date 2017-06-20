package View.OrderKalkylObjects;

import Model.SM;
import Model.Setting;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AreaSelect {
	
	Pane ContentPane;
	public CheckBox[] AreaBox;

	public AreaSelect() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.2), (Setting.CurrentHeight * 0.15));
		ContentPane.relocate((Setting.CurrentWidth * 0.21), (Setting.CurrentHeight * 0.22));
		
		Label Header = new Label("Välj område*");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.06), (Setting.CurrentHeight * 0));
		ContentPane.getChildren().add(Header);
		
		AreaBox = new CheckBox[6];
		
		int extraCounter = 0;
		
		for (int i = 0; i < 6; i++) {
		
			AreaBox[i] = new CheckBox(Setting.areaType[i]);
			AreaBox[i].setScaleX(1.4);
			AreaBox[i].setScaleY(1.4);
			if(i < 3){
				AreaBox[i].relocate(Setting.CurrentWidth * ((0.06 * i) + 0.025), Setting.CurrentHeight * 0.05);
			}
			else{
				AreaBox[i].relocate(Setting.CurrentWidth * ((0.06 * extraCounter) + 0.025), Setting.CurrentHeight * 0.1);
				extraCounter++;
			}
			
			ContentPane.getChildren().add(AreaBox[i]);	
		}
		
		
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public boolean checkInsertData(){
		
		if(AreaBox[0].isSelected() || AreaBox[1].isSelected() || AreaBox[2].isSelected() || AreaBox[3].isSelected() || AreaBox[4].isSelected() || AreaBox[5].isSelected()){
			//OK
		}
		else{
			SM.messageBox("Ange område!");
			return false;
		}
		return true;
	}
	
	public String[] areaValue = new String[6];
	
	public void setInsertVariables(){
		for(int i = 0; i < 6; i++){
			if(AreaBox[i].isSelected()){
				areaValue[i] = "true";
			}
			else{
				areaValue[i] = "false";
			}
		}	
	}
	
	public void clearInsertFields(){
		for(int i = 0; i < 6; i++){
			AreaBox[i].setSelected(false);
		}
	}

}
