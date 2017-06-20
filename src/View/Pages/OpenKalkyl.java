package View.Pages;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import Model.SM;
import Model.FileFolder.FileFolderM;
import Model.KalkylMysql.DeleteKalkyl;
import Model.KalkylMysql.GetKalkyl;
import Model.KalkylMysql.InsertKalkyl;
import Model.KalkylMysql.UpdateKalkyl;
import View.OrderKalkylObjects.AreaDisplay;
import View.OrderKalkylObjects.ClientDisplay;
import View.OrderKalkylObjects.CommentArea;
import View.OrderKalkylObjects.FileCreateButtons;
import View.OrderKalkylObjects.FileList;
import View.OrderKalkylObjects.ImportantList;
import View.OrderKalkylObjects.LabelHeader;
import View.OrderKalkylObjects.MainInfo;
import View.OrderKalkylObjects.UpperCornerButtons;
import View.Windows.NormalWindow;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

public class OpenKalkyl {

	LabelHeader LabelHeader;
	MainInfo MainInfo;
	AreaDisplay AreaDisplay;
	public ClientDisplay ClientDisplay;
	ImportantList ImportantList;
	CommentArea CommentArea;
	public FileCreateButtons FileCreateButtons;
	FileList FileList;
	
	//Public objecs
	public NormalWindow Window;
	public GetKalkyl GetKalkyl;
	public UpperCornerButtons UpperCornerButtons;
	public DeleteKalkyl DeleteKalkyl;
	public UpdateKalkyl UpdateKalkyl;
	
	
	public OpenKalkyl() {
		
		Window = new NormalWindow("Öppna Kalkyl");
		
		LabelHeader = new LabelHeader();
		LabelHeader.openKalkyl();
		LabelHeader.addToPane(Window.MainPane);
		
		MainInfo = new MainInfo();
		MainInfo.addToPane(Window.MainPane);
		MainInfo.viewMode();
		
		AreaDisplay = new AreaDisplay();
		AreaDisplay.addToPane(Window.MainPane);
		AreaDisplay.viewMode();
		
		ClientDisplay = new ClientDisplay();
		ClientDisplay.addToPane(Window.MainPane);
		ClientDisplay.viewMode();
		
		ImportantList = new ImportantList();
		ImportantList.addToPane(Window.MainPane);
		ImportantList.openView();
		
		CommentArea = new CommentArea();
		CommentArea.addToPane(Window.MainPane);
		CommentArea.viewMode();
		
		FileList = new FileList();
		FileList.addToPane(Window.MainPane);
		FileList.setKalkylFolder();
		
		FileCreateButtons = new FileCreateButtons(FileList.FileList);
		FileCreateButtons.setKalkylFolder();
		FileCreateButtons.addToPane(Window.MainPane);
		
		UpperCornerButtons = new UpperCornerButtons();
		UpperCornerButtons.setOpenKalkylMode();
		UpperCornerButtons.addToPane(Window.MainPane);
		
		//MYSQL
		GetKalkyl = new GetKalkyl();
		DeleteKalkyl = new DeleteKalkyl();
		UpdateKalkyl = new UpdateKalkyl();
		
		//UPDATE MAIN INFO BUTTON
		MainInfo.BtnUpdate.setOnAction(e->{
			if(MainInfo.BtnUpdate.getText().equalsIgnoreCase("Ändra")){
				setMainInfoUpdateView();
			}
			else if(MainInfo.BtnUpdate.getText().equalsIgnoreCase("Spara")){
				updateMainInfoAndSetNormalView();
			}
		});
		
		//UPDATE AREA BUTTON
		AreaDisplay.BtnUpdate.setOnAction(e->{
			if(AreaDisplay.BtnUpdate.getText().equalsIgnoreCase("Ändra")){
				setAreaUpdateSate();
			}
			else if(AreaDisplay.BtnUpdate.getText().equalsIgnoreCase("Spara")){
				setAreaNormalSate();
			}
		});
		
		//INSERT EXTRA CLIENT BUTTON
		ClientDisplay.BtnUpdate.setOnAction(e->{
			if(ClientDisplay.BtnUpdate.getText().equalsIgnoreCase("Lägg till beställare")){
				setClientInsertState();
			}
			else if(ClientDisplay.BtnUpdate.getText().equalsIgnoreCase("Spara beställare")){
				setClientNormalState();
			}
		});
		
		//UPDATE IMPORTANT LIST BUTTON
		ImportantList.BtnUpdate.setOnAction(e->{
			updateImportantList();
		});
		
		
		//UPDATE COMMENT BUTTON
		CommentArea.BtnUpdate.setOnAction(e->{
			updateComment();
		});
		
		
		
		
	}
	
