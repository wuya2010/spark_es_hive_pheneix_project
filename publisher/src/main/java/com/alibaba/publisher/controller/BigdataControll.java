package com.alibaba.publisher.controller;

import com.alibaba.publisher.bean.Bigdata_message;
import com.alibaba.publisher.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>Description: 添加描述</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: TY</p>
 *
 * @author kylin
 * @version 1.0
 * @date 2021/6/23 15:15
 */

//获取 mapper 端查询的数据
@Controller
public class BigdataControll {

    //@Controller 是返回页面，需要调用UserMapper，所以用@AutoWired

    @Autowired
    BaseMapper baseMapper;


    /*
        报错： The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.

        可能是 mysql 服务没有开启

     */
    @RequestMapping("/get_bigdata")
    public String getBigdata(Model resultModel){
        List<Bigdata_message> rowBySql = baseMapper.getRow("1");
        resultModel.addAttribute(rowBySql);
        System.out.println(rowBySql);
        return "bigdata";  //controller 返回页面， WEB_INF/jsp/bigdata.jsp
    }

}
