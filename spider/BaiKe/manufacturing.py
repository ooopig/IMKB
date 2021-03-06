from bs4 import BeautifulSoup
import lxml.etree as etree
import requests

# 获取页面内容
def get_content(url):
    base_url = 'https://baike.baidu.com/item/'
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36'
    }
    para={

    }
    resp = requests.get(url=url,headers = headers)
    if resp.status_code == '200':
        resp.encoding = resp.apparent_encoding
        # 以文本的形式返回获取到的网页内容
        return resp.text
    return ''
# 解析页面内容,获得抓取信息
def page_parse(page_content):
    print('响应数据的长度为:',len(page_content))
    return ''


if __name__ == '__main__':
    target = '制造业'
    url = "https://baike.baidu.com/item/" + target
    page_content = get_content(url)
