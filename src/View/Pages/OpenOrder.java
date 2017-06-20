package View.Pages;

import Model.SM;
import Model.FileFolder.FileFolderM;
import Model.OrderMysql.DeleteOrder;
import Model.OrderMysql.GetOrder;
import Model.OrderMysql.UpdateOrder;
import View.OrderKalkylObjects.AreaDisplay;
import View.OrderKalkylObjects.CommentArea;
import View.OrderKalkylObjects.CustomerDisplay;
import View.OrderKalkylObjects.FileCreateButtons;
import View.OrderKalkylObjects.FileList;
import View.OrderKalkylObjects.ImportantList;
import View.OrderKalkylObjects.LabelHeader;
import View.OrderKalkylObjects.MainInfo;
import View.OrderKalkylObjects.OrderList;
import View.OrderKalkylObjects.ProductionMontage;
import View.OrderKalkylObjects.UpperCornerButtons;
import View.Windows.NormalWindow;

public class OpenOrder {

	public NormalWindow Window;
	MainInfo MainInfo;
	LabelHeader LabelHeader;
	public UpperCornerButtons UpperCornerButtons;
	CustomerDisplay CustomerDisplay;
	AreaDisplay AreaDisplay;
	ProductionMontage ProductionMontage;
	ImportantList ImportantList;
	OrderList OrderList;
	CommentArea CommentArea;
	public GetOrder GetOrder;
	public DeleteOrder DeleteOrder;
	FileList FileList;
	public FileCreateButtons FileCreateButtons;

	public OpenOrder() {

		Window = new NormalWindow("Öppna Order");

		LabelHeader = new LabelHeader();
		LabelHeader.openOrder();
		LabelHeader.addToPane(Window.MainPane);

		UpperCornerButtons = new UpperCornerButtons();
		UpperCornerButtons.addToPane(Window.MainPane);

		MainInfo = new MainInfo();
		MainInfo.addToPane(Window.MainPane);
		MainInfo.viewMode();

		AreaDisplay = new AreaDisplay();
		AreaDisplay.addToPane(Window.MainPane);
		AreaDisplay.viewMode();

		CustomerDisplay = new CustomerDisplay();
		CustomerDisplay.addToPane(Window.MainPane);

		ProductionMontage = new ProductionMontage();
		ProductionMontage.openMode();
		ProductionMontage.addToPane(Window.MainPane);

		ImportantList = new ImportantList();
		ImportantList.addToPane(Window.MainPane);
		ImportantList.openView();

		OrderList = new OrderList();
		OrderList.addToPane(Window.MainPane);

		CommentArea = new CommentArea();
		CommentArea.addToPane(Window.MainPane);
		CommentArea.viewMode();

		FileList = new FileList();
		FileList.addToPane(Window.MainPane);
		FileList.setActiveOrderFolder();

		FileCreateButtons = new FileCreateButtons(FileList.FileList);
		FileCreateButtons.setActiveOrderFolder();
		FileCreateButtons.addToPane(Window.MainPane);

		// MYSQL
		GetOrder = new GetOrder();
		DeleteOrder = new DeleteOrder();

		// Update MainInfo Button
		MainInfo.BtnUpdate.setOnAction(e -> {
			if (MainInfo.BtnUpdate.getText().equalsIgnoreCase("Ändra")) {
				setMainInfoUpdateView();
			} else if (MainInfo.BtnUpdate.getText().equalsIgnoreCase("Spara")) {
				updateMainInfoAndSetNormalView();
			}
		});

		// Update area Button
		AreaDisplay.BtnUpdate.setOnAction(e -> {
			if (AreaDisplay.BtnUpdate.getText().equalsIgnoreCase("Ändra")) {
				setAreaUpdateSate();
			} else if (AreaDisplay.BtnUpdate.getText().equalsIgnoreCase("Spara")) {
				setAreaNormalSate();
			}
		});

		// Update Production
		ProductionMontage.BtnUpdate.setOnAction(e -> {
			if (ProductionMontage.BtnUpdate.getText().equalsIgnoreCase("Ändra")) {
				setProductionMontageUpdateState();
			} else if (ProductionMontage.BtnUpdate.getText().equalsIgnoreCase("Spara")) {
				setProductionMontageNormalState();
			}
		});

		// Update Importantlist
		ImportantList.BtnUpdate.setOnAction(e -> {
			updateImportantList();
		});

		// Update OrderList
		OrderList.BtnUpdate.setOnAction(e -> {
			updateOrderList();
		});

		// Update Comment
		CommentArea.BtnUpdate.setOnAction(e -> {
			updateComment();
		});
	}

	public void setSelectedOrderDataAll(int Number) {

		if (GetOrder.setAllOrder(Number) == false) {
			return;
		}

		MainInfo.setViewData(GetOrder.OrderNr, GetOrder.OrderName, GetOrder.OrderAddress, GetOrder.OrderType);
		LabelHeader.setViewOrder(GetOrder.OrderCreator, GetOrder.KalkylDateCreated, GetOrder.OrderDateCreated);
		CustomerDisplay.setCustomer(GetOrder.Customer, GetOrder.CustomerPhone, GetOrder.CustomerEmail,
				GetOrder.CustomerWeb);
		CustomerDisplay.setContactPerson(GetOrder.ContactName, GetOrder.ContactPhone, GetOrder.ContactEmail);
		ProductionMontage.setProductionView(GetOrder.ProductionStart, GetOrder.ProductionStop,
				GetOrder.ProductionTimeDone, GetOrder.ProductionTime);
		ProductionMontage.setMontageView(GetOrder.MontageStart, GetOrder.MontageStop, GetOrder.MontageTimeDone,
				GetOrder.MontageTime);
		AreaDisplay.setViewData(GetOrder.Area, AreaDisplay.list1);
		ImportantList.setViewData(GetOrder.Imp);
		OrderList.setViewData(GetOrder.Order);
		CommentArea.setViewData(GetOrder.Comment);
		FileList.setNumber(GetOrder.OrderNr);
		FileCreateButtons.setNumber(GetOrder.OrderNr);

		FileFolderM.setFolderList("Aktiv", GetOrder.OrderNr, FileList.FileList);
		Window.use();
	}

