package View.OrderKalkylObjects;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import Model.FileFolder.FileFolderM;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class FileCreateButtons {

	Pane ContentPane;

	// VARIABLES FOR DATABASE
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	// TEXTFIELD FOR FILE SPOT
	String[] FileSpot = new String [6];
	// TEXTFIELD FOR DESC
	String[] FileSpotName = new String [6];
	
	Button[] CreateFileBtn;
	
	private ListView FileList;

	public FileCreateButtons(ListView list) {
		
		this.FileList = list;

		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.33), (Setting.CurrentHeight * 0.2));
		ContentPane.relocate((Setting.CurrentWidth * 0.57), (Setting.CurrentHeight * 0.39));

		Label Header = new Label("Skapa Filer");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.01));
		ContentPane.getChildren().add(Header);

		CreateFileBtn = new Button[6];
		int extraCounter = 0;

		for (int i = 0; i < 6; i++) {
			CreateFileBtn[i] = new Button("Skapa fil " + (i + 1));
			CreateFileBtn[i].setPrefSize(Setting.CurrentWidth * 0.1, Setting.CurrentHeight * 0.05);
			CreateFileBtn[i].setStyle("-fx-font-size: 16pt");
			if (i < 3) {
				CreateFileBtn[i].relocate(Setting.CurrentWidth * ((0.105 * i) + 0.01), Setting.CurrentHeight * 0.06);
			} else {
				CreateFileBtn[i].relocate(Setting.CurrentWidth * ((0.105 * extraCounter) + 0.01),
						Setting.CurrentHeight * 0.13);
				extraCounter++;
			}
			ContentPane.getChildren().add(CreateFileBtn[i]);
		}

		CreateFileBtn[0].setOnAction(e -> {
			createFile(0);
		});

		CreateFileBtn[1].setOnAction(e -> {
			createFile(1);
		});

		CreateFileBtn[2].setOnAction(e -> {
			createFile(2);
		});

		CreateFileBtn[3].setOnAction(e -> {
			createFile(3);
		});

		CreateFileBtn[4].setOnAction(e -> {
			createFile(4);
		});

		CreateFileBtn[5].setOnAction(e -> {
			createFile(5);
		});
		
		setFilePathAndName();
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	public void setFilePathAndName() {
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM FileSave";
			rs = st.executeQuery(s);

			int i = 0;

			while (rs.next()) {
				FileSpot[i] = (rs.getString("FileName"));
				FileSpotName[i] = (rs.getString("FileDesc"));
				
				if(FileSpotName[i].isEmpty()){
					//nothing
				}
				else{
					CreateFileBtn[i].setText(FileSpotName[i]);	
				}
				i++;
			}
		} catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! FileSave");
		}
	}
	
	String Folder;
	String Number;
	

	void createFile(int i){
		try {
			if(FileSpot[i].length() == 0){
				SM.messageBox("Ingen fil på denna plats");
			}
			else{
				String source = Setting.FileLocation + "Mallar/" + FileSpot[i];
				String destination = Setting.FileLocation + Folder + "/" + Number + "/" + Number + FileSpot[i];
				File f1 = new File(source);
				File f2 = new File(destination);
				if(f2.exists()){
					SM.messageBox("Denna fil finns redan!");
				}
				else{
					Files.copy(f1.toPath(), f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
					FileFolderM.setFolderList(Folder, Number, FileList);
				}
			}
		} catch (Exception e) {
			SM.messageBox("Det gick inte att skapa dokumentet!");
		}
	}
	
	public void setNumber(String NumberInput) {
		Number = NumberInput;
	}
	
	public void setKalkylFolder() {
		Folder = "Kalkyl";
	}

	public void setActiveOrderFolder() {
		Folder = "Aktiv";
	}

	public void setFinishedOrderFolder() {
		Folder = "Avslutade";
	}
	
}
