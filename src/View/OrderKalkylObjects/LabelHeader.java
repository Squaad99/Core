package View.OrderKalkylObjects;

import Model.Setting;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class LabelHeader {
	
	Pane ContentPane;
	
	Label Header;
	Label LCreator;
	Label LKalkylCreated;
	Label LOrderCreated;
	
	public LabelHeader() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.5), (Setting.CurrentHeight * 0.1));
		ContentPane.relocate((Setting.CurrentWidth * 0.2), (Setting.CurrentHeight * 0));
	
		Header = new Label("Header");
		Header.setStyle("-fx-font-size: 30pt");
		Header.relocate((Setting.CurrentWidth * 0.15), (Setting.CurrentHeight * 0));
		ContentPane.getChildren().add(Header);
		
		LCreator = new Label("Skapare");
		LCreator.setStyle("-fx-font-size: 20pt");
		LCreator.relocate((Setting.CurrentWidth * 0.02), (Setting.CurrentHeight * 0.01));
		ContentPane.getChildren().add(LCreator);
		
		LKalkylCreated = new Label("Kalkyl skapad: ");
		LKalkylCreated.setStyle("-fx-font-size: 20pt");
		LKalkylCreated.relocate((Setting.CurrentWidth * 0.3), (Setting.CurrentHeight * 0));
		ContentPane.getChildren().add(LKalkylCreated);
		
		LOrderCreated = new Label("Order skapad: ");
		LOrderCreated.setStyle("-fx-font-size: 20pt");
		LOrderCreated.relocate((Setting.CurrentWidth * 0.3), (Setting.CurrentHeight * 0.04));
		ContentPane.getChildren().add(LOrderCreated);
		
		
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}
	
	public void createKalkyl() {
		Header.setText("Skapa Kalkyl");
		LCreator.setVisible(false);
		LKalkylCreated.setVisible(false);
		LOrderCreated.setVisible(false);
	}
	
	public void openKalkyl() {
		Header.setText("Kalkyl");
		LCreator.setVisible(true);
		LKalkylCreated.setVisible(true);
		LOrderCreated.setVisible(false);
	}
	
	public void createOrder() {
		Header.setText("Skapa Order");
		LCreator.setVisible(false);
		LKalkylCreated.setVisible(false);
		LOrderCreated.setVisible(false);
	}
	
	public void openOrder() {
		Header.setText("Order");
		LCreator.setVisible(true);
		LKalkylCreated.setVisible(true);
		LOrderCreated.setVisible(true);
	}
	
	public void openOrderDone() {
		Header.setText("Avslutad Order");
		LCreator.setVisible(true);
		LKalkylCreated.setVisible(true);
		LOrderCreated.setVisible(true);
	}
	
	public void kalkylToOrder() {
		Header.setText("Kalkyl till Order");
		LCreator.setVisible(false);
		LKalkylCreated.setVisible(false);
		LOrderCreated.setVisible(false);
	}
	
	public void setViewKalkyl(String Creator, String KalkylCreated){
		LCreator.setText(Creator);
		LKalkylCreated.setText("Kalkyl skapad: " + KalkylCreated);
	}
	
	public void setViewOrder(String Creator, String KalkylCreated, String OrderCreated){
		LCreator.setText(Creator);
		if(KalkylCreated.equalsIgnoreCase("false")){
			LKalkylCreated.setText("Kalkyl skapad: " + "Ej förkalkyl");
		}
		else{
			LKalkylCreated.setText("Kalkyl skapad: " + KalkylCreated);	
		}
		LOrderCreated.setText("Order skapad: " + OrderCreated);
	}
}
