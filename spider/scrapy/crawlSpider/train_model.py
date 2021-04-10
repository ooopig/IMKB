import config
import utils
from gensim.models import word2vec
from gensim.models.word2vec import LineSentence

def train(sentense,model_output_path):
    print('开始训练')
    model = word2vec.Word2Vec(sentences=sentense,min_count=0,alpha=0.01)

    model.save(model_output_path)
    print('训练完成')

def get_sentences():
    sentences = []
    with open('data/cut_train_data.csv','w')as f:
        for line in f:
            line_list = line.split(',')
            if(line_list>1):
                del line_list[0]
                sentences.append(line_list)
    return sentences


def sentense_sim(text):
    model = word2vec.Word2Vec.load('E:\java\Imkg01\spider\scrapy\crawlSpider\model\word2vec_news.model')
    vocab = list(model.wv.vocab)
    #print(vocab)
    candidates = []
    stop_words = utils.get_stop_words('E:\java\Imkg01\spider\scrapy\crawlSpider\data\stop_words.txt')
    with open('E:\java\Imkg01\spider\scrapy\crawlSpider\data\cut_train_data.csv')as f:
        for line in f:
            lines = line.strip().split(' ')
            candidates.append(lines)  # 将语料放到列表中便于操作
    #print(candidates)
    words = list(utils.jieba_cut(text.strip(),stop_words))  # 分词
    #print("分词为：",words)
    flag = False
    word = []
    for w in words:
        if w not in vocab:
            # print('给定词',w,'不在词典中')
            return []
        else:
            word.append(w)
    # 文本匹配
    res = []
    index = 0
    for candidate in candidates:
        #print("candidate", candidate)
        flag = True
        for c in candidate:
            if c not in vocab:
                # print('候选词',c,'不在词典中')
                flag = False
        if flag:
            # 计算两组单词之间的相似度
            try:
                score = model.n_similarity(word, candidate)
                resultInfo = {'id': index, "score": score, "text": " ".join(candidate)}
                res.append(resultInfo)
                index += 1
            except ZeroDivisionError:pass
    res.sort(key=lambda x: x['score'], reverse=True)  # 进行排序

    # k = 0
    result = []  # 存放最终结果
    for i in range(len(res)):
        if res[i]['score'] > 0.70:  # 认为文本相似
            dict_temp = {res[i]['id']: res[i]['text'], "score": res[i]['score']}
            result.append(dict_temp)
    return result

def get_candate():
    candates = []
    with open('data/cut_train_data.csv','r')as f:
        for line in f:
            candate = []
            line = line.split(' ')
            for it in line:
                candate.append(it.strip())
            candates.append(candate)
    return candates
if __name__ == '__main__':
    #stop_words = utils.get_stop_words(config.stop_word_dir)
    # sentences = utils.preprocessing_text('data/train_data.csv', 'data/cut_train_data.csv', 'data/stop_words.txt')
    # print(sentences)
    # 训练过程
    # sentences = get_candate()
    #print(sentences)
    # print(sentences)
    # train(sentences, config.model_output_path)
    # 查看训练结果
    #model = word2vec.Word2Vec.load(config.model_output_path)
    #vocab = list(model.wv.vocab)
    # try:
    #     y = model.most_similar(positive=['制造业'], topn=5)
    #     print(y)
    # except KeyError:
    #     print('词典中没有改词')
    #
    #
    #print(vocab)
    # print(len(vocab))

    print(sentense_sim('减速器'))