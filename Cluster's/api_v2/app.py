from fastapi import FastAPI
from Base_de_donnée import *
from features import *
from fastapi import FastAPI, HTTPException
from sqlite3 import Error
from typing import Optional
import datetime
import json
app = FastAPI()

#message d'entée
@app.get("/", tags=['ROOT'])
async def root():
    return {"Project":" Hello cluster_robot !"}


# addMedicin
@app.get('/setMedicine/{MedicineID}/{MedicineName}')
async def add_medicine(MedicineID : int, MedicineName : str):
    try:
        load_setMedicine(MedicineID, MedicineName)
        return {"code": 200}
    except:
        return {"code": 404, "error": "error"} #error facultative à traiter plustard

@app.get('/addPatient/{room}/{patientID}/{week}')
async def add_patient(room:int,patientID:str,week:str):
    try:
        load_addPatient(room,patientID,week)
        return {"code":200}
    except:
        return {"code":404 , "error":"room occupied"}



@app.get('/setRoom/{room}/{name}')
async def setRoom(room:int,name:str):
    try:
        load_room(room,name)
        return {"code":200}
    except:
        return {"code":404 , "error":"room occupied"}

@app.get('/setPatientCondition/{patientID}/{etat}')
async def setPatientCondition(patientID:str,etat:str):
    try:
        load_set_condition(patientID,etat)
        return {"code":200}
    except:
        return {"code":404,"error":"error"}



@app.get('/getPatientCondition/{patientID}')
async def getPatientCondition(patientID:str):
    try:
        condition= load_patientCondition(patientID)
        #{'condition': '{}'.format(load_patientCondition(patientID))}
        return {"condition": condition}
    except:
        return {"code":404,"error":"error"}

#week est opptionel
@app.get('/setRoomMedicine/{room}/{medicine}')
async def setRoomMedicine(room:int,medicine:int):
    try:
        load_setRoomMedicine(room,medicine)
        return {"code":200}
    except:
        return {"code":404,"error":"error"}

#getRoomMedicine
#week est opptionel
@app.get('/getRoomMedicine/{room}')
async def getRoomMedicine(room:int):
    try:
        medicine=load_getRoommedicine(room)
        return {"room":room, "medicine": medicine}
    except:
        return {"code":404,"error":"error"}


@app.get('/setPath/{room}/{path}')
async def setPath(room:int,path:str):
    try:
        load_setPath(room,path)
        return {'code':200}
    except:
        return {"code":404,"error":"not recorded"}

@app.get('/getPath/{room}')
async def getPath(room:int):
    try:
        path = load_getPath(room)
        return {'room':room,'path':path}
    except:
        return {"code":404, "error":"URI format not correct"}



"""
@app.get('/addPatient/{room_id}/{patient_id}/{week}')
async def addPatient(room_id :int,patientID:int,week:int):
    #test avec le data base

    if room_id not in room:
        if patientID not in patientIDs:
            if week <1:
                raise HTTPException(status_code=404,detail="Item non enregistré ou nom non saisi")
                return {"code":404,"error":"error"}
    else:
        #relier le patientID avec N°SS
        tb_name.append(name)
        print(tb_name)
        return {"code":200}
"""