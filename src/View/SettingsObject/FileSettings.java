package View.SettingsObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class FileSettings {

	// VARIABLES FOR DATABASE
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	Pane ContentPane;
	
	public Button BtnSaveFileUpdate;

	// TEXTFIELD FOR FILE SPOT
	TextField[] TextFieldFileSpot;
	// TEXTFIELD FOR DESC
	TextField[] TextFieldFileSpotName;
	
	//FilePath String
	String[] FullFilePath = new String[6];

	public FileSettings() {

		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.41), (Setting.CurrentHeight * 0.41));
		ContentPane.relocate((Setting.CurrentWidth * 0), (Setting.CurrentHeight * 0));

		// LABEL FÖR PLATS
		Label[] LabelFileSpot = new Label[6];
		for (int i = 0; i < 6; i++) {
			LabelFileSpot[i] = new Label("Filplats " + (i + 1));
			LabelFileSpot[i].setStyle("-fx-font-size: 16pt");
			LabelFileSpot[i].relocate(Setting.CurrentWidth * 0.02, Setting.CurrentHeight * (0.05 * i + 0.05));
			ContentPane.getChildren().add(LabelFileSpot[i]);
		}
		
		// TEXTFIELD FÖR AND DRA IN FIL
		TextFieldFileSpot = new TextField[6];
		for (int i = 0; i < 6; i++) {
			TextFieldFileSpot[i] = new TextField();
			TextFieldFileSpot[i].setStyle("-fx-font-size: 14pt");
			TextFieldFileSpot[i].relocate(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * (0.05 * i + 0.05));
			TextFieldFileSpot[i].setPromptText("Dra fil hit!");
			TextFieldFileSpot[i].setPrefWidth(Setting.CurrentWidth * 0.15);
			
			TextFieldFileSpot[i].setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					if (db.hasFiles()) {
						event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
					} else {
						event.consume();
					}
				}
			});
	
			final int d = i;

			TextFieldFileSpot[i].setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					boolean success = false;
					if (db.hasFiles()) {
						success = true;
						String filePath = null;
						for (File file : db.getFiles()) {

							filePath = file.getAbsolutePath();

							try {
								String lastSegment;
								Path path = Paths.get(filePath);
								FullFilePath[d] = filePath;
								String fileC = lastSegment = path.getName(path.getNameCount() - 1).toString();
								TextField curr = (TextField) event.getSource();

								curr.setText(fileC);
								String source = filePath;
								String destination = "C:/Core";
								File f1 = new File(source);
								File f2 = new File(destination);
							} catch (Exception e) {
								SM.messageBox("Denna fil funkar tytvärr inte");
							}
						}
					}
					event.setDropCompleted(success);
					event.consume();
				}
			});

			ContentPane.getChildren().add(TextFieldFileSpot[i]);
		}

		// TEXTFIELD FÖR ATT ANGE NAMN
		TextFieldFileSpotName = new TextField[6];
		for (int i = 0; i < 6; i++) {
			TextFieldFileSpotName[i] = new TextField();
			TextFieldFileSpotName[i].setStyle("-fx-font-size: 14pt");
			TextFieldFileSpotName[i].relocate(Setting.CurrentWidth * 0.24, Setting.CurrentHeight * (0.05 * i + 0.05));
			TextFieldFileSpotName[i].setPromptText("Namn");
			TextFieldFileSpotName[i].setPrefWidth(Setting.CurrentWidth * 0.15);
			ContentPane.getChildren().add(TextFieldFileSpotName[i]);
		}

		BtnSaveFileUpdate = new Button("Spara");
		BtnSaveFileUpdate.relocate(Setting.CurrentWidth * 0.31, Setting.CurrentHeight * 0.35);
		BtnSaveFileUpdate.setPrefSize(Setting.CurrentWidth * 0.08, Setting.CurrentHeight * 0.04);
		BtnSaveFileUpdate.setStyle("-fx-font-size: 12pt;");

		ContentPane.getChildren().add(BtnSaveFileUpdate);
		
		setMysqlFileSave();
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	public void setMysqlFileSave() {
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM FileSave";
			rs = st.executeQuery(s);

			int i = 0;

			while (rs.next()) {
				TextFieldFileSpot[i].setText(rs.getString("FileName"));
				TextFieldFileSpotName[i].setText(rs.getString("FileDesc"));
				i++;
			}
		} catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! FileSave");
		}
	}

	public void updateMysqlFileSave(){
		for(int i = 0; i < 6; i++){
			try{
				Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
				Statement myStmt = myConn.createStatement();
				String sql = "Update FileSave Set FileName = '"+TextFieldFileSpot[i].getText()+"', FileDesc = '"+TextFieldFileSpotName[i].getText()+"' where FileId = "+(i + 1)+"";
				myStmt.executeUpdate(sql);
				myStmt.close();
			}
			catch(Exception e){
				SM.messageBox("Fel vid uppdatering av 'FileSave'!");
			}	
		}
		for(int i = 0; i < 6; i++){
			System.out.println(FullFilePath[i]);
		}
		SM.messageBox("Uppdatering klar!");
	}
	
	public void checkMoveFile(){
		for(int i = 0; i <6; i++){
			if(FullFilePath[i] == null){
				//Nothing
			}
			else{
				//Move file
				String filePath = FullFilePath[i];
				Path path = Paths.get(filePath);
				String fileC = path.getName(path.getNameCount() - 1).toString();

				String source = filePath;
				String destination = Setting.FileLocation + "Mallar/" + fileC;

				File f1 = new File(source);
				File f2 = new File(destination);

				try {
					Files.copy(f1.toPath(), f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					SM.messageBox("Det gick inte att flytta fil");
					return;
				}
			}	
		}
	}
	
}
