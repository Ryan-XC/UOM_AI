# Q3 is a script that returns, for each student, his or her ID, the number of points 
#   scored on the autograded questions, the number of points scored on the manually graded 
#   questions, and total number of points scored. 
#   
# You should create and write your results into a .csv file q3Out.csv
# with 4 columns and the header row  studentID, RawAutogradedMark, RawManualMark, RawTotalMark.
#

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
manualmark=0
totalmark=0
a=[]
for i in range(row):
    if i%6 != 5:
        autoscore1 = autoscore1 + float(data[i][5])
    else :
        manualmark = manualmark + float(data[i][6])
        totalmark = autoscore1 + manualmark
        autoscore1 = format(autoscore1, '.2f')
        manualmark = format(manualmark, '.2f')
        totalmark = format(totalmark, '.2f')
        print(data[i][0], autoscore1,manualmark,totalmark)
        a=[data[i][0], autoscore1,manualmark,totalmark]
        autoscore.append(a)
        autoscore1=0
        manualmark = 0
        totalmark = 0

with open('q3Out.csv', 'w',newline='') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['studentID','RawAutogradedMark','RawManualMark','RawTotalMark'])
    for i in range(68):
        writer.writerow((autoscore[i]))

