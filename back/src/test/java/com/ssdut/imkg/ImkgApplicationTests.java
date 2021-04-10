package com.ssdut.imkg;

import com.ssdut.imkg.mapper.NodeMapper;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
