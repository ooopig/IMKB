//package com.ssdut.imkg.graph.repositories.relation;
//
//import com.ssdut.imkg.graph.node.PersonNode;
//import com.ssdut.imkg.graph.relation.LoveRelation;
//import org.springframework.data.neo4j.annotation.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.data.repository.query.Param;
//
///**
// * @Author Fan Yuanxin
// * @Date 2021/4/6 9:52
// * @Version 1.0
// */
//public interface LoveRelationRepository extends Neo4jRepository<LoveRelation,Long> {
//
//    @Query(value = "MATCH (a:PersonNode),(b:PersonNode) WHERE id(a) = :#{#from.id} and id(b) = :#{#to.id} CREATE (a)-[love:LOVE]->(b) RETURN love")
//    LoveRelation createRelationWithExistNode(@Param("from")PersonNode from, @Param("to")PersonNode to);
//
//
//}
