# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter

import utils
from gensim.models import word2vec
import train_model
from py2neo import Graph,Relationship,Node
import time

class CrawlspiderPipeline:
    begin_time = time.time()
    fp = None
    nodes = []
    # begin_time = time.time()
    add_sentenses = []
    count = 0
    graph = Graph('http://localhost:7474', username='neo4j', password='779428136')
 # 只在开启爬虫的时候调用一次
    def open_spider(self,spider):
        print('open_spider')
        self.fp = open('data.txt','a+')
        with open('data.txt','r')as f:
            for line in f:
                self.nodes.append(line.strip())
    def process_item(self, item, spider):
        # 处理爬取的数据
        # print(self.count)
        self.count = self.count+1
        title = item['title']

        if title in self.nodes:
            return item
        # print(title)
        results = train_model.sentense_sim(title)
        if results != []:
            print(title)
            # 更新训练集
            for lines in item['summary']:
                line = lines.strip().split('。')
                for hh in line:
                    if hh != '':
                        self.add_sentenses.append(hh.strip())
            self.nodes.append(title)
            self.fp.write(title + '\n')
            # 写入neo4j
            node = Node('Node',title = item.get('title',None),
                                 pic = item.get('pic',None),
                                 summary=item.get('summary', None),
                                 basic_info=str(item.get('basic_info', None)),
                                 specific_info = str(item.get('specific_info',None))
                        )
            self.graph.create(node)

        return item
        # print(item)
        # end_time = time.time()
        # span = int(end_time-self.begin_time)
        # print(type(span))
        # print(span)
        # 每隔5分钟计算一次当前实体的数目，并且重新训练模型
        # if(span == 60 | span > 60):
        #     print('当前实体数量：',len(self.nodes))
        #     self.begin_time = end_time
            # 重新训练
            # model = word2vec.Word2Vec.load('E:\java\Imkg01\spider\scrapy\crawlSpider\model\word2vec_news.model')
            # stop_words = utils.get_stop_words('E:\java\Imkg01\spider\scrapy\crawlSpider\data\stop_words.txt')
            # add_sentenses = self.add_sentenses
            # sentenses =[]
            # for line in add_sentenses:
            #     hh = utils.delete_r_n(line)
            #     word_list = utils.jieba_cut(hh, stop_words)
            #     sentenses.append(word_list)
            # model.build_vocab(sentenses = sentenses,update=True)
            # model.train(sentenses,epochs=50)
            # model.save('E:\java\Imkg01\spider\scrapy\crawlSpider\model\word2vec_news.model')



    # 只在爬虫关闭的时候调用一次
    def close_spider(self,spider):
        end_time = time.time()
        span = int(end_time - self.begin_time)
        print('用时：',span,'s')
        print('close_spider')
        # print(self.nodes)
        self.fp.close()
        # print(self.add_sentenses)
        # 写入文件
        with open('new_sentenses.txt','w',encoding='utf-8')as f:
            for line in self.add_sentenses:
                f.write(line+'\n')

    # 判断爬取的节点是否有用
    def sentense_sim(self,text):
        model = word2vec.Word2Vec.load('E:\java\Imkg01\spider\scrapy\crawlSpider\model\word2vec_news.model')
        vocab = list(model.wv.vocab)
        # print(vocab)
        candidates = []
        stop_words = utils.get_stop_words('E:\java\Imkg01\spider\scrapy\crawlSpider\data\stop_words.txt')
        with open('E:\java\Imkg01\spider\scrapy\crawlSpider\data\cut_train_data.csv')as f:
            for line in f:
                line = line.split(' ')
                for word in line:
                    candidates.append(word.strip())  # 将语料放到列表中便于操作
        # print(candidates)
        words = list(utils.jieba_cut(text.strip(), stop_words))  # 分词
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
            # print("candidate", candidate)
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
