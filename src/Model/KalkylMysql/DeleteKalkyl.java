package Model.KalkylMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Model.SM;
import Model.Setting;

public class DeleteKalkyl {

	public DeleteKalkyl(){
	}

	public void deleteMainInfo(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM Kalkyler where KalkylID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort kalkyl! InfoMain");
		}
	}
	
	public void deleteClients(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM KalkylClient where KalkylID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort kalkyl! CLIENTS");
		}
	}
	
	public void deleteImportantList(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM ImportantKalkyl where KalkylID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort kalkyl! IMPORTANT");
		}
	}

	public void deleteAreas(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM KalkylAreas where KalkylID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort kalkyl! Areas");
		}
	}
	
	public void deleteComment(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Delete FROM KalkylComment where KalkylID = '" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du skulle ta bort kalkyl! Comment");
		}
	}
	
	public void deleteAll(String Number){
		deleteMainInfo(Number);
		deleteClients(Number);
		deleteImportantList(Number);
		deleteAreas(Number);
		deleteComment(Number);
		SM.messageBox("Kalkyl borttagen!");
	}
	
}
