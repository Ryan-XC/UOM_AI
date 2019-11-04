# Q5 is a script that returns the average (across all students) raw mark for the exam. 
#   
# You should create and write your results into a .csv file q5Out.csv
# with 1 column and the header row AverageMark
#

import csv
with open("exam_for_2019.csv") as csvfile:
    a=csv.reader(csvfile)
    headers=next(a)
    data=[]
    for row in a:
        data.append(row)
row=len(data)
number=row/6
autoscore=[]
autoscore1=0
a=[]
for i in range(row):
    if i%6 != 5:
        autoscore1 = autoscore1 + float(data[i][5])
    else :
        autoscore1 = autoscore1 + float(data[i][6])


AverageMark= autoscore1/number
AverageMark=[format(AverageMark,'.2f')]
print(AverageMark)

with open('q5Out.csv', 'w',newline='') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['Rawmark'])
    writer.writerow(AverageMark)
