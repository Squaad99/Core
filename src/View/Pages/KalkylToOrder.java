package View.Pages;

import Model.SM;
import Model.Setting;
import Model.FileFolder.FileFolderM;
import Model.KalkylMysql.DeleteKalkyl;
import Model.OrderMysql.DeleteOrder;
import Model.OrderMysql.GetOrder;
import Model.OrderMysql.InsertOrder;
import View.OrderKalkylObjects.AreaSelect;
import View.OrderKalkylObjects.CommentArea;
import View.OrderKalkylObjects.FileList;
import View.OrderKalkylObjects.ImportantList;
import View.OrderKalkylObjects.InsertCustomer;
import View.OrderKalkylObjects.LabelHeader;
import View.OrderKalkylObjects.MainInfo;
import View.OrderKalkylObjects.ProductionMontage;
import View.OrderKalkylObjects.SaveCloseBtn;
import View.OrderKalkylObjects.TypeSelect;
import View.Windows.NormalWindow;
import javafx.scene.paint.Color;

public class KalkylToOrder {
	
	public NormalWindow Window;
	LabelHeader LabelHeader;
	MainInfo MainInfo;
	InsertCustomer InsertCustomer;
	ProductionMontage ProductionMontage;
	TypeSelect TypeSelect;
	AreaSelect AreaSelect;
	ImportantList ImportantList;
	CommentArea CommentArea;
	public SaveCloseBtn SaveCloseBtn;
	public GetOrder GetOrder;
	InsertOrder InsertOrder;
	DeleteKalkyl DeleteKalkyl;
	FileList FileList;

	public KalkylToOrder() {
		
		Window = new NormalWindow("Kalkyl till Order");
		
		LabelHeader = new LabelHeader();
		LabelHeader.kalkylToOrder();
		LabelHeader.addToPane(Window.MainPane);
		
		MainInfo = new MainInfo();
		MainInfo.addToPane(Window.MainPane);
		MainInfo.createMode();
		
		InsertCustomer = new InsertCustomer();
		InsertCustomer.addToPane(Window.MainPane);
		
		ProductionMontage = new ProductionMontage();
		ProductionMontage.insertMode();
		ProductionMontage.addToPane(Window.MainPane);
		
		TypeSelect = new TypeSelect();
		TypeSelect.addToPane(Window.MainPane);
		
		AreaSelect = new AreaSelect();
		AreaSelect.addToPane(Window.MainPane);
		
		ImportantList = new ImportantList();
		ImportantList.createView();
		ImportantList.addToPane(Window.MainPane);
		
		CommentArea = new CommentArea();
		CommentArea.addToPane(Window.MainPane);
		CommentArea.createMode();
		
		FileList = new FileList();
		FileList.addToPane(Window.MainPane);
		FileList.transferMode();
		
		SaveCloseBtn = new SaveCloseBtn();
		SaveCloseBtn.saveMode();
		SaveCloseBtn.addToPane(Window.MainPane);
		
		//Mysql
		GetOrder = new GetOrder();
		InsertOrder = new InsertOrder();
		DeleteKalkyl = new DeleteKalkyl();
	}
	
	String KalkylCreatedStore;
	
	String KalkylNrStored;
	
