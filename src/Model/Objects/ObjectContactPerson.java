package Model.Objects;

import javafx.beans.property.SimpleStringProperty;

public class ObjectContactPerson {

	public SimpleStringProperty Id = new SimpleStringProperty();
	public SimpleStringProperty Name = new SimpleStringProperty();
	public SimpleStringProperty LastName = new SimpleStringProperty();
	public SimpleStringProperty TeleNr = new SimpleStringProperty();
	public SimpleStringProperty Mail = new SimpleStringProperty();

	public ObjectContactPerson(String Id, String name, String lastname, String tele, String mail) {
		this.Id = new SimpleStringProperty(Id);
		this.Name = new SimpleStringProperty(name);
		this.LastName = new SimpleStringProperty(lastname);
		this.TeleNr = new SimpleStringProperty(tele);
		this.Mail = new SimpleStringProperty(mail);
	}

	public String getId() {
		return Id.get();
	}

	public void setId(SimpleStringProperty id) {
		Id = id;
	}

	public String getName() {
		return Name.get();
	}

	public String getLastName() {
		return LastName.get();
	}

	public String getTeleNr() {
		return TeleNr.get();
	}

	public String getMail() {
		return Mail.get();
	}

	public void setName(SimpleStringProperty name) {
		Name = name;
	}

	public void setLastName(SimpleStringProperty lastName) {
		LastName = lastName;
	}

	public void setTeleNr(SimpleStringProperty teleNr) {
		TeleNr = teleNr;
	}

	public void setMail(SimpleStringProperty mail) {
		Mail = mail;
	}

}
