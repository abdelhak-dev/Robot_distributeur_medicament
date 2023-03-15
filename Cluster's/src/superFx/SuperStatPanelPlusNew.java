package superFx;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import communication.Main;
import communication.Statut;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;


public class SuperStatPanelPlusNew extends Application {
	// ------------------INIT THE THINGS ICI------------------------------
    
	TabPane tabPane = new TabPane();
	
    Tab tab1 = new Tab("Statut", new Label(""));
    Tab tab2 = new Tab("Statistique"  , new Label(""));
    Tab tab3 = new Tab("Robot" , new Label(""));

    
    Button buttonOK1 = new Button("OK");
    Button buttonOK2 = new Button("OK");
    Button buttonOK3 = new Button("OK");
    Button buttonOK4 = new Button("OK");
    
    Label patIdLabel = new Label("Numéro de SS");
    
    TextField patIdText1 = new TextField();
    TextField patIdText2 = new TextField();
    TextField patIdText3 = new TextField();
    TextField patIdText4 = new TextField();
    
    Label medNameLabel = new Label("Nom médicament");
    
    TextField medNameText1 = new TextField();
    TextField medNameText2 = new TextField();
    TextField medNameText3 = new TextField();
    TextField medNameText4 = new TextField();
    
    Label numRoomLabel1 = new Label("Chambre n°1");
    Label numRoomLabel2 = new Label("Chambre n°2");
    Label numRoomLabel3 = new Label("Chambre n°3");
    Label numRoomLabel4 = new Label("Chambre n°4");
    
    Label medIdLabel = new Label("Id du med");
    
	ComboBox<Integer> medId1 = new ComboBox<Integer>();
    ComboBox<Integer> medId2 = new ComboBox<Integer>();
    ComboBox<Integer> medId3 = new ComboBox<Integer>();
    ComboBox<Integer> medId4 = new ComboBox<Integer>();
    
    Label etatPatLabel = new Label("Etat du patient");
    
    ComboBox<Statut> etatPat1 = new ComboBox<Statut>();
    ComboBox<Statut> etatPat2 = new ComboBox<Statut>();
    ComboBox<Statut> etatPat3 = new ComboBox<Statut>();
    ComboBox<Statut> etatPat4 = new ComboBox<Statut>();
    
    Label numSemaineLabel = new Label("Choisir la semaine");
    DatePicker numSemaine = new DatePicker();
    
    
    
    AnchorPane statutPane = new AnchorPane();
    AnchorPane statistiquePane = new AnchorPane();
    AnchorPane robotPane = new AnchorPane();
    
    ScrollPane scrollPane1 = new ScrollPane();
  
    ScrollPane scrollPane2 = new ScrollPane();
    
    ScrollPane scrollPane3 = new ScrollPane();
   

    VBox vBox = new VBox(tabPane);    
    Scene scene = new Scene(vBox,900,600);
    
        
    //-------------------------ROBOT ACTION LIST--------------------------------------------
    final ObservableList<String> obsTache = FXCollections.observableArrayList(new ArrayList <String>());
	final ListView<String> listeTache = new ListView<>(obsTache);
	
    //--------------------------------------------------------------------------------------
    
    //initGraph dead
    
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();

    final LineChart<String,Number> lineChartDead = new LineChart<String,Number>(xAxis,yAxis);
   
    
    //fin initGraph dead
    
    //initGraph stable
    final CategoryAxis xAxisStable = new CategoryAxis();
    final NumberAxis yAxisStable = new NumberAxis();

    final LineChart<String,Number> lineChartStable = new LineChart<String,Number>(xAxisStable,yAxisStable);
    
    //fin initGraph stable
    
    //initGraph cured
    final CategoryAxis xAxisCured = new CategoryAxis();
    final NumberAxis yAxisCured = new NumberAxis();

    final LineChart<String,Number> lineChartCured = new LineChart<String,Number>(xAxisCured,yAxisCured);
   
    //fin initGraph cured
    
    //init alert
    Alert popupConfirmation = new Alert(AlertType.CONFIRMATION);
    Alert popupWarningSaisie = new Alert(AlertType.WARNING);
    Alert infoOrderStart = new Alert(AlertType.INFORMATION);
    Alert infoOrderSend = new Alert(AlertType.INFORMATION);
    Alert infoPatIdAlreadyExist = new Alert(AlertType.INFORMATION);
    Alert errorNopeAdd = new Alert(AlertType.ERROR);
    // fin init alert
    
