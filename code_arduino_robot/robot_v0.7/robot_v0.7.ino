#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h> 
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>
#include <WiFiClient.h>
#include <HCSR04.h>

// TAILLE MEMOIRE FIXE AVEC ALLOCATEUR MONOTONE
StaticJsonDocument <1024> output;
 
// INITIALISATION PINS NODE MCU
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
#define num_robot 1 // NUMERO IDENTIFICATION ROBOT

// INFORMATIONS CONNEXION WIFI
const char *ssid = "IMERIR_IoT";  // IDENTIFIANT
const char *password = "kohWoong5oox";  // MOT DE PASSE

// INFORMATIONS CONNEXION SERVEUR
//String serverName = "http://10.3.7.41:8000/";
String serverName = "http://10.3.1.37:8000/";

// GETORDER
int getRoomToDo = 0;
int numeroChambre = 0;

// GETPATH
String getTrajetToDo = "";
String trajetChambre = "";

// CHAINE DE CARACTERE
int cpt_nombre = 0; // INTERSECTIONS
int cpt_lettre = 1; // DEPLACEMENTS

// MACHINE A ETAT
int etat = 0;

// EVENEMENTS DEPLACEMENT ROBOT
bool finTrajet = false;
bool medicamentDonne = false;

// CAPTEURS LUMINOSITE
int lnd = digitalRead(D5);
int lng = digitalRead(D6);

// CAPTEUR ULTRASON
const int trigPin = D7;
const int echoPin = D8;
float distance;
UltraSonicDistanceSensor distanceSensor(trigPin, echoPin);

//////////////////////////////
// GETORDER //////////////////
//////////////////////////////

int getOrder() {
  if ( WiFi.status() == WL_CONNECTED )
  {
    HTTPClient http;
    String serverPath = serverName + "getOrder";
    http.begin(serverPath.c_str());
    int httpResponseCode = http.GET();
    if ( httpResponseCode > 0 )
    {
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
      String payload = http.getString();
      Serial.println(payload);
      deserializeJson(output, payload);
      int getRoomToDo = output["room"][0];
      Serial.println(getRoomToDo);
      return getRoomToDo;
    }
    else
    {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
      return -1;
    }
    http.end();
  }
  else
  {
    Serial.println("WiFi Disconnected");
    return -1;
  }
}

//////////////////////////////
// GETPATH ///////////////////
//////////////////////////////

String getPath( int room ) {
  if ( WiFi.status() == WL_CONNECTED )
  {
    HTTPClient http;
    String serverPath = serverName + "getPath/" + room ;
    http.begin(serverPath.c_str());
    int httpResponseCode = http.GET();
    if ( httpResponseCode > 0 )
    {
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
      String payload = http.getString();
      Serial.println(payload);
      deserializeJson(output, payload);
      String getTrajetToDo = output["path"][0];
      Serial.println(getTrajetToDo);
      return getTrajetToDo;
    }
    else
    {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
      return "erreur";
    }
    http.end();
  }
  else
  {
    Serial.println("WiFi Disconnected");
    return "erreur";
  }
}
 
//////////////////////////////
// SETPOSITION ///////////////
//////////////////////////////

void setPosition(char a)
{
  if ( WiFi.status() == WL_CONNECTED )
  {
    HTTPClient http;
    String serverPath = serverName + "setPosition/" + num_robot + "/" + a;
    http.begin(serverPath.c_str());
    int httpResponseCode = http.GET();
    if ( httpResponseCode > 0 )
    {
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
      String payload = http.getString();
      Serial.println(payload);
      deserializeJson(output, payload);
    }
    else
    {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
    }
    http.end();
    }
  else
  {
    Serial.println("WiFi Disconnected");
  }
}

//////////////////////////////
// AVANCER ///////////////////
//////////////////////////////
 
void avancer() {
  analogWrite(D1, 700);   // MOTEUR DROITE : ON
  analogWrite(D2, 720);   // MOTEUR GAUCHE : ON
  digitalWrite(D3, LOW);  // MOTEUR DROITE : AVANT
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}
 
//////////////////////////////
// ARRETER ///////////////////
//////////////////////////////
 
void arreter() {
  digitalWrite(D1, LOW);  // MOTEUR DROITE : OFF
  digitalWrite(D2, LOW);  // MOTEUR GAUCHE : OFF
}
 
//////////////////////////////
// TOURNERGAUCHE//////////////
//////////////////////////////
 
void tournerGauche() {
  digitalWrite(D1, HIGH); // MOTEUR DROITE : ON
  digitalWrite(D2, LOW);  // MOTEUR GAUCHE : OFF
  digitalWrite(D3, LOW);  // MOTEUR DROITE : AVANT
}
 
//////////////////////////////
// TOURNERDROITE /////////////
//////////////////////////////
 
void tournerDroite() {
  digitalWrite(D1, LOW);  // MOTEUR DROITE : OFF
  digitalWrite(D2, HIGH); // MOTEUR GAUCHE : ON
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}
 
