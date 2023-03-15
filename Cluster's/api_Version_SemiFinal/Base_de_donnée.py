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
from datetime import datetime

databaseName = "Pharmacie.db"


def connectBase():
    ''' return a connector to the database
    '''
    try:
        conn = sqlite3.connect(databaseName)
        return conn
    except:
        return False

def VerifTables():
    try:
        conn = sqlite3.connect(databaseName)
    except Error as e:
        quit

    c = conn.cursor()
    try:
        c.execute("SELECT 1 FROM Patient;")
        c.execute("SELECT 1 FROM BOOKED;")
        c.execute("SELECT 1 FROM MOUVEMENTS;")
        c.execute("SELECT 1 FROM ROOM;")
        c.execute("SELECT 1 FROM HISTORICORDER;")
        c.execute("SELECT 1 FROM CHOIX;")

    except Error as notable:
        conn.close()
        return False

    conn.close()
    return True

def baseExists():
    ''' check if a database exists with the appropriate name
            . we check if the file exists
            . AND we check if we can open it as a sqlite3 database
    '''
    file = Path(databaseName)
    if file.exists () and connectBase():
        return True
    return False
#table robot
#roomArrive represente la "chambre de distination"
# #pos == node = room
def createBase():

    #création de la base 
    try:
        conn = sqlite3.connect(databaseName)
    except Error as e:
        quit

    c = conn.cursor()
    #table patient
    c.execute(''' CREATE TABLE Patient(
                                    patientID VARCHAR(21) NOT NULL PRIMARY KEY,
                                    week TEXT,
                                    room INT,
                                    etat VARCHAR(10),
                                    FOREIGN KEY(room) REFERENCES MEDICAMENT (room))''')

    c.execute('''CREATE TABLE BOOKED (
                        node         INT  PRIMARY KEY,
                        etat           VARCHAR(10))''')

        ##robotID         INT NOT NULL PRIMARY KEY
    c.execute('''CREATE TABLE MOUVEMENTS (
                            robotID         INT,
                            node            INT, 
                            time  text,
                            FOREIGN KEY(node) REFERENCES BOOKED(node))''')

    c.execute('''CREATE TABLE HISTORICORDER (
                                robotID         INT,
                                orderID         INTEGER PRIMARY KEY AUTOINCREMENT ,
                                room            INT,
                                medicineID      INT,
                                status          INT,
                                time            TEXT )''')

    c.execute('''CREATE TABLE MEDICAMENT (
                        MedicineID        INT NOT NULL PRIMARY KEY,
                        room              INT,
                        MedicineName     VARCHAR(10),
                        week            TEXT,
                        FOREIGN KEY(room) REFERENCES ROOM(room))''')

    c.execute('''CREATE TABLE ROOM (
                            room           INT NOT NULL PRIMARY KEY,
                            room_name     VARCHAR(10),
                            path          VARCHAR(15))''')

    c.execute('''CREATE TABLE CHOIX ( orderID INTEGER PRIMARY KEY AUTOINCREMENT , 
                                     room INT,
                                     FOREIGN KEY(orderID) REFERENCES HISTORICORDER(orderID ))''')
    conn.commit()
    
    conn.close()
    

def addRobotID(robotID:int):
    ''' add Robot in MOUVEMNTS table
        '''
    # control parameter
    if type(robotID) != type(1): return{"code":404 ,"error" : "robotID not correct"}
    with connectBase() as conn:
        c = conn.cursor()
        # in order to avoid replication, we control if this record already exists:
        # in this example, we try to delete directly the potential duplicate
        rSQL = '''DELETE FROM MOUVEMENTS WHERE robotID = '{}';'''
        c.execute(rSQL.format(robotID))
        # and now, insert 'new' record (really new or not)
        rSQL = '''INSERT INTO MOUVEMENTS (robotID)
                            VALUES ('{}'); '''
        c.execute(rSQL.format(robotID))
        conn.commit()
    return "Done,robot added"

def position_robot(robotID,node):
    if type(robotID) != type(1):return {"code":404,"error": "robotID not correct"}
    if type(node) != type(1):return {"code":404,"error": "node not correct"}
    with connectBase() as conn:
            time = datetime.now()
            c = conn.cursor()
            try:
                # and now, insert 'new' record (really new or not)
                rSQL = '''INSERT INTO MOUVEMENTS (robotId,node,time)
                            VALUES ('{}','{}','{}');'''
                c.execute(rSQL.format(robotID,node,time))
                conn.commit()
                return {"code": 200}
            except:
                rSQL = ''' UPDATE MOUVEMENTS SET robotID ='{}',node = '{}',time = '{}' 
                                            WHERE MedicineID = '{}';'''
                c.execute(rSQL.format(robotID, node, time,robotID))
                conn.commit()
                return {"code":200}

