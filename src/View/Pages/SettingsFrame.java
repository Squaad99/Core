package View.Pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.Setting;
import View.SettingsObject.FileSettings;
import View.Windows.SmallWindow;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class SettingsFrame {

	public SmallWindow SmallWindow;
	public FileSettings FileSettings;
	
	public SettingsFrame() {

		SmallWindow = new SmallWindow("Inställningar");

		// TAB PANE
		TabPane TabPane = new TabPane();
		TabPane.setPrefSize(Setting.CurrentWidth * 0.41, Setting.CurrentHeight * 0.41);
		TabPane.relocate(Setting.CurrentWidth * 0, Setting.CurrentHeight * 0);
		TabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		SmallWindow.MainPane.getChildren().add(TabPane);

		TabPane.setTabMinHeight(Setting.CurrentHeight * 0.025);
		TabPane.setTabMaxHeight(Setting.CurrentHeight * 0.025);
		TabPane.setTabMinWidth(Setting.CurrentWidth * 0.05);
		TabPane.setTabMaxWidth(Setting.CurrentWidth * 0.05);

		Tab[] Tab = new Tab[1];
		for (int i = 0; i < 1; i++) {
			Tab[i] = new Tab();
			Tab[i].setStyle("-fx-font-size: 12pt;");
			TabPane.getTabs().add(Tab[i]);
		}

		Tab[0].setText("Inställningar");

		FileSettings = new FileSettings();
		FileSettings.addToPane(SmallWindow.MainPane);
	}

	

}
