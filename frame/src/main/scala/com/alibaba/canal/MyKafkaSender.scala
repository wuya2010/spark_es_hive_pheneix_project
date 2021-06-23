package com.alibaba.canal

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * @author kylinWang
  * @data 2020/7/10 7:25
  *       实现 Kafka 生产者
  */
object MyKafkaSender {

  //发送数据数据到kafka
  val props = new Properties()
  // Kafka服务端的主机名和端口号
  props.put("bootstrap.servers", "hadoop105:9092,hadoop106:9092,hadoop107:9092")
  // key序列化
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  // value序列化
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  //新建 kafkaProduce
  val producer = new KafkaProducer[String, String](props)

  //将 cannel 中数据发送到 kafka 中
  def sendToKafka(topic: String, content: String) = {
    producer.send(new ProducerRecord[String, String](topic, content))
  }
}
