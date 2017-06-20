package View.OrderKalkylObjects;

import Model.SM;
import Model.Setting;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class MainInfo {

	// MainInfo has 3 stages, 1, Create 2, View 3, Update
	Pane ContentPane;

	// CREATE MODE
	Label[] LabelCreate;
	public TextField[] TextFieldCreate;

	// VIEW MODE
	Label[] LabelView;
	Label[] LabelViewHolder;

	// UPDATE MODE
	Label[] LabelUpdate;
	public TextField[] TextFieldUpdate;
	public Button BtnUpdate;
	CheckBox[] BoxTypeUpdate;
	
	//INSERT VARIABLES
	public String number = "";
	public String name = "";
	public String address = "";
	
	public MainInfo() {

		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.2), (Setting.CurrentHeight * 0.28));
		ContentPane.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.05));

		// CREATE MODE

		LabelCreate = new Label[3];

		for (int i = 0; i < 3; i++) {
			LabelCreate[i] = new Label();
			LabelCreate[i].setStyle("-fx-font-size: 20pt; -fx-underline: true");
			LabelCreate[i].relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * ((0.06 * i) + 0.05));
			LabelCreate[i].setVisible(false);
			ContentPane.getChildren().add(LabelCreate[i]);
		}

		LabelCreate[0].setText("Nummer*");
		LabelCreate[1].setText("Namn*");
		LabelCreate[2].setText("Adress*");
		TextFieldCreate = new TextField[3];

		for (int i = 0; i < 3; i++) {
			TextFieldCreate[i] = new TextField();
			TextFieldCreate[i].setStyle("-fx-font-size: 16pt");
			TextFieldCreate[i].setMaxWidth(Setting.CurrentWidth * 0.11);
			TextFieldCreate[i].relocate(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * ((0.06 * i) + 0.05));
			TextFieldCreate[i].setVisible(false);
			ContentPane.getChildren().add(TextFieldCreate[i]);
		}
		TextFieldCreate[0].setPromptText("Nummer");
		TextFieldCreate[1].setPromptText("Namn");
		TextFieldCreate[2].setPromptText("Adress");
		
		// VIEW MODE

		LabelView = new Label[4];

		for (int i = 0; i < 4; i++) {
			LabelView[i] = new Label();
			LabelView[i].setStyle("-fx-font-size: 20pt");
			LabelView[i].relocate(Setting.CurrentWidth * 0.02, Setting.CurrentHeight * ((0.06 * i) + 0.05));
			LabelView[i].setVisible(false);
			ContentPane.getChildren().add(LabelView[i]);
		}

		LabelView[0].setText("Nummer:");
		LabelView[1].setText("Namn:");
		LabelView[2].setText("Adress:");
		LabelView[3].setText("Typ:");

		LabelViewHolder = new Label[4];

		for (int i = 0; i < 4; i++) {
			LabelViewHolder[i] = new Label();
			LabelViewHolder[i].setStyle("-fx-font-size: 20pt");
			LabelViewHolder[i].relocate(Setting.CurrentWidth * 0.09, Setting.CurrentHeight * ((0.06 * i) + 0.05));
			LabelViewHolder[i].setVisible(false);
			ContentPane.getChildren().add(LabelViewHolder[i]);
		}

		// UPDATE MODE

		BtnUpdate = new Button("Ändra");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.05), (Setting.CurrentHeight * 0.03));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.14), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");
	
		ContentPane.getChildren().add(BtnUpdate);
		LabelUpdate = new Label[3];

		for (int i = 0; i < 3; i++) {
			LabelUpdate[i] = new Label();
			LabelUpdate[i].setStyle("-fx-font-size: 20pt");
			LabelUpdate[i].relocate(Setting.CurrentWidth * 0.02, Setting.CurrentHeight * ((0.06 * i) + 0.05));
			LabelUpdate[i].setVisible(false);
			ContentPane.getChildren().add(LabelUpdate[i]);
		}

		LabelUpdate[0].setText("Nummer:*");
		LabelUpdate[1].setText("Namn:*");
		LabelUpdate[2].setText("Adress:*");
		
		TextFieldUpdate = new TextField[2];

		for (int i = 0; i < 2; i++) {
			TextFieldUpdate[i] = new TextField();
			TextFieldUpdate[i].setStyle("-fx-font-size: 16pt");
			TextFieldUpdate[i].setMaxWidth(Setting.CurrentWidth * 0.11);
			TextFieldUpdate[i].relocate(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * ((0.06 * i) + 0.11));
			TextFieldUpdate[i].setVisible(false);
			ContentPane.getChildren().add(TextFieldUpdate[i]);
		}
		TextFieldUpdate[0].setPromptText("Namn");
		TextFieldUpdate[1].setPromptText("Adress");
		
		BoxTypeUpdate = new CheckBox[3];
		
		for (int i = 0; i < 3; i++) {
			BoxTypeUpdate[i] = new CheckBox();
			BoxTypeUpdate[i].setScaleX(1.4);
			BoxTypeUpdate[i].setScaleY(1.4);
			BoxTypeUpdate[i].setVisible(false);
			BoxTypeUpdate[i].relocate(Setting.CurrentWidth * ((0.06 * i) + 0.02), Setting.CurrentHeight * 0.24);
			ContentPane.getChildren().add(BoxTypeUpdate[i]);
		}
		
		BoxTypeUpdate[0].setText("Sapa");
		BoxTypeUpdate[0].setOnAction(e ->{
			BoxTypeUpdate[1].setSelected(false);
			BoxTypeUpdate[2].setSelected(false);
		});
		
		BoxTypeUpdate[1].setText("Stålprofil");
		BoxTypeUpdate[1].setOnAction(e ->{
			BoxTypeUpdate[0].setSelected(false);
			BoxTypeUpdate[2].setSelected(false);
		});
		
		BoxTypeUpdate[2].setText("Ståldörr");
		BoxTypeUpdate[2].setOnAction(e ->{
			BoxTypeUpdate[0].setSelected(false);
			BoxTypeUpdate[1].setSelected(false);
		});
		
		// UPDATE MODE
		// ------------------------------------------------------------
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	public void createMode() {
		for (int i = 0; i < 3; i++) {
			LabelCreate[i].setVisible(true);
			TextFieldCreate[i].setVisible(true);
			LabelView[i].setVisible(false);
			LabelViewHolder[i].setVisible(false);
			LabelUpdate[i].setVisible(false);
			BoxTypeUpdate[i].setVisible(false);
		}
		TextFieldUpdate[0].setVisible(false);
		TextFieldUpdate[1].setVisible(false);
		LabelView[3].setVisible(false);
		LabelViewHolder[3].setVisible(false);
		BtnUpdate.setVisible(false);
	}

	public void viewMode() {
		for (int i = 0; i < 3; i++) {
			LabelCreate[i].setVisible(false);
			TextFieldCreate[i].setVisible(false);
			LabelView[i].setVisible(true);
			LabelViewHolder[i].setVisible(true);
			LabelUpdate[i].setVisible(false);
			BoxTypeUpdate[i].setVisible(false);
		}
		TextFieldUpdate[0].setVisible(false);
		TextFieldUpdate[1].setVisible(false);
		BtnUpdate.setVisible(true);
		BtnUpdate.setText("Ändra");
		LabelView[3].setVisible(true);
		LabelViewHolder[3].setVisible(true);
	}

	public void updateMode() {
		for (int i = 0; i < 3; i++) {
			LabelCreate[i].setVisible(false);
			TextFieldCreate[i].setVisible(false);
			LabelView[i].setVisible(false);
			LabelUpdate[i].setVisible(true);
			BoxTypeUpdate[i].setVisible(true);
		}
		LabelViewHolder[1].setVisible(false);
		LabelViewHolder[2].setVisible(false);
		TextFieldUpdate[0].setVisible(true);
		TextFieldUpdate[1].setVisible(true);
		LabelView[3].setVisible(false);
		LabelViewHolder[3].setVisible(false);
		BtnUpdate.setVisible(true);
		BtnUpdate.setText("Spara");
	}
	
	public boolean checkInsertData(){
		
		//NUMBER
		if(TextFieldCreate[0].getText().isEmpty()){
			SM.messageBox("Ange nummer!");
			return false;
		} 	
		if(TextFieldCreate[0].getText().matches("^[0-9]*$")){
			//OK INPUT
		}
		else{
			SM.messageBox("Nummer får bara bestå av siffror!");
			return false;
		}
		
		//NAME
		if(TextFieldCreate[1].getText().isEmpty()){
			SM.messageBox("Ange namn!");
			return false;
		}
		
		//ADDRESS
		if(TextFieldCreate[2].getText().isEmpty()){
			SM.messageBox("Ange adress!");
			return false;
		}
		
		return true;
	}
	
	public void setInsertVariables(){
		number = TextFieldCreate[0].getText();
		name = TextFieldCreate[1].getText();
		address = TextFieldCreate[2].getText();
	}
	
	public void clearInsertFields(){
		TextFieldCreate[0].clear();	
		TextFieldCreate[1].clear();	
		TextFieldCreate[2].clear();
	}
	
	public void setViewData(String Number, String Name, String Address, String Type){
		viewMode();
		LabelViewHolder[0].setText(Number);
		LabelViewHolder[1].setText(Name);
		LabelViewHolder[2].setText(Address);
		LabelViewHolder[3].setText(Type);
	}
	
	public void setUpdateFields(){
		TextFieldUpdate[0].setText(LabelViewHolder[1].getText());
		TextFieldUpdate[1].setText(LabelViewHolder[2].getText());			
		String Type = LabelViewHolder[3].getText();
				
		if(Type.equalsIgnoreCase("Sapa")){
			BoxTypeUpdate[0].setSelected(true);
		}
		else{
			BoxTypeUpdate[0].setSelected(false);
		}
		if(Type.equalsIgnoreCase("Stålprofil")){
			BoxTypeUpdate[1].setSelected(true);
		}
		else{
			BoxTypeUpdate[1].setSelected(false);
		}
		if(Type.equalsIgnoreCase("Ståldörr")){
			BoxTypeUpdate[2].setSelected(true);
		}
		else{
			BoxTypeUpdate[2].setSelected(false);
		}
	}
	
	public String Type;
	
	public void setUpdateType(){
		if(BoxTypeUpdate[0].isSelected()){
			Type = "Sapa";
		}
		else if(BoxTypeUpdate[1].isSelected()){
			Type = "Stålprofil";
		}
		else if(BoxTypeUpdate[2].isSelected()){
			Type = "Ståldörr";
		}
	}
	
	
	
	
}
