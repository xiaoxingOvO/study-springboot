<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>update Emp</title>
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
                    <a href="#">更新员工</a>
                </h1>
            </div>
            <div id="navigation">
            </div>
        </div>
        <div id="content">
            <p id="whereami">
            </p>
            <h1>
                更新员工信息:
            </h1>
            <form action="${pageContext.request.contextPath}/empl/update" method="post">
                <table cellpadding="0" cellspacing="0" border="0"
                       class="form_table">
                    <tr>
                        <td valign="middle" align="right">
                            id:
                        </td>
                        <td valign="middle" align="left">
                            <input value="${empl.id}" name="id" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            姓名:
                        </td>
                        <td valign="middle" align="left">
                            <input type="text" class="inputgri" name="name" value="${empl.name}"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            工资:
                        </td>
                        <td valign="middle" align="left">
                            <input type="text" class="inputgri" name="salary" value="${empl.salary}"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            生日:
                        </td>
                        <td valign="middle" align="left">
                            <input type="date" class="inputgri" name="birthday"
                                   value="<fmt:formatDate value="${empl.birthday}" pattern="yyyy-MM-dd"/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            性别:
                        </td>
                        <td valign="middle" align="left">
                            <select name="gender">

                                <option value="1"
                                        <c:if test="${empl.gender}">
                                            selected
                                        </c:if>
                                >男
                                </option>

                                <option value="0"
                                        <c:if test="${!empl.gender}">
                                            selected
                                        </c:if>
                                >女
                                </option>

                            </select>
                        </td>
                    </tr>
                </table>
                <p>
                    <input type="submit" class="button" value="提交"/>
                </p>
            </form>
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
