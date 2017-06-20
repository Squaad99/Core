package View.OrderKalkylObjects;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import Model.SM;
import Model.Setting;
import Model.FileFolder.FileFolderM;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class FileList {

	Pane ContentPane;
	Button BtnUpdate;
	public ListView<String> FileList;

	private String Folder;
	private String Number;

	public FileList() {

		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.33), (Setting.CurrentHeight * 0.28));
		ContentPane.relocate((Setting.CurrentWidth * 0.57), (Setting.CurrentHeight * 0.6));

		Label Header = new Label("Filer");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.01));
		ContentPane.getChildren().add(Header);

		BtnUpdate = new Button("Uppdatera");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.06), (Setting.CurrentHeight * 0.03));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.04), (Setting.CurrentHeight * 0.01));
		BtnUpdate.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(BtnUpdate);
		BtnUpdate.setOnAction(e->{
			FileFolderM.setFolderList(Folder, Number, FileList);
		});
		
		
		Button BtnDelete = new Button("Ta bort markerad");
		BtnDelete.setPrefSize((Setting.CurrentWidth * 0.08), (Setting.CurrentHeight * 0.03));
		BtnDelete.relocate((Setting.CurrentWidth * 0.12), (Setting.CurrentHeight * 0.01));
		BtnDelete.setStyle("-fx-font-size: 12pt");
		ContentPane.getChildren().add(BtnDelete);
		BtnDelete.setOnAction(e->{
			String selected = (String) FileList.getSelectionModel().getSelectedItem();
			String source = Setting.FileLocation + Folder + "/" + Number + "/" + selected;
			File f1 = new File(source);
			f1.delete();
			FileFolderM.setFolderList(Folder, Number, FileList);
		});

		FileList = new ListView<>();
		FileList.setPrefSize(Setting.CurrentWidth * 0.315, Setting.CurrentHeight * 0.21);
		FileList.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.05);
		FileList.setStyle("-fx-font-size: 10pt");
		ContentPane.getChildren().add(FileList);

		FileList.setOnDragOver(new EventHandler<DragEvent>() {
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

		// FileList Drag Handler
		FileList.setOnDragDropped(new EventHandler<DragEvent>() {
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
							String fileC = lastSegment = path.getName(path.getNameCount() - 1).toString();

							String source = filePath;
							String destination = Setting.FileLocation + Folder + "/" + Number + "/" + fileC;

							File f1 = new File(source);
							File f2 = new File(destination);

							Files.copy(f1.toPath(), f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
							f1.delete();

							FileFolderM.setFolderList(Folder, Number, FileList);

						} catch (Exception e) {
							SM.messageBox("Går inte att flytta fil");
						}
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		FileList.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				
				if(FileList.getSelectionModel().isEmpty()){
					//nothing
				}
				else{
					String selected = FileList.getSelectionModel().getSelectedItem().toString();
					Desktop desktop = Desktop.getDesktop();
					String targetFilePath = Setting.FileLocation + Folder + "/" + Number + "/" + selected;
					try {
						desktop.open(new File(targetFilePath));
					} catch (IOException ed) {
						ed.printStackTrace();
					}
				}
				
			}
		});
	}

	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

	public void transferMode() {
		BtnUpdate.setVisible(false);
		ContentPane.relocate((Setting.CurrentWidth * 0.57), (Setting.CurrentHeight * 0.4));
		Folder = "Kalkyl";
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
