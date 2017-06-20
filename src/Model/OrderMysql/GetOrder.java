package Model.OrderMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import javafx.scene.paint.Color;

public class GetOrder {
	
	
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	public GetOrder() {
		
	}
	
	public boolean checkOrderNumber(String Number){
		
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orders";
			rs = st.executeQuery(s);

			int requestInsertValue = Integer.parseInt(Number);

			while (rs.next()) {
				int Id = rs.getInt("OrderID");

				if (Id == requestInsertValue) {
					SM.messageBox("Detta Order Nr finns redan i databasen!");
					return false;
				}
			}
		} catch (Exception e) {
			SM.messageBox("Kunde inte hitta databasen kontakta support! Kolla ID");
			return false;
		}
		
		return true;
	}

	public String OrderNr;
	public String OrderName;
	public String OrderAddress;
	public String OrderType;
	public String OrderCreator;
	public String CustomerId;
	public String ContactPersonId;
	public String OrderDateCreated;
	public String KalkylDateCreated;
	
	public String Customer;
	public String CustomerPhone;
	public String CustomerEmail;
	public String CustomerWeb;
	
	public String ContactName;
	public String ContactPhone;
	public String ContactEmail;
	
	
	public boolean  setMainInfoAndCustomer(int Number){
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Orders where OrderID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				OrderNr = rs.getString("OrderID");
				OrderName = rs.getString("Name");
				OrderAddress = rs.getString("Address");
				OrderCreator = rs.getString("Creator");
				OrderType = rs.getString("Typ");
				CustomerId = rs.getString("KundID");
				ContactPersonId = rs.getString("KontaktPersonID");
				OrderDateCreated = rs.getString("DateOrder");
				KalkylDateCreated = rs.getString("DateKalkyl");
			}
			

			try {
				con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
				st = con.createStatement();
				s = "SELECT * FROM customer where CustomerId = '" + CustomerId + "'";
				rs = st.executeQuery(s);

				while (rs.next()) {
					Customer = (rs.getString("CompanyName"));
					CustomerPhone = (rs.getString("CompanyTeleNr"));
					CustomerEmail = (rs.getString("CompanyEmail"));
					CustomerWeb = (rs.getString("CompanyWeb"));
				}
			} catch (Exception e) {
				SM.messageBox("Fail att hämta kund");
				return false;
			}
			try {
				con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
				st = con.createStatement();
				s = "SELECT * FROM customercontact where PersonId = '" + ContactPersonId + "'";
				rs = st.executeQuery(s);

				while (rs.next()) {
					ContactName = (rs.getString("Name") + " " + rs.getString("LastName"));
					ContactPhone = (rs.getString("TeleNr"));
					ContactEmail = (rs.getString("Mail"));
				}
			} catch (Exception e) {
				SM.messageBox("Fel Kontaktperson");
				return false;
			}
		} catch (Exception e) {
			SM.messageBox("Fail att hämta main info order");
			return false;

		}
		return true;
	}
	
	public String[] Area = new String[6]; 
	
	public boolean setAreasList(int Number){
		
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orderareas where OrderID = '" + Number + "'";
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
			SM.messageBox("När områden skullle läsas in blev det fel!");
			return false;
		}
		return true;
	}
	
	public String[] Imp = new String[5];
	
	public boolean setImportantList(int Number){
		
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM importantorder where OrderID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Imp[0] = rs.getString("Item1");
				Imp[1] = rs.getString("Item2");
				Imp[2] = rs.getString("Item3");
				Imp[3] = rs.getString("Item4");
				Imp[4] = rs.getString("Item5");
			}

		} catch (Exception e) {
			SM.messageBox("Fel att ladda viktigt SQL");
			return false;
		}	
		return true;
	}
	
	public String[] Order = new String[5];
	
	public boolean setOrdetList(int Number){
		
		try {

			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orderlist where OrderID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Order[0] = rs.getString("Item1");
				Order[1] = rs.getString("Item2");
				Order[2] = rs.getString("Item3");
				Order[3] = rs.getString("Item4");
				Order[4] = rs.getString("Item5");
			}
		} catch (Exception e) {
			SM.messageBox("Gick inte att hämta OrderLista");
			return false;
		}
		return true;
	}

	public String ProductionStart;
	public String ProductionStop;
	public String ProductionTime;
	public String MontageStart;
	public String MontageStop;
	public String MontageTime;
	
	public boolean setProductionMontage(int Number){
		
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orderproductiondate where OrderID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				
				ProductionStart = rs.getString("VeckaStartP");
				ProductionStop = rs.getString("VeckaStopP");
				ProductionTime = rs.getString("ProductionTime");
			
				MontageStart = rs.getString("VeckaStartM");
				MontageStop = rs.getString("VeckaStopM");
				MontageTime = rs.getString("MontageTime");
			}
		} catch (Exception e) {
			SM.messageBox("Mysql Fel Production Montage");
			return false;
		}
		return true;
	}
	
	public float ProductionTimeDone = 0;
	public float MontageTimeDone = 0;
	
	public boolean setDoneHours(int Number){
		
		ProductionTimeDone = 0;
		MontageTimeDone = 0;
		
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM timesave where OrderId = '" + Number + "'";
			rs = st.executeQuery(s);

			String beforeValue = "";
			String afterValue = "";

			
			while (rs.next()) {
				
				if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-70")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-71")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-72")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-73")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-74")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-75")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-76")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-77")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-78")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-79")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				else if(rs.getString("WorkTyp").substring(0, 3).equalsIgnoreCase("-80")){
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					MontageTimeDone += Float.parseFloat(afterValue);
				}
				//PRODUCTION
				else{
					beforeValue = rs.getString("Hours");
					afterValue = beforeValue.replace(",",".");
					ProductionTimeDone += Float.parseFloat(afterValue);
				}
			}
		
		}
		catch(Exception e){
			SM.messageBox("Mysql fel när du försökte hämta timmar för ett projekt");
			return false;
		}
		
		return true;
	}

	public String Comment;
	
	public boolean setComment(int Number){
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM ordercomment where OrderID = '" + Number + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Comment = rs.getString("OrderComment");
			}
		} catch (Exception e) {
			SM.messageBox("Gick inte att hitta order kommentar");
			return false;
		}
		return true;
	}
	
	public boolean setAllOrder(int Number){
		if(setMainInfoAndCustomer(Number) == false){
			return false;
		}
		
		if(setAreasList(Number) == false){
			return false;
		}

		if(setImportantList(Number)== false){
			return false;
		}

		if(setOrdetList(Number) == false){
			return false;
		}

		if(setProductionMontage(Number)== false){
			return false;
		}

		if(setDoneHours(Number)== false){
			return false;
		}

		if(setComment(Number)== false){
			return false;
		}
		
		return true;
	}
	
	
	
	
	
}
