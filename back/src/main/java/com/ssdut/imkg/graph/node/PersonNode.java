//package com.ssdut.imkg.graph.node;
//
//import com.alibaba.fastjson.JSONObject;
//import com.oracle.webservices.internal.api.message.PropertySet;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import org.neo4j.ogm.annotation.*;
//import org.neo4j.ogm.annotation.Properties;
//
//
//import java.util.*;
//
///**
// * @Author Fan Yuanxin
// * @Date 2021/4/5 11:18
// * @Version 1.0
// */
//@Data
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class PersonNode {
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @Labels
//    private List<String> labels;
//
//    @Relationship(type = "love",direction = Relationship.OUTGOING)
//    private Set<PersonNode> loves = new HashSet<>();
//
//    @Properties
//    private Map<String, Object> properties;
//
//
////    @Property
////    private String nodeProperties;
//
//
//    /**
//     * 添加属性
//     * @param key
//     * @param value
//     */
//    public void addProperty(String key,Object value){
//        if(properties == null){
//            properties = new HashMap<>();
//        }
//        properties.put(key,value);
//    }
//
//    /**
//     * 获得单个属性
//     * @param key
//     * @return
//     */
//    public Object getProperty(String key){
//        return properties.get(key);
//    }
//    /**
//     * 移除属性
//     * @param key
//     */
//    public void removeProperty(String key){
//        properties.remove(key);
//    }
//
//    public String propertiesToJsonString(){
//        JSONObject jsonObject= new JSONObject();
//        for (String key : properties.keySet()){
//            jsonObject.put(key,jsonObject.get(key));
//        }
//        return jsonObject.toJSONString();
//    }
//}
