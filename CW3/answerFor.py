# You can run this with "python3 PyRelaxNgValidator.py <path_to_xml_file> <path_to_RelaxNG_file>" 
# It only works on RelaxNG schemas in XML syntax, so you may have to translate yours first
#

from lxml import etree
from io import StringIO
import sys

filename_xml = sys.argv[1]
filename_rng = sys.argv[2]

# open and read schema file
with open(filename_rng, 'r') as schema_file:
    schema_to_check = schema_file.read()

# open and read xml file
with open(filename_xml, 'r') as xml_file:
    xml_to_check = xml_file.read()


# ...fill in something to parse xml, then report either
# print("The input XML document is well-formed.") or 
# print("Parse error: the input XML file is not well-formed".) 
try:
    xmldoc = etree.parse(filename_xml)
    print('The input XML document is well-formed.')
except:
    print('Parse error: the input XML file is not well-formed.')
    quit()

# ...fill in something to validate xml against given schema, then report either
# print("XML valid wrt given schema.") or
# print("Schema validation error: the input XML file is not valid wrt the given schema".) 
# validate against schema
relaxng_doc=etree.parse(filename_rng)
relaxng = etree.RelaxNG(relaxng_doc)
try:
    relaxng.validate(relaxng_doc)
    print('XML valid wrt given schema.')
except:
    print('Schema validation error: the input XML file is not valid wrt the given schema')
    quit()

# ...fill in something to return the answer for the quiz question,
# i.e., the value  the arithmetic expression in the XML file evaluates to (as a number, no strings around it)
# print(...)
tree = etree.ElementTree(file=filename_xml)
root = tree.getroot()



# 从根节点偏离element树
def list_tree(element, depth,a):
    if element.tag=="plus":
        a.append("+")
    elif element.tag=="minus":
        a.append("-")
    elif element.tag=="times":
        a.append("*")
    elif element.tag=="int":
        a.append(float(element.attrib["value"]))
    children_elements = element.getchildren()
    if children_elements:
        for e_ in children_elements:
            a.append("(")
            list_tree(e_, depth+1,a)
            a.append(")")

a=[]
list_tree(root,0,a)
n=0
for i in range(0,len(a)):
    if a[i]=="(" :
        if(a[i+1]!="+" and a[i+1]!="-" and a[i+1]!="*"):
            del a[i]
            del a[i+1]
            a.append("")
            a.append("")
            n=n+1
for i in range(0,2*n):
    a.pop()

def cal(a):
    if isinstance(a[0],str):
        c = 0
        for i in range(0, len(a)):
            if a[i] == "(":
                c = i
                d = i+1
        b=[]
        while a[d]!=")":
            b.append(a[d])
            d=d+1
        num=len(b)
        if b[0]=="+":
            sum = 0
            for i in range(1,num):
                sum=sum+b[i]
        elif b[0]=="*":
            sum=1
            for i in range(1,num):
                sum=sum*b[i]
        elif b[0]=="-":
            sum=b[1]-b[2]

        a[c]=sum
        for i in range(0,num+1):
            del a[c+1]
        cal(a)
    else:
        a[0]=(format(a[0], '.2f'))
        return (a[0])

cal(a)
print (a[0])





