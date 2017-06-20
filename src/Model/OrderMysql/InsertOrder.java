package Model.OrderMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Model.SM;
import Model.Setting;

public class InsertOrder {

	public InsertOrder() {
		
	}
	
	public boolean insertMainInfo(String OrderId, String Name, String Address, String Type, int CustomerId, int PersonId, String DateKalkylCreated) {

		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);

			Statement myStmt = myConn.createStatement();

			String sql = "insert into Orders"
					+ " (OrderID, Name, Address, Creator, Typ, KundID, KontaktPersonID, DateOrder, DateKalkyl, STATUS)"
					+ " values ('" + OrderId + "', '" + Name + "', '" + Address+ "', "
					+ "'"+Setting.User+"', '" + Type + "', '" + CustomerId + "', "
					+ "'" + PersonId + "', '" + SM.getDate() + "', '"+DateKalkylCreated+"', 'Active')";

			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till MYSQL 'Order MainInfo'");
			return false;
		}
		return true;
	}
	
	public boolean insertOrderAreas(String Number, String Area1, String Area2, String Area3, String Area4, String Area5, String Area6) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into OrderAreas" + " (OrderID, Item1, Item2, Item3, Item4, Item5, Item6)" + " values"
					+ " ('"	+ Number + "', '" + Area1 + "', '" + Area2 + "', '" + Area3 + "', '" + Area4 + "', '"
					+ Area5 + "', '" + Area6 + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till 'MYSQL Area'");
			return false;
		}
		return true;
	}

	public boolean insertOrderProductionDate(String Numnber, String StartP, String StopP, String HoursP, String StartM, String StopM, String HoursM) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into OrderProductionDate"
					+ " (OrderID, VeckaStartP, VeckaStopP, VeckaStartM, VeckaStopM, ProductionTime, MontageTime)"
					+ " values ('" + Numnber + "', " + "'" + StartP + "'," + "'" + StopP + "'," + "'" + StartM + "'," + "'" + StopM + "',"
					+ "'" + HoursP + "', '" + HoursM + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till 'MYSQL ProductionDate'");
			return false;
		}
		return true;
	}
	
	public boolean insertImportantList(String Number, String Imp1, String Imp2, String Imp3, String Imp4, String Imp5) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);

			Statement myStmt = myConn.createStatement();

			String sql = "insert into importantorder" + " (OrderID, Item1, Item2, Item3, Item4, Item5)"
					+ " values ('" + Number + "', '" + Imp1 + "', '" + Imp2 + "', '" + Imp3 + "', '" + Imp4 + "', '" + Imp5 + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till 'MYSQL Important'");
			return false;
		}
		return true;
	}
	
	public boolean insertOrderList(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into OrderList" + " (OrderID, Item1, Item2, Item3, Item4, Item5)" + " values ('"
					+ Number + "', 'false', 'false', 'false', 'false', 'false')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till 'MYSQL Orderlist'");
			return false;
		}
		return true;
	}
	
	public boolean insertComment(String Number, String Comment){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into OrderComment" + " (OrderID, OrderComment)" + " values ('" + Number + "', '" + Comment + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till 'MYSQL Comment'");
			return false;
		}  
		return true;
	}
	
	
	
	
}
