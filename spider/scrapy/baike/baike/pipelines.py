# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter


class BaikePipeline:
    # 处理item类型的对象
    # 持久化存储

    # 只在开启爬虫的时候调用一次
    def open_spider(self,spider):
        print('open_spider')

    def process_item(self, item, spider):
        title = item['title']
        pic = item['pic']
        with open(title+'.txt','w',encoding='utf-8')as f:
            f.write('title '+title+'\n')
            f.write('pic '+pic+'\n')
        f.close()
        return item

    # 只在爬虫关闭的时候调用一次
    def close_spider(self,spider):
        print('close_spider')

