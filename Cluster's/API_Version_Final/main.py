import uvicorn
import Base_de_donnée

if __name__ == "__main__":
    uvicorn.run("app:app", port=8000,host='127.0.0.3',reload=True)
    __test__()
    #host_by_default =127.0.0.3:8000
    # DOCUMENTATION :
    # http://127.0.0.3:8000/docs
