package Model.KalkylMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;

public class InsertKalkyl {
	
	static Connection con = null;
	static Statement st = null;
	static ResultSet rs = null;
	static String s;

	public InsertKalkyl() {
		//EMPTY CONSTRUCTOR
	}
	
	public boolean insertMainInfo(String Number, String Name, String Adress, String TypeInsert) {
		//In MAININFO Type is also inserted
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();

			String sql = "insert into Kalkyler" + " (KalkylID, Name, Address, Creator, Typ, DateKalkyl)" + " values ('"
					+ Number + "', '" + Name + "', '" + Adress + "', '"+Setting.User+"', '"
					+ TypeInsert + "', '" + SM.getDate() + "')";
			
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till MYSQL MainInfo");
			return false;
		}
		return true;
	}
	
	public static boolean insertClients(int CustomerId, int PersonId, String NumberId, String Date){
		
		String CustomerName = null;
		String CustomerMail = null;
		String CustomerPhone = null;
		String ContactName = null;
		String ContactLastName = null;
		String ContactFullName = null;
		String ContactMail = null;
		String ContactPhone = null;

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Customer where CustomerId = '" + CustomerId + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				CustomerName = rs.getString("CompanyName");
				CustomerMail = rs.getString("CompanyEmail");
				CustomerPhone = rs.getString("CompanyTeleNr");
			}

			st.close();
			rs.close();
			con.close();

			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Customercontact where PersonId = '" + PersonId + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				ContactName = rs.getString("Name");
				ContactLastName = rs.getString("LastName");
				ContactPhone = rs.getString("TeleNr");
				ContactMail = rs.getString("Mail");
			}

			st.close();
			rs.close();
			con.close();

			ContactFullName = ContactName + " " + ContactLastName;

			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);

			Statement myStmt = myConn.createStatement();

			String sql = "insert into KalkylClient"
					+ " (KalkylID, ClientName, ClientMail, ClientPhone, ContactFullName, ContactMail, ContactPhone, DateExpire)"
					+ " values ('" + NumberId + "', '" + CustomerName + "', '" + CustomerMail + "', '"
					+ CustomerPhone + "', '" + ContactFullName + "', '" + ContactMail + "', '" + ContactPhone + "',"
					+ " '"+ Date + "')";

			myStmt.executeUpdate(sql);
			myStmt.close();

		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till MYSQL Clients");
			return false;
		}
		
		return true;
	}
	
	public boolean insertAreas(String Number, String area1, String area2, String area3, String area4, String area5, String area6){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into  KalkylAreas" + " (KalkylID, Item1, Item2, Item3, Item4, Item5, Item6)"
					+ " values ('" + Number + "', '" + area1 + "', '" + area2 + "', '" + area3 + "', '"
					+ area4 + "', '" + area5 + "', '" + area6 + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till MYSQL Area");
			return false;
		}
		return true;
	}

	public boolean insertImportant(String Number, String imp1 , String imp2, String imp3, String imp4, String imp5){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into ImportantKalkyl" + " (KalkylID, Item1, Item2, Item3, Item4, Item5)"
					+ " values ('" + Number + "', '" + imp1 + "', '" + imp2 + "', '" + imp3 + "', '" + imp4	+ "', '" + imp5 + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till MYSQL Important");
			return false;
		}
		
		return true;
	}
	
	public boolean insertComment(String Number, String Comment){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into KalkylComment" + " (KalkylID, KalkylComment)" + " values ('" + Number+ "', '" + Comment + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till MYSQL Comment");
			return false;
		}
		return true;
	}
}
