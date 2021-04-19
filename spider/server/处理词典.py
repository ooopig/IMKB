with open('new_dict.txt', 'r',encoding='utf-8')as f:
    new_dic = []
    old_dic = f.readlines()
    # print(old_dic)
    print(len(old_dic))
    # for item in old_dic:
    #     if item in new_dic:
    #         pass
    #     else:
    #         item = item.strip()+' '+'3'+' '+'n'+'\n'
    #         new_dic.append(item)
    #         break
    for item in old_dic:
        item = item.strip() + ' ' + '3' + ' ' + 'n' + '\n'
        new_dic.append(item)

    print(len(new_dic))


    with open('new_dict_add_n.txt','w',encoding='utf-8') as ff:
        ff.writelines(new_dic)