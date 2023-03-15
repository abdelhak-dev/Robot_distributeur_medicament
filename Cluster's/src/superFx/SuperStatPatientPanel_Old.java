/*
package superFx;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import communication.Main;
import communication.Statut;


public class SuperStatPatientPanel_Old extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	Integer[] lesSemaines = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
				 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
				 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52 };
	
	Integer[] lesIdMeds = new Integer[] {1,2,3,4,5,6};
						
	
	Container container = getContentPane();
	//Container container2 = getContentPane(); SOON
	
	JLabel roomNumLabel1 = new JLabel("Chambre n°1");
	JLabel roomNumLabel2 = new JLabel("Chambre n°2");
	JLabel roomNumLabel3 = new JLabel("Chambre n°3");
	JLabel roomNumLabel4 = new JLabel("Chambre n°4");
	
	JLabel patIdLabel = new JLabel("Numéro de SS");
	
	
	JLabel medNameLabel = new JLabel("Nom du medicament");
	
	
	JLabel medIdLabel = new JLabel("Id du Médicament");
	
	JLabel patStatutLabel = new JLabel("Etat du Patient");
	
	JLabel numSemaineLabel = new JLabel("numero de la semaine");
	
	JTextField patIdText1 = new JTextField();
	JTextField patIdText2 = new JTextField();
	JTextField patIdText3 = new JTextField();
	JTextField patIdText4 = new JTextField();
	
	JTextField medNameText1 = new JTextField();
	JTextField medNameText2 = new JTextField();
	JTextField medNameText3 = new JTextField();
	JTextField medNameText4 = new JTextField();
	
	JComboBox<Integer> medId1 = new JComboBox<>(lesIdMeds);
	JComboBox<Integer> medId2 = new JComboBox<>(lesIdMeds);
	JComboBox<Integer> medId3 = new JComboBox<>(lesIdMeds);
	JComboBox<Integer> medId4 = new JComboBox<>(lesIdMeds);
	
	JComboBox<Statut> etatPat1 = new JComboBox<Statut>();
	JComboBox<Statut> etatPat2 = new JComboBox<Statut>();
	JComboBox<Statut> etatPat3 = new JComboBox<Statut>();
	JComboBox<Statut> etatPat4 = new JComboBox<Statut>();
	
	JComboBox<Integer> numSemaine = new JComboBox<>(lesSemaines);
	
	JButton confirm = new JButton("OK");
	
	
	// ONGLETS SOON
	
	//JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
	//JPanel patientStat = new JPanel();
    //JLabel titreOnglet1 = new JLabel("Statut des Patients");
	
	
	
	
	
	
	public SuperStatPatientPanel_Old() {
		
		container.setLayout(null);
		setPlaceAndSizeOfThings();
		addThings();
		addActionToThings();
		
	}
	
	public void setPlaceAndSizeOfThings() {
		roomNumLabel1.setBounds(50, 150, 100, 30);
		roomNumLabel2.setBounds(50, 200, 100, 30);
		roomNumLabel3.setBounds(50, 250, 100, 30);
		roomNumLabel4.setBounds(50, 300, 100, 30);
		
		patIdText1.setBounds(150, 150, 150, 30);
		patIdText2.setBounds(150, 200, 150, 30);
		patIdText3.setBounds(150, 250, 150, 30);
		patIdText4.setBounds(150, 300, 150, 30);
		
		medNameLabel.setBounds(320, 100, 150, 30);
		
		patIdLabel.setBounds(150, 100, 150, 30);
		
		medNameText1.setBounds(320, 150, 200, 30);
		medNameText2.setBounds(320, 200, 200, 30);
		medNameText3.setBounds(320, 250, 200, 30);
		medNameText4.setBounds(320, 300, 200, 30);
		
		medId1.setBounds(570, 150, 50, 30);
		medId2.setBounds(570, 200, 50, 30);
		medId3.setBounds(570, 250, 50, 30);
		medId4.setBounds(570, 300, 50, 30);
		
		etatPat1.setBounds(680, 150, 100, 30);
		etatPat2.setBounds(680, 200, 100, 30);
		etatPat3.setBounds(680, 250, 100, 30);
		etatPat4.setBounds(680, 300, 100, 30);
		
		numSemaine.setBounds(150, 350, 50, 30);
		
		
		medIdLabel.setBounds(530, 100 , 200, 30);
		
		patStatutLabel.setBounds(680, 100, 200, 30);
		
		confirm.setBounds(50, 400, 100, 30);
	}
	
	public void addThings() {
		container.add(patIdLabel);
		
		container.add(patIdText1);
		container.add(patIdText2);
		container.add(patIdText3);
		container.add(patIdText4);
		
		container.add(medNameLabel);
		
		container.add(patIdLabel);
		
		container.add(medIdLabel);
		
		container.add(patStatutLabel);

		container.add(medNameText1);
		container.add(medNameText2);
		container.add(medNameText3);
		container.add(medNameText4);
		
		container.add(roomNumLabel1);
		container.add(roomNumLabel2);
		container.add(roomNumLabel3);
		container.add(roomNumLabel4);
		
		container.add(medId1);
		container.add(medId2);
		container.add(medId3);
		container.add(medId4);
		
		container.add(etatPat1);
		container.add(etatPat2);
		container.add(etatPat3);
		container.add(etatPat4);
		
		container.add(numSemaine);
		
		etatPat1.addItem(Statut.Cured);
		etatPat1.addItem(Statut.Stable);
		etatPat1.addItem(Statut.Dead);
		
		etatPat2.addItem(Statut.Cured);
		etatPat2.addItem(Statut.Stable);
		etatPat2.addItem(Statut.Dead);
		
		etatPat3.addItem(Statut.Cured);
		etatPat3.addItem(Statut.Stable);
		etatPat3.addItem(Statut.Dead);
		
		etatPat4.addItem(Statut.Cured);
		etatPat4.addItem(Statut.Stable);
		etatPat4.addItem(Statut.Dead);
		
		container.add(confirm);
	}
	
	public void addActionToThings() {
		confirm.addActionListener(this);
	}
	
	
	/**
	 * actionPerformed : Ici cela recup' l'event du bouton "OK"
	 * Applique les changement au quatres pastients puis utilise les fonction de
	 * la class Patient pour send les info au Service web
	 */
	/*
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirm) {
			JOptionPane.showMessageDialog(this, "Your modification have been saved");
			 
			
			//JOptionPane.showMessageDialog(this, "Id du patient dans la chambre 1 : " + patIdText1.getText() + "\n" + "prend le médicament" + medNameText1.getText() + " n°" + medId1.getSelectedItem() );
			
			Main.patient1.setCondition((Statut) etatPat1.getSelectedItem());
			Main.patient1.setRoom(1);
			Main.patient1.setWeek((int) numSemaine.getSelectedItem());
			Main.patient1.setPatientID(patIdText1.getText());
			
			System.out.println(Main.patient1.getPatientID());
			System.out.println(Main.patient1.getWeek());
			System.out.println(Main.patient1.getRoom());
			
			try {
				Main.patient1.setPatientCondition();
				Main.patient1.addPatient();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			 	
			
			Main.patient2.setCondition((Statut) etatPat2.getSelectedItem());
			Main.patient2.setRoom(1);
			Main.patient2.setWeek((int) numSemaine.getSelectedItem());
			Main.patient2.setPatientID(patIdText2.getText());
			
			System.out.println(Main.patient2.getPatientID());
			System.out.println(Main.patient2.getWeek());
			System.out.println(Main.patient2.getRoom());
			
			try {
				Main.patient2.setPatientCondition();
				Main.patient2.addPatient();
				
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			Main.patient3.setCondition((Statut) etatPat3.getSelectedItem());
			Main.patient3.setRoom(1);
			Main.patient3.setWeek((int) numSemaine.getSelectedItem());
			Main.patient3.setPatientID(patIdText3.getText());
			
			System.out.println(Main.patient3.getPatientID());
			System.out.println(Main.patient3.getWeek());
			System.out.println(Main.patient3.getRoom());
			
			try {
				Main.patient3.setPatientCondition();
				Main.patient3.addPatient();
				
			} catch (IOException e3) {
				e3.printStackTrace();
			}
			
			Main.patient4.setCondition((Statut) etatPat4.getSelectedItem());
			Main.patient4.setRoom(1);
			Main.patient4.setWeek((int) numSemaine.getSelectedItem());
			Main.patient4.setPatientID(patIdText4.getText());
			
			System.out.println(Main.patient4.getPatientID());
			System.out.println(Main.patient4.getWeek());
			System.out.println(Main.patient4.getRoom());
			
			try {
				Main.patient4.setPatientCondition();
				Main.patient4.addPatient();
				
			} catch (IOException e4) {
				e4.printStackTrace();
			}
			 
			 
		}
	}
}
*/