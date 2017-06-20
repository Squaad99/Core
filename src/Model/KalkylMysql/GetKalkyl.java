package Model.KalkylMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import Model.Objects.ObjectClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class GetKalkyl {
	
	// Databas variablar
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	public GetKalkyl() {
		
	}
	
	public boolean checkConnection(){
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM kalkyler";
			rs = st.executeQuery(s);

		} catch (Exception e) {
			SM.messageBox("Kunde inte hitta databasen kontakta support!");
			return false;
		}
		return true;	
	}
	
	public boolean checkKalkylNumber(String Number){
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM kalkyler";
			rs = st.executeQuery(s);

			int requestInsertValue = Integer.parseInt(Number);

			while (rs.next()) {
				int Id = rs.getInt("KalkylID");

				if (Id == requestInsertValue) {
					SM.messageBox("Detta Kalkyl Nr finns redan i databasen!");
					return false;
				}
			}
		} catch (Exception e) {
			SM.messageBox("Kunde inte hitta databasen kontakta support! Kolla ID");
			return false;
		}
		
		return true;
	}

	public String KalkylNr = "null";
	public String KalkylName = "null";
	public String KalkylAddress = "null";
	public String KalkylType = "null";
	public String KalkylCreator = "null";
	public String KalkylDateCreated = "null";
	
	public boolean setMainInfo(int Number){
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Kalkyler where KalkylID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				KalkylNr = rs.getString("KalkylID");
				KalkylName = rs.getString("Name");
				KalkylAddress = rs.getString("Address");
				KalkylType = rs.getString("Typ");
				KalkylCreator = rs.getString("Creator");
				KalkylDateCreated = rs.getString("DateKalkyl");
			}
		} catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! MainInfo");
			return false;
		}	
		return true;
	}
	
	public String[] Area = new String[6];
		
	public boolean setAreaData(int Number){
		
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM kalkylareas where KalkylID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Area[0] = rs.getString("Item1");
				Area[1] = rs.getString("Item2");
				Area[2] = rs.getString("Item3");
				Area[3] = rs.getString("Item4");
				Area[4] = rs.getString("Item5");
				Area[5] = rs.getString("Item6");
			}
		} catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! Area");
			return false;
		}
		return true;
	}
	
	public String[] Imp = new String[5];
	
	public boolean setImpData(int Number){
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM importantkalkyl where KalkylID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Imp[0] = rs.getString("Item1");
				Imp[1] = rs.getString("Item2");
				Imp[2] = rs.getString("Item3");
				Imp[3] = rs.getString("Item4");
				Imp[4] = rs.getString("Item5");
			}

		} catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! Important");
			return false;
		}
		return true;
	}
	
	public String Comment;
	
	public boolean setCommentData(int Number){
		
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM kalkylcomment where KalkylID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Comment = rs.getString("KalkylComment");
			}

		} catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! Comment");
			return false;
		}
		return true;
	}
	
	public ObservableList<ObjectClient> ClientList = FXCollections.observableArrayList();
	
	public boolean setClientData(int Number){
		
		ClientList.clear();
		
		String clientName = "null";
		String clientMail = "null";
		String clientPhone = "null";
		String contactFullName = "null";
		String contactMail = "null";
		String contactPhone = "null";
		String dateExpire = "null";

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM kalkylclient where KalkylID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {

				clientName = rs.getString("ClientName");
				clientMail = rs.getString("ClientMail");
				clientPhone = rs.getString("ClientPhone");
				contactFullName = rs.getString("ContactFullName");
				contactMail = rs.getString("ContactMail");
				contactPhone = rs.getString("ContactPhone");
				dateExpire = rs.getString("DateExpire");

				ClientList.add(new ObjectClient(clientName, clientMail, clientPhone, contactFullName, contactMail, contactPhone, dateExpire));
			}
		} catch (Exception e) {
			SM.messageBox("Det gick inte att hitta datasen! Client");
			return false;
		}
		return true;
	}
	
	public boolean setAllData(int Number){
		if(setMainInfo(Number)== false){
			return false;
		}
		if(setAreaData(Number)== false){
			return false;
		}
		if(setImpData(Number)== false){
			return false;
		}
		if(setCommentData(Number)== false){
			return false;
		}
		if(setClientData(Number) == false){
			return false;
		}
		return true;
	}
	
	
	
}