	public void setExistingKalkylData(String KalkylNr, String Name, String Address, String Type, String KalkylCreated, String[] Area, String[] Imp, String Comment){
		
		KalkylNrStored = KalkylNr;
		clearAll();
		
		MainInfo.TextFieldCreate[1].setText(Name);
		MainInfo.TextFieldCreate[2].setText(Address);
		KalkylCreatedStore = KalkylCreated;
		
		if(Type.equalsIgnoreCase("Sapa")){
			TypeSelect.TypeBox[0].setSelected(true);
		}
		else if(Type.equalsIgnoreCase("Stålprofil")){
			TypeSelect.TypeBox[1].setSelected(true);
		}
		else if(Type.equalsIgnoreCase("Ståldörr")){
			TypeSelect.TypeBox[2].setSelected(true);
		}
		
		for(int i = 0; i < 6; i++){
			if(Area[i].equalsIgnoreCase("true")){
				AreaSelect.AreaBox[i].setSelected(true);
			}
			else{
				AreaSelect.AreaBox[i].setSelected(false);
			}
		}
		
		for(int i = 0; i < 5; i++){
			if(Imp[i].equalsIgnoreCase("true")){
				ImportantList.ImpBox[i].setSelected(true);
				ImportantList.LabelImpBox[i].setText(Setting.impVar[i] + ": Ja");
				ImportantList.LabelImpBox[i].setTextFill(Color.GREEN);
			}
			else{
				ImportantList.ImpBox[i].setSelected(false);
				ImportantList.LabelImpBox[i].setText(Setting.impVar[i] + ": Nej");
				ImportantList.LabelImpBox[i].setTextFill(Color.RED);
			}
		}
		
		CommentArea.CommentTa.setText(Comment);
		
		FileList.setNumber(KalkylNrStored);
		FileFolderM.setFolderList("Kalkyl", KalkylNrStored, FileList.FileList);
	}
	
	public void checkInsertDataAll(){
		if(MainInfo.checkInsertData() == false){
			return;
		}
		MainInfo.setInsertVariables();
		if(GetOrder.checkOrderNumber(MainInfo.number)== false){
			return;
		}
		if(InsertCustomer.checkInsertData() == false){
			return;
		}
		InsertCustomer.setInsertVariables();
		
		if(ProductionMontage.checkInsertData() == false){
			return;
		}
		ProductionMontage.setInsertVariables();
	
		if(TypeSelect.checkInsertData() == false){
			return;
		}
		TypeSelect.setInsertVariables();
		
		if(AreaSelect.checkInsertData() == false){
			return;
		}
		AreaSelect.setInsertVariables();
	
		ImportantList.setInsertVariables();
		
		CommentArea.setInsertVariables();
		
		if(InsertOrder.insertMainInfo(MainInfo.number, MainInfo.name, MainInfo.address, TypeSelect.Type, InsertCustomer.CustomerId, InsertCustomer.ContactPersonId, KalkylCreatedStore) == false){
			return;
		}
		
		if(InsertOrder.insertOrderAreas(MainInfo.number, AreaSelect.areaValue[0], AreaSelect.areaValue[1], AreaSelect.areaValue[2], AreaSelect.areaValue[3], AreaSelect.areaValue[4], AreaSelect.areaValue[5]) == false){
			return;
		}
		
		if(InsertOrder.insertOrderProductionDate(MainInfo.number, ProductionMontage.StartP, ProductionMontage.StopP, ProductionMontage.HoursP, ProductionMontage.StartM, ProductionMontage.StopM, ProductionMontage.HoursM) == false){
			return;
		}
		
		if(InsertOrder.insertImportantList(MainInfo.number, ImportantList.impValue[0], ImportantList.impValue[1], ImportantList.impValue[2], ImportantList.impValue[3], ImportantList.impValue[4])== false){
			return;
		}
		
		if(InsertOrder.insertOrderList(MainInfo.number) == false){
			return;
		}
		
		if(InsertOrder.insertComment(MainInfo.number, CommentArea.Comment)== false){
			return;
		}
		
		FileFolderM.FolderKalkylToOrder(KalkylNrStored, MainInfo.number);		
		SM.messageBox("Order inlagd! Nummer: " + MainInfo.number);
		DeleteKalkyl.deleteAll(KalkylNrStored);
		clearAll();
		Window.window.hide();
	}
	
	void clearAll(){
		MainInfo.clearInsertFields();
		TypeSelect.clearInsertFields();
		AreaSelect.clearInsertFields();
		InsertCustomer.clearAllFields();
		ProductionMontage.clearAllFields();
		ImportantList.clearInsertFields();
		CommentArea.clearInsertFields();
	}

}
