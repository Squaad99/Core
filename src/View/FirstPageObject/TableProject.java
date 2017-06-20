package View.FirstPageObject;

import Model.Setting;
import Model.Objects.ObjectKalkyl;
import Model.Objects.ObjectOrder;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class TableProject {

	Pane ContentPane;
	
	Label Header;
	public TableView ProjectTable;
	public Button UpdateButton;
	TextField SearchField;
	
	public TableProject() {
		
		ContentPane = new Pane();
		ContentPane.setPrefSize((Setting.CurrentWidth * 0.6), (Setting.CurrentHeight * 1));
		ContentPane.relocate((Setting.CurrentWidth * 0), (Setting.CurrentHeight * 0));
		ContentPane.setStyle("-fx-border-color: black;");
		
		Header = new Label("Kalkyler");
		Header.setStyle("-fx-font-size: 40pt");
		Header.relocate((Setting.CurrentWidth * 0.1), (Setting.CurrentHeight * 0.02));
		ContentPane.getChildren().add(Header);
		
		SearchField = new TextField();
		SearchField.setStyle("-fx-font-size: 14pt");
		SearchField.relocate((Setting.CurrentWidth * 0.1), (Setting.CurrentHeight * 0.1));
		SearchField.setPromptText("Sök");
		ContentPane.getChildren().add(SearchField);
		
		UpdateButton = new Button();
		UpdateButton.setStyle("-fx-font-size: 14pt");
		UpdateButton.relocate((Setting.CurrentWidth * 0.42), (Setting.CurrentHeight * 0.1));
		UpdateButton.setPrefSize((Setting.CurrentWidth * 0.08), (Setting.CurrentHeight * 0.04));
		UpdateButton.setText("Uppdatera");
		ContentPane.getChildren().add(UpdateButton);
		
		ProjectTable = new TableView();
		ProjectTable.setPrefSize((Setting.CurrentWidth * 0.4), (Setting.CurrentHeight * 0.7));
		ProjectTable.relocate((Setting.CurrentWidth * 0.1), (Setting.CurrentHeight * 0.15));
		ProjectTable.setStyle("-fx-font: 130% arial, sans-serif;");
		ContentPane.getChildren().add(ProjectTable);
		
	}
	
	public ObservableList<ObjectKalkyl> KalkylTableData = FXCollections.observableArrayList();
	
	public void setKalkylMode(){
		
		ContentPane.setStyle("-fx-background-color: #FFFFE0");
		Header.setText("Kalkyler");
		
		//COLUMNS
		ProjectTable.getColumns().clear();
		
		TableColumn<ObjectKalkyl, String> colKalkylID = new TableColumn<>("Nummer");
		colKalkylID.setMinWidth(Setting.CurrentWidth * 0.08);
		colKalkylID.setCellValueFactory(new PropertyValueFactory<>("KalkylID"));
		
		TableColumn<ObjectKalkyl, String> colName = new TableColumn<>("Namn");
		colName.setMinWidth(Setting.CurrentWidth * 0.08);
		colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		TableColumn<ObjectKalkyl, String> colCreator = new TableColumn<>("Skapare");
		colCreator.setMinWidth(Setting.CurrentWidth * 0.08);
		colCreator.setCellValueFactory(new PropertyValueFactory<>("Creator"));
		
		TableColumn<ObjectKalkyl, String> colTyp = new TableColumn<>("Typ");
		colTyp.setMinWidth(Setting.CurrentWidth * 0.08);
		colTyp.setCellValueFactory(new PropertyValueFactory<>("Typ"));
		
		TableColumn<ObjectKalkyl, String> colDateKalkyl = new TableColumn<>("Kalkyl Skapad");
		colDateKalkyl.setMinWidth(Setting.CurrentWidth * 0.08);
		colDateKalkyl.setCellValueFactory(new PropertyValueFactory<>("DateKalkyl"));
		
		ProjectTable.getColumns().addAll(colKalkylID, colName, colCreator, colTyp, colDateKalkyl);
		
		
		
		SearchField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if(SearchField.textProperty().get().isEmpty()) {
                	ProjectTable.setItems(KalkylTableData);
                    return;
                }
                ObservableList<ObjectKalkyl> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<ObjectKalkyl, ?>> cols = ProjectTable.getColumns();

                for(int i=0; i<KalkylTableData.size(); i++) {

                    for(int j=0; j<cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(KalkylTableData.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if(cellValue.contains(SearchField.textProperty().get().toLowerCase())) {
                            tableItems.add(KalkylTableData.get(i));
                            break;
                        }                       
                    }
                }
                ProjectTable.setItems(tableItems);
            }
		});
		
	}
	
	public ObservableList<ObjectOrder> OrderActiveTableData = FXCollections.observableArrayList();
	
	public void setOrderActiveMode(){
		
		ContentPane.setStyle("-fx-background-color: #90EE90");
		Header.setText("Order");
		
		ProjectTable.getColumns().clear();
		
		TableColumn<ObjectOrder, String> colOrderId = new TableColumn<>("Nummer");
		colOrderId.setMinWidth(Setting.CurrentWidth * 0.08);
		colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
		
		TableColumn<ObjectOrder, String> colOrderName = new TableColumn<>("Namn");
		colOrderName.setMinWidth(Setting.CurrentWidth * 0.08);
		colOrderName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		TableColumn<ObjectOrder, String> colOrderCreator = new TableColumn<>("Skapare");
		colOrderCreator.setMinWidth(Setting.CurrentWidth * 0.08);
		colOrderCreator.setCellValueFactory(new PropertyValueFactory<>("Creator"));
		
		TableColumn<ObjectOrder, String> colOrderTyp = new TableColumn<>("Typ");
		colOrderTyp.setMinWidth(Setting.CurrentWidth * 0.08);
		colOrderTyp.setCellValueFactory(new PropertyValueFactory<>("Typ"));
		
		TableColumn<ObjectOrder, String> colOrderCreated = new TableColumn<>("Order Skapad");
		colOrderCreated.setMinWidth(Setting.CurrentWidth * 0.08);
		colOrderCreated.setCellValueFactory(new PropertyValueFactory<>("DateOrder"));
		
		ProjectTable.getColumns().addAll(colOrderId, colOrderName, colOrderCreator, colOrderTyp, colOrderCreated);
		
		SearchField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if(SearchField.textProperty().get().isEmpty()) {
                	ProjectTable.setItems(OrderActiveTableData);
                    return;
                }
                ObservableList<ObjectOrder> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<ObjectOrder, ?>> cols = ProjectTable.getColumns();

                for(int i=0; i<OrderActiveTableData.size(); i++) {

                    for(int j=0; j<cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(OrderActiveTableData.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if(cellValue.contains(SearchField.textProperty().get().toLowerCase())) {
                            tableItems.add(OrderActiveTableData.get(i));
                            break;
                        }                       
                    }
                }
                ProjectTable.setItems(tableItems);
            }
		});
		
		
	}
	
	public ObservableList<ObjectOrder> OrderFinishedTableData = FXCollections.observableArrayList();
	
	public void setOrderFinishedMode(){
		setOrderActiveMode();
		
		ContentPane.setStyle("-fx-background-color: #D3D3D3");
		Header.setText("Avslutade Order");
		
		SearchField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if(SearchField.textProperty().get().isEmpty()) {
                	ProjectTable.setItems(OrderFinishedTableData);
                    return;
                }
                ObservableList<ObjectOrder> tableItems = FXCollections.observableArrayList(); 
                ObservableList<TableColumn<ObjectOrder, ?>> cols = ProjectTable.getColumns();

                for(int i=0; i<OrderFinishedTableData.size(); i++) {

                    for(int j=0; j<cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(OrderFinishedTableData.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if(cellValue.contains(SearchField.textProperty().get().toLowerCase())) {
                            tableItems.add(OrderFinishedTableData.get(i));
                            break;
                        }                       
                    }
                }
                ProjectTable.setItems(tableItems);
            }
		});
	}
	
	public Pane getPane(){
		return ContentPane;
	}
	
	public void addToPane(Pane pane) {
		pane.getChildren().add(ContentPane);
	}

}
