package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;

public class SM {

	static Connection con = null;
	static Statement st = null;
	static ResultSet rs = null;
	static String s;

	public static void messageBox(String Text) {
		Alert alertBox = new Alert(AlertType.INFORMATION);
		alertBox.setTitle("Core*");
		alertBox.setHeaderText(null);
		alertBox.setContentText(Text);
		alertBox.showAndWait();
	}

	public static boolean confirmationBox(String Text){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Core*");
		alert.setHeaderText(null);
		alert.setContentText(Text);
		Optional<ButtonType> action = alert.showAndWait();
		if (action.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void setCustomerList(ComboBox<String> CBox) {
		CBox.getItems().clear();
		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Customer";
			rs = st.executeQuery(s);

			while (rs.next()) {
				String name = rs.getString("CompanyName");
				String input = name;
				CBox.getItems().addAll(input);
			}
			CBox.getItems().sort(null);
		} catch (Exception e) {
			SM.messageBox("Mysql Fel Customer");
		}

	}

	public static int getSelectedCustomerId(ComboBox<String> CBox) {
		int Id = 0;

		if (CBox.getSelectionModel().isEmpty()) {
			return (Integer) null;
		}

		String companyName = CBox.getValue().toString();

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Customer where CompanyName = '" + companyName + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Id = rs.getInt("CustomerId");
			}
		} catch (Exception e) {
			SM.messageBox("Kunde inte hitta databasen kontakta support!");
		}
		return Id;
	}

	public static int getSelectedPersonId(ComboBox<String> combobox) {
		int Id = 0;
		String selectedValue = (String) combobox.getValue();

		String förnamn = selectedValue.substring(0, selectedValue.indexOf(" "));
		String efternamn = selectedValue.substring(selectedValue.indexOf(" ")).trim();

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM CustomerContact where Name = '" + förnamn + "' AND LastName = '" + efternamn + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Id = rs.getInt("PersonId");
			}
		} catch (Exception e) {
			SM.messageBox("Kunde inte hitta databasen kontakta support!");
		}
		return Id;
	}

	public static void setContactPersonBox(ComboBox<String> ComboBoxGetValue, ComboBox<String> combobox) {

		combobox.getItems().clear();
		int Id = 0;

		String valueComboBox = (String) ComboBoxGetValue.getValue();

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Customer where CompanyName = '" + valueComboBox + "'";
			rs = st.executeQuery(s);

			while (rs.next()) {
				Id = rs.getInt("CustomerId");
			}
			try {
				con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
				st = con.createStatement();
				s = "SELECT * FROM CustomerContact where CustomerId = '" + Id + "'";
				rs = st.executeQuery(s);

				while (rs.next()) {
					String Name = rs.getString("Name");
					String LastName = rs.getString("LastName");
					String input = Name + " " + LastName;
					combobox.getItems().addAll(input);
				}

			} catch (Exception e) {
				SM.messageBox("Kontakta support! MYSQL FEL");
			}
		} catch (Exception e) {
			SM.messageBox("Kontakta support! MYSQL FEL");
		}

	}

	public static void setYearComboBox(ComboBox<String> cbox) {
		cbox.getItems().clear();
		Calendar mCalendar = new GregorianCalendar();
		int i = 1;
		String year = "" + mCalendar.get(Calendar.YEAR);
		cbox.setValue(year);
		while (i <= 5) {
			year = "" + (mCalendar.get(Calendar.YEAR) - 1 + i);
			cbox.getItems().add(year);
			i++;
		}
	}

	public static void setWeekCombo(ComboBox<String> cbox) {
		
		cbox.getItems().clear();
		Calendar mCalendar = new GregorianCalendar();
		int i = mCalendar.get(Calendar.WEEK_OF_YEAR);
		int x = mCalendar.getWeeksInWeekYear();
		String currentweek = "" + i;
		cbox.setValue(currentweek);
		while (i <= x) {
			String addweek = "" + i;
			cbox.getItems().add(addweek);
			i++;
		}
	}

	public static void setYearForSelectedValue(ComboBox<String> CboxValue, ComboBox<String> CboxDisplay) {
		
		int year = Integer.parseInt(CboxValue.getValue().toString());
		System.out.println(year);
		LocalDate date = LocalDate.now();
		int currentYear = date.getYear();

		if (year == currentYear) {
			setWeekCombo(CboxDisplay);
		} else {
			CboxDisplay.getItems().clear();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);

			int weeks = cal.getWeeksInWeekYear();
			
			int valueX = 0+1;
			String StringValueX = Integer.toString(valueX);

			CboxDisplay.setValue(StringValueX);

			for (int i = 0; i < weeks; i++) {
				int valueY = i + 1;
				String StringValueY = Integer.toString(valueY);
				CboxDisplay.getItems().add(StringValueY);
			}
		}
	}

}
