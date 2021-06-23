package com.alibaba.spring_web.controller

import com.alibaba.fastjson.{JSON, JSONObject}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation._



/**
  * @author kylinWang
  * @data 2020/7/6 7:53
  *
  */

@RestController
class LoggerController {

  @RequestMapping(Array("/hello"))
  def hello(): String = {
     "hello"
  }


  //http://localhost:8080/log
  //@RestController
  //  @PostMapping("/log") //@RequestParam：将请求参数绑定到你控制器的方法参数上

  @RequestMapping(Array("/log"))
  def doLog(@RequestParam("log") log: String) = {

    //添加时间戳
    val logWithTS: String = addTS(log)

    print(logWithTS)


    //fixme: 保存到2个路径下
    //保存到log
    saveLog2File(logWithTS)
    //保存到kafka
    //    send2Kafka(logWithTS)

  }





  /** 方法体不能放在dolog
   * obj 添加时间字段， 给日志添加时间戳
   */
  def addTS(log: String) = {
    val jsonObj: JSONObject = JSON.parseObject(log)
    jsonObj.put("ts", System.currentTimeMillis())
    jsonObj.toJSONString
  }

  //传递到kafka, 根据不同的日志类型，传递数据到不同的topic
  @Autowired
  var templete: KafkaTemplate[String, String] = _

  def send2Kafka(logWithTS: String) = {
    var topic: String = "spark_startup" // 写入数据的 kafka_topic
    //获取日志内容的日志类型
    if (JSON.parseObject(logWithTS).getString("logType") == "event") {
      topic = "spark_event" //contant.ConstantUtil.EVENT_TOPIC
    }
    templete.send(topic, logWithTS)
  }


  //日志写入本地
  private val logger = LoggerFactory.getLogger(this.getClass) //获取class

  def saveLog2File(logWithTS: String) = {
    logger.info(logWithTS)
  }


  /*
  java -cp ./gmall0508-logger-0.0.1-SNAPSHOT.jar:/opt/module/scala-2.11.8/lib/scala-library.jar org.springframework.boot.loade
  r.JarLauncher

  java -Djava.ext.dirs=/opt/module/scala-2.11.8/lib -jar gmall0508-logger-0.0.1-SNAPSHOT.jar

  java -Djava.ext.dirs=/opt/module/scala-2.11.8/lib -cp ./gmall0508-logger-0.0.1-SNAPSHOT.jar org.springframework.boot.loader.
  JarLauncher

   */


}
