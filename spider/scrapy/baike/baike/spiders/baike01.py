import scrapy
from baike.items import BaikeItem

class Baike01Spider(scrapy.Spider):
    name = 'baike01'
    # allowed_domains = ['https://baike.baidu.com/']
    start_urls = ['https://baike.baidu.com/item/制造业']

    def parse(self, response):
        item = BaikeItem()

        tree = response
        # 获取title
        print('=================title================')
        title = tree.xpath('//h1/text()')[0].extract()
        print(title)
        item['title'] = title

        # 获取概述图pic
        print('=================pic================')
        # <a class="more-link" href="/pic/%E5%88%B6%E9%80%A0%E4%B8%9A/523699?fr=lemma" target="_blank" nslog-type="10000204">
        pic = tree.xpath('//div[@class="summary-pic"]/a/img/@src').extract()[0]
        print(pic)
        item['pic'] = pic

        # 获取summary
        print('=================summary================')
        summary = tree.xpath('//div[@class="lemma-summary"]/div[@class="para"]')
        for ite in summary:
            # print(etree.tostring(item,encoding='utf8', method='html').decode())
            print(ite.xpath('string(.)').extract()[0])

        # 获取基本信息basic-info
        print('=================basic-info================')

        names = []
        values = []
        basic_name = tree.xpath('//div[@class="basic-info cmn-clearfix"]/dl/dt')
        for bname in basic_name:
            name = bname.xpath('string(.)').extract()[0]
            names.append(name)
        basic_value = tree.xpath('//div[@class="basic-info cmn-clearfix"]/dl/dd')
        for bvalue in basic_value:
            value = bvalue.xpath('string(.)').extract()[0].strip()
            values.append(value)
        for i in range(len(names)):
            print(names[i],': ',values[i])

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
            n = url.xpath('string(.)').extract()
            u = url.xpath('@href').extract()
            if (u == []):
                {}
            elif (u[0].find('item') > 0):
                print(n[0], '   ', u[0])

        # 将item提交给管道
        yield item