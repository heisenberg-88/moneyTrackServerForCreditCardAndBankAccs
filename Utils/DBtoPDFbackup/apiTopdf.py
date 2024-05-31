import requests
import pandas as pd
api_array = ["http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/CreditCardTxnDetails/txn/allTxns",
             "http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/CreditCardTxnDetails/txn/Summary/allTxns",
             "http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/BankTxnDetails/txn/allTxns",
             "http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/BankTxnDetails/txn/Summary/allTxns",
             "http://192.168.29.179:8080/parth-moneyserver-services/moneyServer/CreditCardTxnDetails/txn/Toptxns/allTxns"]
excel_filename_array = ["CreditCardTxns.xlsx","CreditCardTxnsSummary.xlsx","BankTxns.xlsx","BankSummary.xlsx","TopTxns.xlsx"]

for i in range(5):
    print("Creating : "+excel_filename_array[i])
    response = requests.get(api_array[i])
    data =  response.json()
    df = pd.DataFrame(data)
    xlsx_file_path = excel_filename_array[i]
    df.to_excel(xlsx_file_path, index=False)