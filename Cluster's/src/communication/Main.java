package communication;
import java.io.IOException;


import superFx.SuperStatPanelPlusNew;
import superFx.SuperStats;



public class Main {

	
	
	public static MedicamentV2 Nizoferon = new MedicamentV2(1,"");
	public static MedicamentV2 Metroprin = new MedicamentV2(2,"");
	public static MedicamentV2 Tetracor  = new MedicamentV2(3,"");
	public static MedicamentV2 Olosone   = new MedicamentV2(4,"");
	public static MedicamentV2 Ampydosyn = new MedicamentV2(5,"");
	public static MedicamentV2 doliprane = new MedicamentV2(6,"");
	
	public static Patient patient1 = new Patient(null, 1, "", null, Ampydosyn);
	public static Patient patient2 = new Patient(null, 2, "", null, Ampydosyn);
	public static Patient patient3 = new Patient(null, 3, "", null, Ampydosyn);
	public static Patient patient4 = new Patient(null, 4, "", null, Ampydosyn);
	
	public static Robot robot1 = new Robot(null, 0, 1,-1);
	public static Robot robot2 = new Robot(null, 0, 2, -1);
	
	public static SuperStats stats = new SuperStats(null, null, 0);
	
	
	public static void main (String[] args) throws IOException{
		//Main.stats.getHistory();
		SuperStatPanelPlusNew.main(args);
		
	}

	
}
 