import requests
from bs4 import BeautifulSoup
import lxml.etree as etree

if __name__ == "__main__":
    target = '机床'
    basic_url = 'https://baike.baidu.com'
    url = "https://baike.baidu.com/item/"+target
    headers = {
        'User-Agent':'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36'
    }
    resp = requests.get(url=url, headers=headers)
    resp.encoding = resp.apparent_encoding
    #
    tree = etree.HTML(resp.text)
    #print(etree.tostring(tree, encoding='utf8', method='html').decode())
    # 获取main-content
    #main_content = tree.xpath('//div[@class="main-content"]')



    # 获取title
    print('=================title================')
    title = tree.xpath('//h1/text()')[0]
    print(title)



    # 获取概述图pic
    print('=================pic================')
    # <a class="more-link" href="/pic/%E5%88%B6%E9%80%A0%E4%B8%9A/523699?fr=lemma" target="_blank" nslog-type="10000204">
    pic = tree.xpath('//div[@class="summary-pic"]/a/img/@src')
    print(pic)

    # 获取summary
    print('=================summary================')
    summary = tree.xpath('//div[@class="lemma-summary"]/div[@class="para"]')
    for item in summary:
        # print(etree.tostring(item,encoding='utf8', method='html').decode())
        print(item.xpath('string(.)'))



    # 获取基本信息basic-info
    print('=================basic-info================')


    # names = []
    # values = []
    # basic_name = tree.xpath('//div[@class="basic-info cmn-clearfix"]/dl/dt')
    # for bname in basic_name:
    #     name = bname.xpath('string(.)')
    #     print(name)
    #     names.append(name)
    # basic_value = tree.xpath('//div[@class="basic-info cmn-clearfix"]/dl/dd')
    # for bvalue in basic_value:
    #     value = bvalue.xpath('string(.)')
    #     print(value)
    #     values.append(value)



    # 获取level2 level3
    print('=================level2 level3================')
    divs = tree.xpath('//div[@class="anchor-list "]/following-sibling::*')
    # for div in divs:
    #     #print(div.xpath('@class'))
    #     if(div.xpath('@class')==[]):{}
    #     elif(div.xpath('@class')[0]=='para-title level-2'):
    #         print('222')
    #         print(div.xpath('h2/text()')[0])
    #     elif (div.xpath('@class')[0] == 'para-title level-3'):
    #         print('333')
    #         print(div.xpath('h3/text()')[0])
    #     elif (div.xpath('@class')[0] == 'para'):
    #         print(div.xpath('string(.)'))


    # 获取所有url
    print('=================url================')
    urls = tree.xpath('//div[@class="main-content"]//a')
    for url in urls:
        n = url.xpath('string(.)')
        u = url.xpath('@href')
        if(u == []):{}
        elif(u[0].find('item')>0):
            print(n,'   ',u[0])



    # soup = BeautifulSoup(resp.text,'lxml')
    # main_content = soup.find("div",class_="main-content")
    # #获得当前页面的title
    # title = soup.h1.string
    # #获取summary
    # summary = soup.find("div",class_='lemma-summary').get_text()
    # #获取概述图片
    # pic = 'https://baike.baidu.com/pic/'+target+'/' +soup.find('a',class_='more-link')['href'].split('?')[0].split('/')[-1]+'/1'
    #
    # #获取当前页面main-content下的所有a标签
    # a = soup.select('.content > .main-content a' )
    # #title = soup.select("#lemmaWgt-lemmaTitle-title")
    # print(pic)

