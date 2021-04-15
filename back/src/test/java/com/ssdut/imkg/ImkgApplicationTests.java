package com.ssdut.imkg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssdut.imkg.mapper.NodeMapper;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class ImkgApplicationTests {
    @Autowired
    private NodeService nodeService;

    @Autowired
    private NodeMapper nodeMapper;



    @Test
    void contextLoads() {
        Node node = nodeService.getNodeByNodeName("合金");
        System.out.println(node);
    }

    @Test
    void test01(){
        System.out.println("aaaaa:"+nodeMapper.getNodeModifyUser(12));
    }

    @Test
    void test02(){

        List<Node> nodes = nodeMapper.selectList(new QueryWrapper<Node>()
                .eq("create_user", 1001)
                .eq("status", 0));

        for (Node node: nodes) {
            try{
                String prop = node.getProperties();
                //System.out.println(prop);

                JSONObject jsonObject = JSONObject.parseObject(prop);
                //System.out.println(jsonObject);
                String summary = (String)jsonObject.get("summary");
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(summary);
                summary = m.replaceAll("");
                summary = summary.substring(1,summary.length()-2);

                String specific_info = (String)jsonObject.get("specific_info");

                String basic_info = (String)jsonObject.get("basic_info");
                Matcher m1 = p.matcher(basic_info);
                basic_info = m1.replaceAll("");
                JSONObject jsonObject1 = new JSONObject();
                if(basic_info.length()>5){
                    basic_info = basic_info.substring(1,basic_info.length()-2);
                    String[] strings = basic_info.split(",");

                    for (String s:strings) {
                        String[] split = s.split(":");
                        String k = split[0].replaceAll("\'","");
                        String v = split[1].replaceAll("\'","");
                        jsonObject1.put(k,v);
                    }
                }
                String pic = (String)jsonObject.get("pic");

//        System.out.println("summary："+ summary);
//        System.out.println("specific_info："+specific_info);
//        System.out.println("basic_info："+basic_info);
//        System.out.println("pic："+pic);

                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("summary",summary);
                jsonObject2.put("basic_info",jsonObject1);
                jsonObject2.put("specific_info","");
                jsonObject2.put("pic",pic);
                jsonObject2.put("best_practices",new ArrayList<>());

                //System.out.println(jsonObject2);

                node.setProperties(jsonObject2.toJSONString());
                node.setStatus(1);
                nodeService.updateById(node);

            }catch (Exception e){
                System.out.println(node.getId());
            }

        }

    }

    @Test
    void test03(){
        Node node = nodeMapper.selectOne(new QueryWrapper<Node>()
                .eq("id", 170));
        String prop = node.getProperties();
        //System.out.println(prop);

        JSONObject jsonObject = JSONObject.parseObject(prop);
        System.out.println(jsonObject);
        String summary = (String)jsonObject.get("summary");
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(summary);
        summary = m.replaceAll("");
        summary = summary.substring(1,summary.length()-2);

        String specific_info = (String)jsonObject.get("specific_info");

        String basic_info = (String)jsonObject.get("basic_info");
        Matcher m1 = p.matcher(basic_info);
        basic_info = m1.replaceAll("");
        JSONObject jsonObject1 = new JSONObject();
        if(basic_info.length()>5){
            basic_info = basic_info.substring(1,basic_info.length()-2);
            String[] strings = basic_info.split(",");

            for (String s:strings) {
                String[] split = s.split(":");
                for (String ss:split) {
                    System.out.println(ss);
                }
                String k = split[0].replaceAll("\'","");
                String v = split[1].replaceAll("\'","");
                jsonObject1.put(k,v);
            }
        }
    }
}
