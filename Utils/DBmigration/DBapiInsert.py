import pandas as pd
import numpy as np
import requests
api_url = "http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/CreditCardTxnDetails/txn"
emi_api_url = "http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/CreditCardTxnDetails/txn/addEMItxn"

df = pd.read_excel('moneyPlan.xlsx',index_col=None)
newdf = df.to_numpy()


startyear = 2023
monthsData = ["September", "October", "November", "December", "January", "February", "March", "April", "May", "June", "July", "August"]
monthno = 0


for col in range(0,newdf.shape[1],3):
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    currmonth = monthsData[monthno%12]
    if(currmonth=="January"):
        startyear+=1
    print(str(currmonth)+" "+str(startyear))
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

    for row in range(newdf.shape[0]):
        if(pd.isnull(newdf[row][col])==False and newdf[row][col+2]!=1):
            if(newdf[row][col].startswith("AmznCC")):
                if(newdf[row][col+2]==2):
                    print("---------------->EMI txn started for: "+newdf[row][col]+" : "+str(newdf[row][col+1]))
                    noOfEmis = 0
                    tempcol = col
                    prefixstring = newdf[row][col]
                    while(tempcol+2<newdf.shape[1] and newdf[row][tempcol].startswith(prefixstring)):
                        newdf[row][tempcol + 2] = 1
                        noOfEmis+=1
                        tempcol+=3
                    print("----------------->No of emis: "+str(noOfEmis))
                    emibody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                                  "txnCCused": "AmazonPay ICICI Visa", "txnDetails": str(newdf[row][col]),
                                  "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": True}
                    params = {'noOfEMIs': noOfEmis}
                    response = requests.post(emi_api_url, params=params, json=emibody)
                    print(response.status_code)
                elif(newdf[row][col+2]==0):
                    newdf[row][col + 2] = 1
                    normalbody = {"txnBillingMonth": str(currmonth), "txnBillingYear" : str(startyear),
                                  "txnCCused" : "AmazonPay ICICI Visa", "txnDetails" : str(newdf[row][col]),
                                  "txnAmount" : float(newdf[row][col+1]), "txnIsEmi" : False }
                    response = requests.post(api_url, json=normalbody)
                    print(response.status_code)

            if(newdf[row][col].startswith("StanCharCC")):
                if (newdf[row][col + 2] == 2):
                    print("---------------->EMI txn started for: " + newdf[row][col] + " : " + str(newdf[row][col + 1]))
                    noOfEmis = 0
                    tempcol = col
                    prefixstring = newdf[row][col]
                    while (tempcol + 2 < newdf.shape[1] and newdf[row][tempcol].startswith(prefixstring)):
                        newdf[row][tempcol + 2] = 1
                        noOfEmis += 1
                        tempcol += 3
                    print("----------------->No of emis: " + str(noOfEmis))
                    emibody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                               "txnCCused": "Standard Chartered Ultimate MasterCard-WORLD", "txnDetails": str(newdf[row][col]),
                               "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": True}
                    params = {'noOfEMIs': noOfEmis}
                    response = requests.post(emi_api_url, params=params, json=emibody)
                    print(response.status_code)
                elif (newdf[row][col + 2] == 0):
                    newdf[row][col + 2] = 1
                    normalbody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                                  "txnCCused": "Standard Chartered Ultimate MasterCard-WORLD", "txnDetails": str(newdf[row][col]),
                                  "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": False}
                    response = requests.post(api_url, json=normalbody)
                    print(response.status_code)

            if(newdf[row][col].startswith("REGALIAgold")):
                if (newdf[row][col + 2] == 2):
                    print("---------------->EMI txn started for: " + newdf[row][col] + " : " + str(newdf[row][col + 1]))
                    noOfEmis = 0
                    tempcol = col
                    prefixstring = newdf[row][col]
                    while (tempcol + 2 < newdf.shape[1] and newdf[row][tempcol].startswith(prefixstring)):
                        newdf[row][tempcol + 2] = 1
                        noOfEmis += 1
                        tempcol += 3
                    print("----------------->No of emis: " + str(noOfEmis))
                    emibody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                               "txnCCused": "HDFC Regalia Gold MasterCard-WORLD",
                               "txnDetails": str(newdf[row][col]),
                               "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": True}
                    params = {'noOfEMIs': noOfEmis}
                    response = requests.post(emi_api_url, params=params, json=emibody)
                    print(response.status_code)
                elif (newdf[row][col + 2] == 0):
                    newdf[row][col + 2] = 1
                    normalbody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                                  "txnCCused": "HDFC Regalia Gold MasterCard-WORLD", "txnDetails": str(newdf[row][col]),
                                  "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": False}
                    response = requests.post(api_url, json=normalbody)
                    print(response.status_code)


            if(col<8 and newdf[row][col].startswith("AmznPL")):
                if (newdf[row][col + 2] == 2):
                    print("---------------->EMI txn started for: " + newdf[row][col] + " : " + str(newdf[row][col + 1]))
                    noOfEmis = 0
                    tempcol = col
                    prefixstring = newdf[row][col]
                    while (tempcol + 2 < newdf.shape[1] and newdf[row][tempcol].startswith(prefixstring)):
                        newdf[row][tempcol + 2] = 1
                        noOfEmis += 1
                        tempcol += 3
                    print("----------------->No of emis: " + str(noOfEmis))
                    emibody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                               "txnCCused": "AmazonPayLater OLD",
                               "txnDetails": str(newdf[row][col]),
                               "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": True}
                    params = {'noOfEMIs': noOfEmis}
                    response = requests.post(emi_api_url, params=params, json=emibody)
                    print(response.status_code)
                elif (newdf[row][col + 2] == 0):
                    newdf[row][col + 2] = 1
                    normalbody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                                  "txnCCused": "AmazonPayLater OLD", "txnDetails": str(newdf[row][col]),
                                  "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": False}
                    response = requests.post(api_url, json=normalbody)
                    print(response.status_code)


            if(col<8 and newdf[row][col].startswith("FlkrtPL")):
                if (newdf[row][col + 2] == 2):
                    print("---------------->EMI txn started for: " + newdf[row][col] + " : " + str(newdf[row][col + 1]))
                    noOfEmis = 0
                    tempcol = col
                    prefixstring = newdf[row][col]
                    while (tempcol + 2 < newdf.shape[1] and newdf[row][tempcol].startswith(prefixstring)):
                        newdf[row][tempcol + 2] = 1
                        noOfEmis += 1
                        tempcol += 3
                    print("----------------->No of emis: " + str(noOfEmis))
                    emibody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                               "txnCCused": "FlipkartPayLater OLD",
                               "txnDetails": str(newdf[row][col]),
                               "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": True}
                    params = {'noOfEMIs': noOfEmis}
                    response = requests.post(emi_api_url, params=params, json=emibody)
                    print(response.status_code)
                elif (newdf[row][col + 2] == 0):
                    newdf[row][col + 2] = 1
                    normalbody = {"txnBillingMonth": str(currmonth), "txnBillingYear": str(startyear),
                                  "txnCCused": "FlipkartPayLater OLD", "txnDetails": str(newdf[row][col]),
                                  "txnAmount": float(newdf[row][col + 1]), "txnIsEmi": False}
                    response = requests.post(api_url, json=normalbody)
                    print(response.status_code)


    monthno+=1



