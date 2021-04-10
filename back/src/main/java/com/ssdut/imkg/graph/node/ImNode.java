package com.ssdut.imkg.graph.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.security.web.PortResolverImpl;

import java.util.List;

/**
 * @Author Fan Yuanxin
 * @Date 2021/4/5 9:50
 * @Version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class ImNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String title;

    @Property
    private String pic;

    @Property
    private Integer group;

    @Property
    private List<String>summary;

    @Property
    private List<String> files;

    @Property
    private String basic_info;

    @Property
    private String specific_info;

}
