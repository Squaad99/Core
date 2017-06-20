package Model.Objects;

import javafx.beans.property.SimpleStringProperty;

public class ObjectClient {

	public SimpleStringProperty CustomerName = new SimpleStringProperty();
	public SimpleStringProperty CustomerMail = new SimpleStringProperty();
	public SimpleStringProperty CustomerPhone = new SimpleStringProperty();
	public SimpleStringProperty ContactName = new SimpleStringProperty();
	public SimpleStringProperty ContactMail = new SimpleStringProperty();
	public SimpleStringProperty ContactPhone = new SimpleStringProperty();
	public SimpleStringProperty DateExpire = new SimpleStringProperty();

	public ObjectClient(String custName, String custMail, String custPhone, String contName, String contMail,
			String contPhone, String dateExpire) {
		
		this.CustomerName = new SimpleStringProperty(custName);
		this.CustomerMail = new SimpleStringProperty(custMail);
		this.CustomerPhone = new SimpleStringProperty(custPhone);
		this.ContactName = new SimpleStringProperty(contName);
		this.ContactMail = new SimpleStringProperty(contMail);
		this.ContactPhone = new SimpleStringProperty(contPhone);
		this.DateExpire = new SimpleStringProperty(dateExpire);
		
	}

	public String getCustomerName() {
		return CustomerName.get();
	}

	public void setCustomerName(SimpleStringProperty customerName) {
		CustomerName = customerName;
	}

	public String getCustomerMail() {
		return CustomerMail.get();
	}

	public void setCustomerMail(SimpleStringProperty customerMail) {
		CustomerMail = customerMail;
	}

	public String getCustomerPhone() {
		return CustomerPhone.get();
	}

	public void setCustomerPhone(SimpleStringProperty customerPhone) {
		CustomerPhone = customerPhone;
	}

	public String getContactName() {
		return ContactName.get();
	}

	public void setContactName(SimpleStringProperty contactName) {
		ContactName = contactName;
	}

	public String getContactMail() {
		return ContactMail.get();
	}

	public void setContactMail(SimpleStringProperty contactMail) {
		ContactMail = contactMail;
	}

	public String getContactPhone() {
		return ContactPhone.get();
	}

	public void setContactPhone(SimpleStringProperty contactPhone) {
		ContactPhone = contactPhone;
	}
	
	public String getDateExpire() {
		return DateExpire.get();
	}

	public void setDateExpire(SimpleStringProperty dateExpire) {
		DateExpire = dateExpire;
	}
}
