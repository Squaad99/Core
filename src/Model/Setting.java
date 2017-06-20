package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;

public class Setting {
	
	//USER
	public static String User;
	
	//MYSQL CONNECTION INFO
	public static String MysqlConnection;
	public static String MysqlUser;
	public static String MysqlPW;
	
	public static String FileLocation;
	
	public static int CurrentWidth;
	public static int CurrentHeight;
	
	public static String[] areaType = new String[6];
	public static String[] impVar = new String[5];
	public static String[] orderVar = new String[5];
	
	public static int WorkersInside = 8;
	public static int WorkersOutside = 2;

	public static int insideHoursPerWeek = 40;
	public static int outsideHoursPerWeek = 40;
	

	public Setting() {
		//SET SCREEN VARIABLES
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		CurrentWidth = (int) primaryScreenBounds.getWidth();
		CurrentHeight = (int) primaryScreenBounds.getHeight() - 30;
		
		//SET AREA VARIABLES
		areaType[0] = "Dörr";
		areaType[1] = "Fönster";
		areaType[2] = "Fasad";
		areaType[3] = "BRF";
		areaType[4] = "Entré";
		areaType[5] = "Robust";
		
		//SET IMP VARIABLES
		impVar[0] = "Brandkrav";
		impVar[1] = "Kran";
		impVar[2] = "Ställning";
		impVar[3] = "Ytbehandling";
		impVar[4] = "Utomhus";
		
		orderVar[0] = "Profiler";
		orderVar[1] = "Lås";
		orderVar[2] = "Handtag";
		orderVar[3] = "Glas";
		orderVar[4] = "Packningar";
		
		try {
			Scanner TextScanner = new Scanner(new File("Info.txt"));
			
			while(TextScanner.hasNext()){
				MysqlConnection = TextScanner.next();
				MysqlUser = TextScanner.next();
				MysqlPW = TextScanner.next();
				FileLocation = TextScanner.next();
			}
			
		} catch (FileNotFoundException e) {
			
			Alert alertBox = new Alert(AlertType.INFORMATION);
			alertBox.setTitle("Mysql Connect");
			alertBox.setHeaderText(null);
			alertBox.setContentText("Fel " + e);
			alertBox.showAndWait();
			Platform.exit();
		}
		
		User = MysqlUser;	
	}

}
