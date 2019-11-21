<%@ page import="com.wzbfq.util.DButil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2019/11/19
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>list</title>
    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body background="/img/0.jpg">
<div class="container" >
    <center><h4><a href="index.jsp">返回数据库操作页面</a></h4> </center>
    <center> <h3>数据库表users信息:</h3> </center>
    <form id="form2">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th style="text-align: center">username</th>
                <th style="text-align: center">password</th>
            </tr>
            <c:forEach var="user" items="${usersList}" >
                <tr>
                    <td align="center">${user.username}</td>
                    <td align="center">${user.password}</td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
<br>
<div class="container">
    <center> <h3>数据库表person信息:</h3> </center>
    <form id="form">
        <table border="1" class="table table-bordered table-hover" >
            <tr class="success">
                <th style="text-align: center">username</th>
                <th style="text-align: center">name</th>
                <th style="text-align: center">age</th>
                <th style="text-align: center">telenum</th>
            </tr>

            <c:forEach var="person" items="${personList}" >
                <tr>
                    <td align="center">${person.username}</td>
                    <td align="center">${person.name}</td>
                    <td align="center">${person.age}</td>
                    <td align="center">${person.teleno}</td>
                </tr>
            </c:forEach>


        </table>
    </form>
</div>
<%
    DButil dButil = (DButil)getServletConfig().getServletContext().getAttribute("DB");
    dButil.close();
%>
</body>
</html>
