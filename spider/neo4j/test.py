from py2neo import Graph,Relationship,Node
import json


if __name__ == '__main__':
    graph = Graph('http://localhost:7474',username='neo4j',password='779428136')
    # item = {'title': '家具制造业',
    #       'summary': ['指用木材、金属、塑料、竹、藤等材料制作的，具有坐卧、凭倚、储藏、间隔等功能，可用于住宅、旅馆、办公室、学校、餐馆、医院、剧场、公园、船舰、飞机、机动车等任何场所的各种家具的制造。'],
    #      'basic_info': {'中文名': '家具制造业', '材料': '用木材、金属、塑料、竹、藤', '功能': '坐卧、凭倚、储藏、间隔', '用于': '住宅、旅馆、办公室、学校、餐馆'}
    #       }
    # node = Node('Node', title=item.get('title', None),
    #             pic=item.get('pic', None),
    #             summary=item.get('summary', None),
    #             basic_info=str(item.get('basic_info', None)),
    #             specific_info=str(item.get('specific_info', None))
    #             )
    #
    # graph.create(node)

    print(graph.nodes[1])




