package com.ssdut.imkg.pojo.pub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页公共返回对象
 * @Author Fan Yuanxin
 * @Date 2021/3/24 17:01
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    private Long total;
    private List<?> data;
}