def getPosition(robotID:int):
    if type(robotID) != type(1):return {"code":404,"error": "robotID not correct"}
    with connectBase() as conn:
        c = conn.cursor()
        rSQL = '''SELECT node, time FROM MOUVEMENTS WHERE robotID = '{}' LIMIT 1 ;'''
        c.execute(rSQL.format(robotID))
        val = c.fetchall()
        return {"robot":robotID, "node":val[0][0], "timestamp":val[0][1]}


def setNode(node, booked):
    if type(node) != type(1): return {"code": 404, "error": "node not correct"}
    if type(booked) != type('A'): return {"code": 404, "error": " not booked "}
    with connectBase() as conn:
        try:
            c = conn.cursor()
            rSQL = ''' INSERT INTO BOOKED (node,etat) VALUES ('{}','{}');'''
            c.execute(rSQL.format(node, booked))
            conn.commit()
            return {"code": 200, "booked": booked}
        except:

            rSQL = ''' UPDATE BOOKED SET etat = '{}' WHERE node = '{}';'''
            c.execute(rSQL.format(booked,node))
            conn.commit()
            return {"code": 200, "booked": booked}



#la fonction getNode permet de savoir si l
def get_Node(node):
    if type(node) != type(1): return {"code": 404, "error": "node not correct"}
    booking_option = ["Yes", "No"]
    with connectBase() as conn:
        c = conn.cursor()
        rSQL = ''' SELECT etat FROM BOOKED WHERE node = '{}'; '''
        c.execute(rSQL.format(node))
        verif = c.fetchone()
        return {"code": 200, "booked": verif[0]}


#add new Medicine with an ID and a Name in the DB table "MEDICAMENT"
def addMedicine(MedicineID, MedicineName):
    ''' add Medicament in Medicament table
    '''
    # control parameters
    if type(MedicineID) != type(1): return {"code":404, "error": "MedicineID not correct"}
    if type(MedicineName) != type('A') or len(MedicineName) <= 0:return {"code":404, "error": "MedicineNamne not correct"}
    with connectBase() as conn:

            c = conn.cursor()
            
            try:
            			# in order to avoid replication, we control if this record already exists:
            			# in this example, we try to update directly the potential duplicate
                rSQL = '''INSERT INTO MEDICAMENT (MedicineID, MedicineName)
            										VALUES ('{}','{}' );'''
                c.execute(rSQL.format(MedicineID, MedicineName,))
                conn.commit()
            except:
            			# and now, we update
                rSQL = '''UPDATE MEDICAMENT SET MedicineName ='{}' 
            										WHERE MedicineID = '{}';'''
                c.execute(rSQL.format(MedicineName, MedicineID,))
                conn.commit()
                return {"code": 200}
            return {"code": 200}


#Add a Patient with ID and room to book also the nb weeks passed
#fonction d'essai sans room & room_name !!!
def addPatient(room:int, patientID:str,week:str):
    if type(room) != type(1):return {"code":404 , "error":"room not correct"}
    if type(patientID) != type('A'): return {"code":404 , "error":"patientID not correct"}
    if type(week) != type('2020-11-11'): return {"code":404 , "error":"week not correct"}
    with connectBase() as conn:
        try:
            c = conn.cursor()
            rSQL = '''INSERT INTO PATIENT (room,patientID,week)
                                VALUES('{}','{}','{}')'''
            c.execute(rSQL.format(room,patientID,week))
            conn.commit()
            return {"code": 200, "PatientID": patientID}
        except Error:
            return {"code": 404, "error": "patient already in database "}

#Add aroom and have the medicine given  as a result
#fonction d'essai sans room,room_name !!!
def addRoom(room:int,name:str):
    # control parameters
    if type(room) != type(1): return {"code":404, "error": "room not correct"}
    if type(name) != type('1'): return {"code":404, "error": "name not correct"}
    with connectBase() as conn:
        c = conn.cursor()
        try:
            # in order to avoid replication, we control if this record already exists:
            # in this example, we try to update directly the potential existed version
            rSQL = '''INSERT INTO ROOM  (room,room_name)
                                VALUES ('{}','{}') ; '''
            c.execute(rSQL.format(room,name))
            conn.commit()
        except:
            c = conn.cursor()
            # and now, we update
            rSQL = '''UPDATE ROOM SET room_name ='{}'  WHERE room = '{}';'''
            c.execute(rSQL.format(name,room))
            conn.commit()
            return {"code": 200}
    return {"code": 200}

