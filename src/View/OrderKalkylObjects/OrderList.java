package View.OrderKalkylObjects;

import Model.Setting;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class OrderList {

	Pane ContentPane;
	CheckBox[] OrderBox;
	public Button BtnUpdate;
	Label[] LabelOrderBox;
	
	public String[] orderValue = new String[5];
	
	public OrderList() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.16), (Setting.CurrentHeight * 0.4));
		ContentPane.relocate((Setting.CurrentWidth * 0.39), (Setting.CurrentHeight * 0.1));
		ContentPane.setStyle("-fx-border-color: black;");
		
		Label Header = new Label("Beställningar");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.01));
		ContentPane.getChildren().add(Header);
		
		BtnUpdate = new Button("Spara");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.03));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.095), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");	
		ContentPane.getChildren().add(BtnUpdate);
		
		
		
		LabelOrderBox = new Label[5];
		OrderBox = new CheckBox[5];
		for (int i = 0; i < 5; i++) {
			OrderBox[i] = new CheckBox();
			OrderBox[i].relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * ((0.06 * i) + 0.075));
			OrderBox[i].setScaleX(1.4);
			OrderBox[i].setScaleY(1.4);
			ContentPane.getChildren().add(OrderBox[i]);
			
			LabelOrderBox[i] = new Label(Setting.orderVar[i] +": Ej Beställt");
			LabelOrderBox[i].relocate(Setting.CurrentWidth * 0.03, Setting.CurrentHeight * ((0.06 * i) + 0.065));
			LabelOrderBox[i].setStyle("-fx-font-size: 18pt;");
			LabelOrderBox[i].setTextFill(Color.RED);
			ContentPane.getChildren().add(LabelOrderBox[i]);
			
			final int value = i;
			
			OrderBox[i].setOnAction(e -> {
				if (OrderBox[value].isSelected()) {
					LabelOrderBox[value].setText(Setting.orderVar[value] + ": Beställt");
					LabelOrderBox[value].setTextFill(Color.GREEN);
				} else {
					LabelOrderBox[value].setText(Setting.orderVar[value] + ": Ej Beställt");
					LabelOrderBox[value].setTextFill(Color.RED);
				}
			});		
		}
		
	}
	
	public void setViewData(String[] Area){
		for(int i = 0; i < 5; i++){
			if(Area[i].equalsIgnoreCase("true")){
				OrderBox[i].setSelected(true);
				LabelOrderBox[i].setText(Setting.orderVar[i] + ": Beställt");
				LabelOrderBox[i].setTextFill(Color.GREEN);
			}
			else{
				OrderBox[i].setSelected(false);
				LabelOrderBox[i].setText(Setting.orderVar[i] + ": Ej Beställt");
				LabelOrderBox[i].setTextFill(Color.RED);
			}
		}
	}

	public void setInsertVariables(){
		for(int i = 0; i < 5; i++){
			if(OrderBox[i].isSelected()){
				orderValue[i] = "true";
			}
			else{
				orderValue[i] = "false";
			}
		}
	}
	
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
}
