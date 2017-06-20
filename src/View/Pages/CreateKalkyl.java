
package View.Pages;

import Model.SM;
import Model.FileFolder.FileFolderM;
import Model.KalkylMysql.GetKalkyl;
import Model.KalkylMysql.InsertKalkyl;
import View.OrderKalkylObjects.AreaSelect;
import View.OrderKalkylObjects.ClientInsertKalkyl;
import View.OrderKalkylObjects.CommentArea;
import View.OrderKalkylObjects.ImportantList;
import View.OrderKalkylObjects.LabelHeader;
import View.OrderKalkylObjects.MainInfo;
import View.OrderKalkylObjects.SaveCloseBtn;
import View.OrderKalkylObjects.TypeSelect;
import View.Windows.NormalWindow;

public class CreateKalkyl {

	public NormalWindow Window;
	
	public MainInfo MainInfo;
	public TypeSelect TypeSelect;
	public AreaSelect AreaSelect;
	public ClientInsertKalkyl ClientInsertKalkyl;
	public SaveCloseBtn SaveCloseBtn;
	ImportantList ImportantList;
	CommentArea CommentArea;
	
	//MYSQL 
	InsertKalkyl InsertKalkyl;
	GetKalkyl GetKalkyl;
	
	public CreateKalkyl() {
		
		Window = new NormalWindow("Skapa Kalkyl");
		
		LabelHeader LabelHeader = new LabelHeader();
		LabelHeader.createKalkyl();
		LabelHeader.addToPane(Window.MainPane);
		
		MainInfo = new MainInfo();
		MainInfo.addToPane(Window.MainPane);
		MainInfo.createMode();
		
		ClientInsertKalkyl = new ClientInsertKalkyl();
		ClientInsertKalkyl.addToPane(Window.MainPane);
		
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
		
		//MYSQL OBJECTS
		InsertKalkyl = new InsertKalkyl();
		GetKalkyl = new GetKalkyl();
	}
	
	public void checkInsertDataAll(){
		//CHECK ALL INSERT DATA GATHER METHOD
		if(MainInfo.checkInsertData() == false){
			return;
		}
		MainInfo.setInsertVariables();
		if(GetKalkyl.checkKalkylNumber(MainInfo.number) == false){
			return;
		}
		if(TypeSelect.checkInsertData() == false){
			return;
		}
		TypeSelect.setInsertVariables();
			
		if(AreaSelect.checkInsertData() == false){
			return;
		}
		AreaSelect.setInsertVariables();
		
		if(ClientInsertKalkyl.checkInsertData() == false){
			return;
		}
		
		//INSERT
		if(InsertKalkyl.insertMainInfo(MainInfo.number, MainInfo.name, MainInfo.address, TypeSelect.Type) == false){
			return;
		}
		
		int numberOfClients = Integer.parseInt(ClientInsertKalkyl.chooseClientsBox.getValue().toString());
		for (int i = 0; i < numberOfClients; i++) {
			if(InsertKalkyl.insertClients(SM.getSelectedCustomerId(ClientInsertKalkyl.CustomerBox[i]), SM.getSelectedPersonId(ClientInsertKalkyl.ContactPersonBox[i]), MainInfo.number, ClientInsertKalkyl.ClientDatePicker[i].getValue().toString()) == false){
				return;
			}
		}
		
		if(InsertKalkyl.insertAreas(MainInfo.number, AreaSelect.areaValue[0], AreaSelect.areaValue[1], AreaSelect.areaValue[2], AreaSelect.areaValue[3], AreaSelect.areaValue[4], AreaSelect.areaValue[5]) == false){
			return;
		}
		
		ImportantList.setInsertVariables();
		if(InsertKalkyl.insertImportant(MainInfo.number, ImportantList.impValue[0], ImportantList.impValue[1], ImportantList.impValue[2], ImportantList.impValue[3], ImportantList.impValue[4]) == false){
			return;
		}
		
		CommentArea.setInsertVariables();
		if(InsertKalkyl.insertComment(MainInfo.number, CommentArea.Comment) == false){
			return;
		}
		
		FileFolderM.createFolder("Kalkyl", MainInfo.number);
		
		SM.messageBox("Kalkyl inlagd! Nummer: " + MainInfo.number);
		clearInsertAll();
		Window.window.hide();
	}

	private void clearInsertAll(){
		MainInfo.clearInsertFields();
		ClientInsertKalkyl.clearInsertFields();
		TypeSelect.clearInsertFields();
		AreaSelect.clearInsertFields();
		ImportantList.clearInsertFields();
		CommentArea.clearInsertFields();
	}
	
}
