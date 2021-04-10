# 各种设置
encoding = "utf-8"  # 文件编码设置，utf-8为中文
stop_word_dir = "data/stop_words.txt"  # 停止词的文件路径
train_set_dir = "data/train_data.csv"  # 原始训练集路径
train_after_process_text_dir = "data/cut_train_data.csv"  # 处理后训练集路径
test_set_dir = "data/test.csv"  # 原始测试集路径
test_after_process_text_dir = "data/test_after_process.csv"  # 处理后测试集路径
model_output_path = "model/word2vec_news.model"  # 训练好的word2vec模型的路径；win系统下/和\没有本质区别

train_num = 485686
test_num = 50

result_out_path = "data/sim_result.csv"
sim_result_path = "data/result.txt"

# word2vec参数
train_size = 100
train_window = 2
min_count = 2
