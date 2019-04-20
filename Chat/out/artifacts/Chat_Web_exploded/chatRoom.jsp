<%--
  Created by IntelliJ IDEA.
  User: meng
  Date: 2018/5/23
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String username=request.getParameter("username");
String stuID=request.getParameter("stuID");
String name= (String) request.getSession().getAttribute("usernmae");%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=username%></title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

    <script type="text/javascript">

        var username="<%=username%>";
      /* var ws = new WebSocket("ws:menghong.natapp1.cc/webSocket/"+username);
        //var ws = new WebSocket("ws:localhost:80/webSocket/"+username);
        /!*
         *监听三种状态的变化 。js会回调
         *!/
        ws.onopen = function(message) {

        };
        ws.onclose = function(message) {

        };
        ws.onmessage = function(message) {

                showMessage(message.data);

        };
        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function() {
            ws.close();
        };
        //关闭连接
        function closeWebSocket() {
            ws.close();
        }
        //发送消息
        function send() {
            var input = document.getElementById("msg");
            var text = input.value;
            ws.send(text);
            input.value = "";
        }
*/

        //



        // 考试安排查询
        function examFunction(studentNum){
            $.ajax({
                type:'post',
                //url:'http://hong.s1.natapp.cc/Chat-1.0-SNAPSHOT/addFriend',
                url:'http://localhost:8080/api-0.0.1-SNAPSHOT/examSchedule',
                data:{"stuNum": studentNum},
                cache:false,
                dataType:'json',
                success:function(data){
                    for (var i=0;i<data.data.length;i++) {

                        $("#examInfoDiv").append("<br>"+data.data[i].course+"<br>"+data.data[i].date+
                                                "<br>"+data.data[i].time+"<br>"+data.data[i].classroom+"<br>"+data.data[i].seat);
                    }
                }
            });
        }

     /*   // 补考安排查询
        function reexamFunction( stuNum){
            $.ajax({
                type:'post',
                //url:'http://hong.s1.natapp.cc/Chat-1.0-SNAPSHOT/addFriend',
                url:'http://localhost:8080/api-0.0.1-SNAPSHOT/examReexam',
                data:{"stuNum": stuNum},
                cache:false,
                dataType:'json',
                success:function(data){
                    for (var i=0;i<data.data.length;i++) {

                        $("#reexamInfoDiv").append("<br>"+data.data[i].course+"<br>"+data.data[i].date+
                        "<br>"+data.data[i].time+"<br>"+data.data[i].classroom+"<br>"+data.data[i].seat);
                    }
                }
            });
        }*/
    </script>
</head>
<body>
<b>发送信息时 最后加->username 发送私人信息
    如：你好 ->孟宏</b>
<br>
<b>直接发送信息，就是发给所有在这个聊天室的人</b>
<br>
<br>
<b>
    好友列表点击一次就会看到你的好友
</b>
<br>
<b>
    添加好友，输入他的username，然后他会收到你的好友申请
</b>
<br>
<br>

<div id="user">
    <h2>我的username：<%=username%></h2>
</div>
<div
        style="width: 600px; height: 240px; overflow-y: auto; border: 1px solid #333;"
        id="show">
    <div id="showChatMessage"></div>
    <input type="text" size="80" id="msg" name="msg" placeholder="please input" />
    <input type="button" value="send" id="sendBn" name="sendBn"
           onclick="send()">
</div>

<div id="friendList">
<button id="getFriendList" onclick="friendList(username)">好友列表</button>
</div>

<div id="addFriend">
    <input type="text" id="addFriendName">
    <button id="addFriendButton" onclick="addFriend(addFriendName.value)">添加</button>
</div>

<a href="robotChat.jsp">与图灵机器人聊天</a>

<%--
<button id="searchCourseInfo"  onclick="courseInfoFunction(<%=stuID%>,false)">课表查询</button>

<br>课表查询
<div id="CourseInfoDiv">
    学号：<input type="text" id="stu_num">
    是否更新缓存：<input type="text" id="courseForceFetch">
    <button id="couserInfoButton" onclick="courseInfoFunction(stu_num.value,courseForceFetch.value)">查找</button>
</div>
<div id="courseInfoDiv">
</div>--%>

<%--<br>空教室查询
<div id="empty">
    星期：<input type="text" id="weekdayNum">
    第几节课：<input type="text" id="sectionNum">
    教学楼号：<input type="text" id="buildNum">
    第几周：<input type="text" id="week">
    是否更新缓存<input type="text" id="ForceFetch">
    <button id="emptyRommButton" onclick="emptyRoomFunction(weekdayNum.value,sectionNum.value,
                                                    buildNum.value,week.value,ForceFetch.value)">查找</button>
</div>
<div id="emptyRoomDiv">
</div>--%>


<br>考试查询
<div>
    学号：<input type="text" id="stuNum">

    <button id="examInfoButton" onclick="examFunction(stuNum.value)">查找</button>

</div>
<div id="examInfoDiv">
</div>

<%--<br>补考查询
<div id="reexam">
    学号：<input type="text" id="studentNum">
    是否更新缓存：<input type="text" id="reexamForceFetch">
    <button id="reexamInfoButtom" onclick="reexamFunction(studentNum.value)">查找</button>

</div>
<div id="reexamInfoDiv">
</div>--%>




</body>
</html>