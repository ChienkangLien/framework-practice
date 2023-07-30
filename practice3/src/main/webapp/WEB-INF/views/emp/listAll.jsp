<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
    <title>全部員工資料</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/table.css">
</head>
<body>
    <h3>所有員工資料</h3>
    <c:if test="${not empty errorMsgs}">
        <font color='red'>請修正以下錯誤:
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li>${message}</li>
                </c:forEach>
            </ul>
        </font>
    </c:if>
    <table>
        <tr>
            <th>員工編號</th>
            <th>員工姓名</th>
            <th>職位</th>
            <th>雇用日期</th>
            <th>薪水</th>
            <th>獎金</th>
            <th>部門</th>
            <th>修改</th>
            <th>刪除</th>
        </tr>
        <c:forEach var="empVO" items="${empVOs}">
            <tr align='center' valign='middle'>
                <td>${empVO.empno}</td>
                <td>${empVO.ename}</td>
                <td>${empVO.job}</td>
                <td>${empVO.hiredate}</td>
                <td>${empVO.sal}</td>
                <td>${empVO.comm}</td>
                <td>${empVO.deptVO.deptno}
                    <c:forEach var="deptVO" items="${deptVOs}">
                        <c:if test="${empVO.deptVO.deptno==deptVO.deptno}">
                            【${deptVO.dname} - ${deptVO.loc}】
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/emp/getOne_For_Update">
                        <input type="submit" value="修改">
                        <input type="hidden" name="empno" value="${empVO.empno}">
                    </form>
                </td>
                <td>
                    <form method="POST" action="${pageContext.request.contextPath}/emp/delete">
                        <input type="submit" value="刪除">
                        <input type="hidden" name="empno" value="${empVO.empno}">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <a href="${pageContext.request.contextPath}/">回首頁</a>
</body>
</html>
