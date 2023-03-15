**Explications des fonctions utilisées dans le programme du robot**
---

**Fonctions de communication :**

- _getOrder()_  
Permet d'envoyer une requête au serveur en lui demandant un numéro de chambre. Le numero de chambre est ensuite utilisé dans la fonction getPath().

- _getPath()_   
Permet d'envoyer une requête au serveur en lui demandant le trajet que doit réaliser le robot pour aller à la chambre placée en paramètre de cette fonction.

- _setNode()_  
Permet d'envoyer une requête au serveur en lui indiquant le changement de statut d'une position.
ex : Une position qui était "free" avant que le robot ne s'y trouve devient "booked" suite à l'appel de cette fonction. Et inversement, si une position est "booked" alors que le robot ne s'y trouve plus devient "free" suite à l'appel de cette fonction.

- _getNode()_  
Permet d'envoyer une requête au serveur en lui demandant si la prochaine position à laquelle le robot doit se rendre est libre ou occupée par un autre robot.

- _setPosition()_  
Permet de renvoyer au serveur la position à laquelle se trouve le robot.

**Fonctions de déplacement :**

- _avancer()_  
Permet de mettre en marche les moteurs sur robot pour le faire avancer.

- _arreter()_  
Permet de mettre à l'arrêt les moteurs sur le robot pour faire arrêter tout mouvements.

- _tournerGauche()_  
Permet de mettre en marche uniquement le moteur de droite du robot pour le faire tourner sur la gauche.

- _tournerDroite()_   
Permet de mettre en marche uniquement le moteur de gauche du robot pour le faire tourner sur la droite.

- _donnerMedicament()_  
Permet de mettre en marche un Timer pour simuler la pause d'un médicament dans une des 4 chambres.

- _arriverPharmacie()_  
Permet de mettre en marche un Timer pour marquer un arrêt dans la pharmacie.

- _demiTour()_  
Permet d'effectuer un demi-tour au robot au sein de la pharmacie pour lui permettre de repartir aisément en cas de nouvelle demande.

- _rotationDemiTour()_  
Permet de mettre en marche les moteurs dans un sens opposée afin que le robot fasse une rotation sur lui même.
