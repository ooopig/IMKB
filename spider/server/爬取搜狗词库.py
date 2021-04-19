import os


dir_list_1 = os.listdir('C:\\Users\\13029\\Desktop\\毕业设计\\资料\\词库')

dict = []
new_dict = []
for item in dir_list_1:
    dir_list_2 = os.listdir('C:\\Users\\13029\\Desktop\\毕业设计\\资料\\词库\\'+str(item))
    l = 0
    for item2 in dir_list_2:
        with open('C:\\Users\\13029\\Desktop\\毕业设计\\资料\\词库\\'+str(item)+'\\'+str(item2),'r',encoding='utf-8')as f:
            temp = f.readlines()
            l=l+len(temp)
    dict.append({item,l})


for i in dict:
    print(i)


{'农业', 47055}
{293, '刺绣织染'}
{120, '包装'}
{32052, '化学'}
{'化工', 7221}
{4112, '医疗器械'}
{1008, '印刷印染'}
{'家用电器', 598}
{2956, '工业设计'}
{'建筑', 41540}
{'机械工程', 9988}
{16339, '林业'}
{'汽车', 3860}
{8842, '汽车工程'}
{'渔业', 11242}
{'环境能源', 6900}
{77361, '电力电气'}
{20809, '电子工程'}
{'畜牧业', 4013}
{2692, '纺织服装'}
{'计算机', 222}
{1225, '造纸'}
{17, '金属工艺'}
{'钢铁冶金', 227219}

