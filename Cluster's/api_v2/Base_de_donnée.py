#-*- coding: utf-8 -*-
''' Exemple pour IMERIR 3ème année (bac+3) :
    Gestionnaire de la base de données WEATHER

    *   il est nécessaire de lancer ce code une fois en direct afin de créer la
        base de données initiales
        . createBase         crée la base avec sa table des prévisions
        
    *   cette bibliothèque cntient les fonctions nécessaires à la gestion des
        prévisions météorologiques :
        
        . connectBase        connexion à la base de données

        . addForecast        ajouter des prévisions depuis OPENWEATHE
        . forecasts          générateur / liste (filtrée) des prévisions


'''
import sqlite3
from sqlite3 import Error
from pathlib import Path
import datetime
databaseName = "Pharmacie.db"


def connectBase():
    ''' return a connector to the database
    '''
    try:
        conn = sqlite3.connect(databaseName)
        return conn
    except:
        return False
    
def baseExists():
    ''' check if a database exists with the appropriate name
            . we check if the file exists
            . AND we check if we can open it as a sqlite3 database
    '''
    file = Path(databaseName)
    if file.exists () and connectBase():
        return True
    return False

def createBase():

    #création de la base 
    try:
        conn = sqlite3.connect(databaseName)
    except Error as e:
        quit

    c = conn.cursor()
    #table patient
    c.execute(''' CREATE TABLE Patient(
                                    patientID VARCHAR(21),
                                    week TEXT,
                                    room INT,
                                    etat VARCHAR(10))''')
    #table robot
    c.execute('''CREATE TABLE ROBOT (
                        robotID        int,
                        pos            int,
                        posArrive      int,
                        dateLivraison   text,
                        statut       VARCHAR(10))''')

    c.execute('''CREATE TABLE MEDICAMENT (
                        MedicineID        int,
                        room              INT,
                        MedicineName     VARCHAR(10))''')

    c.execute('''CREATE TABLE ROOM(
                            room        INT,
                            room_name     VARCHAR(10),
                            path        VARCHAR(15)) ''')


    conn.commit()
    conn.close()





def addRobot(robotID, pos, posArrive, dateLivraison, statut):
    ''' add Robot in Robot table
    '''

    # control parameters
    if type(robotID)   != type(1) : return "robotID not correct"
    if type(pos)  != type(1) :              return "pos not correct"
    if type(posArrive)  != type(1) :              return "posArrive not correct"
    if type(dateLivraison)  != type('A') or len(dateLivraison)<=0:return "dateLivraison not correct"
    if type(statut)   != type('A') or len(statut) <= 0: return "statut not correct"

    
    
    with connectBase() as conn:   
        c = conn.cursor()

        # in order to avoid replication, we control if this record already exists:
        # in this example, we try to delete directly the potential duplicate
        rSQL = '''DELETE FROM ROBOT WHERE robotID = '{}'
                                           AND pos = {}
                                           AND posArrive = {}
                                           AND dateLivraison = '{}'
                                           AND statut = '{}' ;'''
        c.execute(rSQL.format(robotID, pos, posArrive, dateLivraison, statut))

        #and now, insert 'new' record (really new or not) 
        rSQL = '''INSERT INTO ROBOT (robotID, pos, posArrive, dateLivraison, statut)
                        VALUES ('{}', '{}', '{}','{}','{}') ; '''

        c.execute(rSQL.format(robotID, pos, posArrive, dateLivraison, statut))
        conn.commit()
    return True


def addMedicine(MedicineID, MedicineName):
    ''' add Medicament in Medicament table
    '''
    # control parameters
    if type(MedicineID) != type(1): return "idMedicament not correct"
    if type(MedicineName) != type('A') or len(MedicineName) <= 0: return "MedicineName not correct"

    with connectBase() as conn:
        c = conn.cursor()

        # in order to avoid replication, we control if this record already exists:
        # in this example, we try to delete directly the potential duplicate
        rSQL = '''DELETE FROM MEDICAMENT WHERE MedicineID = '{}'
                                           AND MedicineName = '{}' ;'''
        c.execute(rSQL.format(MedicineID, MedicineName))

        # and now, insert 'new' record (really new or not)
        rSQL = '''INSERT INTO MEDICAMENT (MedicineID, MedicineName)
                        VALUES ('{}', '{}') ; '''

        c.execute(rSQL.format(MedicineID, MedicineName, ))
        conn.commit()
    return True


#fonction d'essai sans room & room_name !!!
def addPatient(room:int, patientID:str,week:str):
    #if type(room) != type(1): return "room not correct"
    if type(room) != type(1):return "room not correct"
    if type(patientID) != type('A'): return "patientID not correct"
    if type(week) != type('2020-11-11'): return "week not correct"

    with connectBase() as conn:
        try:
            c = conn.cursor()
            # in order to avoid replication, we control if this record already exists:
            # in this example, we try to delete directly the potential duplicate
            rSQL = '''DELETE FROM PATIENT WHERE room = '{}'
                                                AND patientID = '{}'
                                                AND week = '{}' ;'''
            c.execute(rSQL.format(room,patientID,week))
            #and now, insert 'new' record (really new or not)
            rSQL = '''INSERT INTO PATIENT (room,patientID,week) VALUES ('{}','{}','{}') ; '''
            c.execute(rSQL.format(room,patientID,week)) #patientID,room,room,week)
            conn.commit()
        except Error as e:
            print(e)
    return True


