package Control;

import View.FirstPage;
import View.Pages.*;
import View.SettingsObject.FileSettings;
import javafx.scene.control.TableRow;

import java.io.File;

import Model.SM;
import Model.FileFolder.FileFolderM;
import Model.Objects.*;
import Model.OrderMysql.UpdateOrder;

public class Control {

	private FirstPage FirstPage;
	private CreateKalkyl CreateKalkyl;
	private CreateOrder CreateOrder;
	private OpenKalkyl OpenKalkyl;
	private OpenOrder OpenOrder;
	private Customer Customer;
	private OpenOrderDone OpenOrderDone;
	private KalkylToOrder KalkylToOrder;
	private CalendarPage CalendarPage;
	private TimeAccounting TimeAccounting;
	private SettingsFrame SettingsFrame;

	@SuppressWarnings("unchecked")
	public Control(FirstPage FirstPage, CreateKalkyl CreateKalkyl, CreateOrder CreateOrder, Customer Customer,
			OpenKalkyl OpenKalkyl, OpenOrder OpenOrder, OpenOrderDone OpenOrderDone, KalkylToOrder KalkylToOrder,
			CalendarPage CalendarPage, TimeAccounting TimeAccounting, SettingsFrame SettingsFrame) {

		this.FirstPage = FirstPage;
		this.CreateKalkyl = CreateKalkyl;
		this.CreateOrder = CreateOrder;
		this.Customer = Customer;
		this.OpenKalkyl = OpenKalkyl;
		this.OpenOrder = OpenOrder;
		this.OpenOrderDone = OpenOrderDone;
		this.KalkylToOrder = KalkylToOrder;
		this.CalendarPage = CalendarPage;
		this.TimeAccounting = TimeAccounting;
		this.SettingsFrame = SettingsFrame;

		// CRETA KALKYL BUTTON
		this.FirstPage.createKalkylButton(e -> {
			this.CreateKalkyl.Window.use();
		});

		// SAVE KALKYL BUTTON
		this.CreateKalkyl.SaveCloseBtn.saveButton(e -> {
			this.CreateKalkyl.checkInsertDataAll();
			this.FirstPage.setKalkylTable();
		});

		// DELETE KALKYL BUTTON
		this.OpenKalkyl.UpperCornerButtons.Btns[0].setOnAction(e -> {
			if (SM.confirmationBox("Vill du ta bort Kalkyl?") == true) {
				this.OpenKalkyl.DeleteKalkyl.deleteAll(this.OpenKalkyl.GetKalkyl.KalkylNr);
				FileFolderM.deleteFolder("Kalkyl", this.OpenKalkyl.GetKalkyl.KalkylNr);
				this.OpenKalkyl.Window.window.hide();
				this.FirstPage.setKalkylTable();
			}
		});

		// Change to order
		this.OpenKalkyl.UpperCornerButtons.Btns[1].setOnAction(e -> {
			if (SM.confirmationBox("Vill du göra till Order?") == true) {
				this.KalkylToOrder.Window.window.show();
				this.KalkylToOrder.setExistingKalkylData(this.OpenKalkyl.GetKalkyl.KalkylNr,
						this.OpenKalkyl.GetKalkyl.KalkylName, this.OpenKalkyl.GetKalkyl.KalkylAddress,
						this.OpenKalkyl.GetKalkyl.KalkylType, this.OpenKalkyl.GetKalkyl.KalkylDateCreated,
						this.OpenKalkyl.GetKalkyl.Area, this.OpenKalkyl.GetKalkyl.Imp,
						this.OpenKalkyl.GetKalkyl.Comment);
				this.OpenKalkyl.Window.window.hide();
			}
		});

		// change to order save button
		this.KalkylToOrder.SaveCloseBtn.Button.setOnAction(e -> {
			this.KalkylToOrder.checkInsertDataAll();
			this.FirstPage.setKalkylTable();
			this.FirstPage.setActiveOrdersTable();
		});

		// -------------- ORDER ----------------------------

		// CREATE ORDER BUTTON
		this.FirstPage.createOrderButton(e -> {
			this.CreateOrder.Window.use();
		});

		// SAVE ORDER BUTTON
		this.CreateOrder.SaveCloseBtn.saveButton(e -> {
			this.CreateOrder.checkInsertDataAll();
			this.FirstPage.setActiveOrdersTable();
		});

		// DELETE ORDER BUTTON
		this.OpenOrder.UpperCornerButtons.Btns[0].setOnAction(e -> {
			if (SM.confirmationBox("Vill du ta bort Order?") == true) {
				this.OpenOrder.DeleteOrder.deleteOrderAll(this.OpenOrder.GetOrder.OrderNr);
				FileFolderM.deleteFolder("Aktiv", this.OpenOrder.GetOrder.OrderNr);
				this.OpenOrder.Window.window.hide();
				this.FirstPage.setActiveOrdersTable();
			}
		});

		// SET ORDER INACTIVE
		this.OpenOrder.UpperCornerButtons.Btns[1].setOnAction(e -> {
			if (SM.confirmationBox("Vill du ändra status till inaktiv?") == true) {
				UpdateOrder.updateInActive(this.OpenOrder.GetOrder.OrderNr);
				FileFolderM.FolderActiveToInActive(this.OpenOrder.GetOrder.OrderNr);
				this.FirstPage.setActiveOrdersTable();
				this.FirstPage.setFinishedOrdersTable();
				this.OpenOrder.Window.window.hide();
			}
		});

		// -------------OrderDone---------------------

		// Delete Order BUtton
		this.OpenOrderDone.UpperCornerButtons.Btns[0].setOnAction(e -> {
			if (SM.confirmationBox("Vill du ta bort Order?") == true) {
				this.OpenOrderDone.DeleteOrder.deleteOrderAll(this.OpenOrderDone.GetOrder.OrderNr);
				FileFolderM.deleteFolder("Avslutade", this.OpenOrderDone.GetOrder.OrderNr);
				this.OpenOrderDone.Window.window.hide();
				this.FirstPage.setActiveOrdersTable();
				this.FirstPage.setFinishedOrdersTable();
			}
		});

		// SET ORDER ACTIVE
		this.OpenOrderDone.UpperCornerButtons.Btns[1].setOnAction(e -> {
			if (SM.confirmationBox("Vill du ändra status till aktiv?") == true) {
				UpdateOrder.updateActive(this.OpenOrderDone.GetOrder.OrderNr);
				FileFolderM.FolderInActiveToActive(this.OpenOrderDone.GetOrder.OrderNr);
				this.FirstPage.setActiveOrdersTable();
				this.FirstPage.setFinishedOrdersTable();
				this.OpenOrderDone.Window.window.hide();
			}
		});

		// -------------- ORDER ----------------------------

		// CUSTOMER FRAME BUTTON
		this.FirstPage.customerButton(e -> {
			this.Customer.Window.use();
		});

		// OPEN CALENDAR WINDOW
		this.FirstPage.MenuButton[2].setOnAction(e -> {
			this.CalendarPage.Window.use();
		});

		// OPEN TIMEACCOUNTING WINDOW
		this.FirstPage.MenuButton[3].setOnAction(e -> {
			this.TimeAccounting.Window.use();
		});

		// OPEN SETTINGS WINDOW
		this.FirstPage.SubMenueButton.setOnAction(e -> {
			this.SettingsFrame.SmallWindow.use();
		});

		// -----------------TABLELISTERNERS------------------------------

		// KALKYL TABLE MOUSE LISTENER
		this.FirstPage.TableProjectKalkyl.ProjectTable.setRowFactory(e -> {
			TableRow<ObjectKalkyl> row = new TableRow<>();
			row.setOnMouseClicked(event -> {

				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					ObjectKalkyl rowData = row.getItem();
					this.OpenKalkyl.setSelectedKalkylDataAll(Integer.parseInt(rowData.getKalkylID()));
				}
			});
			return row;
		});