	// UPDATE MAININFO

	void setMainInfoUpdateView() {
		MainInfo.setUpdateFields();
		MainInfo.updateMode();
	}

	void updateMainInfoAndSetNormalView() {
		MainInfo.setUpdateType();
		UpdateOrder.updateMainInfo(GetOrder.OrderNr, MainInfo.TextFieldUpdate[0].getText(),
				MainInfo.TextFieldUpdate[1].getText(), MainInfo.Type);
		GetOrder.setMainInfoAndCustomer(Integer.parseInt(GetOrder.OrderNr));
		MainInfo.setViewData(GetOrder.OrderNr, GetOrder.OrderName, GetOrder.OrderAddress, GetOrder.OrderType);
		MainInfo.viewMode();
	}

	// Update Areas

	void setAreaUpdateSate() {
		AreaDisplay.updateMode();
		AreaDisplay.setUpdateBoxes(GetOrder.Area);
	}

	void setAreaNormalSate() {
		if (AreaDisplay.setUpdateVariables() == false) {
			return;
		}
		UpdateOrder.updateAreas(GetOrder.OrderNr, AreaDisplay.updateArea);
		GetOrder.setAreasList(Integer.parseInt(GetOrder.OrderNr));
		AreaDisplay.setViewData(GetOrder.Area, AreaDisplay.list1);
		AreaDisplay.viewMode();
	}

	// Update ProductionMontge

	void setProductionMontageUpdateState() {
		ProductionMontage.updateProductionState(GetOrder.ProductionStart, GetOrder.ProductionTime);
		ProductionMontage.updateMontageState(GetOrder.MontageStart, GetOrder.MontageTime);
		ProductionMontage.BtnUpdate.setText("Spara");

		if (GetOrder.ProductionStart.equalsIgnoreCase("false")) {
			// nothing
		} else {
			// PRODUCTION TIME
			String startPWeek = GetOrder.ProductionStart.substring(5);
			String startPYear = GetOrder.ProductionStart.substring(0, 4);
			String stopPWeek = GetOrder.ProductionStop.substring(5);
			String stopPYear = GetOrder.ProductionStop.substring(0, 4);

			ProductionMontage.YearBox[0].setValue(startPYear);
			ProductionMontage.YearBox[1].setValue(stopPYear);
			ProductionMontage.WeekBox[0].setValue(startPWeek);
			ProductionMontage.WeekBox[1].setValue(stopPWeek);
		}

		if (GetOrder.MontageStart.equalsIgnoreCase("false")) {
			// nothing
		} else {
			String startMWeek = GetOrder.MontageStart.substring(5);
			String startMYear = GetOrder.MontageStart.substring(0, 4);
			String stopMWeek = GetOrder.MontageStop.substring(5);
			String stopMYear = GetOrder.MontageStop.substring(0, 4);

			ProductionMontage.YearBox[2].setValue(startMYear);
			ProductionMontage.YearBox[3].setValue(stopMYear);
			ProductionMontage.WeekBox[2].setValue(startMWeek);
			ProductionMontage.WeekBox[3].setValue(stopMWeek);
		}
	}

	void setProductionMontageNormalState() {
		if (ProductionMontage.checkInsertData() == false) {
			return;
		}
		ProductionMontage.setInsertVariables();

		UpdateOrder.updateProductionMontage(GetOrder.OrderNr, ProductionMontage.StartP, ProductionMontage.StopP,
				ProductionMontage.HoursP, ProductionMontage.StartM, ProductionMontage.StopM, ProductionMontage.HoursM);
		GetOrder.setProductionMontage(Integer.parseInt(GetOrder.OrderNr));
		ProductionMontage.setProductionView(GetOrder.ProductionStart, GetOrder.ProductionStop,
				GetOrder.ProductionTimeDone, GetOrder.ProductionTime);
		ProductionMontage.setMontageView(GetOrder.MontageStart, GetOrder.MontageStop, GetOrder.MontageTimeDone,
				GetOrder.MontageTime);
		ProductionMontage.TextFieldHour[0].clear();
		ProductionMontage.TextFieldHour[1].clear();
		ProductionMontage.BtnUpdate.setText("Ändra");

		for (int i = 0; i < 4; i++) {
			SM.setYearComboBox(ProductionMontage.YearBox[i]);
			SM.setWeekCombo(ProductionMontage.WeekBox[i]);
		}
	}

	// Update ImportantList
	void updateImportantList() {
		ImportantList.setInsertVariables();
		UpdateOrder.updateImportantList(GetOrder.OrderNr, ImportantList.impValue);
	}

	// Update OrderList
	void updateOrderList() {
		OrderList.setInsertVariables();
		UpdateOrder.updateOrderList(GetOrder.OrderNr, OrderList.orderValue);
	}

	// Update Comment
	void updateComment() {
		CommentArea.setInsertVariables();
		UpdateOrder.updateComment(GetOrder.OrderNr, CommentArea.Comment);
	}
}
