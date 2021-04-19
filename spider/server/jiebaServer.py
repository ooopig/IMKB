import flask
from flask import Flask
from flask import jsonify
from flask import request
from flask import json
import time
import _json

import jieba
import jieba.posseg as pseg #词性标注
import jieba.analyse as anls #关键词提取

app = flask.Flask(__name__)

@app.route('/key',methods=['post'])
def test():
    data = request.get_json(silent=True)  # 获取  post 参数
    test_secstense = data['data']
    # 基于textrank提取关键词
    print("基于textrank提取关键词结果：")
    result = jieba.analyse.textrank(sentence=test_secstense, withWeight=True, allowPOS=('ns', 'n', 'vn'), topK=10)

    re = []
    k = 1
    for r in result:
        a = str(r[0])
        b = str(r[1])
        re.append({
            "lable":k,
            "value":b,
            "key":a,
        })
        k = k+1

    resp = json.dumps(re, ensure_ascii=False)
    print(resp)
    return resp

@app.route('/cut',methods=['post'])
def cutword():

    data = request.get_json(silent=True)  # 获取  post 参数
    print(data)
    seg_list = jieba.cut(data['data'])
    param = ' '.join(seg_list)

    return param

if __name__ == '__main__':
    app.run(debug=True)

