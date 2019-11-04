import sys
import json
from jsonschema import validate


def validate_address(address_file_path):
    """This function validates the input JSON ﬁle against your
       schema and returns
            "yes"
       if your addresses contain a large household (as per M1),
            "no"
       otherwise, and that uses the JSON Schema validator
       documented at
           https://python-jsonschema.readthedocs.io/en/stable/validate/
       to validate the input against your schema. In case this validation
       fails, your program should return
            "invalid".

       You can use "pip install jsonschema" to install jsonschema."""
    with open("address.json.schema", "r") as a:
        l = json.load(a)
    with open(address_file_path, "r") as m:
        n = json.load(m)
    try:
        validate(n, l)  # 触发异常
    except:
        return"invalid"
    else:
        flag = 0
        for i in range(0, len(n["person"]) - 1):
            c = 0
            a = n["person"][i]["postal"]
            for j in range(0, len(n["person"])):
                b = n["person"][j]["postal"]
                if a == b:
                    c = c + 1
                    if c > 3:
                        flag = 1
        if flag == 1:
            return"yes"
        else:
            return"no"



if __name__=='__main__':

    address_file_path = sys.argv[1]
    print(validate_address(address_file_path))