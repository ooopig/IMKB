//package com.ssdut.imkg.graph.repositories.node;
//
//import com.ssdut.imkg.graph.node.ImNode;
//import com.ssdut.imkg.graph.node.PersonNode;
//import org.springframework.data.neo4j.annotation.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.data.repository.query.Param;
//
//import java.util.Optional;
//
///**
// * @Author Fan Yuanxin
// * @Date 2021/4/5 11:24
// * @Version 1.0
// */
//public interface PersionNodeRepository extends Neo4jRepository<PersonNode,Long> {
//
////    @Query(value = "create (n:Person)")
////    PersonNode addPersonNode(String prop);
//
//    @Query(value = "MATCH(n:Person)WHERE n.`properties.姓名`= {name} RETURN n")
//    PersonNode getWithName(String name);
//
//    @Query(value = "MATCH(n) where id(n) = #{id} set " +
//            "n.properties=:#{#node.properties} " +
//            "return n")
//    PersonNode updatePersonNode(@Param("id")Long id,@Param("prop")String Prop );
//
//
//}
