package Model.Objects;
import javafx.beans.property.SimpleStringProperty;

public class ObjectSmallOrder {

	public SimpleStringProperty OrderId = new SimpleStringProperty();
	public SimpleStringProperty Name = new SimpleStringProperty();
	
	public ObjectSmallOrder(String OrderId, String Name){
		this.OrderId = new SimpleStringProperty(OrderId);
        this.Name = new SimpleStringProperty(Name);
        
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
}
