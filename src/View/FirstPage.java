package View;

import Model.Setting;
import Model.FirstPageMysql.*;
import View.FirstPageObject.TableProject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;

public class FirstPage {
	
	public Pane pane;
	
	TabPane TabPane;
	public Button[] MenuButton;
	public Button SubMenueButton;

	FirstPageGet FirstPageGetMysql;
	public TableProject TableProjectKalkyl;
	public TableProject TableProjectActiveOrders;
	public TableProject TableProjectFinshedOrders;
	
	public FirstPage() {
		
		pane = new Pane();
		pane.setStyle("-fx-background-color: #a7bfe5, linear-gradient(to top left, #a7bfe5, #e6f3ff); -fx-opacity: 0.8;");
		
		//MENU BUTTONS
		
		Separator separator = new Separator();
		separator.relocate(Setting.CurrentWidth * 0.39, Setting.CurrentHeight * 0);
		separator.setOrientation(Orientation.VERTICAL);
		separator.setPrefSize(Setting.CurrentWidth * 0.02, Setting.CurrentHeight * 1.1);
		pane.getChildren().add(separator);
		
		MenuButton = new Button[5];
		for (int i = 0; i < 5; i++) {
			MenuButton[i] = new Button();
			MenuButton[i].setPrefSize(Setting.CurrentWidth * 0.12, Setting.CurrentHeight * 0.08);
			MenuButton[i].setStyle("-fx-font-size: 16pt; -fx-font-weight: bold;");
			pane.getChildren().add(MenuButton[i]);
		}
		MenuButton[0].setText("Skapa Kalkyl");
		MenuButton[0].relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.2);
		
		MenuButton[1].setText("Skapa Order");
		MenuButton[1].relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.3);
		
		MenuButton[2].setText("Kalender");
		MenuButton[2].relocate(Setting.CurrentWidth * 0.14, Setting.CurrentHeight * 0.2);
		
		MenuButton[3].setText("Tidsredovisning");
		MenuButton[3].relocate(Setting.CurrentWidth * 0.14, Setting.CurrentHeight * 0.3);
		
		MenuButton[4].setText("Kunder");
		MenuButton[4].relocate(Setting.CurrentWidth * 0.27, Setting.CurrentHeight * 0.2);
		
		SubMenueButton = new Button("Inställningar");
		SubMenueButton.setPrefSize(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * 0.04);
		SubMenueButton.setStyle("-fx-font-size: 14pt");
		SubMenueButton.relocate(Setting.CurrentWidth * 0.02, Setting.CurrentHeight * 0.7);
		pane.getChildren().add(SubMenueButton);
		
		//TAB PANE
		TabPane = new TabPane();
		TabPane.setPrefSize(Setting.CurrentWidth * 0.6, Setting.CurrentHeight * 1.02);
		TabPane.relocate(Setting.CurrentWidth * 0.4, 0);
		TabPane.setStyle("-fx-background-color: gray");
		TabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		pane.getChildren().add(TabPane);
		
		TabPane.setTabMinHeight(Setting.CurrentHeight * 0.03);
		TabPane.setTabMaxHeight(Setting.CurrentHeight * 0.03);
		TabPane.setTabMinWidth(Setting.CurrentWidth * 0.09);
		TabPane.setTabMaxWidth(Setting.CurrentWidth * 0.09);
		
		Tab[] Tab = new Tab[3];
		for (int i = 0; i < 3; i++) {
			Tab[i] = new Tab();
			Tab[i].setStyle("-fx-font-size: 14pt;");
			TabPane.getTabs().add(Tab[i]);
		}
		
		Tab[0].setText("Kalkyler");
		Tab[1].setText("Aktiva Ordrar");
		Tab[2].setText("Avslutade Ordrar");
		
		//OBJECTS CRATED
		TableProjectKalkyl = new TableProject();
		TableProjectKalkyl.setKalkylMode();
		
		TableProjectActiveOrders = new TableProject();
		TableProjectActiveOrders.setOrderActiveMode();
		
		TableProjectFinshedOrders = new TableProject();
		TableProjectFinshedOrders.setOrderFinishedMode();
		
		FirstPageGetMysql = new FirstPageGet(TableProjectKalkyl.KalkylTableData, TableProjectActiveOrders.OrderActiveTableData, TableProjectFinshedOrders.OrderFinishedTableData);
		
		
		//GET MYSQL DATA
		setKalkylTable();
		setActiveOrdersTable();
		setFinishedOrdersTable();
		
		
		//FIRST TAB KALKYL
		Tab[0].setContent(TableProjectKalkyl.getPane());
		TableProjectKalkyl.UpdateButton.setOnAction(e-> setKalkylTable());
		
		//SECOND TAB AKTIVA ORDERS
		Tab[1].setContent(TableProjectActiveOrders.getPane());
		TableProjectActiveOrders.UpdateButton.setOnAction(e-> setActiveOrdersTable());
		
		//THIRD TAB AVSLUTADE ORDERS
		Tab[2].setContent(TableProjectFinshedOrders.getPane());
		TableProjectFinshedOrders.UpdateButton.setOnAction(e-> setFinishedOrdersTable());
		
	}
	
	public void createKalkylButton(EventHandler<ActionEvent> eventHandler){
		MenuButton[0].setOnAction(eventHandler);
	}
	
	public void createOrderButton(EventHandler<ActionEvent> eventHandler){
		MenuButton[1].setOnAction(eventHandler);
	}
	
	public void customerButton(EventHandler<ActionEvent> eventHandler){
		MenuButton[4].setOnAction(eventHandler);
	}
	
	public void setKalkylTable(){
		FirstPageGetMysql.setKalkylTable(TableProjectKalkyl.ProjectTable);
	}
	
	public void setActiveOrdersTable(){
		FirstPageGetMysql.setOrderActiveTable(TableProjectActiveOrders.ProjectTable);
	}
	
	public void setFinishedOrdersTable(){
		FirstPageGetMysql.setOrderFinishedTable(TableProjectFinshedOrders.ProjectTable);
	}
	
	

	
	
	
	
	
	
}
