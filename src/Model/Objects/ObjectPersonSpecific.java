package Model.Objects;

import javafx.beans.property.SimpleStringProperty;

public class ObjectPersonSpecific {
	
	public SimpleStringProperty OrderId = new SimpleStringProperty();
	public float Hours;

	public ObjectPersonSpecific(String Order, float Hour) {
		this.OrderId = new SimpleStringProperty(Order);
		this.Hours = Hour;
	}
	
	public String getOrderId() {
		return OrderId.get();
	}

	public void setOrderId(SimpleStringProperty numberId) {
		this.OrderId = numberId;
	}
	
	public float getHours() {
		return Hours;
	}

	public void setHours(float Hours) {
		this.Hours = Hours;
	}
	

}
