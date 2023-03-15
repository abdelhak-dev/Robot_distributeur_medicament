from Base_de_donnée import *
import datetime
import requests


#ajouter un médicament pour la chambre choisi
def load_setMedicine(MedicineID : int, MedicineName: str):
    return addMedicine(MedicineID, MedicineName)

#week pour l'instant je le laisse comme str , ensuite demain j'implaimente une fct convrs str to datetime
#Le load_Patient ajouter un patient avec son ID 'nss°' , et lui réserver un chambre ,le paramètre week indique la durré de séjour en semaine
def load_addPatient(room:int ,patientID:str, week:str):
    return addPatient(room,patientID,week)

#La fonction load_room ajoute une chambre avec nom de chambre
def load_room(room :int,name:str):
    return addRoom(room,name)

#load_set_condition mets à jour l'état d'un patient selon son ID
def load_set_condition(patientID : str,etat:str):
    return setCondition(patientID,etat)

#appel de la fonction setorder cote bdd
def load_setOrder(order:int,status:str):
    return setorder(order,status)

#ajoute une chambre dans la base de donnée
def load_addOrder(room:int,medicineID:int):
	return add_Order(room,medicineID)

#load_setRoommedicine accord un médicament à chaque chambre
def load_setRoomMedicine(room:int,medicineID:int):
    return setRoomMedicine(room,medicineID)


#load_set_Path défini un trajet pour la chambre en question
def load_setPath(room:int, path:str):
    return setPath(room,path)

#ajouter un robot en lui attribuant un ID_Robot:
def load_addRobot(robotID:int):
    return addRobot(robotID)

#ajouter un robot en lui attribuant un ID_Robot:
def load_addRobotID(robotID:int):
    return addRobotID(robotID)
##La fonction load_position_robot permet de signaler la position (position_robot(robotID))
def load_position_robot(robotID:int,node:int):
    return position_robot(robotID,node)

##La fonction load_getposition_robot permet conaitre la position du robot
def load_getPosition(robotID:int):
    return getPosition(robotID)
###La fonction load_setNode permet de réserver une node pour éviter la collusion
def load_setNode(node:int,booked:str):
    return setNode(node,booked)

###La fonction load_getNode permet de savoir si une case est  réservée  pour éviter la collusion
def load_getNode(node:int):
    return get_Node(node)

#load_getRoommedicine affiche le médicament accordé à la chambre en question
def load_getRoommedicine(room:int):
    return getRoommedicine(room)

#load_get_condition affiche l'état d'un patient selon son ID
def load_patientCondition(patientID:str):
    return getCondition(patientID)

#load_get_Path affiche le trajet pour la chambre en question
def load_getPath(room:int):
    return getPath(room)

#cette fonction affiche les ordres en attentes²
def load_getOrder(robot:int):
	return getorder(robot)

#Cette fonction permets de d'avoir l'état du patient comme réponse
def load_getStats(room:int):
    return getStats(room)

def load_getHistory(robot:int):
    return getHistory(robot)
