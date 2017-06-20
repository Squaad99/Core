package Model.FileFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import Model.SM;
import Model.Setting;
import javafx.scene.control.ListView;

public class FileFolderM {

	public FileFolderM() {
		
	}

	public static void createFolder(String Location, String NameNumber){
			File dir = new File(Setting.FileLocation + Location +"/" + NameNumber);
			if(!dir.exists()){
				dir.mkdir();
			}
			else{
				SM.messageBox("Det finns redan en mapp med detta nr. Den gamla mappen kommer att användas och en ny har ej skapas.");
			}
	}
	
	public static void setFolderList(String Location, String NameNumber, ListView<String> List){
		List.getItems().clear();
		try {
			File o = new File(Setting.FileLocation + Location + "/" + NameNumber);

			File[] yourFileList = o.listFiles();
			for (File f : yourFileList) {
				List.getItems().addAll(f.getName());
			}
		} catch (Exception e) {
			SM.messageBox("Mappen till denna kalkyl gick inte att hitta!");
		}
	}
	
	public static void deleteFolder(){
		
	}
	
	public static void FolderKalkylToOrder(String OldNumber, String NewNumber){
		try {
			Files.move(new File(Setting.FileLocation + "Kalkyl/" + OldNumber).toPath(), new File("C:/Core/Aktiv/" + OldNumber).toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			SM.messageBox("Det gick inte att flytta mappen för detta projekt!");
		}
		File dir = new File(Setting.FileLocation + "Aktiv/" + OldNumber);
		File dirNew = new File(Setting.FileLocation + "Aktiv/" + NewNumber);
		dir.renameTo(dirNew);
	}

	public static void FolderActiveToInActive(String Number){
		try {
			Files.move(new File(Setting.FileLocation + "Aktiv/" + Number).toPath(), new File("C:/Core/Avslutade/" + Number).toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			SM.messageBox("Det gick inte att flytta mappen för detta projekt!");
		}
	}
	
	public static void FolderInActiveToActive(String Number){
		try {
			Files.move(new File(Setting.FileLocation +"Avslutade/" + Number).toPath(), new File("C:/Core/Aktiv/" + Number).toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			SM.messageBox("Det gick inte att flytta mappen för detta projekt!");
		}
	}

	public static void deleteFolder(String a, String b){
		File dir = new File(Setting.FileLocation + a +"/" + b);
		try {
			delete(dir);
		} catch (IOException e) {
			System.out.println("fail");
		}	
	}
	
	static void delete(File f) throws IOException {
		if (f.isDirectory()) {
		   for (File c : f.listFiles())
		   delete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}
}