		// ACTIVE ORDERS TABLE MOUSE LISTENER
		this.FirstPage.TableProjectActiveOrders.ProjectTable.setRowFactory(e -> {
			TableRow<ObjectOrder> row = new TableRow<>();
			row.setOnMouseClicked(event -> {

				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					ObjectOrder rowData = row.getItem();
					this.OpenOrder.setSelectedOrderDataAll(Integer.parseInt(rowData.getOrderId()));
				}
			});
			return row;
		});

		// FINISHED ORDERS TABLE MOUSE LISTENER
		this.FirstPage.TableProjectFinshedOrders.ProjectTable.setRowFactory(e -> {
			TableRow<ObjectOrder> row = new TableRow<>();
			row.setOnMouseClicked(event -> {

				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					ObjectOrder rowData = row.getItem();

					this.OpenOrderDone.setSelectedOrderDataAll(Integer.parseInt(rowData.getOrderId()));
				}
			});
			return row;
		});

		// FileSaveSettings
		SettingsFrame.FileSettings.BtnSaveFileUpdate.setOnAction(e -> {
			this.SettingsFrame.FileSettings.updateMysqlFileSave();
			this.SettingsFrame.FileSettings.checkMoveFile();
			this.OpenKalkyl.FileCreateButtons.setFilePathAndName();
			this.OpenOrder.FileCreateButtons.setFilePathAndName();
			this.OpenOrderDone.FileCreateButtons.setFilePathAndName();
		});

		// --------------------------------------CUSTOMER--------------------------------------------------------

		// CREATE NEW CUSTOMER BUTTON
		this.Customer.CustomerInsert.addButton(e -> {
			this.Customer.checkDataInsertCustomer();
			SM.setCustomerList(this.Customer.CustomerDisplay.SelectCustomerBox);
			SM.setCustomerList(this.Customer.ContactInsert.SelectCustomerBox);
			SM.setCustomerList(this.OpenKalkyl.ClientDisplay.ClientBox);
			SM.setCustomerList(this.CreateOrder.InsertCustomer.CustomerInsert[0]);
			SM.setCustomerList(this.CreateKalkyl.ClientInsertKalkyl.CustomerBox[0]);
			SM.setCustomerList(this.CreateKalkyl.ClientInsertKalkyl.CustomerBox[1]);
			SM.setCustomerList(this.CreateKalkyl.ClientInsertKalkyl.CustomerBox[2]);
			SM.setCustomerList(this.CreateKalkyl.ClientInsertKalkyl.CustomerBox[3]);
			SM.setCustomerList(this.CreateKalkyl.ClientInsertKalkyl.CustomerBox[4]);
		});

		// CREATE NEW CONTACTPERSON BUTTON
		this.Customer.ContactInsert.addButton(e -> {
			this.Customer.checkDataInsertContactPerson();
			this.Customer.CustomerDisplay.getSelectedCustomer();
			this.Customer.CustomerDisplay.getContactPerson();
		});

	}

}