    //------------------FIN DE INIT THE THINGS-------------------------------
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Creation de la fenêtre avec les configs
     * @throws IOException 
     */
    public void start(Stage primaryStage) throws IOException {
    	
    	primaryStage.getIcons().add(new Image("iconApp.jpg"));
    	
        //CALL OF METHODES
        addThings();
        setPlaceToThings();
        theGraphThings();
        ceateAlertToThings();
        setActionToThings();
        theRobotTings();
        setDateToConvert();
        //-----------------
       
        
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        scrollPane1.fitToWidthProperty().set(true);
        scrollPane1.fitToHeightProperty().set(true);
        scrollPane1.pannableProperty().set(true);
        scrollPane1.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane1.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);	
        
      
        scrollPane2.fitToWidthProperty().set(true);
        scrollPane2.fitToHeightProperty().set(true);
        scrollPane2.pannableProperty().set(true);
        scrollPane2.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane2.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);	

        
        scrollPane3.fitToWidthProperty().set(true);
        scrollPane3.fitToHeightProperty().set(true);
        scrollPane3.pannableProperty().set(true);
        scrollPane3.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane3.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);	

        
        tabPane.getSelectionModel().getSelectedItem();
        
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10,10, 10,10));
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Super Supervisor Panel Plus");

        primaryStage.show();
    }

    
    /**
     * Ajoute des actions aux bontons:
     * 
     * -add les Alertes popup de confirmation,Warning et Error.
     * -Teste si les champs sont bien remplis avant de send les infos au SW
     * 
     */
	private void setActionToThings() {
	
        
        //--------------------SET ACTION TO THINGS--------------------------------
        
        buttonOK1.setOnAction((event1) -> {
        	Main.patient1.setCondition(etatPat1.getSelectionModel().getSelectedItem());
			Main.patient1.setRoom(1);
			//Main.patient1.setWeek(numSemaine.setConverter();
			
			Main.patient1.setPatientID(patIdText1.getText());
			Main.doliprane.setMedID((int) medId1.getSelectionModel().getSelectedItem());
			Main.doliprane.setMedName((String) medNameText1.getText());
			Main.patient1.setWeek(numSemaine.getValue().toString());
			
			System.out.println(Main.patient1.getPatientID());
			System.out.println(Main.patient1.getWeek());
			System.out.println(Main.patient1.getRoom());
			System.out.println(numSemaine.getValue());
        	
			Optional<ButtonType> result = popupConfirmation.showAndWait();
        	if (result.get() == ButtonType.OK){
        		if ((Main.patient1.getPatientID().isEmpty()) || Main.doliprane.getMedName().isEmpty())
				{
        			
        			popupWarningSaisie.showAndWait();
					
				} 
        		else 
				{
					try {
						if ( Main.patient1.addPatient() && Main.doliprane.setMedicine() && Main.patient1.setPatientCondition() ) 
						{
							infoOrderStart.showAndWait();
							Main.robot1.addOrder(Main.patient1.getRoom(),(int) medId1.getSelectionModel().getSelectedItem());
							infoOrderSend.showAndWait();
						}
						else {
							infoPatIdAlreadyExist.showAndWait();
						}  		
					}
					catch (IOException e1) {
						errorNopeAdd.showAndWait();
					}
					
				}
        	}
        		
        		else {
        	    // other thing to do
        	}
        });
        
        buttonOK2.setOnAction((event2) -> {
        	Main.patient2.setCondition(etatPat2.getSelectionModel().getSelectedItem());
			Main.patient2.setRoom(2);
			Main.patient2.setWeek(numSemaine.getValue().toString());
			Main.patient2.setPatientID(patIdText2.getText());
			Main.doliprane.setMedID((int) medId2.getSelectionModel().getSelectedItem());
			Main.doliprane.setMedName((String) medNameText2.getText());
			
			System.out.println(Main.patient2.getPatientID());
			System.out.println(Main.patient2.getWeek());
			System.out.println(Main.patient2.getRoom());

        	
			Optional<ButtonType> result = popupConfirmation.showAndWait();
        	if (result.get() == ButtonType.OK){
        		if ((Main.patient2.getPatientID().isEmpty()) || Main.doliprane.getMedName().isEmpty())
				{
        			
        			popupWarningSaisie.showAndWait();
					
				} 
        		else 
				{
					try {
						if ( Main.patient2.addPatient() && Main.doliprane.setMedicine() && Main.patient2.setPatientCondition() ) 
						{
							infoOrderStart.showAndWait();
							Main.robot1.addOrder(Main.patient2.getRoom(),(int) medId2.getSelectionModel().getSelectedItem());
							infoOrderSend.showAndWait();
						}
						else {
							infoPatIdAlreadyExist.showAndWait();
						}  		
					}
					catch (IOException e1) {
						errorNopeAdd.showAndWait();
					}
					
				}
        	}
        		
        	else {
        	    // other thing to do
        	}
        });
        
        buttonOK3.setOnAction((event3) -> {
        	Main.patient3.setCondition(etatPat3.getSelectionModel().getSelectedItem());
			Main.patient3.setRoom(3);
			Main.patient3.setWeek(numSemaine.getValue().toString());
			Main.patient3.setPatientID(patIdText1.getText());
			Main.doliprane.setMedID((int) medId3.getSelectionModel().getSelectedItem());
			Main.doliprane.setMedName((String) medNameText3.getText());
			
			System.out.println(Main.patient3.getPatientID());
			System.out.println(Main.patient3.getWeek());
			System.out.println(Main.patient3.getRoom());

        	
			Optional<ButtonType> result = popupConfirmation.showAndWait();
        	if (result.get() == ButtonType.OK){
        		if ((Main.patient3.getPatientID().isEmpty()) || Main.doliprane.getMedName().isEmpty())
				{
        			
        			popupWarningSaisie.showAndWait();
					
				} 
        		else 
				{
					try {
						if ( Main.patient3.addPatient() && Main.doliprane.setMedicine() && Main.patient3.setPatientCondition() ) 
						{
							infoOrderStart.showAndWait();
							Main.robot1.addOrder(Main.patient3.getRoom(),(int) medId3.getSelectionModel().getSelectedItem());
							infoOrderSend.showAndWait();
						}
						else {
							infoPatIdAlreadyExist.showAndWait();
						}  		
					}
					catch (IOException e1) {
						errorNopeAdd.showAndWait();
					}
					
				}
        	}
        		
        		else {
        	    // other thing to do
        	}
        });
        
        buttonOK4.setOnAction((event4) -> {
        	Main.patient4.setCondition(etatPat4.getSelectionModel().getSelectedItem());
			Main.patient4.setRoom(4);
			Main.patient4.setWeek(numSemaine.getValue().toString());
			Main.patient4.setPatientID(patIdText4.getText());
			Main.doliprane.setMedID((int) medId4.getSelectionModel().getSelectedItem());
			Main.doliprane.setMedName((String) medNameText4.getText());
			
			System.out.println(Main.patient4.getPatientID());
			System.out.println(Main.patient4.getWeek());
			System.out.println(Main.patient4.getRoom());

        	
			Optional<ButtonType> result = popupConfirmation.showAndWait();
        	if (result.get() == ButtonType.OK){
        		if ((Main.patient4.getPatientID().isEmpty()) || Main.doliprane.getMedName().isEmpty())
				{
        			
        			popupWarningSaisie.showAndWait();
					
				} 
        		else 
				{
					try {
						if ( Main.patient4.addPatient() && Main.doliprane.setMedicine() && Main.patient4.setPatientCondition() ) 
						{
							infoOrderStart.showAndWait();
							Main.robot1.addOrder(Main.patient4.getRoom(),(int) medId4.getSelectionModel().getSelectedItem());
							infoOrderSend.showAndWait();
						}
						else {
							infoPatIdAlreadyExist.showAndWait();
						}  		
					}
					catch (IOException e1) {
						errorNopeAdd.showAndWait();
					}
					
				}
        	}
        		
        		else {
        	    // other thing to do
        	}
        });
        
        
        
        //------------------FIN SET ACTION TO THINGS------------------------------
		
	}

	
	private void setDateToConvert() {
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        public String toString(LocalDate date) {
	            if (date != null) {
	                return dateFormatter.format(date);
	            } else {
	                return "";
	            }
	        }
	        public LocalDate fromString(String string) {
	            if (string != null && !string.isEmpty()) {
	                return LocalDate.parse(string, dateFormatter);
	            } else {
	                return null;
	            }
	        }
	    }; 
	    numSemaine.setConverter(converter);
	    numSemaine.setPromptText("yyyy-MM-dd");
	}
	
	
	/**
	 * Creatation des Alertes Popup:
	 * - confirmation,Warning et Error.
	 */
	private void ceateAlertToThings() {
		//-------------------CREATE ALERT TO THINGS-------------------------------
        //CONFIRMATION ALERT
       
        popupConfirmation.setTitle("Confirmation saisie");
        popupConfirmation.setHeaderText("U sure bro ?");
        popupConfirmation.setContentText("Are you ok with this?");
        
    	
        //WARNING ALERT
    	
    	popupWarningSaisie.setTitle("Warning Saisie");
    	popupWarningSaisie.setHeaderText("Nope Nope, not gud !");
    	popupWarningSaisie.setContentText("Mauvaise saisie");
    	//INFO POP
    	
    	infoOrderStart.setTitle("Information");
    	infoOrderStart.setHeaderText(null);
    	infoOrderStart.setContentText("Ajout avec succès");

    	
    	infoOrderSend.setTitle("Information");
    	infoOrderSend.setHeaderText(null);
    	infoOrderSend.setContentText("Lancement de l'ordre");
    	
    	
    	infoPatIdAlreadyExist.setTitle("Information");
    	infoPatIdAlreadyExist.setHeaderText(null);
    	infoPatIdAlreadyExist.setContentText("n°SS deja enregistré");
    	
    	
    	errorNopeAdd.setTitle("Error");
    	errorNopeAdd.setHeaderText(null);
    	errorNopeAdd.setContentText("Echec de l'ajout");
    	
    	
        
        //-----------------FIN CREATE ALERT TO THINGS-----------------------------
		
	}

	
	/**
	 * Ajoute les graphiques:
	 * 
	 * - reçois les donnés puis les affiches
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void theGraphThings() throws IOException {
		//------------------------GRAPH AREA--------------------------------------
		lineChartDead.setTitle("Graph des Morts");
	    xAxis.setLabel("Semaine");
	    yAxis.setLabel("Poucentage");
	    lineChartStable.setTitle("Graph des Stables");
	    xAxisStable.setLabel("Semaine");
	    yAxisStable.setLabel("Poucentage");
	    lineChartCured.setTitle("Graph des Cured");
	    xAxisCured.setLabel("Semaine");
	    yAxisCured.setLabel("Poucentage");
	    
	    
	    
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Nizoferon");
       
        series1.getData().add(new XYChart.Data("02/04", 0));
        series1.getData().add(new XYChart.Data("09/04", 50));  
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Metroprin");
        
        series2.getData().add(new XYChart.Data("02/04", 0));
        series2.getData().add(new XYChart.Data("09/04", 0));  
    
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Tetracor");

        series3.getData().add(new XYChart.Data("02/04", 0));
        series3.getData().add(new XYChart.Data("09/04", 0));  
        
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Olosone");
        
        series4.getData().add(new XYChart.Data("02/04", 0));
        series4.getData().add(new XYChart.Data("09/04", 0));  
        
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("Ampydosyn");
        
        series5.getData().add(new XYChart.Data("02/04", 0));
        series5.getData().add(new XYChart.Data("09/04", 0));  
        
        XYChart.Series series6 = new XYChart.Series();
        series6.setName("Doliprane");
        
        series6.getData().add(new XYChart.Data("02/04", 0));
        series6.getData().add(new XYChart.Data("09/04", 0));  
        
        
        
        lineChartDead.getData().addAll(series1, series2, series3, series4, series5, series6);
        //###############################################################################
        XYChart.Series series11 = new XYChart.Series();
        series11.setName("Nizoferon");
        
        series11.getData().add(new XYChart.Data("02/04", 0));
        series11.getData().add(new XYChart.Data("09/04", 50));

        
        XYChart.Series series21 = new XYChart.Series();
        series21.setName("Metroprin");
        series21.getData().add(new XYChart.Data("02/04", 0));
        series21.getData().add(new XYChart.Data("09/04", 0));
        
        XYChart.Series series31 = new XYChart.Series();
        series31.setName("Tetracor");
        series31.getData().add(new XYChart.Data("02/04", 0));
        series31.getData().add(new XYChart.Data("09/04", 0));  
        
        XYChart.Series series41 = new XYChart.Series();
        series41.setName("Olosone");
        series41.getData().add(new XYChart.Data("02/04", 0));
        series41.getData().add(new XYChart.Data("09/04", 0));  
        
        XYChart.Series series51 = new XYChart.Series();
        series51.setName("Ampydosyn");
        series51.getData().add(new XYChart.Data("02/04", 0));
        series51.getData().add(new XYChart.Data("09/04", 0));  
        
        XYChart.Series series61 = new XYChart.Series();
        series61.setName("Doliprane");
        
        series61.getData().add(new XYChart.Data("02/04", 0));
        series61.getData().add(new XYChart.Data("09/04", 0));  
        
        lineChartStable.getData().addAll(series11, series21, series31, series41, series51, series61);
        //###############################################################################
        XYChart.Series series111 = new XYChart.Series();
        series111.setName("Nizoferon");
        
        series111.getData().add(new XYChart.Data("02/04", 0));
        series111.getData().add(new XYChart.Data("09/04", 0));
        
        XYChart.Series series211 = new XYChart.Series();
        series211.setName("Metroprin");
        series211.getData().add(new XYChart.Data("02/04", 0));
        series211.getData().add(new XYChart.Data("09/04", 100));
        
        XYChart.Series series311 = new XYChart.Series();
        series311.setName("Tetracor");
        series311.getData().add(new XYChart.Data("02/04", 0));
        series311.getData().add(new XYChart.Data("09/04", 0));
        
        XYChart.Series series411 = new XYChart.Series();
        series411.setName("Olosone");
        series411.getData().add(new XYChart.Data("02/04", 0));
        series411.getData().add(new XYChart.Data("09/04", 0));
        
        XYChart.Series series511 = new XYChart.Series();
        series511.setName("Ampydosyn");
        series511.getData().add(new XYChart.Data("02/04", 0));
        series511.getData().add(new XYChart.Data("09/04", 0));
        
        XYChart.Series series611 = new XYChart.Series();
        series611.setName("Doliprane");
        series611.getData().add(new XYChart.Data("02/04", 0));
        series611.getData().add(new XYChart.Data("09/04", 0));
        
        lineChartCured.getData().addAll(series111, series211, series311, series411, series511, series611);
        //----------------------FIN GRAPH AREA------------------------------------
		
	}
	
	
	
	private void theRobotTings() throws IOException {
		//String[] zfa = new String[]{"eqvevevevqe","qqvqfvqrvqvvz","VRVVeqqvertbsrb","rsbrbbsrbrs","eqvevevevqe","qqvqfvqrvqvvz","VRVVeqqvertbsrb","rsbrbbsrbrs","eqvevevevqe","qqvqfvqrvqvvz","VRVVeqqvertbsrb","rsbrbbsrbrs"};
		
		obsTache.addAll(Main.stats.getHistory());
	}
	
	/**
	 * Place les objects aux coordonnées voulu
	 */
	private void setPlaceToThings() {
		//-----------------SET PLACE TO THESE THINGS------------------------------
        patIdLabel.setLayoutX(150);
        patIdLabel.setLayoutY(100);
        
        patIdText1.setLayoutX(150);
        patIdText1.setLayoutY(150);
        
        patIdText2.setLayoutX(150);
        patIdText2.setLayoutY(200);
        
        patIdText3.setLayoutX(150);
        patIdText3.setLayoutY(250);
        
        patIdText4.setLayoutX(150);
        patIdText4.setLayoutY(300);
        
        numRoomLabel1.setLayoutX(50);
        numRoomLabel1.setLayoutY(155);
        
        numRoomLabel2.setLayoutX(50);
        numRoomLabel2.setLayoutY(205);
        
        numRoomLabel3.setLayoutX(50);
        numRoomLabel3.setLayoutY(255);
        
        numRoomLabel4.setLayoutX(50);
        numRoomLabel4.setLayoutY(305);
        
        medNameLabel.setLayoutX(350);
        medNameLabel.setLayoutY(100);
        
        medNameText1.setLayoutX(350);
        medNameText1.setLayoutY(150);

        medNameText2.setLayoutX(350);
        medNameText2.setLayoutY(200);

        medNameText3.setLayoutX(350);
        medNameText3.setLayoutY(250);

        medNameText4.setLayoutX(350);
        medNameText4.setLayoutY(300);
        
        medIdLabel.setLayoutX(550);
        medIdLabel.setLayoutY(100);
        
        medId1.setLayoutX(550);
        medId1.setLayoutY(150);
        
        medId2.setLayoutX(550);
        medId2.setLayoutY(200);
        
        medId3.setLayoutX(550);
        medId3.setLayoutY(250);
        
        medId4.setLayoutX(550);
        medId4.setLayoutY(300);
        
        etatPatLabel.setLayoutX(650);
        etatPatLabel.setLayoutY(100);
        
        etatPat1.setLayoutX(650);
        etatPat1.setLayoutY(150);
        
        etatPat2.setLayoutX(650);
        etatPat2.setLayoutY(200);
        
        etatPat3.setLayoutX(650);
        etatPat3.setLayoutY(250);
        
        etatPat4.setLayoutX(650);
        etatPat4.setLayoutY(300);
        
        buttonOK1.setLayoutX(750);
        buttonOK1.setLayoutY(150);
        
        buttonOK2.setLayoutX(750);
        buttonOK2.setLayoutY(200);
        
        buttonOK3.setLayoutX(750);
        buttonOK3.setLayoutY(250);
        
        buttonOK4.setLayoutX(750);
        buttonOK4.setLayoutY(300);
        
        numSemaineLabel.setLayoutX(150);
        numSemaineLabel.setLayoutY(350);
        
        numSemaine.setLayoutX(350);
        numSemaine.setLayoutY(350);
        
        //###############################################################################
        
        //lineChartDead,lineChartStable,lineChartCured
        
        lineChartDead.setLayoutX(100);
        lineChartDead.setLayoutY(100);
        
        lineChartStable.setLayoutX(100);
        lineChartStable.setLayoutY(550);
        
        lineChartCured.setLayoutX(100);
        lineChartCured.setLayoutY(1000);
        
        

   
        //------------------FIN SET PLACE TO THINGS-------------------------------
	}

	
	/**
	 * ajoute les objects aux different Panels et tabs
	 */
	private void addThings() {
		//-------------------ADD THING AUX TABS----------------------------------
        
        
        tabPane.getTabs().add(tab1);
        statutPane.getChildren().addAll(patIdText1,patIdText2,patIdText3,patIdText4,
        								numRoomLabel1,numRoomLabel2,numRoomLabel3,numRoomLabel4,
        								medNameText1,medNameText2,medNameText3,medNameText4,
        								medId1,medId2,medId3,medId4,
        								etatPat1,etatPat2,etatPat3,etatPat4,
        								buttonOK1,buttonOK2,buttonOK3,buttonOK4,
        								numSemaine,
        								patIdLabel,medNameLabel,medIdLabel,etatPatLabel,numSemaineLabel);
        
        medId1.getItems().addAll(1,2,3,4,5,6);
        medId2.getItems().addAll(1,2,3,4,5,6);
        medId3.getItems().addAll(1,2,3,4,5,6);
        medId4.getItems().addAll(1,2,3,4,5,6);
        
        etatPat1.getItems().addAll(Statut.Stable,Statut.Cured,Statut.Dead);
        etatPat2.getItems().addAll(Statut.Stable,Statut.Cured,Statut.Dead);
        etatPat3.getItems().addAll(Statut.Stable,Statut.Cured,Statut.Dead);
        etatPat4.getItems().addAll(Statut.Stable,Statut.Cured,Statut.Dead);
        
        tab1.setContent(scrollPane1);
        scrollPane1.setContent(statutPane);
        
        
        //----------------------------PANE STATISTIQUE---------------------------------------
        tabPane.getTabs().add(tab2);
        statistiquePane.getChildren().addAll(lineChartDead,lineChartStable,lineChartCured);
        
        
        
        tab2.setContent(scrollPane2);
        scrollPane2.setContent(statistiquePane);
        
        
        
        //------------------------------PANE ROBOT-------------------------------------------
        tabPane.getTabs().add(tab3);
        robotPane.getChildren().addAll(listeTache);
        
        tab3.setContent(scrollPane3);
        scrollPane3.setContent(robotPane);
        
        
        
        //------------------FIN ADD THINGS AUX TABS-------------------------------
		
	}
    
    
    
    
    
}