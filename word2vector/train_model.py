import config
import utils
from gensim.models import word2vec

def train(sentense,model_output_path):
    print('开始训练')
    model = word2vec.Word2Vec(sentences=sentense,min_count=config.min_count)

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
if __name__ == '__main__':
    stop_words = utils.get_stop_words(config.stop_word_dir)
    sentences = utils.preprocessing_text('data/train_data.csv', 'data/cut_train_data.csv', 'data/stop_words.txt')

    # 训练过程
    train(sentences, config.model_output_path)
    # 查看训练结果
    model = word2vec.Word2Vec.load(config.model_output_path)
    vocab = list(model.wv.vocab.keys())
    y = model.most_similar(positive=['谷物','加工'],topn=5)
    print(y)
    # print(vocab)
    # print(len(vocab))