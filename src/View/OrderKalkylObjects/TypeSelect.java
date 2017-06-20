package View.OrderKalkylObjects;

import Model.SM;
import Model.Setting;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TypeSelect {
	
	Pane ContentPane;
	
	public CheckBox[] TypeBox;
	
	public String Type = "";

	public TypeSelect() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.2), (Setting.CurrentHeight * 0.15));
		ContentPane.relocate((Setting.CurrentWidth * 0.21), (Setting.CurrentHeight * 0.05));
		
		Label Header = new Label("Välj typ av projekt*");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.04), (Setting.CurrentHeight * 0.01));
		ContentPane.getChildren().add(Header);
		
		TypeBox = new CheckBox[3];
		
		for (int i = 0; i < 3; i++) {
			TypeBox[i] = new CheckBox();
			TypeBox[i].setScaleX(1.4);
			TypeBox[i].setScaleY(1.4);
			TypeBox[i].relocate(Setting.CurrentWidth * ((0.06 * i) + 0.02), Setting.CurrentHeight * 0.08);
			ContentPane.getChildren().add(TypeBox[i]);	
		}
		
		TypeBox[0].setOnAction(e->{
			TypeBox[1].setSelected(false);
			TypeBox[2].setSelected(false);
		});
		
		TypeBox[1].setOnAction(e->{
			TypeBox[0].setSelected(false);
			TypeBox[2].setSelected(false);
		});
		
		TypeBox[2].setOnAction(e->{
			TypeBox[0].setSelected(false);
			TypeBox[1].setSelected(false);
		});
		
		TypeBox[0].setText("Sapa");
		TypeBox[1].setText("Stålprofil");
		TypeBox[2].setText("Ståldörr");
		
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public boolean checkInsertData(){
		if(TypeBox[0].isSelected() || TypeBox[1].isSelected() || TypeBox[2].isSelected()){
			//OK INPUT
		}
		else{
			SM.messageBox("Ange typ!");
			return false;
		}
		return true;
	}
	
	public void setInsertVariables(){
		if(TypeBox[0].isSelected()){
			Type = "Sapa";
		}
		else if(TypeBox[1].isSelected()){
			Type = "Stålprofil";
		}
		else if(TypeBox[2].isSelected()){
			Type = "Ståldörr";
		}
	}
	
	public void clearInsertFields(){
		TypeBox[0].setSelected(false);
		TypeBox[1].setSelected(false);
		TypeBox[2].setSelected(false);
	}
	

}
