#include <ESP8266WiFi.h>

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

char trajetChambre[20] = "AAAGAMGADAF";

int cpt = 0;
int etat = 0;

void avancer()
{
  analogWrite(D1, 650);   // MOTEUR DROITE : ON
  analogWrite(D2, 700);   // MOTEUR GAUCHE : ON
  digitalWrite(D3, LOW);  // MOTEUR DROITE : AVANT
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}

void arreter()
{
  digitalWrite(D1, LOW); // MOTEUR DROITE : OFF
  digitalWrite(D2, LOW); // MOTEUR GAUCHE : OFF
}

void tournerGauche()
{
  digitalWrite(D1, HIGH); // MOTEUR DROITE : ON
  digitalWrite(D2, LOW);  // MOTEUR GAUCHE : OFF
  digitalWrite(D3, LOW);  // MOTEUR DROITE : AVANT
}

void tournerDroite()
{
  digitalWrite(D1, LOW);  // MOTEUR DROITE : OFF
  digitalWrite(D2, HIGH); // MOTEUR GAUCHE : ON
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}

void donnerMedicament()
{
  delay(2000);
}

void arriverPharmacie()
{
  delay(5000);
}

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

void loop() {
  int lnd = digitalRead(D5);
  int lng = digitalRead(D6);
  switch ( etat )
  {
    case 0 :
      if ( trajetChambre[cpt] == 'A' ) { etat = 1; }
      else if ( trajetChambre[cpt] == 'G' ) { etat = 2; }
      else if ( trajetChambre[cpt] == 'D' ) { etat = 3; }
      else if ( trajetChambre[cpt] == 'M' ) { etat = 4; }
      else if ( trajetChambre[cpt] == 'F' ) { etat = 5; }
      cpt = cpt + 1;
      break;
    case 1 :
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
      while ( lnd == 1 ) { lnd = digitalRead(D5); tournerGauche(); }
      arreter();
      etat = 0;
      break;
    case 3 : 
      while ( lng == 1 ) { lng = digitalRead(D6); tournerDroite(); }
      arreter();
      etat = 0;
      break;
    case 4 :
      donnerMedicament();
      etat = 0;
      break;
    case 5 :
      arriverPharmacie();
      break;
    default :
      arreter();
      break;
  }
}
