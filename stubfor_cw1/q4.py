# Q4 is a script that returns, for each question, the question ID and the number# students  who have passed this question (assuming that a student passes a 
#   question if they score at least half the points possible). 
#   
# You should create and write your results into a .csv file q4Out.csv
# with 2 columns and the header row questionID, StudentsPassed. 
#

import csv
with open("exam_for_2019.csv") as csvfile:
    a = csv.reader(csvfile)
    headers = next(a)
    data = []
    for row in a:
        data.append(row)
StudentsPassed=[]
StudentsPassed1=StudentsPassed2=StudentsPassed3=StudentsPassed4=StudentsPassed5=StudentsPassed6=0
for i in range(68):
    if float(data[i*6][5])>= 0.5:
        StudentsPassed1 = StudentsPassed1+1
    if float(data[i*6+1][5])>= 0.5:
        StudentsPassed2 = StudentsPassed2+1
    if float(data[i*6+2][5])>= 0.5:
        StudentsPassed3 = StudentsPassed3+1
    if float(data[i*6+3][5])>= 0.5:
        StudentsPassed4 = StudentsPassed4+1
    if float(data[i*6+4][5])>= 0.5:
        StudentsPassed5 = StudentsPassed5+1
    if float(data[i*6+5][6])>= 2.5:
        StudentsPassed6 = StudentsPassed6+1

print(StudentsPassed1,StudentsPassed2,StudentsPassed3,StudentsPassed4,StudentsPassed5,StudentsPassed6)
a=('Question ID 1',StudentsPassed1)
StudentsPassed.append(a)
a=('Question ID 2',StudentsPassed2)
StudentsPassed.append(a)
a=('Question ID 3',StudentsPassed3)
StudentsPassed.append(a)
a=('Question ID 4',StudentsPassed4)
StudentsPassed.append(a)
a=('Question ID 5',StudentsPassed5)
StudentsPassed.append(a)
a=('Question ID 6',StudentsPassed6)
StudentsPassed.append(a)
with open('q4Out.csv', 'w',newline='') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['questionID','StudentsPassed'])
    for i in range(6):
        writer.writerow((StudentsPassed[i]))
