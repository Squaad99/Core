package Start;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.FirstPage;
import View.Pages.CalendarPage;
import View.Pages.CreateKalkyl;
import View.Pages.CreateOrder;
import View.Pages.Customer;
import View.Pages.KalkylToOrder;
import View.Pages.OpenKalkyl;
import View.Pages.OpenOrder;
import View.Pages.OpenOrderDone;
import View.Pages.SettingsFrame;
import View.Pages.TimeAccounting;
import Control.Control;
import Model.Setting;
import Model.KalkylMysql.GetKalkyl;

public class StartUp extends Application {
	
	private Stage window;
	private Scene scene;
	
	//Version 1.1.0
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//SET SETTINGS
		@SuppressWarnings("unused")
		Setting SettingsInizilazied = new Setting();
		
		GetKalkyl Test = new GetKalkyl();
		
		if(Test.checkConnection() == false){
			Platform.exit();
			return;
		}
		
		//VIEW OBJECTS FRAMES
		FirstPage FirstPage = new FirstPage();
		
		//ORDER
		CreateOrder CreateOrder = new CreateOrder();
		OpenOrder OpenOrder = new OpenOrder();
		OpenOrderDone OpenOrderDone = new OpenOrderDone();
		
		//KALKYL
		CreateKalkyl CreateKalkyl = new CreateKalkyl();
		OpenKalkyl OpenKalkyl = new OpenKalkyl();
		
		//KALKYL TO ORDER
		KalkylToOrder KalkylToOrder = new KalkylToOrder();
		
		//OTHERS
		CalendarPage CalendarPage =  new CalendarPage();
		Customer Customer = new Customer();
		TimeAccounting TimeAccounting = new TimeAccounting();
		SettingsFrame SettingsFrame = new SettingsFrame();
		
		Control Control = new Control(FirstPage, CreateKalkyl, CreateOrder, Customer, OpenKalkyl, OpenOrder, OpenOrderDone, KalkylToOrder, CalendarPage, TimeAccounting, SettingsFrame);
		
		window = primaryStage;
		window.setTitle("Core Version 1.1.0");
		window.setOnCloseRequest(e -> Platform.exit());
		scene = new Scene(FirstPage.pane , Setting.CurrentWidth, Setting.CurrentWidth);
		scene.getStylesheets().add("/View/Windows/MainStyle.css");
		window.setMaximized(true);

		window.setScene(scene);
		window.show();
	}
	
	
	public static void main(String [] args){
		launch();
	}

}
