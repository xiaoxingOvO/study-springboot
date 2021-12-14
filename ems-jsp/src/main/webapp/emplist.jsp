<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>emplist</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div id="wrap">
    <div id="top_content">
        <div id="header">
            <div id="rightheader">
                <p>
                    <%
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = sdf.format(d);
                    %>
                    <%=date %>
                    <br/>
                </p>
            </div>
            <div id="topheader">
                <h1 id="title">
                    <a href="#">员工列表</a>
                </h1>
            </div>
            <div id="navigation">
            </div>
        </div>
        <div id="content">
            <p id="whereami">
            </p>
            <h1>
                Welcome ${sessionScope.userDB.realname}!
            </h1>
            <table class="table">
                <tr class="table_header">
                    <td>
                        ID
                    </td>
                    <td>
                        Name
                    </td>
                    <td>
                        Salary
                    </td>
                    <td>
                        Birthday
                    </td>
                    <td>
                        Gender
                    </td>
                    <td>
                        Operation
                    </td>
                </tr>
                <c:forEach items="${requestScope.emplList}" var="empl" varStatus="sta">
                    <tr
                            <c:if test="${sta.index%2==0}">
                                class="row1"
                            </c:if>
                            <c:if test="${sta.index%2!=0}">
                                class="row2"
                            </c:if>
                    >
                        <td>${empl.id}</td>
                        <td>${empl.name}</td>
                        <td>${empl.salary}</td>
                        <td><fmt:formatDate value="${empl.birthday}" pattern="yyyy-MM-dd"/></td>
                        <td>${empl.gender?'男':'女'}</td>
                        <td>
                            <a href="javascript:;" onclick="deleteEmployee()">删除</a>
                            <script>
                                function deleteEmployee() {
                                    if (window.confirm('确定要删除这条记录吗?')) {
                                        location.href = '${pageContext.request.contextPath}/empl/delete?id=${empl.id}';
                                    }
                                }
                            </script>
                            <a href="${pageContext.request.contextPath}/empl/detail?id=${empl.id}">更新</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <a href="${pageContext.request.contextPath}/addEmp.jsp">添加员工信息</a>
            </p>
        </div>
    </div>
    <div id="footer">
        <div id="footer_bg">
            ABC@126.com
        </div>
    </div>
</div>
</body>
</html>
