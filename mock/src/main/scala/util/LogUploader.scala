package scala.util

import java.net.{HttpURLConnection, URL}

/**
  * @author kylinWang
  * @data 2020/7/6 7:23
  *
  */
object LogUploader {

  def sendLog(log: String) = {

    try {

      //本地测试, 启动 tomcat ==》 D:\Program Files\apache-tomcat-7.0.50\apache-tomcat-7.0.50\bin

      //本地tomcat访问地址： http://localhost:8080/0508_Day01/index.html 可以获取这个地址信息
      val logUrl = new URL("http://localhost:8080/log") //http://localhost:8080/log 无法连接

      //fixme: 发送数据到指定机器端口
      //      val logUrl = new URL("http://hadoop105:8080/log") //日志地址

      //将 URLConnection 转化为 HttpConnection
      val conn = logUrl.openConnection().asInstanceOf[HttpURLConnection]

      conn.setRequestMethod("POST") //请求方式
      conn.setRequestProperty("clientTime", System.currentTimeMillis + "")
      conn.setDoOutput(true) //是否上传

      //设置请求的头信息, post 请求必须这样设置,
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

      //获取上传输出流
      val out = conn.getOutputStream
      //流数据写出
      out.write(("log=" + log).getBytes())
      //关闭
      out.flush()
      out.close()

      //获取响应
      val code = conn.getResponseCode
      println(code)

    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

}
