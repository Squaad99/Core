package View.Pages;


import Model.SM;
import Model.FileFolder.FileFolderM;
import Model.OrderMysql.GetOrder;
import Model.OrderMysql.InsertOrder;
import View.OrderKalkylObjects.AreaSelect;
import View.OrderKalkylObjects.CommentArea;
import View.OrderKalkylObjects.ImportantList;
import View.OrderKalkylObjects.InsertCustomer;
import View.OrderKalkylObjects.LabelHeader;
import View.OrderKalkylObjects.MainInfo;
import View.OrderKalkylObjects.ProductionMontage;
import View.OrderKalkylObjects.SaveCloseBtn;
import View.OrderKalkylObjects.TypeSelect;
//Interal imports
import View.Windows.NormalWindow;

public class CreateOrder {
	//Create order will be used for creating orders. Insert data to database.
	
	public NormalWindow Window;
	
	public SaveCloseBtn SaveCloseBtn;
	
	MainInfo MainInfo;
	public InsertCustomer InsertCustomer;
	ProductionMontage ProductionMontage;
	TypeSelect TypeSelect;
	AreaSelect AreaSelect;
	ImportantList ImportantList;
	CommentArea CommentArea;
	
	InsertOrder InsertOrder;
	GetOrder GetOrder;
	
	public CreateOrder() {
		
		Window = new NormalWindow("Skapa Order");
		
		LabelHeader LabelHeader = new LabelHeader();
		LabelHeader.createOrder();
		LabelHeader.addToPane(Window.MainPane);
		
		MainInfo = new MainInfo();
		MainInfo.addToPane(Window.MainPane);
		MainInfo.createMode();
		
		InsertCustomer = new InsertCustomer();
		InsertCustomer.addToPane(Window.MainPane);
		
		ProductionMontage = new ProductionMontage();
		ProductionMontage.addToPane(Window.MainPane);
		ProductionMontage.insertMode();

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
		
		SaveCloseBtn = new SaveCloseBtn();
		SaveCloseBtn.saveMode();
		SaveCloseBtn.addToPane(Window.MainPane);
		
		//MYSQL
		InsertOrder = new InsertOrder();
		GetOrder = new GetOrder();
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
		
		if(InsertOrder.insertMainInfo(MainInfo.number, MainInfo.name, MainInfo.address, TypeSelect.Type, InsertCustomer.CustomerId, InsertCustomer.ContactPersonId, "false") == false){
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
		
		FileFolderM.createFolder("Aktiv", MainInfo.number);
		
		SM.messageBox("Order inlagd! Nummer: " + MainInfo.number);
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
