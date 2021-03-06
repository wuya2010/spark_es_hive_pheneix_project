package com.alibaba.canal

import java.util

import com.alibaba.fastjson.JSONObject
import com.alibaba.google.common.base.CaseFormat
import com.alibaba.otter.canal.protocol.CanalEntry
import com.alibaba.otter.canal.protocol.CanalEntry.{EventType, RowData}

/**
  * @author kylinWang
  * @data 2020/7/10 7:21
  *       理从 canal 取来的数据
  */
object CanalHandler {

  /**
    * @param tableName   表名
    * @param eventType   事件类型
    * @param rowDataList 数据类别
    */

  def handle(tableName: String, eventType: EventType, rowDataList: util.List[RowData]) = {

    import scala.collection.JavaConversions._

    if ("order_info" == tableName && eventType == EventType.INSERT && rowDataList.size() > 0) {
      val obj: JSONObject = new JSONObject()
      // 1. rowData 表示一行数据, 通过他得到每一列. 首先遍历每一行数据
      for (rowData <- rowDataList) {
        // 2. 得到每行中, 所有列组成的列表
        val columnList: util.List[CanalEntry.Column] = rowData.getAfterColumnsList
        for (column <- columnList) {
          // 3. 得到列名和列值
          // key下划线转成驼峰
          val newColumn: String = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getName)
          obj.put(newColumn, column.getValue)
        }
      }
      // 4. 发送到 Kafka
      MyKafkaSender.sendToKafka("spark_order", obj.toJSONString)
    }
  }
}
