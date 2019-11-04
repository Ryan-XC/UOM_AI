import json
import sys

def evaluate_expr(parsed_expr):
    """This function takes an expression parsed from the JSON
       arithmetic expression format and returns the full evaluation.
       That is, if the JSON expresses '1+1', this function returns '2'."""
    if 'plus' in parsed_expr.keys():
        num = len(parsed_expr['plus'])
        for i in range(0, num):
            a = parsed_expr['plus'][i]
            if 'plus' in a.keys() or 'minus' in a.keys() or 'times' in a.keys():
                evaluate_expr(parsed_expr['plus'][i])
        else:
            num = len(parsed_expr['plus'])
            a = 0
            for i in range(0, num):
                a = a + parsed_expr['plus'][i]['int']
            parsed_expr.pop('plus')
            parsed_expr['int'] = a
        evaluate_expr(parsed_expr)
    elif 'minus' in parsed_expr.keys():
        num = len(parsed_expr['minus'])
        for i in range(0, num):
            a = parsed_expr['minus'][i]
            if 'plus' in a.keys() or 'minus' in a.keys() or 'times' in a.keys():
                evaluate_expr(parsed_expr['minus'][i])
        else:
            b = parsed_expr['minus'][0]['int'] - parsed_expr['minus'][1]['int']
            parsed_expr.pop('minus')
            parsed_expr['int'] = b
        evaluate_expr(parsed_expr)
    elif 'times' in parsed_expr.keys():
        num = len(parsed_expr['times'])
        for i in range(0, num):
            a = parsed_expr['times'][i]
            if 'plus' in a.keys() or 'minus' in a.keys() or 'times' in a.keys():
                evaluate_expr(parsed_expr['times'][i])
        else:
            b = parsed_expr['times'][0]['int'] * parsed_expr['times'][1]['int']
            parsed_expr.pop('times')
            parsed_expr['int'] = b
        evaluate_expr(parsed_expr)
    return (parsed_expr['int'])

def load_json_expr(json_path):
    """This function takes a file path to a JSON file  represened
       as a string and returns a parsed form. You can presume that
       the JSON file is valid wrt the arithmetic expression format."""
    with open(json_path, "r") as f:
        d = json.load(f)
        d = d['root']
        return d
    
# You do not need to touch anything below this line. To complete
# the assignment you need to replace the keyword "pass" in the above
# two functions with code that does the appropriate work.
if __name__=='__main__':
    expr_file_path = sys.argv[1]
    print(evaluate_expr(load_json_expr(expr_file_path)))
