package com.ssdut.imkg.graph.repositories.node;

import com.ssdut.imkg.graph.node.ImNode;
import org.neo4j.driver.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author Fan Yuanxin
 * @Date 2021/4/5 11:12
 * @Version 1.0
 */
@Repository
public interface ImNodeRepository extends Neo4jRepository<ImNode,Long> {
    ImNode findByTitle(@Param("title")String title);

    @Query(value = "MATCH(n) where id(n) = :#{#node.id} set " +
            "n.title=:#{#node.title}," +
            "n.pic=:#{#node.pic}," +
            "n.group=:#{#node.group}, " +
            "n.summary = :#{#node.summary}, " +
            "n.basic_info=:#{#node.basic_info}," +
            "n.specific_info=:#{#node.specific_info}," +
            "n.files=:#{#node.files} " +
            "return n")
    ImNode updateNode(@Param("node")ImNode node);

//    @Query(value = "MATCH(n) where id(n) = {id} " +
//            "set n.group={group}," +
//            "n.title = {title}, " +
//            "n.pic = {pic}, " +
//            "n.summary = {summary}, " +
//            "n.files = {files}, " +
//            "n.basic_info = {basic_info}, " +
//            "n.specific_info = {specific_info} " +
//            "return n")
//    ImNode updateNode(Long id,
//                      String title,
//                      String pic,
//                      Integer group,
//                      List<String>summary,
//                      List<String>files,
//                      String basic_info,
//                      String specific_info);


}
