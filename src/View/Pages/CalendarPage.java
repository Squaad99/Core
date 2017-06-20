package View.Pages;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import Model.SM;
import Model.Setting;
import View.Windows.NormalWindow;
import View.Windows.SmallWindow;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class CalendarPage {

	// Vairablar för databasen
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String s;

	public NormalWindow Window;
	Pane WeekPane;
	Label[] Label;

	CalendarPage cal;
	LocalDate date;

	public CalendarPage() {

		Window = new NormalWindow("Kalender");

		WeekPane = new Pane();
		WeekPane.relocate(Setting.CurrentWidth * 0, Setting.CurrentHeight * 0.16);
		WeekPane.setPrefSize(Setting.CurrentWidth * 0.9, Setting.CurrentHeight * 0.8);
		Window.MainPane.getChildren().add(WeekPane);

		Label Header = new Label("Kalender");
		Header.setStyle("-fx-font-size: 40pt;");
		Header.relocate((Setting.CurrentWidth * 0.02), (Setting.CurrentHeight * 0));
		Window.MainPane.getChildren().add(Header);

		Label Header1 = new Label("Välj år:");
		Header1.setStyle("-fx-font-size: 30pt;");
		Header1.relocate((Setting.CurrentWidth * 0.02), (Setting.CurrentHeight * 0.07));
		Window.MainPane.getChildren().add(Header1);

		ComboBox<Integer> YearBox = new ComboBox<Integer>();
		YearBox.setStyle("-fx-font-size: 16pt");
		YearBox.setPrefWidth(Setting.CurrentWidth * 0.1);
		YearBox.relocate((Setting.CurrentWidth * 0.09), (Setting.CurrentHeight * 0.0775));
		Window.MainPane.getChildren().add(YearBox);

		YearBox.setOnAction(e -> {
			setWeeks(YearBox.getValue());
			setActiveOrdersHours(YearBox.getValue());
		});

		date = LocalDate.now();
		for (int i = 0; i < 5; i++) {
			YearBox.getItems().add((date.getYear() + i));
		}
		YearBox.setValue(date.getYear());

		Button BtnUpdate = new Button("Uppdatera");
		BtnUpdate.setPrefSize((Setting.CurrentWidth * 0.08), (Setting.CurrentHeight * 0.0325));
		BtnUpdate.relocate((Setting.CurrentWidth * 0.81), (Setting.CurrentHeight * 0.02));
		BtnUpdate.setStyle("-fx-font-size: 16pt");
		BtnUpdate.setOnAction(e -> {
			setWeeks(YearBox.getValue());
			setActiveOrdersHours(YearBox.getValue());
		});
		Window.MainPane.getChildren().add(BtnUpdate);

		Label Header2 = new Label("Produktion");
		Header2.setStyle("-fx-font-size: 30pt; -fx-underline: true");
		Header2.relocate((Setting.CurrentWidth * 0.22), (Setting.CurrentHeight * 0.01));
		Window.MainPane.getChildren().add(Header2);

		Label Header3 = new Label("Montage");
		Header3.setStyle("-fx-font-size: 30pt; -fx-underline: true");
		Header3.relocate((Setting.CurrentWidth * 0.7), (Setting.CurrentHeight * 0.01));
		Window.MainPane.getChildren().add(Header3);

		Label = new Label[5];
		for (int i = 0; i < 5; i++) {
			Label[i] = new Label("Test");
			Label[i].setStyle("-fx-font-size: 20pt;");
			Label[i].relocate((Setting.CurrentWidth * (0.22 + (i * 0.12))), (Setting.CurrentHeight * 0.07));
			Window.MainPane.getChildren().add(Label[i]);
		}

		setWeeks(date.getYear());
		setActiveOrdersHours(date.getYear());

	}

	Button[] btn;
	Integer[] ProductionHours;
	Integer[] MontageHours;

	void setWeeks(int year) {

		WeekPane.getChildren().clear();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		btn = new Button[cal.getWeeksInWeekYear()];
		ProductionHours = new Integer[cal.getWeeksInWeekYear()];
		MontageHours = new Integer[cal.getWeeksInWeekYear()];

		int xwidth = 0;
		int yheigth = 0;
		int count = 0;

		for (int i = 0; i < cal.getWeeksInWeekYear(); i++) {
			count++;
			xwidth = (int) (xwidth + (Setting.CurrentWidth * 0.08));

			btn[i] = new Button("Vecka " + (i + 1));
			btn[i].setPrefSize((Setting.CurrentWidth * 0.07), (Setting.CurrentHeight * 0.12));
			btn[i].relocate((xwidth - Setting.CurrentWidth * 0.06), yheigth);
			btn[i].setStyle(

					"-fx-background-radius: 15pt; -fx-border-color: #000000; -fx-border-width: 2pt; -fx-border-radius: 15pt; -fx-font-size: 16pt; -fx-text-fill: black;");

			WeekPane.getChildren().add(btn[i]);
			ProductionHours[i] = new Integer(0);
			MontageHours[i] = new Integer(0);

			btn[i].setText(
					"Vecka " + (i + 1) + '\n' + "P:      " + ProductionHours[i] + '\n' + "M:     " + MontageHours[i]);

			if (count > 10) {
				xwidth = 0;
				yheigth += Setting.CurrentHeight * 0.14;
				count = 0;
			}

			final int d = i + 1;

			btn[i].setOnAction(e -> {
				weekWindow(d, year);
			});
		}
		
		Label[] DescLabel = new Label[6];
		
		for(int i = 0; i <6; i++){
			DescLabel[i] = new Label("Test");
			DescLabel[i].setStyle("-fx-font-size: 18pt");
			DescLabel[i].relocate((Setting.CurrentWidth * (0.03 + ( 0.14 * i))), (Setting.CurrentHeight * 0.7));
			WeekPane.getChildren().add(DescLabel[i]);
		}

		DescLabel[0].setText("Produktion antal: " + Setting.WorkersInside);
		DescLabel[1].setText("Timmar per vecka: " + Setting.insideHoursPerWeek);
		DescLabel[2].setText("Produktion tot: " + (Setting.WorkersInside * Setting.insideHoursPerWeek) + "(h)");
		DescLabel[3].setText("Montage antal: " + Setting.WorkersOutside);
		DescLabel[4].setText("Timmar per vecka: " + Setting.outsideHoursPerWeek);
		DescLabel[5].setText("Montage tot: " + (Setting.WorkersOutside * Setting.outsideHoursPerWeek) + "(h)");
		
		
		
		
		setTime(year);
	}

	void setTime(int year) {

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orderproductiondate";
			rs = st.executeQuery(s);

			int startPWeek;
			int startPYear;

			int stopPWeek;
			int stopPYear;

			int startMWeek;
			int startMYear;

			int stopMWeek;
			int stopMYear;

			int productionTime;
			int montageTime;

			while (rs.next()) {

				// PRODUCTION
				if (rs.getString("ProductionTime").equalsIgnoreCase("false")) {
					// Nothing
				} else {
					// SET PRODUCTION VARIABLES
					productionTime = Integer.parseInt(rs.getString("ProductionTime"));
					startPWeek = Integer.parseInt(rs.getString("VeckaStartP").substring(5));
					startPYear = Integer.parseInt(rs.getString("VeckaStartP").substring(0, 4));
					stopPWeek = Integer.parseInt(rs.getString("VeckaStopP").substring(5));
					stopPYear = Integer.parseInt(rs.getString("VeckaStopP").substring(0, 4));
					// Kolla om det är på valt år
					if (year == startPYear) {
						// KOLLA OM DET ÄR SAMMA ÅR
						if (startPYear == stopPYear) {
							// KOLLA OM DET ÄR SAMMA VEKCA
							if (startPWeek == stopPWeek) {
								ProductionHours[startPWeek - 1] += productionTime;
							} else {
								int numberOfProductionWeeksFirst = (stopPWeek - startPWeek) + 1;
								int perWeek = productionTime / numberOfProductionWeeksFirst;
								for (int i = (startPWeek - 1); i < (stopPWeek); i++) {
									ProductionHours[i] += perWeek;
								}
							}
						} else {
							Calendar cald = Calendar.getInstance();
							cald.set(Calendar.YEAR, year);

							int weeksFirstYear = cald.getWeeksInWeekYear() - startPWeek + 1;
							int weeksSecondYear = stopPWeek;
							int totalWeeks = weeksFirstYear + weeksSecondYear;
							int perWeek = productionTime / totalWeeks;
							for (int i = (startPWeek - 1); i < cald.getWeeksInWeekYear(); i++) {
								ProductionHours[i] += perWeek;
							}
						}
					} else if (year == stopPYear) {
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.YEAR, (year - 1));

						int weeksFirstYear = cal.getWeeksInWeekYear() - startPWeek + 1;
						int weeksSecondYear = stopPWeek;
						int totalWeeks = weeksFirstYear + weeksSecondYear;
						int perWeek = productionTime / totalWeeks;
						for (int i = 0; i < stopPWeek; i++) {
							ProductionHours[i] += perWeek;
						}
					}
				}

				// MONTAGE
				if (rs.getString("MontageTime").equalsIgnoreCase("false")) {
					// Nothing
				} else {
					// SET PRODUCTION VARIABLES
					montageTime = Integer.parseInt(rs.getString("MontageTime"));
					startMWeek = Integer.parseInt(rs.getString("VeckaStartM").substring(5));
					startMYear = Integer.parseInt(rs.getString("VeckaStartM").substring(0, 4));
					stopMWeek = Integer.parseInt(rs.getString("VeckaStopM").substring(5));
					stopMYear = Integer.parseInt(rs.getString("VeckaStopM").substring(0, 4));
					// Kolla om det är på valt år
					if (year == startMYear) {
						// KOLLA OM DET ÄR SAMMA ÅR
						if (startMYear == stopMYear) {
							// KOLLA OM DET ÄR SAMMA VEKCA
							if (startMWeek == stopMWeek) {
								MontageHours[startMWeek - 1] += montageTime;
							} else {
								int numberOfProductionWeeksFirst = (stopMWeek - startMWeek) + 1;
								int perWeek = montageTime / numberOfProductionWeeksFirst;
								for (int i = (startMWeek - 1); i < (stopMWeek); i++) {
									MontageHours[i] += perWeek;
								}
							}
						} else {
							Calendar cald = Calendar.getInstance();
							cald.set(Calendar.YEAR, year);

							int weeksFirstYear = cald.getWeeksInWeekYear() - startMWeek + 1;
							int weeksSecondYear = stopMWeek;
							int totalWeeks = weeksFirstYear + weeksSecondYear;
							int perWeek = montageTime / totalWeeks;
							for (int i = (startMWeek - 1); i < cald.getWeeksInWeekYear(); i++) {
								MontageHours[i] += perWeek;
							}
						}
					} else if (year == stopMYear) {
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.YEAR, (year - 1));

						int weeksFirstYear = cal.getWeeksInWeekYear() - startMWeek + 1;
						int weeksSecondYear = stopMWeek;
						int totalWeeks = weeksFirstYear + weeksSecondYear;
						int perWeek = montageTime / totalWeeks;
						for (int i = 0; i < stopMWeek; i++) {
							MontageHours[i] += perWeek;
						}
					}
				}
			}
		} catch (Exception e) {
			SM.messageBox("Fel mysql");
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		// Set Text and Colors
		for (int i = 0; i < cal.getWeeksInWeekYear(); i++) {

			float CP = ProductionHours[i];
			float CM = MontageHours[i];

			float productionPercent = (CP / (Setting.WorkersInside * Setting.insideHoursPerWeek) * 100);
			float montagePercent = (CM / (Setting.WorkersOutside * Setting.outsideHoursPerWeek) * 100);

			DecimalFormat df = new DecimalFormat("##.#");
			df.setRoundingMode(RoundingMode.UP);

			btn[i].setText("Vecka " + (i + 1) + '\n' + "P:  " + df.format(productionPercent) + "%" + '\n' + "M: "
					+ df.format(montagePercent) + "%");

			if (productionPercent == 0) {
				btn[i].getStyleClass().add("button-Empty");
			} else if (productionPercent > 0 && productionPercent <= 25) {
				btn[i].getStyleClass().add("button-Green");
			} else if (productionPercent > 25 && productionPercent <= 50) {
				btn[i].getStyleClass().add("button-Yellow");
			} else if (productionPercent > 50 && productionPercent <= 75) {
				btn[i].getStyleClass().add("button-Orange");
			} else if (productionPercent > 75) {
				btn[i].getStyleClass().add("button-Red");
			}
		}

		if (year == date.getYear()) {

			WeekFields weekFields = WeekFields.of(Locale.getDefault());
			btn[date.get(weekFields.weekOfWeekBasedYear()) - 1].getStyleClass().add("button-Current");

			for (int i = 0; i < date.get(weekFields.weekOfWeekBasedYear()) - 1; i++) {
				btn[i].getStyleClass().add("button-Passed");
			}
		}

	}

	void weekWindow(int Week, int Year) {

		SmallWindow SmallWindow = new SmallWindow("Vecka " + Week);
		SmallWindow.use();

		Label Header = new Label("Produktion");
		Header.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.005));
		SmallWindow.MainPane.getChildren().add(Header);

		ListView ProductionList = new ListView<>();
		ProductionList.setPrefSize(Setting.CurrentWidth * 0.38, Setting.CurrentHeight * 0.15);
		ProductionList.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.045);
		ProductionList.setStyle("-fx-font-size: 10pt");
		SmallWindow.MainPane.getChildren().add(ProductionList);

		Label Header1 = new Label("Montage");
		Header1.setStyle("-fx-font-size: 20pt; -fx-underline: true");
		Header1.relocate((Setting.CurrentWidth * 0.01), (Setting.CurrentHeight * 0.205));
		SmallWindow.MainPane.getChildren().add(Header1);

		ListView MontageList = new ListView<>();
		MontageList.setPrefSize(Setting.CurrentWidth * 0.38, Setting.CurrentHeight * 0.15);
		MontageList.relocate(Setting.CurrentWidth * 0.01, Setting.CurrentHeight * 0.245);
		MontageList.setStyle("-fx-font-size: 10pt");
		SmallWindow.MainPane.getChildren().add(MontageList);

		setProductionOrders(ProductionList, Week, Year);
		setMontageOrders(MontageList, Week, Year);
	}

	void setProductionOrders(ListView<String> List, int week, int CurrentYear) {

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orderproductiondate";
			rs = st.executeQuery(s);

			int startPYear;
			int stopPYear;

			int startPWeek;
			int stopPWeek;

			while (rs.next()) {
				// PRODUCTION
				if (rs.getString("ProductionTime").equalsIgnoreCase("false")) {
					// Nothing
				} else {
					// SET PRODUCTION VARIABLES
					startPWeek = Integer.parseInt(rs.getString("VeckaStartP").substring(5));
					startPYear = Integer.parseInt(rs.getString("VeckaStartP").substring(0, 4));
					stopPWeek = Integer.parseInt(rs.getString("VeckaStopP").substring(5));
					stopPYear = Integer.parseInt(rs.getString("VeckaStopP").substring(0, 4));

					Calendar cald = Calendar.getInstance();
					cald.set(Calendar.YEAR, CurrentYear);

					if (CurrentYear == startPYear) {
						if (week >= startPWeek && week <= stopPWeek) {
							List.getItems().add(rs.getString("OrderID") + " --- Total tid: "
									+ rs.getString("ProductionTime") + " timmar");
						}

						else if (CurrentYear != stopPYear) {
							if (week >= startPWeek && week <= cald.getWeeksInWeekYear()) {
								List.getItems().add(rs.getString("OrderID") + " --- Total tid: "
										+ rs.getString("ProductionTime") + " timmar");
							}
						}
					} else if (CurrentYear == stopPYear) {
						if (week >= 1 && week <= stopPWeek) {
							List.getItems().add(rs.getString("OrderID") + " --- Total tid: "
									+ rs.getString("ProductionTime") + " timmar");
						}
					}

				}
			}
		} catch (Exception e) {
			SM.messageBox("Mysql Fel");
		}
	}

	void setMontageOrders(ListView<String> List, int week, int CurrentYear) {

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orderproductiondate";
			rs = st.executeQuery(s);

			int startMWeek;
			int startMYear;

			int stopMWeek;
			int stopMYear;

			while (rs.next()) {
				// MONTAGE
				if (rs.getString("MontageTime").equalsIgnoreCase("false")) {
					// Nothing
				} else {
					// SET PRODUCTION VARIABLES
					startMWeek = Integer.parseInt(rs.getString("VeckaStartM").substring(5));
					startMYear = Integer.parseInt(rs.getString("VeckaStartM").substring(0, 4));
					stopMWeek = Integer.parseInt(rs.getString("VeckaStopM").substring(5));
					stopMYear = Integer.parseInt(rs.getString("VeckaStopM").substring(0, 4));
					
					Calendar cald = Calendar.getInstance();
					cald.set(Calendar.YEAR, CurrentYear);
					
					
					

					if (CurrentYear == startMYear) {
						
						if (week >= startMWeek && week <= stopMWeek) {
							List.getItems().add(rs.getString("OrderID") + " --- Total tid: " + rs.getString("MontageTime") + " timmar");

						}
						
						
						else if (CurrentYear != stopMYear) {
							if (week >= startMWeek && week <= cald.getWeeksInWeekYear()) {
								List.getItems().add(rs.getString("OrderID") + " --- Total tid: "
										+ rs.getString("MontageTime") + " timmar");
							}
						}
						
							
					}
					else if (CurrentYear == stopMYear) {
						
						
						
						if (week >= 1 && week <= stopMWeek) {
							List.getItems().add(rs.getString("OrderID") + " --- Total tid: "
									+ rs.getString("MontageTime") + " timmar");
						}
						
					}
					
					
					
					
					
					
					
				}
			}
		} catch (Exception e) {
			SM.messageBox("Mysql Fel");
			System.out.println(e);
		}

	}

	float SapaHours;
	float StålprofilHours;
	float StåldörrHours;
	float montage;

	void setActiveOrdersHours(int year) {

		ArrayList<String> list = new ArrayList<String>();

		SapaHours = 0;
		StålprofilHours = 0;
		StåldörrHours = 0;
		montage = 0;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		int CurrentOrder;

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM Orders where STATUS = 'Active'";
			ResultSet rs1 = st.executeQuery(s);

			while (rs1.next()) {

				if (list.contains(rs1.getString("OrderId"))) {

				} else {
					list.add(rs1.getString("OrderId"));
				}
			}
		} catch (Exception e) {
			SM.messageBox("Fel med databsen! Kontakta support1");
		}

		// ---------------- TIME PER ORDER---------------------

		int activeYear = Year.now().getValue();

		try {
			con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
			st = con.createStatement();
			s = "SELECT * FROM orderproductiondate";
			ResultSet rs2 = st.executeQuery(s);

			while (rs2.next()) {
				int Currentyear = year;

				WeekFields weekFields = WeekFields.of(Locale.getDefault());

				if (list.contains(rs2.getString("OrderId"))) {

					CurrentOrder = Integer.parseInt(rs2.getString("OrderId"));
					String type = "null";
					try {
						con = DriverManager.getConnection(Setting.MysqlConnection, Setting.MysqlUser, Setting.MysqlPW);
						st = con.createStatement();
						s = "SELECT * FROM Orders where OrderId = '" + CurrentOrder + "'";
						ResultSet rs3 = st.executeQuery(s);

						while (rs3.next()) {
							type = rs3.getString("Typ");
						}
					} catch (Exception e) {
						SM.messageBox("Mysql Fel2");
					}

					// PRODUCTION---------------------------------------------------------

					if (rs2.getString("ProductionTime").equalsIgnoreCase("false")) {
						// no production
					} else {

						int productionTime = Integer.parseInt(rs2.getString("ProductionTime"));
						int startPWeek = Integer.parseInt(rs2.getString("VeckaStartP").substring(5));
						int startPYear = Integer.parseInt(rs2.getString("VeckaStartP").substring(0, 4));
						int stopPWeek = Integer.parseInt(rs2.getString("VeckaStopP").substring(5));
						int stopPYear = Integer.parseInt(rs2.getString("VeckaStopP").substring(0, 4));

						// ----------------

						int numberOfWeeks = stopPWeek - startPWeek + 1;
						int hoursPerWeek = productionTime / numberOfWeeks;
						int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

						if (Currentyear == startPYear && Currentyear == stopPYear) {

							if (Currentyear == activeYear) {
								if (currentWeek < startPWeek) {
									if (type.equalsIgnoreCase("Sapa")) {
										SapaHours += productionTime;
									} else if (type.equalsIgnoreCase("Stålprofil")) {
										StålprofilHours += productionTime;
									} else if (type.equalsIgnoreCase("Ståldörr")) {
										StåldörrHours += productionTime;
									}
								} else if (currentWeek > stopPWeek) {
									// Nothing to add order has passed
								} else {
									int weeksLeft = stopPWeek - currentWeek + 1;

									if (type.equalsIgnoreCase("Sapa")) {
										SapaHours += (weeksLeft * hoursPerWeek);
									} else if (type.equalsIgnoreCase("Stålprofil")) {
										StålprofilHours += (weeksLeft * hoursPerWeek);
									} else if (type.equalsIgnoreCase("Ståldörr")) {
										StåldörrHours += (weeksLeft * hoursPerWeek);
									}
								}
							} else {
								float weeks = (stopPWeek - startPWeek + 1);
								float hoursEveryWeek = productionTime / weeks;

								if (type.equalsIgnoreCase("Sapa")) {
									SapaHours += (weeks * hoursEveryWeek);
								} else if (type.equalsIgnoreCase("Stålprofil")) {
									StålprofilHours += (weeks * hoursEveryWeek);
								} else if (type.equalsIgnoreCase("Ståldörr")) {
									StåldörrHours += (weeks * hoursEveryWeek);
								}
							}

						}

						else if (Currentyear == startPYear) {

							int weeksInNextYear = stopPWeek;
							int weeksLeftInThisYear = 52 - startPWeek;
							int totalWeeks = weeksInNextYear + weeksLeftInThisYear;
							int hoursPerWeek2 = productionTime / totalWeeks;

							if (Currentyear == activeYear) {
								if (currentWeek < startPWeek) {
									if (type.equalsIgnoreCase("Sapa")) {
										SapaHours += (weeksLeftInThisYear * hoursPerWeek2);
									} else if (type.equalsIgnoreCase("Stålprofil")) {
										StålprofilHours += (weeksLeftInThisYear * hoursPerWeek2);
									} else if (type.equalsIgnoreCase("Ståldörr")) {
										StåldörrHours += (weeksLeftInThisYear * hoursPerWeek2);
									}
								} else {
									int weeksleft = 52 - currentWeek + 1;

									if (type.equalsIgnoreCase("Sapa")) {
										SapaHours += (weeksleft * hoursPerWeek2);
									} else if (type.equalsIgnoreCase("Stålprofil")) {
										StålprofilHours += (weeksleft * hoursPerWeek2);
									} else if (type.equalsIgnoreCase("Ståldörr")) {
										StåldörrHours += (weeksleft * hoursPerWeek2);
									}
								}
							} else {
								if (type.equalsIgnoreCase("Sapa")) {
									SapaHours += (weeksLeftInThisYear * hoursPerWeek2);
								} else if (type.equalsIgnoreCase("Stålprofil")) {
									StålprofilHours += (weeksLeftInThisYear * hoursPerWeek2);
								} else if (type.equalsIgnoreCase("Ståldörr")) {
									StåldörrHours += (weeksLeftInThisYear * hoursPerWeek2);
								}
							}
						}

						else if (Currentyear == stopPYear) {

							int weeksInLastYear = 52 - startPWeek + 1;
							int weeksInCurrentYear = stopPWeek;
							int perWeeksTotal = weeksInLastYear + weeksInCurrentYear;
							int hoursPerWeek3 = productionTime / perWeeksTotal;
							int weeksToCount = stopPWeek - currentWeek;

							if (Currentyear == activeYear) {
								if (weeksToCount > 0) {
									if (type.equalsIgnoreCase("Sapa")) {
										SapaHours += (weeksToCount * hoursPerWeek3);
									} else if (type.equalsIgnoreCase("Stålprofil")) {
										StålprofilHours += (weeksToCount * hoursPerWeek3);
									} else if (type.equalsIgnoreCase("Ståldörr")) {
										StåldörrHours += (weeksToCount * hoursPerWeek3);
									}
								}
							} else {
								if (type.equalsIgnoreCase("Sapa")) {
									SapaHours += (stopPWeek * hoursPerWeek3);
								} else if (type.equalsIgnoreCase("Stålprofil")) {
									StålprofilHours += (stopPWeek * hoursPerWeek3);
								} else if (type.equalsIgnoreCase("Ståldörr")) {
									StåldörrHours += (stopPWeek * hoursPerWeek3);
								}
							}

						}
					}

					// MONTAGE---------------------------------------------------------

					if (rs2.getString("MontageTime").equalsIgnoreCase("false")) {
						// //no montage
					} else {
						int montageTime = Integer.parseInt(rs2.getString("MontageTime"));
						int startMWeek = Integer.parseInt(rs2.getString("VeckaStartM").substring(5));
						int startMYear = Integer.parseInt(rs2.getString("VeckaStartM").substring(0, 4));
						int stopMWeek = Integer.parseInt(rs2.getString("VeckaStopM").substring(5));
						int stopMYear = Integer.parseInt(rs2.getString("VeckaStopM").substring(0, 4));

						int currentWeek = date.get(weekFields.weekOfWeekBasedYear()) - 1;

						if (Currentyear == startMYear && Currentyear == stopMYear) {

							int numberOfWeeks = stopMWeek - startMWeek + 1;
							int hoursPerWeek = montageTime / numberOfWeeks;

							if (Currentyear == activeYear) {
								if (currentWeek < startMWeek) {
									montage += montageTime;
								} else if (currentWeek > stopMWeek) {
									// Nothing to add order has passed
								} else {
									int weeksLeft = stopMWeek - currentWeek + 1;
									montage += (weeksLeft * hoursPerWeek);
								}
							} else {
								float weeks = (stopMWeek - startMWeek + 1);
								float hoursEveryWeek = montageTime / weeks;
								montage += (weeks * hoursEveryWeek);
							}

						} else if (Currentyear == startMYear) {

							int weeksInNextYear = stopMWeek;
							int weeksLeftInThisYear = 52 - startMWeek;
							int totalWeeks = weeksInNextYear + weeksLeftInThisYear;
							int hoursPerWeek2 = montageTime / totalWeeks;

							if (Currentyear == activeYear) {
								if (currentWeek < startMWeek) {
									montage += (weeksLeftInThisYear * hoursPerWeek2);
								} else {
									int weeksleft = 52 - currentWeek + 1;

									montage += (weeksleft * hoursPerWeek2);
								}
							} else {
								montage += (weeksLeftInThisYear * hoursPerWeek2);
							}
						}

						else if (Currentyear == stopMYear) {

							int weeksInLastYear = 52 - startMWeek + 1;
							int weeksInCurrentYear = stopMWeek;
							int perWeeksTotal = weeksInLastYear + weeksInCurrentYear;
							int hoursPerWeek3 = montageTime / perWeeksTotal;
							int weeksToCount = stopMWeek - currentWeek;

							if (Currentyear == activeYear) {
								if (weeksToCount > 0) {
									montage += (weeksToCount * hoursPerWeek3);
								}
							} else {
								montage += (stopMWeek * hoursPerWeek3);
							}
						}

					}

				}
			}

		} catch (Exception e) {
			SM.messageBox("Fel med databsen! Kontakt Support3");
			System.out.println(e);
		}

		Label[0].setText("Total: " + (SapaHours + StålprofilHours + StåldörrHours));
		Label[1].setText("Sapa: " + SapaHours);
		Label[2].setText("Stålprofil: " + StålprofilHours);
		Label[3].setText("Ståldörr: " + StåldörrHours);
		Label[4].setText("Montage: " + (montage));

	}

}
