package com.ssdut.imkg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ssdut.imkg.graph.node.ImNode;
//import com.ssdut.imkg.graph.node.PersonNode;
//import com.ssdut.imkg.graph.relation.LoveRelation;
import com.ssdut.imkg.graph.repositories.node.ImNodeRepository;
//import com.ssdut.imkg.graph.repositories.node.PersionNodeRepository;
//import com.ssdut.imkg.graph.repositories.relation.LoveRelationRepository;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.service.NodeService;
import com.ssdut.imkg.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @Author Fan Yuanxin
 * @Date 2021/4/5 11:14
 * @Version 1.0
 */
@SpringBootTest
public class Noe4jTest {
    @Autowired
    private ImNodeRepository imNodeRepository;

    @Autowired
    private NodeService nodeService;

//    @Autowired
//    private PersionNodeRepository persionNodeRepository;
//
//    @Autowired
//    private LoveRelationRepository loveRelationRepository;

//    @Test
//    void test01() {
//
//
//
//
////        ImNode update = imNodeRepository.update();
////        System.out.println(update);
//
////        String jsonNode = JSON.toJSONString(imNode);
////        JSONObject jsonObject= (JSONObject) JSONObject.parse(jsonNode);
////        String basic_info = (String) jsonObject.get("basic_info");
////        JSONObject basic= (JSONObject) JSONObject.parse(basic_info);
////        System.out.println(basic.get("中文名"));
//
////        PersonNode personNode01 = new PersonNode();
////
////        personNode01.addProperty("年龄","18");
////        personNode01.addProperty("性别","男");
////        personNode01.addProperty("姓名","小白");
////        List<String>label = new ArrayList<>();
////        label.add("Person");
////        personNode01.setLabels(label);
////        System.out.println(personNode01);
////        persionNodeRepository.save(personNode01);
////        Optional<PersonNode> node = persionNodeRepository.findById(new Long(113));
////        try{
////            PersonNode personNode = node.get();
////            System.out.println(personNode);
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//
////        PersonNode personNode = persionNodeRepository.getWithName("小白");
////        System.out.println(personNode);
////        Map<String,Object> prop = personNode.getProperties();
////        System.out.println(prop.toString());
////
////        JSONObject jsonObject = new JSONObject();
////        jsonObject.put("id",null);
////        jsonObject.put("class","12");
////        jsonObject.put("name","");
////        // 使用默认序列化方式打印
////        System.out.println(jsonObject.toJSONString());
////        // 使用指定序列化方式
////        System.out.println(jsonObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));
//
//
////        personNode.addProperty("身高","180");
////        PersonNode personNode1 = persionNodeRepository.updatePersonNode(personNode);
////        System.out.println(personNode1);
//
////        Optional<PersonNode> node1 = persionNodeRepository.findById(new Long(523));
////        Optional<PersonNode> node2 = persionNodeRepository.findById(new Long(113));
////
////        try{
////            LoveRelation relationWithExistNode = loveRelationRepository.createRelationWithExistNode(node1.get(), node2.get());
////            System.out.println(relationWithExistNode);
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////
////        System.out.println(node1);
////
//
//
//    }
//
//    @Test
//    void test02(){
//
//        Iterable<ImNode> all = imNodeRepository.findAll();
//        try{
//            for (Iterator iter = all.iterator(); iter.hasNext();) {
//                ImNode node = (ImNode) iter.next();
//
//                System.out.println(node);
//                String title = node.getTitle();
//                System.out.println(title);
//                String pic = node.getPic();
//                List<String> summary = node.getSummary();
//                String basic_info = node.getBasic_info();
//                String specific_info = node.getSpecific_info();
//                String basic_info_new = "";
//                for(int i = 0;i<basic_info.length();i++){
//                    if(basic_info.charAt(i)=='\\'){
//
//                    }else if(basic_info.charAt(i)=='x'){
//
//                    }else if(basic_info.charAt(i)=='a'){
//
//                    }else if(basic_info.charAt(i)=='0'){
//
//                    }else {
//                        basic_info_new+= basic_info.charAt(i);
//                    }
//                }
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("pic",pic);
//                jsonObject.put("summary",summary.toString());
//                jsonObject.put("basic_info",basic_info_new);
//                jsonObject.put("specific_info",specific_info);
//
//                String props = jsonObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
//
//
//                Node node01 = new Node();
//                node01.setLevel(0);
//                String time = TimeUtils.nowStr();
//                node01.setCreateTime(time);
//                node01.setCreateUser(1001);
//                node01.setName(title);
//                node01.setProperties(props);
//
//                nodeService.save(node01);
//
//            }
//        }catch (Exception e){
//            System.out.println(e.fillInStackTrace());
//        }
//    }
//
//    @Test
//    void test03(){
//        ImNode node = imNodeRepository.findByTitle("化学制品");
//        System.out.println(node);
//        String title = node.getTitle();
//        System.out.println(title);
//        String pic = node.getPic();
//        List<String> summary = node.getSummary();
//        String basic_info = node.getBasic_info();
//        String specific_info = node.getSpecific_info();
//        String basic_info_new = "";
//        for(int i = 0;i<basic_info.length();i++){
//            if(basic_info.charAt(i)=='\\'){
//
//            }else if(basic_info.charAt(i)=='x'){
//
//            }else if(basic_info.charAt(i)=='a'){
//
//            }else if(basic_info.charAt(i)=='0'){
//
//            }else {
//                basic_info_new+= basic_info.charAt(i);
//            }
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("pic",pic);
//        jsonObject.put("summary",summary.toString());
//        jsonObject.put("basic_info",basic_info_new);
//        jsonObject.put("specific_info",specific_info);
//
//        String props = jsonObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
//
//
//        Node node01 = new Node();
//        node01.setLevel(0);
//        String time = TimeUtils.nowStr();
//        node01.setCreateTime(time);
//        node01.setCreateUser(1001);
//        node01.setName(title);
//        node01.setProperties(props);
//
//        nodeService.save(node01);
//    }
}
