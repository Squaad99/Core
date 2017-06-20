package View.Pages;

import Model.SM;
import Model.Customer.CustomerMYSQL;
import View.CustomerObject.ContactInsert;
import View.CustomerObject.CustomerDisplay;
import View.CustomerObject.CustomerInsert;
import View.Windows.NormalWindow;

public class Customer {
	
	public NormalWindow Window;
	
	public CustomerInsert CustomerInsert;
	public ContactInsert ContactInsert;
	public CustomerDisplay CustomerDisplay;
	
	//MYSQL
	CustomerMYSQL CustomerMYSQL;

	public Customer() {
		
		Window = new NormalWindow("Kunder");
		
		CustomerDisplay = new CustomerDisplay();
		CustomerDisplay.addToPane(Window.MainPane);
		
		CustomerInsert = new CustomerInsert();
		CustomerInsert.addToPane(Window.MainPane);
		
		ContactInsert = new ContactInsert();
		ContactInsert.addToPane(Window.MainPane);
		
		//MYSQL
		CustomerMYSQL = new CustomerMYSQL();
		

		
	}
	
	public void checkDataInsertCustomer(){
		if(CustomerInsert.checkInsertData() == false){
			return;
		}
		CustomerInsert.setInsertVariables();
		
		if(CustomerMYSQL.checkCustomerName(CustomerInsert.name) == false){
			return;
		}
		if(CustomerMYSQL.insertCustomer(CustomerInsert.name, CustomerInsert.address, CustomerInsert.web, CustomerInsert.phone, CustomerInsert.email, CustomerInsert.orgNr) == false){
			return;
		}
		CustomerInsert.clearAllFields();
		SM.setCustomerList(ContactInsert.SelectCustomerBox);
	}

	public void checkDataInsertContactPerson(){
		if(ContactInsert.checkDataInsert() == false){
			return;
		}
		ContactInsert.setInsertVariables();
		
		if(CustomerMYSQL.insertContactPerson(SM.getSelectedCustomerId(ContactInsert.SelectCustomerBox), ContactInsert.name, ContactInsert.lastname, ContactInsert.phone, ContactInsert.mail) == true){
			ContactInsert.clearAllFields();
			SM.setCustomerList(ContactInsert.SelectCustomerBox);
		}
	}
		
	
	
	
}
