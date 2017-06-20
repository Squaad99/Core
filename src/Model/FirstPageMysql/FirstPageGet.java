package Model.FirstPageMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import Model.Objects.ObjectKalkyl;
import Model.Objects.ObjectOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class FirstPageGet {
	
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;
	
	private ObservableList<ObjectKalkyl> KalkylTableData;
	private ObservableList<ObjectOrder> OrderActiveTableData;
	private ObservableList<ObjectOrder> OrderFinishedTableData;

	public FirstPageGet(ObservableList<ObjectKalkyl> KalkylTableData, ObservableList<ObjectOrder> OrderActiveTableData, ObservableList<ObjectOrder> OrderFinishedTableData) {
		this.KalkylTableData = KalkylTableData;
		this.OrderActiveTableData = OrderActiveTableData;
		this.OrderFinishedTableData = OrderFinishedTableData;
	}
	
	
	public void setKalkylTable(TableView Table){
		Table.getItems().clear();
		KalkylTableData.clear();	
		try{	
			   con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			   st = con.createStatement();
			   s = "SELECT * FROM Kalkyler";
			   rs = st.executeQuery(s);
			  
			   while(rs.next()){
				   KalkylTableData.add(new ObjectKalkyl(rs.getString("KalkylID"), rs.getString("Name"), rs.getString("Creator"), rs.getString("Typ"), rs.getString("DateKalkyl")));
	            }
			   Table.setItems(KalkylTableData);
		 }
		catch(Exception e){
			SM.messageBox("Fel med databsen! Kontakt Support" + e);
		}
	}

	public void setOrderActiveTable(TableView Table){
		Table.getItems().clear();
		try{
			   con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			   st = con.createStatement();
			   s = "SELECT * FROM Orders where STATUS = 'Active'";
			   rs = st.executeQuery(s);

			   while(rs.next()){
				   OrderActiveTableData.add(new ObjectOrder(rs.getString("OrderID"), rs.getString("Name"), rs.getString("Creator"), rs.getString("Typ"), rs.getString("DateOrder")));
	            }
			  
			   Table.setItems(OrderActiveTableData);
		 }
		catch(Exception e){
			SM.messageBox("Fel med databsen! Kontakt Support");
		}
	}
	
	public void setOrderFinishedTable(TableView Table){
		Table.getItems().clear();
		try{
			   con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			   st = con.createStatement();
			   s = "SELECT * FROM Orders where STATUS = 'InActive'";
			   rs = st.executeQuery(s);

			   while(rs.next()){
				   OrderFinishedTableData.add(new ObjectOrder(rs.getString("OrderID"), rs.getString("Name"), rs.getString("Creator"), rs.getString("Typ"), rs.getString("DateOrder")));
	            }
			  
			   Table.setItems(OrderFinishedTableData);
		 }
		catch(Exception e){
			SM.messageBox("Fel med databsen! Kontakt Support");
		}
	}
}
