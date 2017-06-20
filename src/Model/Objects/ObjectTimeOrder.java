package Model.Objects;
import javafx.beans.property.SimpleStringProperty;

public class ObjectTimeOrder {
	
	
	public SimpleStringProperty WorkType = new SimpleStringProperty();
	public SimpleStringProperty Hours = new SimpleStringProperty();

	public ObjectTimeOrder(String OrderId, String Hours) {
		this.WorkType = new SimpleStringProperty(OrderId);
		this.Hours= new SimpleStringProperty(Hours);
		}
	
	public String getWorkType() {
		return WorkType.get();
	}

	public void setWorkType(SimpleStringProperty workType) {
		WorkType = workType;
	}
	
	public String getHours() {
		return Hours.get();
	}

	public void setHours(SimpleStringProperty hours) {
		Hours = hours;
	}

}
