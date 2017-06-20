package Model.Objects;
import javafx.beans.property.SimpleStringProperty;

public class ObjectTimeSave {
	
	public SimpleStringProperty NumberId = new SimpleStringProperty();
	public SimpleStringProperty Employee = new SimpleStringProperty();
	public SimpleStringProperty OrderId = new SimpleStringProperty();
	public SimpleStringProperty WorkTyp = new SimpleStringProperty();
	public SimpleStringProperty Hours = new SimpleStringProperty();
	public SimpleStringProperty DateStamp = new SimpleStringProperty();
	public SimpleStringProperty Week = new SimpleStringProperty();
	public SimpleStringProperty Outside = new SimpleStringProperty();
	public SimpleStringProperty Overtime = new SimpleStringProperty();
	
	public ObjectTimeSave(String NumberId, String Employee, String OrderId, String WorkTyp, String Hours, String DateStamp, String Week, String Outside, String Overtime) {
		this.NumberId = new SimpleStringProperty(NumberId);
		this.Employee = new SimpleStringProperty(Employee);
		this.OrderId = new SimpleStringProperty(OrderId);
		this.WorkTyp = new SimpleStringProperty(WorkTyp);
		this.Hours = new SimpleStringProperty(Hours);
		this.DateStamp = new SimpleStringProperty(DateStamp);
		this.Week = new SimpleStringProperty(Week);
		this.Outside = new SimpleStringProperty(Outside);
		this.Overtime = new SimpleStringProperty(Overtime);
	}
	
	public String getNumberId() {
		return NumberId.get();
	}

	public void setNumberId(SimpleStringProperty numberId) {
		NumberId = numberId;
	}
	
	public String getEmployee() {
		return Employee.get();
	}

	public void setEmployee(SimpleStringProperty employee) {
		Employee = employee;
	}
	
	public String getOrderId() {
		return OrderId.get();
	}

	public void setOrderId(SimpleStringProperty orderId) {
		OrderId = orderId;
	}
	
	public String getWorkTyp() {
		return WorkTyp.get();
	}

	public void setWorkTyp(SimpleStringProperty workTyp) {
		WorkTyp = workTyp;
	}
	
	public String getHours() {
		return Hours.get();
	}

	public void setHours(SimpleStringProperty hours) {
		Hours = hours;
	}
	
	public String getDateStamp() {
		return DateStamp.get();
	}

	public void setDateStamp(SimpleStringProperty dateStamp) {
		DateStamp = dateStamp;
	}
	
	public String getWeek() {
		return Week.get();
	}

	public void setWeek(SimpleStringProperty week) {
		Week = week;
	}
	
	public String getOutside() {
		return Outside.get();
	}

	public void setOutside(SimpleStringProperty outside) {
		Outside = outside;
	}
	
	public String getOvertime() {
		return Overtime.get();
	}

	public void setOvertime(SimpleStringProperty overtime) {
		Overtime = overtime;
	}
}
