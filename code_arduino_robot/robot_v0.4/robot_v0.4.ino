#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h> 
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>

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

// INFORMATIONS DE CONNEXION AU WIFI
const char *ssid = "IMERIR_IoT"; // IDENTIFIANT
const char *password = "kohWoong5oox"; // MOT DE PASSE

// INFORMATIONS DE CONNEXION AU SERVEUR
byte host[4] = { 10, 3, 7, 41 }; // ADRESSE IP
const int httpPort = 8000; // PORT DE CONNEXION

String trajetChambre = ""; // CHAINE DE CARACTERE RECU PAR REQUETE HTTP
const String LinkGetPath = "/getPath/"; // REQUETE DE DEMANDE DE TRAJET
int numeroChambre = 1; // NUMERO DE CHAMBRE RECU PAR REQUETE HTTP

int cpt = 19; // NAVIGUER DANS LA CHAINE DE CARACTERE
int etat = 0; // NAVIGUER DANS LA MACHINE A ETAT
bool fin = false; // DETERMINER SI LE ROBOT A FINI SON TRAJET

int lnd;
int lng;

//////////////////////////////
// AVANCER ///////////////////
//////////////////////////////

void avancer()
{
  analogWrite(D1, 750);   // MOTEUR DROITE : ON
  analogWrite(D2, 800);   // MOTEUR GAUCHE : ON
  digitalWrite(D3, LOW);  // MOTEUR DROITE : AVANT
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}

//////////////////////////////
// ARRETER ///////////////////
//////////////////////////////

void arreter()
{
  digitalWrite(D1, LOW);  // MOTEUR DROITE : OFF
  digitalWrite(D2, LOW);  // MOTEUR GAUCHE : OFF
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
// DEMITOUR //////////////////
//////////////////////////////

void demiTour()
{
  while ( lng == 1 )
  {
    lnd = digitalRead(D5);
    lng = digitalRead(D6);
    rotationDemiTour();
  }
  arreter();
  delay(1000);
  while ( lng == 0 )
  {
    lnd = digitalRead(D5);
    lng = digitalRead(D6);
    rotationDemiTour();
  }
  arreter();
  delay(1000);
}

//////////////////////////////
// ROTATIONDEMITOUR //////////
//////////////////////////////

void rotationDemiTour()
{
  analogWrite(D1, 800);   // MOTEUR DROITE : ON
  analogWrite(D2, 800);   // MOTEUR GAUCHE : ON
  digitalWrite(D3, HIGH); // MOTEUR DROITE : ARRIERE
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
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
  // CONFIGURATION MOTEURS
  pinMode(D1, OUTPUT);  // MOTEUR DROITE VITESSE
  pinMode(D2, OUTPUT);  // MOTEUR GAUCHE VITESSE
  pinMode(D3, OUTPUT);  // MOTEUR DROITE SENS : 0 AVANCER / 1 RECULER
  pinMode(D4, OUTPUT);  // MOTEUR GAUCHE SENS : 0 AVANCER / 1 RECULER
  // CONFIGURATION CAPTEURS
  pinMode(D5, INPUT); // CAPTEUR LIGNE DROITE
  pinMode(D6, INPUT); // CAPTEUR LIGNE GAUCHE
  // CONFIGURATION WIFI
  Serial.begin(115200);
  WiFi.mode(WIFI_OFF);
  delay(500);
  WiFi.mode(WIFI_STA);
  // CONNEXION AU RESEAU WIFI
  WiFi.begin(ssid, password);
  Serial.println("Connection");
  while ( WiFi.status() != WL_CONNECTED )
  {
    delay(500);
    Serial.print(".");
  }
  // AFFICHER LES PARAMETRES DE CONNEXION
  Serial.print("\nConnected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

//////////////////////////////
// LOOP //////////////////////
//////////////////////////////

void loop() {
  // CREATION DU CLIENT
  WiFiClient httpClient;
  // TENTATIVE DE CONNEXION
  int r = 0;
  Serial.print("Tentative de connexion HTTP");
  while ( ( !httpClient.connect(host, httpPort) ) && ( r < 5 ) )
  {
      delay(100);
      Serial.print(".");
      r++;
  }
  if ( r==5 ) { Serial.println("Echec de connection"); }
  else { Serial.println("Connection OK"); }
  // ENVOYER LA REQUETE
  httpClient.print("GET " + LinkGetPath + numeroChambre + " HTTP/1.1\r\n" + "Host: " + host[0] +"." + host[1] + "." + host[2] + "." + host[3] + "\r\n" + "Connection: close\r\n\r\n");
  Serial.println("Envoi OK");
  // RECEVOIR LE MESSAGE
  while ( httpClient.connected() )
  {
    delay(10);
  }
  // AFFICHER LE MESSAGE RECU
  Serial.println("###DEBUT###");
  while(httpClient.available())
  {
    trajetChambre = httpClient.readStringUntil('\n');
    Serial.println(trajetChambre);
  }
  Serial.println("###FIN###");
  // APPELER TOUTES LES DEUX SECONDES
  delay(2000);
  /* BOUCLE POUR SE DEPLACER
   *  Tant que le robot n'est pas retourné à la pharmacie, continuer de permuter dans la machine à état
   */
  while ( fin == false )
  {
    lnd = digitalRead(D5);
    lng = digitalRead(D6);
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
          delay(350);
          etat = 0;
        }
        break;
      case 2 :
      /* ROTATION DU ROBOT D'UN QUART DE TOUR VERS LA GAUCHE
       *  Tant que le capteur gauche de luminosité détecte une ligne noire, récupérer la valeur transmise par ce même capteur et appeler la méthode tournerGauche()
       *  Ensuite, appeler la méthode arreter()
       *  Enfin, passer la valeur de etat à 0
       */
        while ( lng == 1 ) { lnd = digitalRead(D5); lng = digitalRead(D6); tournerGauche(); }
        arreter();
        etat = 0;
        break;
      case 3 : 
      /* ROTATION DU ROBOT D'UN QUART DE TOUR VERS LA DROITE
       *  Tant que le capteur droit de luminosité détecte une ligne noire, récupérer la valeur transmise par ce même capteur et appeler la méthode tournerDroite()
       *  Ensuite, appeler la méthode arreter()
       *  Enfin, passer la valeur de etat à 0
       */
        while ( lnd == 1 ) { lnd = digitalRead(D5); lng = digitalRead(D6); tournerDroite(); }
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
        demiTour();
        arriverPharmacie();
        fin = true;
        break;
      default :
      /* EN CAS D'ERREUR
       *  Appeler la méthode arreter()
       */
        arreter();
        break;
    }
  }
  // Permettre au robot de recevoir une autre instruction
  fin = false; 
}
