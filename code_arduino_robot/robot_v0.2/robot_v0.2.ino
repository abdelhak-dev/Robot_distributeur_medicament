#include <ESP8266WiFi.h>

// INITIALISATION DES PINS DE LA NODE MCU
#define D0 16
#define D1 5
#define D2 4
#define D3 0
#define D4 2
#define D5 14
#define D6 12
#define D7 13
#define D8 15
#define A0 17

char trajetChambre[20] = "AAAGAMGADAF"; // CHAINE DE CARACTERE RECU PAR COMMUNICATION WIFI

int cpt = 0; // NAVIGUER DANS LA CHAINE DE CARACTERE
int etat = 0; // NAVIGUER DANS LA MACHINE A ETAT

//////////////////////////////
// AVANCER ///////////////////
//////////////////////////////

void avancer()
{
  analogWrite(D1, 650);   // MOTEUR DROITE : ON
  analogWrite(D2, 700);   // MOTEUR GAUCHE : ON
  digitalWrite(D3, LOW);  // MOTEUR DROITE : AVANT
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}

//////////////////////////////
// ARRETER ///////////////////
//////////////////////////////

void arreter()
{
  digitalWrite(D1, LOW); // MOTEUR DROITE : OFF
  digitalWrite(D2, LOW); // MOTEUR GAUCHE : OFF
}

//////////////////////////////
// TOURNERGAUCHE//////////////
//////////////////////////////

void tournerGauche()
{
  digitalWrite(D1, HIGH); // MOTEUR DROITE : ON
  digitalWrite(D2, LOW);  // MOTEUR GAUCHE : OFF
  digitalWrite(D3, LOW);  // MOTEUR DROITE : AVANT
}

//////////////////////////////
// TOURNERDROITE /////////////
//////////////////////////////

void tournerDroite()
{
  digitalWrite(D1, LOW);  // MOTEUR DROITE : OFF
  digitalWrite(D2, HIGH); // MOTEUR GAUCHE : ON
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}

//////////////////////////////
// DONNERMEDICAMENT //////////
//////////////////////////////

void donnerMedicament()
{
  delay(2000);
}

//////////////////////////////
// ARRIVERPHARMACIE //////////
//////////////////////////////

void arriverPharmacie()
{
  delay(5000);
}

//////////////////////////////
// SETUP /////////////////////
//////////////////////////////

void setup() {
  // CONFIGURATION MOTEURS :
  pinMode(D1, OUTPUT);  // MOTEUR DROITE VITESSE
  pinMode(D2, OUTPUT);  // MOTEUR GAUCHE VITESSE
  pinMode(D3, OUTPUT);  // MOTEUR DROITE SENS : 0 AVANCER / 1 RECULER
  pinMode(D4, OUTPUT);  // MOTEUR GAUCHE SENS : 0 AVANCER / 1 RECULER
  // CONFIGURATION CAPTEURS :
  pinMode(D5, INPUT); // CAPTEUR LIGNE DROITE
  pinMode(D6, INPUT); // CAPTEUR LIGNE GAUCHE
}

//////////////////////////////
// LOOP //////////////////////
//////////////////////////////

void loop() {
  /* Initialisation des variables
   *  lnd ( ligne noire droite ) reçoit la valeur numérique lue sur la pin D5
   *  lng ( ligne noire gauche ) reçoit la valeur numérique lue sur la pin D6
   *  
   */
  int lnd = digitalRead(D5);
  int lng = digitalRead(D6);
  switch ( etat )
  /* MACHINE A ETAT
   *  Permuter entre les différents états dans le but de poursuivre un comportement spécifique
   */
  {
    case 0 :
    /* LECTURE DE LA CHAINE DE CARACTERE
     *  Lire les caractères de la chaine de caractère un par un ( tableau avec position cpt )
     *  Si le caractère lu est un 'A' alors, passer la valeur de etat à 1
     *  Sinon si le caractère lu est un 'G' alors, passer la valeur de etat à 2
     *  Sinon si le caractère lu est un 'D' alors, passer la valeur de etat à 3
     *  Sinon si le caractère lu est un 'M' alors, passer la valeur de etat à 4
     *  Sinon si le caractère lu est un 'F' alors, passer la valeur de etat à 5
     *  Enfin, incrémenter la valeur de cpt de 1
     */
      if ( trajetChambre[cpt] == 'A' ) { etat = 1; }
      else if ( trajetChambre[cpt] == 'G' ) { etat = 2; }
      else if ( trajetChambre[cpt] == 'D' ) { etat = 3; }
      else if ( trajetChambre[cpt] == 'M' ) { etat = 4; }
      else if ( trajetChambre[cpt] == 'F' ) { etat = 5; }
      cpt = cpt + 1;
      break;
    case 1 :
    /* SUIVI DE LIGNE
     *  Récupérer les valeurs transmisent pas les deux capteurs de luminosité
     *  En fonction de ses valeurs, appeler les fonctions avancer(), tournerGauche() ou tournerDroite() pour rectifier la trajectoire du robot
     *  Ensuite, si le robot rencontre une intesection, regarder le prochain caractère de la chaine de caractère
     *  Si ce caractère est un 'A' alors, appeler la fonction avancer() pendant 0,2 seconde pour faire traverser l'intersection
     *  Sinon, appeler la fonction arreter() pendant 0.2 seconde pour marquer l'intersection
     *  Enfin, passer la valeur de etat à 0
     */
      lnd = digitalRead(D5);
      lng = digitalRead(D6);
      if ( ( lnd == 0 ) && ( lng == 0 ) ) { avancer(); }
      else if ( ( lnd == 0 ) && ( lng == 1 ) ) { tournerGauche(); }
      else if ( ( lnd == 1 ) && ( lng == 0 ) ) { tournerDroite(); }
      else if ( ( lnd == 1 ) && ( lng == 1 ) )
      {
        if ( trajetChambre[cpt] == 'A' )
        {
          avancer();
        }
        else
        {
          arreter();
        }
        delay(200);
        etat = 0;
      }
      break;
    case 2 :
    /* ROTATION DU ROBOT D'UN QUART DE TOUR VERS LA GAUCHE
     *  Tant que le capteur droit de luminosité détecte une ligne noire, récupérer la valeur transmise par ce même capteur et appeler la méthode tournerGauche()
     *  Ensuite, appeler la méthode arreter()
     *  Enfin, passer la valeur de etat à 0
     */
      while ( lnd == 1 ) { lnd = digitalRead(D5); tournerGauche(); }
      arreter();
      etat = 0;
      break;
    case 3 : 
    /* ROTATION DU ROBOT D'UN QUART DE TOUR VERS LA DROITE
     *  Tant que le capteur gauche de luminosité détecte une ligne noire, récupérer la valeur transmise par ce même capteur et appeler la méthode tournerDroite()
     *  Ensuite, appeler la méthode arreter()
     *  Enfin, passer la valeur de etat à 0
     */
      while ( lng == 1 ) { lng = digitalRead(D6); tournerDroite(); }
      arreter();
      etat = 0;
      break;
    case 4 :
    /* DISTRIBUTION DU MEDICAMENT AU PATIENT
     *  Appeler la méthode donnerMedicament()
     *  Enfin, passer la valeur de etat à 0
     */
      donnerMedicament();
      etat = 0;
      break;
    case 5 :
    /* ROBOT A LA PHARMACIE
     *  Appeler la méthode arriverPharmacie()
     */
      arriverPharmacie();
      break;
    default :
    /* EN CAS D'ERREUR
     *  Appeler la méthode arreter()
     */
      arreter();
      break;
  }
}
