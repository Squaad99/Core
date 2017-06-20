package Model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.SM;
import Model.Setting;
import javafx.scene.control.ComboBox;

public class CustomerMYSQL {

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	public CustomerMYSQL() {
		// EMPTY
	}

	public boolean insertCustomer(String Name, String Address, String Web, String Tele, String Email, String OrgNr) {
		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser,
					Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();

			String sql = "insert into customer"
					+ " (CompanyName, CompanyAdress, CompanyWeb, CompanyTeleNr, CompanyEmail, CompanyOrgNr)"
					+ " values ('" + Name + "', '" + Address + "', '" + Web + "', " + "'" + Tele + "', '" + Email
					+ "', '" + OrgNr + "')";

			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Ny kund inlagd!");
		} catch (Exception e) {
			SM.messageBox("Fel när du försökte göra insert till MYSQL Customer");
			return false;
		}
		return true;
	}

	public boolean checkCustomerName(String NameInput) {

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Customer";
			rs = st.executeQuery(s);

			while (rs.next()) {
				String name = rs.getString("CompanyName");

				if (name.equalsIgnoreCase(NameInput)) {
					SM.messageBox("Kund med detta namn finns redan inlagt! Kunder får ej ha samma namn!");
					return false;
				}
			}
		} catch (Exception e) {
			SM.messageBox("Kunde inte hitta databasen kontakta support!");
			return false;
		}

		return true;
	}

	public boolean insertContactPerson(int Id, String Name, String LastName, String Phone, String Mail) {

		try {
			Connection myConn = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			Statement myStmt = myConn.createStatement();
			String sql = "insert into CustomerContact" + " (CustomerId, Name, LastName, TeleNr, Mail)" + " values "
					+ "('"+ Id + "', '" + Name + "', '" + LastName+ "', '" + Phone + "', '" + Mail + "')";
			myStmt.executeUpdate(sql);
			myStmt.close();
			SM.messageBox("Ny kontaktperson tillagd!");
		} catch (Exception exc) {
			SM.messageBox("Kunde inte hitta databasen kontakta support!");
			return false;
		}

		return true;
	}

}
