package com.alibaba.publisher.mapper;

import com.alibaba.publisher.bean.Bigdata_message;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>Description: 添加描述</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: TY</p>
 *
 * @author kylin
 * @version 1.0
 * @date 2021/6/23 14:46
 */
public interface BaseMapper {

    // Mapper 是接口，@Mapper为了把mapper这个DAO交给Spring管理

    //指定id 获取数据
    List<Bigdata_message> getRow(String id);


    //方式2
    @Select("select * from hbase_observer_fail_data where id = #{id} ")
    List<Bigdata_message> getRowBySql(String id);


    //高级用法
    @Select("select * from hbase_observer_fail_data where name like CONCAT('%',#{like},'%')")
    List<Bigdata_message>  selectUserByName (String like);

}
