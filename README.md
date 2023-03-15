# Team 11

Lien Google Drive : https://drive.google.com/drive/folders/1UQt_PxYJhJ00rEch95Y6MIW53G8smAYF?usp=sharing  

---

**Présentation du projet :**
---

Le but du projet est de créer une solution permettant de guider des robots dans un hôpital, afin d’acheminer des traitements anti-COVID, depuis la pharmacie dans les chambres de patients :

- sans contact humain (sécurité sanitaire)
- avec rapidité (efficacité de la distribution logistique)
- avec traçabilité (tous les mouvements seront enregistrés, afin de suivre la traçabilité par horodatage des médicaments délivrés au patient)

L’objectif est de réaliser un test en aveugle (essais cliniques), pour prouver l'efficacité (ou l’inefficacité) de 6 nouveaux médicaments (stockés dans la pharmacie) sur les patients (un par chambre). Un patient, malade du COVID, fait un séjour d’une semaine pendant laquelle le même médicament lui sera administré, 3 fois par jour.

Le superviseur central déterminera de quel médicament sera attribué par patient, et donnera les ordres aux différents robots pour acheminer les traitements de la pharmacie à la chambre appropriée. Au bout d’une semaine de traitement, le patient peut être :
- guéri : il est libéré, 
- dans un état stationnaire : il est redirigé vers un autre hôpital, 
- malheureusement mort

---

**Répartition des rôles :**
---

| Service Web | Base de données | Superviseur | Robot  |
| ----------- | --------------- | ----------- | ------ |
| Abdelhak    | Abdelhak        | Antonin     | Romain |
| Rémi        |                 | Rémi        | Adrian |

_ScrumMaster du projet_ : Adrian

---

**Service Web**
---
**Objectif :**

Le rôle du Service Web est de faciliter le stockage et l'échange d'informations entre les différentes parties. L’outil utilisé pour la délivrance des services est fastAPI, et la base de données est sqlite3, le tout en Python (version supérieure à 3.7).

Le serveur Web Service sera installé en production sur une machine virtuelle.

---

**Superviseur**
---
**Objectif :**

Le rôle du superviseur est le suivi des essais cliniques :
- par semaine, pouvoir saisir quel médicament sera fourni à quel patient (un patient par chambre) et pouvoir saisir le statut de chaque patient à l’issue des traitements (décédé, stationnaire, guéri)
- 3 fois par jour, en déduire les consignes de délivrance pour les robots (le bon médicament dans la bonne chambre)
- fournir les statistiques complètes des mouvements et livraisons des robots (avec horodatage)
- fournir les statistiques de l’efficacité des médicaments (% de guérison, de status quo, de décès) par semaine et par médicament

Ce programme dialoguera avec le service Web (données formatées en JSON) pour stocker/lire les données nécessaires aux traitements indiqués, et avec les robots (fourniture des consignes, récupération de la traçabilité).


---

**Robot**
---
**Objectif :**

Le robot a pour mission d'acheminer les médicaments de la pharmacie à une chambre, selon les consignes du superviseur via le Web Service, puis retourner près de la pharmacie, le tout en respectant les consignes de circulation.

Pour ce faire, le robot doit pouvoir :
- suivre les lignes au sol
- détecter les intersections
- détecter les obstacles (personnel soignant, etc) pour ne pas les percuter
- se localiser grâce aux intersections : le serveur fournit le chemin complet vers les chambres et retour
communiquer en WiFi

Le serveur envoie le chemin complet que doit emprunter le robot pour aller vers les chambres ou la pharmacie. Le robot doit donc réceptionner ce chemin puis le suivre automatiquement. Ce chemin est composé par l’ensemble des intersections à emprunter.

Afin de suivre les mouvements réels des robots, chaque machine doit envoyer au serveur (web service) :
- numéro du robot
- intersection à laquelle il se trouve

---