def setCondition(patientID:str, etat:str):
    if type(patientID) != type('1'): return {"code":404 , "error":"patientID not correct"}
    if type(etat) != type('A'): return {"code":404 , "error":"form 'etat' not correct"}
    if (etat) not in ["Dead", "Cured", "Stable"]:return {"code": 404, "error": "non conforme aux attentes"}
    print(patientID,etat)
    with connectBase() as conn:
        c = conn.cursor()
        try:
            #we select the items needed for our request
            rSQL = "SELECT COUNT(*) FROM PATIENT WHERE patientID = '{}';"
            c.execute(rSQL.format(patientID))
            conn.commit()
            val = c.fetchone()
            if val[0] == 0: return {"code": 404, "error": "pas de patientID correspondant"}
            # and now, we update
            rSQL = '''UPDATE PATIENT SET etat ='{}'  WHERE patientID = '{}'; '''
            c.execute(rSQL.format(etat,patientID))
            conn.commit()
        except:
            return {"code":404, "error":"erreur d'update"}

    return {"code": 200}

def getCondition(patientID:str):
    if type(patientID) != type('1'): return {"code":404,"error": "patientID not correct"}
    with connectBase() as conn:
        c = conn.cursor()
        try:
            #we select the items needed for our request
            rSQL = '''SELECT etat FROM PATIENT WHERE patientID = '{}'; '''
            c.execute(rSQL.format(patientID))
            val = c.fetchall()
        except:
            return {"code":404 , "error":"erreur du SELECT"}
    return {"condition" : val[0][0]}


#week argument is opptionnel
#!!!REMARQUE
#QUAND on ajoute {room !!!une room qui existe déja si non il dit ok ,
# mais il fait rien }et {medicineID},
# alors le midicineName ne se met pas à jour
def setRoomMedicine(room:int,medicineID:int):
    if type(room) != type(1): return {"code":404 , "error":"room not correct"}
    if type(medicineID) != type(1): return {"code":404 , "error":"medicineID not correct"}
    with connectBase() as conn:
        c = conn.cursor()
        try:
            #we update the medicine giveng to the room
            rSQL = '''UPDATE  MEDICAMENT SET room ='{}'
                                WHERE medicineID='{}';'''
            c.execute(rSQL.format(room,medicineID))
            conn.commit()
        except:
            return {"code": 404, "error": "erreur de l'upload"}
    return {"code": 200}



def getRoommedicine(room:int):
    # control parameters : week is opptionnel
    if type(room) != type(1): return {"code":404, "error": "room not correct"}
    #if type(week) != type('1'): return "week not correct"
    with connectBase() as conn:
        c = conn.cursor()
        # we select the items needed for our request:
        rSQL = '''SELECT MedicineID FROM MEDICAMENT WHERE room = '{}'; '''
        c.execute(rSQL.format(room))
        val = c.fetchall()
    return {"room":room, "medicine":val[0][0]}


def setPath(room:int,path:str):
    if type(room)!= type(1):return {"code":404, "error": "room not correct"}
    if type(path) != type('A1'): return {"code":404, "error": "Path not correct"}

    with connectBase() as conn:
        c= conn.cursor()
        # and now, we update
        rSQL = '''UPDATE ROOM SET room='{}',path='{}'
                                WHERE room='{}'; '''
        c.execute(rSQL.format(room,path,room))
        conn.commit()
    return {"code": 200}


def getPath(room:int):
    if type(room)!= type(1):return {"code":404, "error": "room not correct"}

    with connectBase() as conn:
        c= conn.cursor()
        # we select the items needed for our request
        rSQL = '''SELECT path FROM ROOM WHERE room='{}'; '''
        c.execute(rSQL.format(room))
        val = c.fetchone()

        c = conn.cursor()
        # we delete the item from the queue
        rSQL = '''DELETE  FROM CHOIX LIMIT 1  '''
        c.execute(rSQL.format(room))
        conn.commit()
    return {"room": room, "path": val[0]}


def setorder(order,status):
    if type(order) != type(1): return {"code":404, "error": "order not correct"}
    if type(status) != type('DONE'): return {"code":404, "error": "status not correct"}
    if (status) not in ["ToDo", "Delivered", "running","Done"]:return {"code": 404, "error": "non conforme aux attentes"}

    with connectBase() as conn:
        c = conn.cursor()
        # we update the existed items
        rSQL = ''' UPDATE HISTORICORDER SET statut ='{}'
                                WHERE orderID='{}'; '''
        c.execute(rSQL.format(status,order))
        conn.commit()
    return True



