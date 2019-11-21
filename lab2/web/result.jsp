<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.wzbfq.bean.Person" %>
<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2019/11/20
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据库操作结果</title>
</head>
<body background="/img/2.jpg">
    <center><h3>数据库操作结果：</h3></center>
    <c:choose>
        <c:when test="${returnType==0&&operatorResult==0}">
            <h2 align="center">person：${username}&nbsp;插入失败</h2>
        </c:when>
        <c:when test="${returnType==0&&operatorResult==1}">
            <h2 align="center">person：${username}&nbsp;插入成功</h2>
        </c:when>
        <c:when test="${returnType==0&&operatorResult==2}">
            <h2 align="center">person：${username}&nbsp;更新失败</h2>
        </c:when>
        <c:when test="${returnType==0&&operatorResult==3}">
            <h2 align="center">person：${username}&nbsp;更新成功</h2>
        </c:when>
        <c:when test="${returnType==1&&operatorResult==0}">
            <h2 align="center">user：${username}&nbsp;删除失败</h2>
        </c:when>
        <c:when test="${returnType==1&&operatorResult==1}">
            <h2 align="center">user：${username}&nbsp;删除成功</h2>
        </c:when>
        <c:otherwise>
            <h4>qaq</h4>
        </c:otherwise>
    </c:choose>
<%--    <h1>${returnType}</h1>--%>
<%--    <h1>${operatorResult}</h1>--%>
<%--    <h1>${username}</h1>--%>
    <center><h4><a href="<%=request.getContextPath()%>/listServlet">查看数据库数据</a></h4> </center>
</body>
</html>
