# Q2 is a script that returns, for each student, his or her ID and
# the percentage (of all available points) they have scored. 
#   
# You should create and write your results into a .csv file q2Out.csv
# with 2 columns and the header row studentID and PercentageMark.
#

import csv
import csv
with open("exam_for_2019.csv") as csvfile:
    a=csv.reader(csvfile)
    headers=next(a)
    data=[]
    for row in a:
        data.append(row)
row=len(data)

autoscore=[]
autoscore1=0
a=[]
for i in range(row):
    if i%6 != 5:
        autoscore1 = autoscore1 + float(data[i][5])
    else :
        autoscore1 = autoscore1 + float(data[i][6])
        a=[data[i][0], int(autoscore1*10)]
        print(data[i][0], int(autoscore1*10))
        autoscore.append(a)
        autoscore1=0

with open('q2Out.csv', 'w',newline='') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['studentID','Rawmark'])
    for i in range(68):
        writer.writerow((autoscore[i]))