def add_Order(room:int,medicineID:str):
    if type(room) != type(1):return {"code":404, "error": "room not correct"}
    if type(medicineID) != type(1): return {"code": 404, "error": "medicineID not correct"}
    with connectBase() as conn:
        c = conn.cursor()
        try:
            
            # and now, insert 'new' record (really new or not)
            status = "ToDo"
            rSQL = '''INSERT INTO HISTORICORDER (room,medicineID,status)
                                        VALUES ('{}','{}','{}') ; '''
            c.execute(rSQL.format(room,medicineID,status))
            conn.commit()
            rSQL = '''INSERT INTO CHOIX (room)
                                VALUES ('{}') ; '''
                                
            c.execute(rSQL.format(room))
            conn.commit()
            # we select the items needed for our request
            rSQL = ''' SELECT orderID FROM CHOIX ORDER BY orderID DESC  LIMIT 1 '''
            c.execute(rSQL)
            orderID = c.fetchall()
            return {"order":orderID[0][0]}
        except:
            return {"code":404,"error":"not recorded"}


def getorder(robot):
    with connectBase() as conn:
        c = conn.cursor()
        # we select the items needed for our request
        rSQL = '''SELECT orderID, room FROM CHOIX'''
        c.execute(rSQL)
        val = c.fetchall()
        while val != []:
            rSQL = ''' SELECT medicineID FROM HISTORICORDER where orderID = {} ;'''
            c.execute(rSQL.format(val[0][0]))
            val2 = c.fetchone()

            rSQL = '''UPDATE HISTORICORDER set robotID = {} where orderID = {};'''
            c.execute(rSQL.format(robot, val[0][0]))

            return {"order": val[0][0], "room": val[0][1], "medecine": val2[0]}

        else:
            return {"order": 0,"room":0,"medecine":0 }
        
#fonction complaimentaire de getStats qui renvoi medicineID
def medcinefor_getorder(room:int):
    if type(room)!= type(1) : return{"code":404, "error": "room not correct"}
    with connectBase() as conn:
        c = conn.cursor()
        rSQL = '''SELECT medicineID FROM MEDICAMENT WHERE room ='{}'; '''
        c.execute(rSQL.format(room))
        medicineID = c.fetchone()
    return medicineID[0]

#getState week room medicine,state
def getStats(room:int):
    if type(room) != type(1):return {"code":404, "error": "room not correct"}
    with connectBase() as conn:
        c=conn.cursor()
        rSQL=''' SELECT week, room, etat FROM Patient'''
        c.execute(rSQL)
        patient = c.fetchone()
    return {"list":[{"week":patient[0],"room":patient[1] ,"medicine":medcinefor_getorder(room),"condition":patient[2]}]}

#History json for java interface
def getHistory(robot:int):
        if robot == 0:
            with connectBase() as conn:
                c = conn.cursor()
                # we select the items needed for our request
                rSQL='''SELECT JSON_object('robotID',robotID, 'node',node) FROM MOUVEMENTS'''
                c.execute(rSQL)
                val = c.fetchall()
                return {"history": val}
        else : 
            with connectBase() as conn:
                c = conn.cursor()
                rSQL='''SELECT JSON_OBJECT('robotID',robotID, 'node',node) FROM MOUVEMENTS where robotID={}'''
                c.execute(rSQL.format(robot))
                val = c.fetchall()
                print(val[0][0])
                return {"robot" : robot, "history": val[0][0]}
            

#Test function de elements in DB
def __test__():
    ''' unit tests of this module functions
    '''
    print("test de l'existence de la base")
    if not baseExists():
        print("..je crée la base")
        createBase()
    else:
        print("..", "la base existe")
    #Test de l'ajout : patientID,room,week,etat
    """
    print("ajout d'un medicine ")
    print("..", addMedicine(1, "Doliprane"))

    print("ajout d'un nouveau patient")
    print("..", addPatient(1,"123123123","2021-11-11"))

    print("ajout d'un nouveau rooomMedicine")
    print("..", setRoomMedicine(1, 1))

    print("ajout d'un nouveau Path")
    print("..", setPath(1,"A0A5G6"))

    print("ajout d'un nouveau PatientCondition")
    print("..", setCondition("123123123", "malade"))

    print("ajout d'un nouveau setRoom")
    print("..", addRoom(1,"Chambre1"))

    #print("ajout d'un nouveau robot")
    #print("..", addRobot(1, 1, 2,'2018-12-19 09:26:03',"non_affecte"))"""
        
if __name__=='__main__':  #'__Base_de_donnée__'
    __test__()
