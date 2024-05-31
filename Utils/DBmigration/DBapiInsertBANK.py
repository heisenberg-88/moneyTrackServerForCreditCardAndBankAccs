import pandas as pd
import numpy as np
import requests

api_url = "http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/BankTxnDetails/txn"
# api_url = "http://localhost:9100/moneyServer/BankTxnDetails/txn"

df = pd.read_csv("banktxns.csv", index_col=None)
newdf = df.to_numpy()
newdf = np.flip(newdf)

monthArray = {
    "01": "January", "02": "February" , "03": "March", "04": "April",
    "05": "May", "06": "June", "07": "July", "08": "August",
    "09": "September", "10": "October", "11": "November", "12": "December"
}

tempseqNo = 1
data = "x"
for i in newdf:
    datemonthyear = i[6]
    dateString = datemonthyear[8:10]
    yearString = datemonthyear[0:4]
    monthString = datemonthyear[5:7]
    # dateString = datemonthyear[0:2]
    # yearString = datemonthyear[6:10]
    # monthString = datemonthyear[3:5]

    if(dateString!=data):
        data = dateString
        tempseqNo = 1
    if(dateString==data):
        tempseqNo+=1
        print(tempseqNo)
        normalBody = {
                "banktxnBillingMonth": monthArray.get(monthString),
                "banktxnBillingYear" : str(yearString),
                "banktxnBillingDate": str(dateString),
                "bankAccName" : "HDFC Bank Salary Account",
                "banktxnDetails" : str(i[4]),
                "banktxnAmount" : float(i[5]),
                "bankTxnSeqNumOrder" : tempseqNo
        }
        response = requests.post(api_url, json=normalBody)
        print(response.status_code)

    print("--------------------------------------")


# print(newdf[0][6])
# datemonthyear = newdf[0][6]
# dateString = datemonthyear[0:2]
# yearString = datemonthyear[6:10]
# monthString = datemonthyear[3:5]
# normalBody = {
#                 "banktxnBillingMonth": monthArray.get(monthString),
#                 "banktxnBillingYear" : str(yearString),
#                 "banktxnBillingDate": str(dateString),
#                 "bankAccName" : "Bank Of Maharashtra OLD",
#                 "banktxnDetails" : str(newdf[0][4]),
#                 "banktxnAmount" : float(newdf[0][5]),
#                 "bankTxnSeqNumOrder" : tempseqNo
#         }
# response = requests.post(api_url, json=normalBody)
# print(response.status_code)
