from Base_de_donnée import *
import datetime
import requests


def load_setMedicine(MedicineID : int, MedicineName: str):
    return addMedicine(MedicineID, MedicineName)
#week pour l'instant je le laisse comme str , ensuite demain j'implaimente une fct convrs str to datetime
def load_addPatient(room:int ,patientID:str, week:str):
    return addPatient(room,patientID,week)

def load_room(room :int,name:str):
    return addRoom(room,name)


def load_set_condition(patientID : str,etat:str):
    return setCondition(patientID,etat)

def load_patientCondition(patientID:str):
    return getCondition(patientID)

def load_setRoomMedicine(room:int,medicineID:int):
    return setRoomMedicine(room,medicineID)

def load_getRoommedicine(room:int):
    return getRoommedicine(room)
def load_setPath(room:int, path:str):
    return setPath(room,path)
def load_getPath(room:int):
    return getPath(room)