	public void setSelectedKalkylDataAll(int Number){
		
		if(GetKalkyl.setAllData(Number) == false){
			SM.messageBox("fel här");
			return;
		}
		
		MainInfo.setViewData(GetKalkyl.KalkylNr, GetKalkyl.KalkylName, GetKalkyl.KalkylAddress, GetKalkyl.KalkylType);
		LabelHeader.setViewKalkyl(GetKalkyl.KalkylCreator, GetKalkyl.KalkylDateCreated);
		AreaDisplay.setViewData(GetKalkyl.Area, AreaDisplay.list1);
		ClientDisplay.setViewData(GetKalkyl.ClientList);
		ImportantList.setViewData(GetKalkyl.Imp);
		CommentArea.setViewData(GetKalkyl.Comment);
		FileList.setNumber(GetKalkyl.KalkylNr);
		FileCreateButtons.setNumber(GetKalkyl.KalkylNr);
		
		FileFolderM.setFolderList("Kalkyl", GetKalkyl.KalkylNr, FileList.FileList);
		
		Window.use();
	}

	//UPDATE MAININFO
	
	void setMainInfoUpdateView(){
		MainInfo.setUpdateFields();
		MainInfo.updateMode();
	}
	
	void updateMainInfoAndSetNormalView(){
		MainInfo.setUpdateType();
		MainInfo.viewMode();
		UpdateKalkyl.updateMainInfo(GetKalkyl.KalkylNr, MainInfo.TextFieldUpdate[0].getText(), MainInfo.TextFieldUpdate[1].getText(), MainInfo.Type);
		GetKalkyl.setMainInfo(Integer.parseInt(GetKalkyl.KalkylNr));
		MainInfo.setViewData(GetKalkyl.KalkylNr, GetKalkyl.KalkylName, GetKalkyl.KalkylAddress, GetKalkyl.KalkylType);
	}
	
	//UPDATE AREA
	
	void setAreaUpdateSate(){
		AreaDisplay.updateMode();
		AreaDisplay.setUpdateBoxes(GetKalkyl.Area);
	}
	
	void setAreaNormalSate(){
		if(AreaDisplay.setUpdateVariables() == false){
			return;
		}
		UpdateKalkyl.updateAreas(GetKalkyl.KalkylNr, AreaDisplay.updateArea);
		GetKalkyl.setAreaData(Integer.parseInt(GetKalkyl.KalkylNr));
		AreaDisplay.setViewData(GetKalkyl.Area, AreaDisplay.list1);
		AreaDisplay.viewMode();
	}
	
	//INSERT EXTRA CLIENT
	
	void setClientInsertState(){
		ClientDisplay.updateMode();
	}
	
	void setClientNormalState(){
		if(ClientDisplay.setUpdateVariables() == false){
			return;
		}
		InsertKalkyl.insertClients(ClientDisplay.CustomerId, ClientDisplay.ContactId, GetKalkyl.KalkylNr, ClientDisplay.Date);
		GetKalkyl.setClientData(Integer.parseInt(GetKalkyl.KalkylNr));
		ClientDisplay.setViewData(GetKalkyl.ClientList);
		ClientDisplay.viewMode();
	}
	
	//Update ImportantList
	
	void updateImportantList(){
		ImportantList.setInsertVariables();
		UpdateKalkyl.updateImportantList(GetKalkyl.KalkylNr, ImportantList.impValue);
	}
	
	//Update Comment
	
	void updateComment(){
		CommentArea.setInsertVariables();
		UpdateKalkyl.updateComment(GetKalkyl.KalkylNr,CommentArea.Comment);
	}
}
