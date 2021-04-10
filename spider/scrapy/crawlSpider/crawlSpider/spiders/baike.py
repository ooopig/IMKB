import scrapy
from scrapy.linkextractors import LinkExtractor
from scrapy.spiders import CrawlSpider, Rule


class BaikeSpider(CrawlSpider):
    name = 'baike'
    flag = True
    # allowed_domains = ['baike.com']
    start_urls = [
        # 'https://baike.baidu.com/item/零件',
        # 'https://baike.baidu.com/item/精密轴',
        # 'https://baike.baidu.com/item/热处理',
        # 'https://baike.baidu.com/item/冲压',
        # 'https://baike.baidu.com/item/锻造',
        # 'https://baike.baidu.com/item/凸轮',
        # 'https://baike.baidu.com/item/滚子',
        # 'https://baike.baidu.com/item/棘轮',
        # 'https://baike.baidu.com/item/轴承',
        # 'https://baike.baidu.com/item/带轮',
        # 'https://baike.baidu.com/item/链条',
        # 'https://baike.baidu.com/item/滚子链',
        # 'https://baike.baidu.com/item/减速器',
        # 'https://baike.baidu.com/item/涡杆',
        # 'https://baike.baidu.com/item/涡轮',
        # 'https://baike.baidu.com/item/滚刀',
        # 'https://baike.baidu.com/item/传动轴',
    ]

    # 链接提取器
    link = LinkExtractor(allow=r'/item/*',restrict_xpaths='//div[@class="main-content"]//a')

    rules = (
        Rule(link, callback='parse_item', follow=True),
    )

    def parse_item(self, response):
        item = {}

        tree = response
        # 获取title
        # print('=================title================')
        title = tree.xpath('//h1/text()')[0].extract()
        # print(title)
        item['title'] = title

        # 获取概述图pic
        # print('=================pic================')
        # <a class="more-link" href="/pic/%E5%88%B6%E9%80%A0%E4%B8%9A/523699?fr=lemma" target="_blank" nslog-type="10000204">
        try:
            pic = tree.xpath('//div[@class="summary-pic"]/a/img/@src').extract()[0]
            # print(pic)
            item['pic'] = pic
        except IndexError:
            pass
        # 获取summary
        # print('=================summary================')
        summary = tree.xpath('//div[@class="lemma-summary"]/div[@class="para"]')
        summarys = []
        for ite in summary:
            # print(etree.tostring(item,encoding='utf8', method='html').decode())
            # print(ite.xpath('string(.)').extract()[0])
            summarys.append(ite.xpath('string(.)').extract()[0])
        item['summary'] = summarys

        # 获取基本信息basic-info
        # print('=================basic-info================')
        #
        names = []
        values = []
        basic_info = {}
        basic_name = tree.xpath('//div[@class="basic-info cmn-clearfix"]/dl/dt')
        for bname in basic_name:
            name = bname.xpath('string(.)').extract()[0]
            name = name.replace(' ','')
            names.append(name)
        basic_value = tree.xpath('//div[@class="basic-info cmn-clearfix"]/dl/dd')
        for bvalue in basic_value:
            value = bvalue.xpath('string(.)').extract()[0].strip()
            values.append(value)
        for i in range(len(names)):
            #print(names[i], ': ', values[i])
            basic_info[names[i]] = values[i]
        item['basic_info'] = basic_info

        # 获取level2 level3
        #print('=================level2 level3================')
        #divs = tree.xpath('//div[@class="anchor-list "]/following-sibling::*')
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
        return item

