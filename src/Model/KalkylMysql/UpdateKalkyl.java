package Model.KalkylMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Model.SM;
import Model.Setting;

public class UpdateKalkyl {

	public UpdateKalkyl() {
	}
	
	public void updateMainInfo(String Number, String Name, String Address, String Type){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update kalkyler Set Name = '" + Name + "', Address = '"
					+ Address + "', Typ = '" + Type + "' where KalkylID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Uppdatering klar!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'MainInfo'!");
		}
	}
	
	public void updateAreas(String Number, String[] Areas){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update kalkylareas Set Item1 = '" + Areas[0] + "', Item2 = '" + Areas[1] + "', Item3 = '" + Areas[2]
					+ "', Item4 = '" + Areas[3] + "', Item5 = '" + Areas[4] + "', Item6 = '" + Areas[5] + "' where KalkylID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Område Uppdaterad!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'MainInfo'!");
		}
	}

	public void updateImportantList(String Number, String[] Imp){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update importantkalkyl Set Item1 ='" + Imp[0] + "', Item2 ='" + Imp[1]
					+ "' , Item3 ='" + Imp[2] + "' , Item4 = '" + Imp[3] + "' , Item5 = '" + Imp[4] + "' "
					+ "where KalkylID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Uppdatering klar!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'ImportantList'!");
		}
	}
	
	public void updateComment(String Number, String Comment){
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "Update kalkylcomment Set KalkylComment ='" + Comment + "' where KalkylID='" + Number + "'";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Uppdatering klar!");
		} catch (Exception e) {
			SM.messageBox("Fel vid uppdatering av 'Comment'!");
		}
	}
	
}
