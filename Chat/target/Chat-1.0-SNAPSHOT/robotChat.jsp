<%--
  Created by IntelliJ IDEA.
  User: asuspc
  Date: 2018/2/13
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
    </script>
    <script>
    	$(document).ready(function(){
            <%-- js ajax 异步调用接口，向图灵机器人发信息，然后用显示在 chatview div中 --%>
 $("button").click(function(){
            <%--$.post("http://hong.s1.natapp.cc/Chat-1.0-SNAPSHOT/chat", --%>

           $.post("http://menghong.natapp1.cc/chat",
                    {message:message.value},
                    function(data,status){
                       /* window.location.href='http://www.baidu.com';*/
                    	 $("#message").val("");
                       /* alert("数据: \n" + data.text + "\n状态: " + status);*/
                        $("#chatView").append("<p align=\"left\">"+data.message+"</p>");
                        $("#chatView").append("<p align=\"right\">"+data.text+"</p>");
                    },"json");
        });
    	});
       /* $("button").click(function(){
           $.post("http://localhost/chat",
                    {message:message.value},
                    function(data,status){
                        alert("数据: \n" + data + "\n状态: " + status);
                    },"json");
        });*/
    </script>
</head>
<body>
<div id="title">
    <p align="center">与机器人聊天</p>
</div>
<div id="chatView" style="border: 20px">

</div>
<div id="input">

    <input type="text" id="message" align="center">
    <button id="btn1">发送</button>

</div>
</body>
</html>