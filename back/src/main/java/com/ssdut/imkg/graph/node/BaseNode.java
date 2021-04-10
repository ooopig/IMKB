//package com.ssdut.imkg.graph.node;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import org.neo4j.ogm.annotation.*;
//import org.neo4j.ogm.annotation.Properties;
//
//import java.util.*;
//
///**
// * @Author Fan Yuanxin
// * @Date 2021/4/6 16:23
// * @Version 1.0
// * 基本节点
// */
//@Data
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@NodeEntity
//public class BaseNode {
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @Labels
//    private List<String> labels;
//
//    @Properties
//    private Map<String, Object> properties;
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
//}