def addRoom(room:int,name:str):
    # control parameters
    if type(room) != type(1): return "room not correct"
    if type(name) != type('1'): return "patientID not correct"
    with connectBase() as conn:
        c = conn.cursor()
        # in order to avoid replication, we control if this record already exists:
        # in this example, we try to delete directly the potential duplicate
        rSQL = '''DELETE FROM ROOM WHERE room = '{}'
                                            AND room_name = '{}';'''
        c.execute(rSQL.format(room,name))
        # and now, insert 'new' record (really new or not)
        rSQL = '''INSERT INTO ROOM  (room,room_name)
                            VALUES ('{}','{}') ; '''

        c.execute(rSQL.format(room,name))
        conn.commit()
    return True

def setCondition(patientID:str, etat:str):
    if type(patientID) != type('1'): return "patientID not correct"
    if type(etat) != type('A'): return "form 'etat' not correct"

    print(patientID, etat)
    with connectBase() as conn:
        c = conn.cursor()
        # and now, insert 'new' record (really new or not)
        rSQL = '''UPDATE PATIENT SET etat ='{}'  WHERE patientID = '{}'; '''

        c.execute(rSQL.format(etat,patientID))
        conn.commit()
    return True

def getCondition(patientID:str):
    if type(patientID) != type('1'): return "patientID not correct"
    with connectBase() as conn:
        c = conn.cursor()
        # and now, insert 'new' record (really new or not)
        rSQL = '''SELECT etat FROM PATIENT WHERE patientID = '{}'; '''

        c.execute(rSQL.format(patientID))
        val= c.fetchall()

        return val[0][0]
    conn.close()


#week argument is opptionnel
def setRoomMedicine(room:int,medicineID:int):
    if type(room) != type(1): return "room not correct"
    if type(medicineID) != type(1): return "medicineID not correct"
    with connectBase() as conn:
        c = conn.cursor()
        # and now, insert 'new' record (really new or not)
        rSQL = '''DELETE FROM MEDICAMENT WHERE medicineID='{}'
                                            AND room = '{}';'''
        c.execute(rSQL.format(medicineID, room))
        rSQL = '''UPDATE  MEDICAMENT SET room='{}'
                                WHERE medicineID='{}';'''
        c.execute(rSQL.format(room,medicineID))
        return True


def getRoommedicine(room:int):
    # control parameters : week is opptionnel
    if type(room) != type(1): return "room not correct"
    #if type(week) != type('1'): return "week not correct"
    with connectBase() as conn:
        c = conn.cursor()
        # in order to avoid replication, we control if this record already exists:
        # in this example, we try to delete directly the potential duplicate
        rSQL = '''SELECT MedicineID FROM MEDICAMENT WHERE room = '{}'; '''
        c.execute(rSQL.format(room))
        val = c.fetchall()
    return val[0][0]

#def getStats(): opptionnel à faire plutards return =>list



#getState week room medicine,state

"""def getState(room:int,medicineID:int,state:str):
    # control parameters
    if type(room) != type(1): return "room not correct"
    if type(medicineID) != type(1): return "medicineID not correct"
    if type(state) != type('A'): return "state not correct"
    with connectBase() as conn:
        c = conn.cursor()
        # and now, insert 'new' record (really new or not)
        rSQL = '''SELECT etat FROM Patient WHERE '{}' ; '''
        c.execute(rSQL.format(room,medicineID,state))
        conn.commit()
    return c.execute(rSQL.format(room,medicineID,state))"""

def setPath(room:int,path:str):
    if type(room)!= type(1):return "room not correct"
    if type(path) != type('A1'): return "path not correct"

    with connectBase() as conn:
        c= conn.cursor()
        # and now, insert 'new' record (really new or not)
        rSQL = '''UPDATE ROOM SET room='{}',path='{}'
                                WHERE room='{}'; '''
        c.execute(rSQL.format(room,path,room))
        conn.commit()
    return True


def getPath(room:int):
    if type(room)!= type(1):return "room not correct"

    with connectBase() as conn:
        c= conn.cursor()
        # and now, insert 'new' record (really new or not)
        rSQL = '''SELECT path FROM ROOM WHERE room='{}'; '''
        c.execute(rSQL.format(room))
        val = c.fetchone()
    return val[0]

def __test__():
    ''' unit tests of this module functions
    '''
    print("test de l'existence de la base")
    if not baseExists():
        print("..je crée la base")
        createBase()
    else:
        print("..", "la base existe")
    #patientID,room,week,etat
    #print("ajout d'un nouveau patient")
    #print("..", addPatient(1,"123123123","2021-11-11")) #room:int, patientID:str,week:str
    #print("ajout d'un nouveau robot")
    #print("..", addRobot(1, 1, 2,'2018-12-19 09:26:03',"non_affecte"))
        
if __name__=='__main__':  #'__Base_de_donnée__'
    __test__()
