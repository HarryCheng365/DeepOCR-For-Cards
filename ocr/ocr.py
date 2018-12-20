from aip import AipOcr


def OCR(str):
    APP_ID = '10379743'
    API_KEY = 'QGGvDG2yYiVFvujo6rlX4SvD'
    SECRET_KEY = 'PcEAUvFO0z0TyiCdhwrbG97iVBdyb3Pk'

    aipOcr = AipOcr(APP_ID, API_KEY, SECRET_KEY)

    filePath=str;
    info_list = []
    name = ''
    department = ''
    id = ''
    number = ''

    def get_file_content(filePath):

        with open(filePath, 'rb') as fp:
            return fp.read()

    options = {

        'detect_direction': 'true',

        'language_type': 'CHN_ENG',

    }

    #result = aipOcr.basicGeneral(get_file_content(filePath), options)
    result=aipOcr.basicGeneral(str,options);

    for words in result["words_result"]:
        #print(words.get('words'))
        tmp = words.get('words')
        if (':' in tmp):
            s = tmp.split(':', 1)[1]
            info_list.append(s)
            if ((len(s) == 13) & (s.isdigit()) & (s[:2] == '20')):
                id = s
            elif ((len(s) == 6) & (s.isdigit())):
                number = s
            elif ((s.find('学院') > -1) | (s.find('系') > -1) | (s.find('学堂') > -1)):
                department = s
            elif (len(s) > 1):
                name = s
        elif ((len(tmp) == 13) & (tmp.isdigit()) & (tmp[:2] == '20')):
            id = tmp
        elif ((tmp.find('学院') > -1) | (tmp.find('系') > -1) | (tmp.find('学堂') > -1)):
            department = tmp

    #print("=======")

    student = {
        'name': name, 'department': department, 'id': id, 'number': number
    }
    #print(student)
    return student

# stu=OCR("/Users/haibaradu/Desktop/cdemo2.png")
# print(stu)
# stu=OCR('/Users/haibaradu/Desktop/test.png')
# print(stu)
