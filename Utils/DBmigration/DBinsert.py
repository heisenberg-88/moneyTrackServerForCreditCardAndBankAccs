import pandas as pd
import numpy as np
import math
import psycopg2
import uuid


connection = psycopg2.connect(host="localhost",
                         port="port",
                         user="postgres",
                         password="password",
                         database="dbname",
                         options="-c search_path=SchemaName")

cursor = connection.cursor()

postgres_insert_query = """INSERT INTO CREDIT_CARD_TXNS_DETAILS_MAIN (txn_id, txn_billing_month, txn_billing_year,txn_cc_used,txn_details,txn_amount,txn_isemi) VALUES (%s,%s,%s,%s,%s,%s,%s)"""




df = pd.read_excel('moneyPlan.xlsx',index_col=None)
newdf = df.to_numpy()

summaryFlag = False
topN = 0
while(1):
    flaginput = input('Do you want txns summary ? (y/n)\n')
    if(flaginput=='y' or flaginput=='Y'):
        summaryFlag = True
        while(1):
            tempN = input('How many top txns do you want to see ? \n')
            if(tempN.isnumeric()):
                topN = int(tempN)
                break
            else:
                print('Enter valid numeric value... \n')
        break
    elif(flaginput=='n' or flaginput=='N'):
        summaryFlag = False
        break
    else:
        print("Enter valid input y/Y/n/N \n")


startyear = 2023
monthsData = ["September", "October", "November", "December", "January", "February", "March", "April", "May", "June", "July", "August"]
monthno = 0

dict = {}

scTotal = 0
hdfcregGTotal = 0
amzniciciTotal = 0
amznPL = 0
flkrtPL = 0
print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
for col in range(0,newdf.shape[1],2):
    print("Month : "+str(col))
    currmonth = monthsData[monthno%12]
    if(currmonth=="January"):
        startyear+=1
    print(str(currmonth)+" "+str(startyear))
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    sctemp = 0
    reggtemp = 0
    amzntemp = 0
    amznPLtemp = 0
    flkrtPLtemp = 0
    for row in range(newdf.shape[0]):
        if(pd.isnull(newdf[row][col])==False):
            if(newdf[row][col].startswith("AmznCC")):
                if(summaryFlag==True):
                    if newdf[row][col] in dict.keys():
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    else:
                        dict[newdf[row][col]] = 0
                        dict[newdf[row][col]]+=newdf[row][col+1]

                    print(newdf[row][col])
                    print(newdf[row][col+1])

                    unique_id = uuid.uuid4()
                    record_to_insert = (str(unique_id),str(currmonth),str(startyear),'AmazonPay ICICI Visa',str(newdf[row][col]),str(newdf[row][col+1]),False)
                    cursor.execute(postgres_insert_query, record_to_insert)
                amzntemp+=newdf[row][col+1]
            if(newdf[row][col].startswith("StanCharCC")):
                if(summaryFlag==True):
                    if newdf[row][col] in dict.keys():
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    else:
                        dict[newdf[row][col]] = 0
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    print(newdf[row][col])
                    print(newdf[row][col+1])

                    unique_id = uuid.uuid4()
                    record_to_insert = (str(unique_id),str(currmonth),str(startyear),'Standard Chartered Ultimate MasterCard-WORLD',str(newdf[row][col]),str(newdf[row][col+1]),False)
                    cursor.execute(postgres_insert_query, record_to_insert)
                sctemp+=newdf[row][col+1]
            if(newdf[row][col].startswith("REGALIAgold")):
                if(summaryFlag==True):
                    if newdf[row][col] in dict.keys():
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    else:
                        dict[newdf[row][col]] = 0
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    print(newdf[row][col])
                    print(newdf[row][col+1])

                    unique_id = uuid.uuid4()
                    record_to_insert = (str(unique_id),str(currmonth),str(startyear),'HDFC Regalia Gold MasterCard-WORLD',str(newdf[row][col]),str(newdf[row][col+1]),False)
                    cursor.execute(postgres_insert_query, record_to_insert)
                reggtemp+=newdf[row][col+1]
            if(col<8 and newdf[row][col].startswith("AmznPL")):
                if(summaryFlag==True):
                    if newdf[row][col] in dict.keys():
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    else:
                        dict[newdf[row][col]] = 0
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    print(newdf[row][col])
                    print(newdf[row][col+1])

                    unique_id = uuid.uuid4()
                    record_to_insert = (str(unique_id),str(currmonth),str(startyear),'AmazonPayLater OLD-CLOSED',str(newdf[row][col]),str(newdf[row][col+1]),False)
                    cursor.execute(postgres_insert_query, record_to_insert)
                amznPLtemp+=newdf[row][col+1]
            if(col<8 and newdf[row][col].startswith("FlkrtPL")):
                if(summaryFlag==True):
                    if newdf[row][col] in dict.keys():
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    else:
                        dict[newdf[row][col]] = 0
                        dict[newdf[row][col]]+=newdf[row][col+1]
                    print(newdf[row][col])
                    print(newdf[row][col+1])

                    unique_id = uuid.uuid4()
                    record_to_insert = (str(unique_id),str(currmonth),str(startyear),'FlipkartPayLater OLD-CLOSED',str(newdf[row][col]),str(newdf[row][col+1]),False)
                    cursor.execute(postgres_insert_query, record_to_insert)
                flkrtPLtemp+=newdf[row][col+1]

    scTotal+=sctemp
    hdfcregGTotal+=reggtemp
    amzniciciTotal+=amzntemp
    amznPL+=amznPLtemp
    flkrtPL+=flkrtPLtemp
    print("                                ")
    print("                                ")
    print("approximate figures...")
    print("Standard Chartered Ultimate in month "+currmonth+" "+str(startyear)+" : "+str(sctemp))
    print("HDFC Regalia Gold in month "+currmonth+" "+str(startyear)+" : "+str(reggtemp))
    print("AmazonPay icici in month "+currmonth+" "+str(startyear)+" : "+str(amzntemp))
    if(col==8):
        print("Amazon PayLater & Flipkart PayLater are closed after this month")
    if(col<8):
        print("Amazon PayLater in month "+currmonth+" "+str(startyear)+" : "+str(amznPLtemp))
        print("Flipkart PayLater in month "+currmonth+" "+str(startyear)+" : "+str(flkrtPLtemp))
    print("                                                         ")
    print("-> Total spent on Credit Cards in month "+currmonth+" "+str(startyear)+" : "+str(sctemp+reggtemp+amzntemp+amznPLtemp+flkrtPLtemp))
    print("                                ")
    print("                                ")

    monthno+=1
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")


connection.commit()
count = cursor.rowcount
print(count, "Record inserted successfully into mobile table")


print("                                ")
print("                                ")
print("TOTAL:")
print("approximate figures...")
print("Standard Chartered Ultimate Total: "+str(scTotal))
print("HDFC Regalia Gold Total: "+str(hdfcregGTotal))
print("AmazonPay icici Total: "+str(amzniciciTotal))
print("Amazon PayLater Total: "+str(amznPL))
print("Flipkart PayLater Total: "+str(flkrtPL))
print("                                                                ")
print("--> Total spent on Credit Cards: "+str(scTotal+hdfcregGTotal+amzniciciTotal+amznPL+flkrtPL))

print("                                ")
print("                                ")

if(summaryFlag==True):
    print("Showing top "+str(topN)+" txns from data....")
    sorteddict = sorted(dict.items(), reverse=True,key=lambda item: float(item[1]))
    for dataline in sorteddict:
        if(topN==0):
            break
        print(str(dataline[0]) + " : "+str(dataline[1]))
        topN-=1
