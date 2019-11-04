import json
with open("expression5.json","r") as f:
    d = json.load(f)
    d = d['root']


    def cal(d):
        if 'plus' in d.keys() :
            num = len(d['plus'])
            for i in range(0, num):
                a = d['plus'][i]
                if 'plus' in a.keys() or 'minus' in a.keys() or 'times' in a.keys() :
                    cal(d['plus'][i])
            else:
                num = len(d['plus'])
                a = 0
                for i in range(0, num):
                    a = a + d['plus'][i]['int']
                d.pop('plus')
                d['int'] = a
            cal(d)
        elif 'minus' in d.keys() :
            num = len(d['minus'])
            for i in range(0, num):
                a = d['minus'][i]
                if 'plus' in a.keys() or 'minus' in a.keys() or 'times' in a.keys():
                    cal(d['minus'][i])
            else:
                b = d['minus'][0]['int']-d['minus'][1]['int']
                d.pop('minus')
                d['int'] = b
            cal(d)
        elif 'times' in d.keys() :
            num = len(d['times'])
            for i in range(0, num):
                a = d['times'][i]
                if 'plus' in a.keys() or 'minus' in a.keys() or 'times' in a.keys():
                    cal(d['times'][i])
            else:
                b = d['times'][0]['int'] * d['times'][1]['int']
                d.pop('times')
                d['int'] = b
            cal(d)
        return (d['int'])

answer = cal(d)
print(answer)
