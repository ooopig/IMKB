//package com.ssdut.imkg.graph.relation;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ssdut.imkg.graph.node.PersonNode;
//import com.sun.javafx.beans.IDProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import org.neo4j.ogm.annotation.*;
//
//import java.lang.annotation.Retention;
//import java.util.Map;
//
///**
// * @Author Fan Yuanxin
// * @Date 2021/4/5 20:17
// * @Version 1.0
// */
//@Data
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@RelationshipEntity(type = "love")
//public class LoveRelation {
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @StartNode
//    private PersonNode from;
//
//    @EndNode
//    private PersonNode to;
//
//    public LoveRelation(PersonNode from, PersonNode to) {
//        this.from = from;
//        this.to = to;
//    }
//
//}
