package View.OrderKalkylObjects;

import Model.Setting;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class UpperCornerButtons {

	Pane ContentPane;
	public Button[] Btns;
	
	public UpperCornerButtons() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.3), (Setting.CurrentHeight * 0.06));
		ContentPane.relocate((Setting.CurrentWidth * 0.6), (Setting.CurrentHeight * 0.01));
		
		Btns = new Button[2];
		
		for (int i = 0; i < 2; i++) {
			Btns[i] = new Button();
			Btns[i].setPrefSize((Setting.CurrentWidth * 0.09), (Setting.CurrentHeight * 0.04));
			Btns[i].relocate((Setting.CurrentWidth * (0.11 + (0.1 * i))), (Setting.CurrentHeight * 0.01));
			Btns[i].setStyle("-fx-font-size: 14pt");
			ContentPane.getChildren().add(Btns[i]);
		}
		Btns[0].setText("Ta bort Order");
		Btns[1].setText("Sätt status Inaktiv");
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public void setOpenKalkylMode(){
		Btns[0].setText("Ta bort Kalkyl");
		Btns[1].setText("Gör till Order");
	}
	
	public void setOpenOrderActiveMode(){
		Btns[0].setText("Ta bort Order");
		Btns[1].setText("Sätt status Inaktiv");
	}
	
	public void setOpenOrderFinishedMode(){
		Btns[0].setText("Ta bort Order");
		Btns[1].setText("Sätt status Aktiv");
	}
	

}
