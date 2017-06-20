package Model.Objects;
import javafx.beans.property.SimpleStringProperty;

public class ObjectKalkyl {
//Denna kall är för att skapa kalkyler som sen läggs in i tablet
	
	public SimpleStringProperty KalkylID = new SimpleStringProperty();
	public SimpleStringProperty Name = new SimpleStringProperty();
	public SimpleStringProperty Creator = new SimpleStringProperty();
	public SimpleStringProperty Typ = new SimpleStringProperty();
	public SimpleStringProperty DateKalkyl = new SimpleStringProperty();
	
	
	 public ObjectKalkyl(String KalkylID, String Name, String Creator, String Typ, String DateKalkyl){
	        this.KalkylID = new SimpleStringProperty(KalkylID);
	        this.Name = new SimpleStringProperty(Name);
	        this.Creator = new SimpleStringProperty(Creator);
	        this.Typ = new SimpleStringProperty(Typ);
	        this.DateKalkyl = new SimpleStringProperty(DateKalkyl);
	      } 
	 	public String getKalkylID() {
			return KalkylID.get();
		}

		public void setKalkylID(SimpleStringProperty kalkylID) {
			KalkylID = kalkylID;
		}


		public String getName() {
			return Name.get();
		}


		public void setName(SimpleStringProperty name) {
			Name = name;
		}


		public String getCreator() {
			return Creator.get();
		}


		public void setCreator(SimpleStringProperty creator) {
			Creator = creator;
		}


		public String getTyp() {
			return Typ.get();
		}


		public void setTyp(SimpleStringProperty typ) {
			Typ = typ;
		}


		public String getDateKalkyl() {
			return DateKalkyl.get();
		}


		public void setDateKalkyl(SimpleStringProperty dateKalkyl) {
			DateKalkyl = dateKalkyl;
		}
	
	
}
