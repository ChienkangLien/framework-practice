<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
    <title>員工資料新增</title>
</head>
<body>
    <h3>員工資料</h3>
    <c:if test="${not empty errorMsgs}">
        <font color='red'>請修正以下錯誤:
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li>${message}</li>
                </c:forEach>
            </ul>
        </font>
    </c:if>
    <form method="POST" action="${pageContext.request.contextPath}/emp/insert" name="form1">
        <table>
            <tr>
                <td>員工姓名:</td>
                <td>
                    <input type="TEXT" name="ename" size="45" value="${(empVO==null)? "王小明" : empVO.ename}" />
                </td>
            </tr>
            <tr>
                <td>職位:</td>
                <td>
                    <input type="TEXT" name="job" size="45" value="${(empVO==null)? "manager" : empVO.job}" />
                </td>
            </tr>
            <tr>
                <jsp:useBean id="now" class="java.util.Date" />
                <fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
                <td>雇用日期:</td>
                <td>
                    <input type="date" name="hiredate" value="${(empVO==null)? currentDate : empVO.hiredate}" />
                </td>
            </tr>
            <tr>
                <td>薪水:</td>
                <td>
                    <input type="TEXT" name="sal" size="45" value="${(empVO==null)? "10000" : empVO.sal}" />
                </td>
            </tr>
            <tr>
                <td>獎金:</td>
                <td>
                    <input type="TEXT" name="comm" size="45" value="${(empVO==null)? "100" : empVO.comm}" />
                </td>
            </tr>
            <tr>
                <td>部門:<font color=red><b>*</b></font></td>
                <td>
                    <select size="1" name="deptVO.deptno">
                        <c:forEach var="deptVO" items="${deptVOs}">
                            <option value="${deptVO.deptno}" ${(empVO.deptVO.deptno==deptVO.deptno)? 'selected':'' } >${deptVO.dname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <br />
        <a href="${pageContext.request.contextPath}/">回首頁</a>
        <input type="submit" value="送出新增">
    </form>
</body>
</html>
