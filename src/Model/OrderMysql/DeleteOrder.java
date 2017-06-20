package Model.OrderMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Model.SM;
import Model.Setting;

public class DeleteOrder {

	public DeleteOrder() {
		
	}
	
	
	public void deleteOrderAll(String Number){
		deleteMainInfo(Number);
		deleteAreas(Number);
		deleteOrderList(Number);
		deleteImportantList(Number);
		deleteProductionMontage(Number);
		deleteComment(Number);
	}
	
	public void deleteMainInfo(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM Orders where OrderID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort Order! InfoMain");
		}
	}
	
	public void deleteAreas(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM OrderAreas where OrderID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort Order! Areas");
		}
	}
	
	public void deleteOrderList(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM OrderList where OrderID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort Order! OrderList");
		}
	}
	
	public void deleteImportantList(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM ImportantOrder where OrderID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort Order! OrderList");
		}
	}
	
	public void deleteProductionMontage(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM OrderProductionDate where OrderID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort Order! OrderList");
		}
	}
	
	public void deleteComment(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM OrderComment where OrderID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort Order! OrderList");
		}
	}

}
