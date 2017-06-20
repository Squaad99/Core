package Model.Objects;
import javafx.beans.property.SimpleStringProperty;

public class ObjectOrder {
	
	public SimpleStringProperty OrderId = new SimpleStringProperty();
	public SimpleStringProperty Name = new SimpleStringProperty();
	public SimpleStringProperty Creator = new SimpleStringProperty();
	public SimpleStringProperty Typ = new SimpleStringProperty();
	public SimpleStringProperty DateOrder = new SimpleStringProperty();
	
	public ObjectOrder(String OrderId, String Name, String Creator, String Typ, String DateOrder){
		this.OrderId = new SimpleStringProperty(OrderId);
        this.Name = new SimpleStringProperty(Name);
        this.Creator = new SimpleStringProperty(Creator);
        this.Typ = new SimpleStringProperty(Typ);
        this.DateOrder = new SimpleStringProperty(DateOrder);
	}
	
	public String getOrderId() {
		return OrderId.get();
	}

	public void setOrderId(SimpleStringProperty orderId) {
		OrderId = orderId;
	}

	public String getName() {
		return Name.get();
	}

	public void setName(SimpleStringProperty name) {
		Name = name;
	}

	public String getTyp() {
		return Typ.get();
	}
	
	public void setCreator(SimpleStringProperty creator) {
		Creator = creator;
	}

	public String getCreator() {
		return Creator.get();
	}
	
	public void setTyp(SimpleStringProperty typ) {
		Typ = typ;
	}

	public String getDateOrder() {
		return DateOrder.get();
	}

	public void setDateOrder(SimpleStringProperty dateOrder) {
		DateOrder = dateOrder;
	}

}
