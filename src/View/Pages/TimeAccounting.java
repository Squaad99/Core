package View.Pages;

import Model.Setting;
import View.TimeAccountingObjects.PersonSpecific;
import View.TimeAccountingObjects.SpecificOrder;
import View.TimeAccountingObjects.TimeInsert;
import View.TimeAccountingObjects.TimeOverView;
import View.Windows.NormalWindow;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;

public class TimeAccounting{
	
	public NormalWindow Window;

	public TimeAccounting() {

		Window = new NormalWindow("Tidsredovisning");
		
		//TAB PANE
		TabPane TabPane = new TabPane();
		TabPane.setPrefSize(Setting.CurrentWidth * 0.91, Setting.CurrentHeight * 0.91);
		TabPane.relocate(Setting.CurrentWidth * 0, Setting.CurrentHeight * 0);
		TabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Window.MainPane.getChildren().add(TabPane);
		
		TabPane.setTabMinHeight(Setting.CurrentHeight * 0.03);
		TabPane.setTabMaxHeight(Setting.CurrentHeight * 0.03);
		TabPane.setTabMinWidth(Setting.CurrentWidth * 0.09);
		TabPane.setTabMaxWidth(Setting.CurrentWidth * 0.09);
		
		Tab[] Tab = new Tab[2];
		for (int i = 0; i < 2; i++) {
			Tab[i] = new Tab();
			Tab[i].setStyle("-fx-font-size: 14pt;");
			TabPane.getTabs().add(Tab[i]);
		}
		
		Tab[0].setText("Översikt");
		Tab[1].setText("Lägg in");
		
		Pane pane1 = new Pane();
		
		Tab[0].setContent(pane1);
	
		TimeOverView TimeOverView = new TimeOverView();
		TimeOverView.addToPane(pane1);
		
		SpecificOrder SpecificOrder = new SpecificOrder();
		SpecificOrder.addToPane(pane1);
		
		PersonSpecific PersonSpecific = new PersonSpecific();
		PersonSpecific.addToPane(pane1);
		
		TimeInsert TimeInsert = new TimeInsert();
		
		Tab[1].setContent(TimeInsert.getPane());
	}

}
