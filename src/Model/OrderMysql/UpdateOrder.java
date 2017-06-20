package Model.OrderMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.text.StyleContext.SmallAttributeSet;

import Model.SM;
import Model.Setting;

public class UpdateOrder {

	public UpdateOrder() {
	}

	public static void updateMainInfo(String Number, String Name, String Address, String Type) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser,
					Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update Orders Set Name = '" + Name + "', Address = '" + Address + "', Typ = '" + Type
					+ "' WHERE OrderID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Uppdatering klar!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'MainInfo'!");
		}
	}

	public static void updateAreas(String Number, String[] Area) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser,
					Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update orderareas Set Item1 = '" + Area[0] + "', Item2 = '" + Area[1] + "', Item3 = '"
					+ Area[2] + "', Item4 = '" + Area[3] + "', Item5 = '" + Area[4] + "', Item6 = '" + Area[5]
					+ "' where OrderID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Område Uppdaterad!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'MainInfo'!");
		}
	}

	public static void updateProductionMontage(String Number, String StartP, String StopP, String HoursP, String StartM,
			String StopM, String HoursM) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser,
					Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update OrderProductionDate Set VeckaStartP = '" + StartP + "',  VeckaStopP = '" + StopP
					+ "', VeckaStartM = '" + StartM + "', VeckaStopM = '" + StopM + "', ProductionTime = '" + HoursP
					+ "', MontageTime = '" + HoursM + "' WHERE OrderID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();

			SM.messageBox("Uppdatering klar!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'Production Montage'!");
		}
	}

	public static void updateImportantList(String Number, String[] Imp) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update importantorder Set Item1 ='" + Imp[0] + "', Item2 ='" + Imp[1] + "' , Item3 ='"
					+ Imp[2] + "' , Item4 = '" + Imp[3] + "' , Item5 = '" + Imp[4] + "' where OrderID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Viktigt listan uppdaterad!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'viktig listan'!");
		}
	}

	public static void updateOrderList(String Number, String[] Order){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update orderlist Set Item1 ='" + Order[0] + "', Item2 ='" + Order[1] + "', Item3 ='"
					+ Order[2] + "', Item4 = '" + Order[3] + "', Item5 = '" + Order[4] + "' where OrderID='"
					+ Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Beställning uppdaterad!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'Beställning'!");
		}
	}
	
	public static void updateComment(String Number, String Comment){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update ordercomment Set OrderComment ='" + Comment + "' where OrderID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Kommentar uppdaterad!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'Kommentar'!");
		}
	}

	public static void updateActive(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update Orders Set STATUS ='Active' where OrderID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Status för Order har ändrats till aktiv!");
		} catch (Exception e) {
			SM.messageBox("Status Order fel");
		}
	}
	
	public static void updateInActive(String Number){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update Orders Set STATUS ='InActive' where OrderID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Status för Order har ändrats till inaktiv!");
		} catch (Exception e) {
			SM.messageBox("Status Order fel");
		}
	}
}