//////////////////////////////
// DONNERMEDICAMENT //////////
//////////////////////////////
 
void donnerMedicament() {
  delay(5000);
}

//////////////////////////////
// ARRIVERPHARMACIE //////////
//////////////////////////////
 
void arriverPharmacie() {
  delay(1000);
}

//////////////////////////////
// DEMITOUR //////////////////
//////////////////////////////

void demiTour()
{
  while ( lng == 1 ) { lnd = digitalRead(D5); lng = digitalRead(D6); rotationDemiTour(); }
  arreter();
  delay(500);
  while ( lng == 0 ) { lnd = digitalRead(D5); lng = digitalRead(D6); rotationDemiTour(); }
  arreter();
  delay(500);
}

//////////////////////////////
// ROTATIONDEMITOUR //////////
//////////////////////////////

void rotationDemiTour()
{
  analogWrite(D1, 850);   // MOTEUR DROITE : ON
  analogWrite(D2, 870);   // MOTEUR GAUCHE : ON
  digitalWrite(D3, HIGH); // MOTEUR DROITE : ARRIERE
  digitalWrite(D4, LOW);  // MOTEUR GAUCHE : AVANT
}
 
//////////////////////////////
// SETUP /////////////////////
//////////////////////////////
 
void setup() {
  // CONFIGURATION MOTEURS
    // VITESSE
  pinMode(D1, OUTPUT);  // DROITE
  pinMode(D2, OUTPUT);  // GAUCHE
    // SENS ( LOW = AVANCER && HIGH = RECULER )
  pinMode(D3, OUTPUT);  // DROITE
  pinMode(D4, OUTPUT);  // MOTEUR
  // CONFIGURATION CAPTEURS
    // LUMMINOSITE
  pinMode(D5, INPUT);   // DROITE
  pinMode(D6, INPUT);   // GAUCHE
    // ULTRASON
  pinMode(D7, OUTPUT);  // TRIGGER
  pinMode(D8, INPUT);   // ECHO
  // CONFIGURATION WIFI
  Serial.begin(115200); // VITESSE TRANSMISSION WIFI
  WiFi.mode(WIFI_OFF);
  delay(500);
  WiFi.mode(WIFI_STA);
  // CONNEXION WIFI
  WiFi.begin(ssid, password);
  Serial.println("Connection");
  while ( WiFi.status() != WL_CONNECTED )
  {
    delay(500);
    Serial.print(".");
  }
  // AFFICHER PARAMETRES CONNEXION
  Serial.print("\nConnected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}
 
//////////////////////////////
// LOOP //////////////////////
//////////////////////////////
 
void loop() 
{
  // REINITIALISATION VARIABLES
  finTrajet = false;
  medicamentDonne = false;
  etat = 0;
  cpt_nombre = 0;
  cpt_lettre = 1;
  numeroChambre = 0;
  trajetChambre = "";
  /* NUMERO CHAMBRE INVALIDE
   * Si le numéro de chambre n'est pas valide, afficher un message d'erreur
   */
  while ( numeroChambre < 1 || numeroChambre > 4 )
  {
    Serial.println("###DEBUT###");
    numeroChambre = getOrder();
    if ( numeroChambre < 1 || numeroChambre > 4 )
    {
      Serial.println("La chambre n'existe pas ! REDEMARRAGE DU PROGRAMME");
      Serial.println("###FIN###");
    }
    delay(2000);
  }
  /* NUMERO CHAMBRE VALIDE
   * Si le numéro de chambre est valide, demander au service web la chaine de caractère correspondante au trajet permettant de se déplacer jusquà la chambre souhaitée
   */
  if ( numeroChambre >= 1 && numeroChambre <= 4 )
  {
    trajetChambre = getPath(numeroChambre);
    trajetChambre = trajetChambre + "0P";
    Serial.println("trajetChambre à traiter :" + trajetChambre);
    Serial.println("###FIN-Lancement du trajet###");
    /* BOUCLE DEPLACEMENT
     * Tant que le robot n'a pas fini le trajet actuel ( retourné à la pharmacie ), continuer de navigier dans la machine à état
     */
    while ( finTrajet == false )
    {
      lnd = digitalRead(D5);
      lng = digitalRead(D6);
      /* MACHINE A ETAT
       * Permuter entre les différents états dans le but de poursuivre un comportement spécifique
       */
      switch ( etat )
      {
        /* LECTURE CHAINE CARACTERE
         * Lire les trajectoires ( lettres ) de la chaine de caractère un par un ( avec cpt_lettre comme position )
         * Si le caractère lu est un 'F' alors, passer la valeur de etat à 1
         * Sinon si le caractère lu est un 'L' alors, passer la valeur de etat à 2
         * Sinon si le caractère lu est un 'R' alors, passer la valeur de etat à 3
         * Sinon si le caractère lu est un 'P' alors, passer la valeur de etat à 4
         * Lire les intersections ( nombres ) de la chaine de caractère un par un ( avec cpt_nombre comme position )
         * Si l'intersection est 1, 2, 3 ou 4 alors, appeler la fonction arreter(), appeler la fonction donnerMedicament() puis passer la valeur de medicamentDonne à true
         * Sinon si l'intersection est 0 alors, appeler la fonction setPosition() avec comme paramètre la position actuelle dans la chaine de caractère
         * Enfin, incrémenter la valeur de cpt_nombre de 2 et incrémenter la valeur cpt_lettre de 2
         */
        case 0 :
            if ( trajetChambre[cpt_lettre] == 'F' ) { etat = 1; }
            else if ( trajetChambre[cpt_lettre] == 'L' ) { etat = 2; }
            else if ( trajetChambre[cpt_lettre] == 'R' ) { etat = 3; }
            else if ( trajetChambre[cpt_lettre] == 'P' ) { etat = 4; }
            if ( ( trajetChambre[cpt_nombre] == '1' || trajetChambre[cpt_nombre] == '2' || trajetChambre[cpt_nombre] == '3' || trajetChambre[cpt_nombre] == '4' ) && medicamentDonne == false )
            {
              arreter();
              donnerMedicament();
              medicamentDonne = true;
            }
            else if ( trajetChambre[cpt_nombre] == '0' )
            {
              setPosition(trajetChambre[cpt_nombre]);
            }
            cpt_nombre += 2;
            cpt_lettre += 2;
            break;
        /* SUIVRE LIGNE
         * Récupérer les valeurs transmises pas les deux capteurs de luminosité
         * Récupérer et afficher la distance entre le capteur à ultrason et l'obstacle
         * Si la distance est inférieure à 20 centimètres, appeler la fonction arreter() pendant 1 seconde
         * En fonction des valeurs transmises par les deux capteurs de luminosité, appeler les fonctions avancer(), tournerGauche() ou tournerDroite() pour rectifier la trajectoire du robot
         * Ensuite, si le robot rencontre une intesection, regarder le prochain caractère de la chaine de caractère
         * Si ce caractère est un 'F' alors, appeler la fonction avancer() pendant 0,4 seconde dans le but faire traverser l'intersection
         * Sinon, appeler la fonction arreter() pendant 0.4 seconde dans le but de marquer l'intersection
         * Passer la valeur de etat à 0 avec comme paramètre la position actuelle dans la chaine de caractère
         * Enfin, appeler la fonction setPosition()
         */
        case 1 :
            lnd = digitalRead(D5);
            lng = digitalRead(D6);
            /*
            Serial.println(distanceSensor.measureDistanceCm());
            if ( distanceSensor.measureDistanceCm() > 10 || distanceSensor.measureDistanceCm() == -1 )
            {
            */
            if ( ( lnd == 0 ) && ( lng == 0 ) ) { avancer(); }
            else if ( ( lnd == 0 ) && ( lng == 1 ) ) { tournerGauche(); }
            else if ( ( lnd == 1 ) && ( lng == 0 ) ) { tournerDroite(); }
            else if ( ( lnd == 1 ) && ( lng == 1 ) )
            {
              arreter();
              delay(1000);
              if ( trajetChambre[cpt_lettre] == 'F' )
              {
                avancer();
                delay(300);
              }
              etat = 0;
              setPosition(trajetChambre[cpt_nombre]);
            }
            /*
            }
            else
            {
              arreter();
              delay(1000);
            }
            */
            break;
        /* ROTATION ROBOT QUART DE TOUR GAUCHE
         * Tant que le capteur gauche de luminosité détecte une ligne noire, récupérer la valeur transmise par ce même capteur et appeler la fonction tournerGauche()
         * Ensuite, appeler la fonction arreter()
         * Enfin, passer la valeur de etat à 1
         */
        case 2 :
            while ( lng == 1 ) { lnd = digitalRead(D5); lng = digitalRead(D6); tournerGauche(); }
            arreter();
            etat = 1;
            break;
        /* ROTATION ROBOT QUART DE TOUR DROITE
         * Tant que le capteur droit de luminosité détecte une ligne noire, récupérer la valeur transmise par ce même capteur et appeler la fonction tournerDroite()
         * Ensuite, appeler la fonction arreter()
         * Enfin, passer la valeur de etat à 1
         */
        case 3 :
            while ( lnd == 1 ) { lnd = digitalRead(D5); lng = digitalRead(D6); tournerDroite(); }
            arreter();
            etat = 1;
            break;
        /* ROBOT ARRIVE PHARMACIE
         * Appeler la fonction demiTour()
         * Appeler la fonction arriverPharmacie()
         * Enfin, passer la valeur de finTrajet à true et passer la valeur de etat à 0
         */
        case 4 :
            demiTour();
            arriverPharmacie();
            finTrajet = true;
            etat = 0;
            break;
        /* EN CAS D'ERREUR
         * Appeler la fonction arreter()
         */
        default :
            arreter();
            break;
      }
    }
  }
  delay(1000);
}
